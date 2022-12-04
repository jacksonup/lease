package com.hdu.lease.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hdu.lease.contract.*;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.dto.*;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.request.*;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.base.BaseResponse;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.property.OssProperties;
import com.hdu.lease.utils.JwtUtils;
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
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    private UserContract userContract;

    private PlaceContract placeContract;

    private AssetContract assertContract;

    private PlaceAssetContract placeAssetContract;

    private AssetDetailContract assetDetailContract;

    private AuditContract auditContract;

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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<Map<String, List<String>>> create(CreateAssertRequest createAssertRequest) throws Exception {
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
        Map<String, List<String>> detailAssetIds = new HashMap<>(6);
        String placeId = "";
        // count:自提点绑定余量; sum:资产总量
        int count = 0, sum = 0;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Integer> map = list.get(i);
            for(String key : map.keySet()) {
                count = map.get(key);
                sum += count;
                placeId = key;

                List<String> assetDetailIdList = new ArrayList<>();
                // 生产资产明细
                // TODO 修改数据结构后，增加相应字段
                for (int j = 0; j < count; j++) {
                    String blankStr = "";
                    String assetDetailId = UuidUtils.createUuid();
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

                    assetDetailIdList.add(assetDetailId);
                }
                // 获取place信息
                PlaceContract.Place place = placeContract.getById(placeId).send();
                detailAssetIds.put(place.getPlaceName(), assetDetailIdList);
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

        // 创建未分配自提点的物资
//        int assetCount = createAssertRequest.getCount();
//        if (assetCount > sum) {
//            for (int i = 0; i < assetCount - sum; i++) {
//
//            }
//        }

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

        return BaseGenericsResponse.successBaseResp(detailAssetIds);
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
            List<String> placeList = placeAssetContract.getPlaceList(placeContract.getContractAddress(), assetList.get(i).getAssetId()).send();
            assetDTO.setPlaceList(placeList);
            assetDTO.setAssetId(assetList.get(i).getAssetId());

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
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
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
        // 创建物资申请表单
        AuditContract.Audit audit = new AuditContract.Audit(
                UuidUtils.createUuid(),
                assetApplyRequest.getAssetType(),
                assetApplyRequest.getPlacId(),
                borrowerAccount,
                assetApplyRequest.getFrom(),
                assetApplyRequest.getTo(),
                assetApplyRequest.getReason(),
                BigInteger.valueOf(assetApplyRequest.getCount()),
                blankStr,
                new BigInteger("1"),
                blankStr,
                blankStr
        );

        // TODO 校验是否超过违规次数

        // 添加审批记录
        auditContract.insertAudit(audit).send();

        return BaseGenericsResponse.successBaseResp("申请成功，请耐心等待审批");
    }

    /**
     * 立即借用
     *
     * @param assetBorrowRequest
     * @return
     */
    @Override
    public BaseGenericsResponse<String> borrow(AssetBorrowRequest assetBorrowRequest) {
        // 修改物资明细状态

        // TODO 加入任务调度队列

        // 添加借用记录

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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS,"此物资已销毁");
        }

        // 根据主键获取资产信息
        AssetContract.Asset asset =
                assertContract.getAssetByAssetDetailId(assetDetailContract.getContractAddress(), assetId).send();
        ScannedAssetDTO scannedAssetDTO = new ScannedAssetDTO();
        scannedAssetDTO.setUrl(asset.getPicUrl());
        scannedAssetDTO.setName(asset.getAssetName());
        scannedAssetDTO.setValue(asset.getPrice().intValue());
        scannedAssetDTO.setApply(asset.getIsApply());
        scannedAssetDTO.setIsBorrow(assetDetail.getCurrentStatus().intValue() == 1);
        scannedAssetDTO.setRest(asset.getCount().intValue());

        // 获取空闲数量
        List<AssetDetailContract.AssetDetail> assetDetailFreeList = assetDetailContract.getListByStatus(assetDetail.getAssetId(), new BigInteger("0")).send();
        if (CollectionUtils.isEmpty(assetDetailFreeList)) {
            scannedAssetDTO.setFree(0);
        } else {
            scannedAssetDTO.setFree(assetDetailFreeList.size());
        }

        // 判断是否处在借用状态
        if (assetDetail.getCurrentStatus().intValue() == 1) {
            // TODO 资产状态明细表增加两个字段【beginTime】】【endTime】【placeId】
            scannedAssetDTO.setExpiredTime(assetDetail.getEndTime());

            String account = JwtUtils.getTokenInfo(token).getClaim("account").asString();

            // 判断是否被自己借用
            if (assetDetail.getCurrentUserAccount().equals(account)) {
                // 获取place信息
                PlaceContract.Place place = placeContract.getById(assetDetail.getPlaceId()).send();
                scannedAssetDTO.setPlace(place.getPlaceName());
            } else {
                // 获取user信息
                UserContract.User user = userContract.getUserInfo(account).send();
                scannedAssetDTO.setUsername(user.getName());
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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS,"权限不足");
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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS,"权限不足");
        }

        List<AssetContract.Asset> assetList = assertContract.getAllList().send();
        List<AssetsDTO> assetsDTOList = new ArrayList<>();
        for(AssetContract.Asset asset : assetList) {
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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS,"权限不足");
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
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void supply(SupplyRequest supplyRequest) throws Exception {}


    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<CanGroundingDTO>> canGrounding(String token) throws Exception {

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> grounding(ShelfOperateRequest shelfOperateRequest) throws Exception {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> undercarriage(ShelfOperateRequest shelfOperateRequest) throws Exception {
        return null;
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
}
