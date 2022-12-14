package com.hdu.lease.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hdu.lease.contract.*;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.dto.*;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.event.ApplyParamEvent;
import com.hdu.lease.pojo.event.BorrowParamEvent;
import com.hdu.lease.pojo.event.CreateParamEvent;
import com.hdu.lease.pojo.event.ShelfOperateParamEvent;
import com.hdu.lease.pojo.request.*;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.base.BaseResponse;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.property.OssProperties;
import com.hdu.lease.utils.JwtUtils;
import com.hdu.lease.utils.QrCodeUtil;
import com.hdu.lease.utils.UuidUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 资产服务实现类
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
@Service("assertService")
@Slf4j
public class AssetServiceImpl implements AssetService {

    @Setter(onMethod_ = @Autowired)
    private ContractProperties contractProperties;

    @Setter(onMethod_ = @Autowired)
    private ContractMapper contractMapper;

    @Setter(onMethod_ = @Autowired)
    private UserService userService;

    @Setter(onMethod_ = @Autowired)
    private OssProperties ossProperties;

    @Setter(onMethod_ = @Autowired)
    private EventService eventService;

    private UserContract userContract;

    private PlaceContract placeContract;

    private AssetContract assertContract;

    private PlaceAssetContract placeAssetContract;

    private AssetDetailContract assetDetailContract;

    private AuditContract auditContract;

    private EventContract eventContract;

    /**
     * 调用智能合约
     */
    @PostConstruct
    private void init() {
        // 监听本地链
        Web3j web3j = Web3j.build(new HttpService(contractProperties.getHttpService()));

        // 生成资格凭证
        Credentials credentials = Credentials.create(contractProperties.getCredentials());

        StaticGasProvider provider = new StaticGasProvider(
                contractProperties.getGasPrice(),
                contractProperties.getGasLimit());

        // 取合约地址
        Contract userContractEntity = contractMapper.selectById(1);
        Contract placeContractEntity = contractMapper.selectById(2);
        Contract assetContractEntity = contractMapper.selectById(3);
        Contract placeAssetContractEntity = contractMapper.selectById(4);
        Contract assetDetailContractEntity = contractMapper.selectById(5);
        Contract auditContractEntity = contractMapper.selectById(6);
        Contract eventContractEntity = contractMapper.selectById(8);

        // 加载合约
        userContract = UserContract.load(userContractEntity.getContractAddress(), web3j, credentials, provider);
        placeContract = PlaceContract.load(placeContractEntity.getContractAddress(), web3j, credentials, provider);
        assertContract = AssetContract.load(assetContractEntity.getContractAddress(), web3j, credentials, provider);
        placeAssetContract = PlaceAssetContract.load(
                placeAssetContractEntity.getContractAddress(),
                web3j,
                credentials,
                provider
        );
        assetDetailContract = AssetDetailContract.load(
                assetDetailContractEntity.getContractAddress(),
                web3j,
                credentials,
                provider
        );
        auditContract = AuditContract.load(
                auditContractEntity.getContractAddress(),
                web3j,
                credentials,
                provider
        );
        eventContract = EventContract.load(
                eventContractEntity.getContractAddress(),
                web3j,
                credentials,
                provider
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<Map<String, String>> create(CreateAssertRequest createAssertRequest) throws Exception {
        // 取出account
        String account = JwtUtils.getTokenInfo(createAssertRequest.getToken()).getClaim("account").asString();

        // 生成资产唯一标识
        String assetId = UuidUtils.createUuid();

        // 校验assetName是否重复
        BigInteger userCount = userContract.count().send();

        List<UserContract.User> userList = userContract.getAllList().send();
        for (UserContract.User user : userList) {
            if (user.getName().equals(createAssertRequest.getName())) {
                return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "物资名重复");
            }
        }

        // 绑定自提点资产,生成List<PlaceAsset>
        List<PlaceAssetContract.PlaceAsset> placeAssetList = new ArrayList<>();
        List<Map<String, Integer>> list = createAssertRequest.getPlaceList();
        List<AssetDetailContract.AssetDetail> assetDetailList = new ArrayList<>();

        String placeId = "";
        // count:自提点绑定余量; sum:资产总量
        int count = 0, sum = 0;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Integer> map = list.get(i);
            for (String key : map.keySet()) {
                count = map.get(key);
                sum += count;
                placeId = key;

                // 生产资产明细
                for (int j = 0; j < count; j++) {
                    String blankStr = "";
                    String assetDetailId = String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());

                    AssetDetailContract.AssetDetail assetDetail = new AssetDetailContract.AssetDetail(
                            assetDetailId,
                            blankStr,
                            assetId,
                            placeId,
                            blankStr,
                            blankStr,
                            new BigInteger("0"),
                            new BigInteger("0")
                    );
                    assetDetailList.add(assetDetail);
                    Thread.sleep(5);
                }
            }

            // 自提点和资产绑定
            PlaceAssetContract.PlaceAsset placeAsset = new PlaceAssetContract.PlaceAsset(
                    UuidUtils.createUuid(),
                    placeId,
                    assetId,
                    new BigInteger(String.valueOf(count)),
                    new BigInteger("0")
            );
            placeAssetList.add(placeAsset);
        }

        placeAssetContract.bindAsset(placeAssetList).send();

        // 创建资产
        // 是否需要借用审核、需要改数据类型、
        AssetContract.Asset asset = new AssetContract.Asset(
                assetId,
                createAssertRequest.getName(),
                createAssertRequest.getApply(),
                createAssertRequest.getPicUrl(),
                new BigInteger(String.valueOf(createAssertRequest.getValue())),
                new BigInteger(String.valueOf(createAssertRequest.getCount())),
                new BigInteger("0")
        );

        assertContract.createAsset(asset).send();
        assetDetailContract.insertAssetDetail(assetDetailList).send();

        HashMap<String, String> map = new HashMap<>(6);

        // 生成二维码
        for (AssetDetailContract.AssetDetail assetDetail : assetDetailList) {
            // 获取placeName
            String placeName = placeContract.getById(assetDetail.getPlaceId()).send().getPlaceName();

            // 创建事件参数
            CreateParamEvent createParamEvent = new CreateParamEvent();
            createParamEvent.setIsBorrow(createAssertRequest.getApply());
            createParamEvent.setAssetName(createAssertRequest.getName());
            createParamEvent.setPlaceName(placeName);
            createParamEvent.setValue(String.valueOf(createAssertRequest.getValue()));

            // 生成事件
            eventService.insert("2",
                    assetDetail.getAssetDetailId(),
                    assetDetail.getPlaceId(),
                    account,
                    createParamEvent.toString());

            // 二维码内容
            String content = assetDetail.getAssetDetailId();

            // 二维码底部文字
            String bottomContent = createAssertRequest.getName() + "-" + content + "-" + placeName;

            BufferedImage image = QrCodeUtil.createImage(content, bottomContent, true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            try {
                ImageIO.write(image, "png", byteArrayOutputStream);

                // 二维码转base64
                String img = "data:image/png;base64," +
                        Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
                map.put(bottomContent, img);
            } catch (final IOException ioe) {
                throw new UncheckedIOException(ioe);
            }
        }

        return BaseGenericsResponse.successBaseResp(map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<AssetDTO>> getList(String token, String placeId) throws Exception {
        // 判断角色
        if (!userService.judgeRoles(token, 1, 2)) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "角色权限不足");
        }

        // 获取资产信息
        List<AssetContract.Asset> assetList = assertContract.getAssetListPlaceId(placeAssetContract.getContractAddress(), placeId).send();
        List<AssetDTO> assetDTOList = new ArrayList<>();

        for (int i = 0; i < assetList.size(); i++) {
            // 获取可用余量
            List<AssetDetailContract.AssetDetail> assetDetailList = assetDetailContract.getStatusListByPlaceId(placeId,
                    assetList.get(i).getAssetId(),
                    new BigInteger("0")).send();
            AssetDTO assetDTO = one(assetList.get(i));
            assetDTO.setRest(assetDetailList.size());

            // 获取绑定的自提点
            List<String> placeNameList = placeAssetContract.getPlaceList(placeContract.getContractAddress(), assetList.get(i).getAssetId()).send();

            assetDTO.setPlaceList(placeNameList);
            assetDTO.setAssetId(assetList.get(i).getAssetId());

            // 获取assetId对应的余量
            assetDTO.setFree(assetDetailContract.getListByStatus(assetList.get(i).getAssetId(), new BigInteger("0")).send().size());
            assetDTOList.add(assetDTO);
        }


        return BaseGenericsResponse.successBaseResp(assetDTOList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> back(String token, String assetId) throws Exception {
        if (!userService.judgeRole(token, 1)) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "用户权限不足");
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> uploadPic(String token, MultipartFile picture, String assetId) throws Exception {
        log.info("上传图片至阿里云oss...");
        // 校验权限
        if (!userService.judgeRole(token, 2)) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "用户权限不足");
        }

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(
                ossProperties.getEndpoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret());

        try {
            //获取上传文件输入流
            InputStream inputStream = picture.getInputStream();

            // 获取文件名称
            log.info("生成文件名称...");
            String fileName = picture.getOriginalFilename();

            // 1、在文件名称里面添加随机唯一值(避免上传文件名称相同的话，后面的问号会将前面的文件给覆盖了)
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            // 2、文件按照日期进行分类：2022/11/28/1.jpg
            // 获取当前日期
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate date = LocalDate.now();
            String datePath = formatter.format(date);

            fileName = datePath + "/" + fileName;
            log.info("文件名为：{}", fileName);

            // 调用oss方法进行上传
            ossClient.putObject(ossProperties.getBucketName(), fileName, inputStream);

            // 关闭ossClient
            ossClient.shutdown();

            // 设置URL过期时间为...
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 10000000);
            String url = ossClient.generatePresignedUrl(ossProperties.getBucketName(), fileName, expiration).toString();

            log.info("上传路径为：{}", url);
            log.info("上传成功");
            return BaseGenericsResponse.successBaseResp(url);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传失败");

        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> apply(AssetApplyRequest assetApplyRequest) throws Exception {
        // 借用者学号
        String borrowerAccount = JwtUtils.getTokenInfo(assetApplyRequest.getToken()).getClaim("account").asString();

        String blankStr = "";

        // 格式化时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedBeginDateTime = LocalDateTime.parse(assetApplyRequest.getFrom(), formatter);
        LocalDateTime parsedEndDateTime = LocalDateTime.parse(assetApplyRequest.getTo(), formatter);

        formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        BigInteger beginTime = new BigInteger(parsedBeginDateTime.format(formatter));
        BigInteger endTime = new BigInteger(parsedEndDateTime.format(formatter));

        String auditId = UuidUtils.createUuid();

        // 格式化时间
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String nowTime = localDateTime.format(dateTimeFormatter);

        // 查找空闲物资随机分配assetDetailId
        List<AssetDetailContract.AssetDetail> assetDetailList = assetDetailContract.getStatusListByPlaceId(
                assetApplyRequest.getPlaceId(),
                assetApplyRequest.getAssetType(),
                new BigInteger("0")).send();

        if (assetDetailList.size() < assetApplyRequest.getCount()) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "余量不足");
        }
        StringBuilder assetDetailIds = new StringBuilder();
        // 修改明细物资状态
        for (AssetDetailContract.AssetDetail assetDetail : assetDetailList) {
            // 生成明细Id
            assetDetailIds.append(assetDetail.getAssetDetailId()).append("#");

            // 更新明细物资状态
            AssetDetailContract.AssetDetail newAssetDetail = new AssetDetailContract.AssetDetail(
                    assetDetail.getAssetDetailId(),
                    assetDetail.getCurrentUserAccount(),
                    assetDetail.getAssetId(),
                    assetDetail.getPlaceId(),
                    assetDetail.getBeginTime(),
                    assetDetail.getEndTime(),
                    new BigInteger("2"),
                    new BigInteger("0")
            );

            assetDetailContract.update(newAssetDetail).send();

            // 事件
            ApplyParamEvent applyParamEvent = new ApplyParamEvent();
            applyParamEvent.setCount(assetApplyRequest.getCount());
            applyParamEvent.setApplyName(userContract.getUserInfo(borrowerAccount).send().getName());
            applyParamEvent.setTime(localDateTime.format(formatter));
            applyParamEvent.setPlaceName(placeContract.getById(assetApplyRequest.getPlaceId()).send().getPlaceName());

            eventService.insert("8",
                    assetDetail.getAssetDetailId(),
                    assetDetail.getPlaceId(),
                    borrowerAccount,
                    applyParamEvent.toString()
            );
        }

        // 创建物资申请表单
        AuditContract.Audit newAudit = new AuditContract.Audit(
                auditId,
                assetApplyRequest.getAssetType(),
                assetApplyRequest.getPlaceId(),
                borrowerAccount,
                assetApplyRequest.getReason(),
                BigInteger.valueOf(assetApplyRequest.getCount()),
                blankStr,
                new BigInteger("1"),
                assetDetailIds.toString(),
                new BigInteger(nowTime),
                new BigInteger("")
        );

        // 添加审批记录
        auditContract.insertAudit(newAudit, beginTime, endTime).send();

        return BaseGenericsResponse.successBaseResp("申请成功，请耐心等待审批");
    }

    /**
     * 立即借用
     *
     * @param assetBorrowRequest
     * @return
     */
    @Override
    public BaseGenericsResponse<String> borrow(AssetBorrowRequest assetBorrowRequest) throws Exception {
        // 借用者学号
        String borrowerAccount = JwtUtils.getTokenInfo(assetBorrowRequest.getToken()).getClaim("account").asString();

        // 修改物资明细状态
        AssetDetailContract.AssetDetail assetDetail =
                assetDetailContract.getByPrimaryKey(assetBorrowRequest.getAssetDetailId()).send();

        // 格式化时间
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String beginTime = localDateTime.format(formatter);

        // 格式化结束时间
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedBeginDateTime = LocalDateTime.parse(assetBorrowRequest.getTo(), formatter);
        formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String endTime = parsedBeginDateTime.format(formatter);

        // 更新明细物资状态
        AssetDetailContract.AssetDetail newAssetDetail = new AssetDetailContract.AssetDetail(
                assetDetail.getAssetDetailId(),
                borrowerAccount,
                assetDetail.getAssetId(),
                assetDetail.getPlaceId(),
                beginTime,
                endTime,
                new BigInteger("1"),
                new BigInteger("0")
        );

        assetDetailContract.update(newAssetDetail).send();

        // 事件
        BorrowParamEvent borrowParamEvent = new BorrowParamEvent();
        borrowParamEvent.setBorrowName(userContract.getUserInfo(borrowerAccount).send().getName());
        borrowParamEvent.setTo(endTime);
        borrowParamEvent.setAssetName(assertContract.getById(assetDetail.getAssetId()).send().getAssetName());

        eventService.insert("7",
                assetDetail.getAssetDetailId(),
                assetDetail.getPlaceId(),
                borrowerAccount,
                borrowParamEvent.toString()
        );

        return BaseGenericsResponse.successBaseResp("借用成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<ScannedAssetDTO> scanned(String token, String assetId) throws Exception {
        log.info("正在扫码获取物资信息...");
        // 判断物资是否被借用
        AssetDetailContract.AssetDetail assetDetail = assetDetailContract.getByPrimaryKey(assetId).send();
        if (ObjectUtils.isEmpty(assetDetail)) {
            log.info("此物资已销毁");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "此物资已销毁");
        }

        // 根据主键获取资产信息
        AssetContract.Asset asset =
                assertContract.getAssetByAssetDetailId(assetDetailContract.getContractAddress(), assetId).send();

        // 1.显示物资图片、名称、价值、当前状态基本信息
        ScannedAssetDTO scannedAssetDTO = new ScannedAssetDTO();
        scannedAssetDTO.setUrl(asset.getPicUrl());
        scannedAssetDTO.setName(asset.getAssetName());
        scannedAssetDTO.setValue(asset.getPrice().intValue());
        scannedAssetDTO.setApply(asset.getIsApply());

        // 2.判断当前状态
        int currentStatus = assetDetail.getCurrentStatus().intValue();
        scannedAssetDTO.setStatus(currentStatus);

        // 校验状态
        if (currentStatus == 1) {
            // 处于借用中
            // 格式化时间
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime parsedBeginDateTime = LocalDateTime.parse(String.valueOf(assetDetail.getEndTime()), formatter);
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = parsedBeginDateTime.format(formatter);

            scannedAssetDTO.setExpiredTime(time);
        }

        if (currentStatus == 1 || currentStatus == 2) {
            String account = JwtUtils.getTokenInfo(token).getClaim("account").asString();

            // 判断是否被自己借用
            if (!assetDetail.getCurrentUserAccount().equals(account)) {
                // 获取user信息
                UserContract.User user = userContract.getUserInfo(account).send();
                scannedAssetDTO.setUsername(user.getName());
                scannedAssetDTO.setPhone(user.getPhone());
            }
        }

        // 判断是否有归属仓库
        if (StringUtils.isNotEmpty(assetDetail.getPlaceId())) {
            PlaceContract.Place place = placeContract.getById(assetDetail.getPlaceId()).send();
            if (ObjectUtils.isNotEmpty(place)) {
                scannedAssetDTO.setPlace(place.getPlaceName());
                // 仓库管理员
                String manageAccount = place.getPlaceManagerAccount();
                // 获取管理员信息
                UserContract.User user = userContract.getUserInfo(manageAccount).send();
                scannedAssetDTO.setPlaceManager(user.getName());
                scannedAssetDTO.setManagerPhone(user.getPhone());
            }
        }

        return BaseGenericsResponse.successBaseResp(scannedAssetDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> edit(EditAssetRequest editAssetRequest) throws Exception {
        // 判断权限
        if (!userService.judgeRoles(editAssetRequest.getToken(), 1, 2)) {
            log.info("权限不足");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
        }

        if (StringUtils.isEmpty(editAssetRequest.getAssetId())) {
            log.info("主键assetId不允许为空");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "主键assetId不允许为空");
        }

        // 判断是否有空闲物资
        List<AssetDetailContract.AssetDetail> assetDetailList = assetDetailContract.getListByStatus(editAssetRequest.getAssetId(), new BigInteger("0")).send();

        if (CollectionUtils.isEmpty(assetDetailList) || assetDetailList.size() == 0) {
            log.info("无空闲物资");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "无空闲物资无法编辑");
        }

        AssetContract.Asset asset = assertContract.getById(editAssetRequest.getAssetId()).send();

        if (ObjectUtils.isEmpty(asset)) {
            log.info("物资不存在");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "物资不存在");
        }

        AssetContract.Asset newAsset = new AssetContract.Asset(
                asset.getAssetId(),
                StringUtils.isEmpty(editAssetRequest.getName()) ? asset.getAssetName() : editAssetRequest.getName(),
                editAssetRequest.getApply(),
                StringUtils.isEmpty(editAssetRequest.getPicUrl()) ? asset.getPicUrl() : editAssetRequest.getPicUrl(),
                editAssetRequest.getValue(),
                asset.getCount(),
                asset.getStatus()
        );

        assertContract.update(newAsset).send();

        return BaseGenericsResponse.successBaseResp("编辑成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<AssetsDTO>> all(String token) throws Exception {
        // 判断权限
        if (!userService.judgeRoles(token, 2)) {
            log.info("权限不足");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
        }

        List<AssetContract.Asset> assetList = assertContract.getAllList().send();
        List<AssetsDTO> assetsDTOList = new ArrayList<>();
        for (AssetContract.Asset asset : assetList) {
            AssetsDTO assetsDTO = new AssetsDTO();
            assetsDTO.setAssetId(asset.getAssetId());
            assetsDTO.setPicUrl(asset.getPicUrl());
            assetsDTO.setName(asset.getAssetName());
            assetsDTO.setValue(asset.getPrice());
            assetsDTO.setApply(asset.getIsApply());

            // 查找仓库名列表
            assetsDTO.setPlaces(placeAssetContract.getPlaceList(placeContract.getContractAddress(), asset.getAssetId()).send());

            // 查找总余量
            List<AssetDetailContract.AssetDetail> assetDetailList = assetDetailContract.getListByStatus(asset.getAssetId(), new BigInteger("0")).send();
            assetsDTO.setRest(
                    CollectionUtils.isEmpty(assetDetailList) ? 0 : assetDetailList.size()
            );
            assetsDTOList.add(assetsDTO);
        }

        return BaseGenericsResponse.successBaseResp(assetsDTOList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<AssetInfoDTO> info(String token, String assetId) throws Exception {
        // 判断权限
        if (!userService.judgeRoles(token, 1, 2)) {
            log.info("权限不足");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
        }

        // 判断主键是否为空
        if (StringUtils.isEmpty(assetId)) {
            log.info("主键assetId不允许为空");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "主键assetId不允许为空");
        }

        AssetContract.Asset asset = assertContract.getById(assetId).send();
        if (ObjectUtils.isEmpty(asset)) {
            log.info("物资不存在");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "物资不存在");
        }

        AssetInfoDTO assetInfoDTO = new AssetInfoDTO();
        assetInfoDTO.setName(asset.getAssetName());
        assetInfoDTO.setValue(asset.getPrice().intValue());
        assetInfoDTO.setPicUrl(asset.getPicUrl());
        assetInfoDTO.setApply(asset.getIsApply());

        return BaseGenericsResponse.successBaseResp(assetInfoDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> updateStatus(UpdateStatusRequest updateStatusRequest) {

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<DetailsDTO> details(DetailsRequest detailsRequest) throws Exception {
        // 判断权限
        if (!userService.judgeRoles(detailsRequest.getToken(), 1, 2)) {
            log.info("权限不足");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
        }

        DetailsDTO detailsDTO = new DetailsDTO();
        List<DetailsDTO.DetailInfo> detailInfoList = new ArrayList<>();

        // 获取总数
        List<AssetDetailContract.AssetDetail> allAssetDetailList = assetDetailContract.getStatusListByPlaceId(detailsRequest.getPlaceId(), detailsRequest.getAssetId(), new BigInteger("-1")).send();

        if (CollectionUtils.isEmpty(allAssetDetailList)) {
            detailsDTO.setCount(0);
        } else {
            detailsDTO.setCount(allAssetDetailList.size());
        }

        if ("-1".equals(detailsRequest.getStatus())) {
            int count = 0;
            for (AssetDetailContract.AssetDetail assetDetail : allAssetDetailList) {
                if (count++ == 10) {
                    break;
                }
                DetailsDTO.DetailInfo detailInfo = new DetailsDTO.DetailInfo();
                detailInfo.setDetailId(assetDetail.getAssetDetailId());
                detailInfo.setStatus(assetDetail.getCurrentStatus().intValue());
                detailInfoList.add(detailInfo);
            }
        } else {
            int begin = detailsRequest.getFromIndex().intValue() * 10;

            // 获取状态列表
            List<AssetDetailContract.AssetDetail> assetDetailList = assetDetailContract.pageGetStatusListByPlaceId(detailsRequest.getPlaceId(),
                    detailsRequest.getAssetId(),
                    new BigInteger(detailsRequest.getStatus()),
                    new BigInteger(String.valueOf(begin)),
                    new BigInteger(String.valueOf(begin + 9))).send();

            for (AssetDetailContract.AssetDetail assetDetail : assetDetailList) {
                DetailsDTO.DetailInfo detailInfo = new DetailsDTO.DetailInfo();
                detailInfo.setDetailId(assetDetail.getAssetDetailId());
                detailInfo.setStatus(assetDetail.getCurrentStatus().intValue());
                detailInfoList.add(detailInfo);
            }
        }

        detailsDTO.setDetailInfoList(detailInfoList);
        return BaseGenericsResponse.successBaseResp(detailsDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<Map<String, String>> supply(SupplyRequest supplyRequest) throws Exception {
        // 判断权限
        if (!userService.judgeRole(supplyRequest.getToken(), 2)) {
            log.info("权限不足");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
        }

        List<AssetDetailContract.AssetDetail> assetDetailList = new ArrayList<>();

        // 生成明细物资
        for (int j = 0; j < supplyRequest.getCount(); j++) {
            String blankStr = "";
            String assetDetailId = String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());;
            AssetDetailContract.AssetDetail assetDetail = new AssetDetailContract.AssetDetail(
                    assetDetailId,
                    blankStr,
                    supplyRequest.getAssetId(),
                    supplyRequest.getPlaceId(),
                    blankStr,
                    blankStr,
                    new BigInteger("0"),
                    new BigInteger("0")
            );
            assetDetailList.add(assetDetail);
            Thread.sleep(5);
        }

        assetDetailContract.insertAssetDetail(assetDetailList).send();

        // 补充资产表中余量
        AssetContract.Asset asset = assertContract.getById(supplyRequest.getAssetId()).send();
        BigInteger assetCount = asset.getCount().add(new BigInteger(String.valueOf(supplyRequest.getCount())));

        // 获取物资名
        String assetName = asset.getAssetName();

        AssetContract.Asset newAsset = new AssetContract.Asset(
                asset.getAssetId(),
                asset.getAssetName(),
                asset.getIsApply(),
                asset.getPicUrl(),
                asset.getPrice(),
                assetCount,
                asset.getStatus()
        );

        assertContract.update(newAsset).send();

        // 补充自提点资产表余量
        PlaceAssetContract.PlaceAsset placeAsset = placeAssetContract.getByAssetIdAndPlaceId(supplyRequest.getAssetId(), supplyRequest.getPlaceId()).send();

        if (placeAsset.placeId.equals("")) {
            PlaceAssetContract.PlaceAsset newPlaceAsset = new PlaceAssetContract.PlaceAsset(
                    UuidUtils.createUuid(),
                    supplyRequest.getPlaceId(),
                    supplyRequest.getAssetId(),
                    new BigInteger(String.valueOf(supplyRequest.getCount())),
                    new BigInteger("0")
            );
            List<PlaceAssetContract.PlaceAsset> placeAssetList = new ArrayList<>();
            placeAssetList.add(newPlaceAsset);
            placeAssetContract.bindAsset(placeAssetList).send();
        } else {
            BigInteger placeAssetCount = placeAsset.getCount().add(new BigInteger(String.valueOf(supplyRequest.getCount())));

            PlaceAssetContract.PlaceAsset newPlaceAsset = new PlaceAssetContract.PlaceAsset(
                    placeAsset.getPlaceAssetId(),
                    supplyRequest.getPlaceId(),
                    supplyRequest.getAssetId(),
                    placeAssetCount,
                    placeAsset.getStatus()
            );
            placeAssetContract.update(newPlaceAsset).send();
        }

        // 获取仓库名
        String placeName = placeContract.getById(supplyRequest.getPlaceId()).send().getPlaceName();

        HashMap<String, String> map = new HashMap<>(6);

        // 生成二维码
        for (AssetDetailContract.AssetDetail assetDetail : assetDetailList) {
            // 二维码内容
            String content = assetDetail.getAssetDetailId();

            // 二维码底部文字
            String bottomContent = assetName + "-" + content + "-" + placeName;

            BufferedImage image = QrCodeUtil.createImage(content, bottomContent, true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            try {
                ImageIO.write(image, "png", byteArrayOutputStream);

                // 二维码转base64
                String img = "data:image/png;base64," +
                        Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
                map.put(bottomContent, img);
            } catch (final IOException ioe) {
                throw new UncheckedIOException(ioe);
            }
        }

        return BaseGenericsResponse.successBaseResp(map);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<CanGroundingDTO>> canGrounding(String token) throws Exception {
        // 判断权限
        if (!userService.judgeRole(token, 2)) {
            log.info("权限不足");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
        }

        List<BigInteger> list = Stream.of(new BigInteger("0"), new BigInteger("5")).collect(Collectors.toList());

        // 获取空闲及下架物资
        List<AssetDetailContract.AssetDetail> assetDetailList = assetDetailContract.getListByStatusAndPlaceId(list, "").send();
        HashMap<String, Integer> map = new HashMap<>(6);

        for (AssetDetailContract.AssetDetail assetDetail : assetDetailList) {
            String assetId = assetDetail.getAssetId();
            // 统计count
            if (map.containsKey(assetDetail.getAssetId())) {
                map.put(assetId, map.get(assetId) + 1);
            } else {
                map.put(assetDetail.getAssetId(), 1);
            }
        }

        List<CanGroundingDTO> canGroundingDTOList = new ArrayList<>(6);

        // 根据assetId获取物资
        for (String key : map.keySet()) {
            CanGroundingDTO canGroundingDTO = new CanGroundingDTO();
            AssetContract.Asset asset = assertContract.getById(key).send();
            if (!ObjectUtils.isEmpty(asset)) {
                canGroundingDTO.setAssetId(asset.getAssetId());
                canGroundingDTO.setName(asset.getAssetName());
                canGroundingDTO.setPicUrl(asset.getPicUrl());
                canGroundingDTO.setCount(assetDetailList.size());
                canGroundingDTOList.add(canGroundingDTO);
            }
        }

        return BaseGenericsResponse.successBaseResp(canGroundingDTOList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> grounding(ShelfOperateRequest shelfOperateRequest) throws Exception {
        // 判断权限
        if (!userService.judgeRole(shelfOperateRequest.getToken(), 2)) {
            log.info("权限不足");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
        }

        // 获取指定物资的空闲状态的明细物资
        List<AssetDetailContract.AssetDetail> assetDetailList = assetDetailContract.getStatusListByPlaceId(
                shelfOperateRequest.getPlaceId(),
                shelfOperateRequest.getAssetId(),
                new BigInteger("0")).send();

        // 统计count
        int count = 0;
        if (CollectionUtils.isEmpty(assetDetailList) && assetDetailList.size() == 0) {
            log.info("空闲状态的明细物资列表为空");
        } else {
            count += assetDetailList.size();

            // 更新placeId
            for (AssetDetailContract.AssetDetail assetDetail : assetDetailList) {
                AssetDetailContract.AssetDetail newAssetDetail = new AssetDetailContract.AssetDetail(
                        assetDetail.getAssetDetailId(),
                        assetDetail.getCurrentUserAccount(),
                        assetDetail.getAssetId(),
                        shelfOperateRequest.getPlaceId(),
                        assetDetail.getBeginTime(),
                        assetDetail.getEndTime(),
                        new BigInteger("0"),
                        assetDetail.getStatus()
                );
                assetDetailContract.update(newAssetDetail).send();
            }
        }

        assetDetailList = assetDetailContract.getStatusListByPlaceId(
                shelfOperateRequest.getPlaceId(),
                shelfOperateRequest.getAssetId(),
                new BigInteger("5")).send();

        if (CollectionUtils.isEmpty(assetDetailList) && assetDetailList.size() == 0) {
            log.info("下架状态的明细物资列表为空");
        } else {
            count += assetDetailList.size();

            // 更新placeId
            for (AssetDetailContract.AssetDetail assetDetail : assetDetailList) {
                AssetDetailContract.AssetDetail newAssetDetail = new AssetDetailContract.AssetDetail(
                        assetDetail.getAssetDetailId(),
                        assetDetail.getCurrentUserAccount(),
                        assetDetail.getAssetId(),
                        shelfOperateRequest.getPlaceId(),
                        assetDetail.getBeginTime(),
                        assetDetail.getEndTime(),
                        new BigInteger("0"),
                        assetDetail.getStatus()
                );
                assetDetailContract.update(newAssetDetail).send();
            }
        }

        // 插入绑定物资信息
        PlaceAssetContract.PlaceAsset placeAsset = new PlaceAssetContract.PlaceAsset(
                UuidUtils.createUuid(),
                shelfOperateRequest.getPlaceId(),
                shelfOperateRequest.getAssetId(),
                new BigInteger(String.valueOf(count)),
                new BigInteger("0")
        );

        List<PlaceAssetContract.PlaceAsset> placeAssetList = Stream.of(placeAsset).collect(Collectors.toList());
        placeAssetContract.bindAsset(placeAssetList).send();

        return BaseGenericsResponse.successBaseResp("上架成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> undercarriage(ShelfOperateRequest shelfOperateRequest) throws Exception {
        // 取出account
        String account = JwtUtils.getTokenInfo(shelfOperateRequest.getToken()).getClaim("account").asString();

        // 判断权限
        if (!userService.judgeRole(shelfOperateRequest.getToken(), 2)) {
            log.info("权限不足");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
        }

        List<AssetDetailContract.AssetDetail> assetDetailList = assetDetailContract.getStatusListByPlaceId(
                shelfOperateRequest.getPlaceId(),
                shelfOperateRequest.getAssetId(),
                new BigInteger("-1")).send();


        // 初始化set
        Set<Integer> set = Stream.of(0, 3, 4).collect(Collectors.toCollection(HashSet::new));
        for (AssetDetailContract.AssetDetail assetDetail : assetDetailList) {
            // 1、校验状态
            if (!set.contains(assetDetail.getCurrentStatus().intValue())) {
                return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "下架失败,存在明细物资不符合下架状态");
            }
        }

        // 清除绑定关系
        String blankStr = "";
        for (AssetDetailContract.AssetDetail assetDetail : assetDetailList) {
            AssetDetailContract.AssetDetail newAssetDetail = new AssetDetailContract.AssetDetail(
                    assetDetail.getAssetDetailId(),
                    blankStr,
                    assetDetail.getAssetId(),
                    blankStr,
                    blankStr,
                    blankStr,
                    new BigInteger("5"),
                    new BigInteger("0")
            );
            assetDetailContract.update(newAssetDetail).send();

            // 下架事件
            ShelfOperateParamEvent shelfOperateParamEvent = new ShelfOperateParamEvent();
            shelfOperateParamEvent.setAssetName(assertContract.getById(assetDetail.getAssetId()).send().getAssetName());
            shelfOperateParamEvent.setType(2);
            shelfOperateParamEvent.setManagerName(userContract.getUserInfo(account).send().getName());
            shelfOperateParamEvent.setPlaceName(placeContract.getById(shelfOperateRequest.getPlaceId()).send().getPlaceName());

            eventService.insert("4",
                    assetDetail.getAssetDetailId(),
                    assetDetail.getPlaceId(),
                    account,
                    shelfOperateParamEvent.toString());
        }

        placeAssetContract.deleteByAssetId(shelfOperateRequest.getAssetId(), shelfOperateRequest.getPlaceId()).send();




        return BaseGenericsResponse.successBaseResp("下架成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<EventDTO>> timeline(String token, String assetDetailId) throws Exception {
        if (!userService.judgeRoles(token, 1, 2)) {
            log.info("权限不足");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
        }

        List<EventContract.Event> eventList = eventContract.getListByDetailId2(assetDetailId).send();

        List<EventDTO> eventDTOS = new ArrayList<>();

        for (EventContract.Event event : eventList) {
            EventDTO newEventDTO = new EventDTO();

            // 格式化时间
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime parsedBeginDateTime = LocalDateTime.parse(String.valueOf(event.getCreateTime()), formatter);
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = parsedBeginDateTime.format(formatter);

            newEventDTO.setTime(time);

            // 获取事件名
            String type = String.valueOf(event.getEventType());
            newEventDTO.setName(eventService.getType(type));

            newEventDTO.setContent(event.getContent());

            // 获取物资最新状态
            AssetDetailContract.AssetDetail assetDetail =
                    assetDetailContract.getByPrimaryKey(event.getAssetDetailId()).send();
            newEventDTO.setStatus(getAssetDetailStatus(String.valueOf(assetDetail.getCurrentStatus())));
            newEventDTO.setPlace(placeContract.getById(event.getPlaceId()).send().getPlaceName());
            newEventDTO.setOperator(userContract.getUserInfo(event.getOperatorAccount()).send().getName());
            eventDTOS.add(newEventDTO);
        }

        return BaseGenericsResponse.successBaseResp(eventDTOS);
    }

    /**
     * Asset -> AssetDTO
     *
     * @return
     */
    public AssetDTO one(AssetContract.Asset asset) {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setApply(asset.getIsApply());
        assetDTO.setPicUrl(asset.getPicUrl());
        assetDTO.setName(asset.getAssetName());
        assetDTO.setValue(asset.getPrice().intValue());
        return assetDTO;
    }

    /**
     * 获取当前最新状态
     *
     * @param currentStatus
     * @return
     */
    public String getAssetDetailStatus(String currentStatus) {
        String status = "";

        if (StringUtils.isEmpty(currentStatus)) {
            return status;
        }

        switch (currentStatus) {
            case "0" : status = "可借用"; break;
            case "1" : status = "使用中"; break;
            case "2" : status = "审核中"; break;
            case "3" : status = "丢失"; break;
            case "4" : status = "损坏"; break;
            case "5" : status = "已下架"; break;
            default: status = "";
        }

        return status;
    }
}
