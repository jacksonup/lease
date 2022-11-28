package com.hdu.lease.service;

import com.hdu.lease.contract.*;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.dto.AssetDTO;
import com.hdu.lease.pojo.dto.ScannedAssetDTO;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.request.AssetApplyRequest;
import com.hdu.lease.pojo.request.AssetBorrowRequest;
import com.hdu.lease.pojo.request.CreateAssertRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.base.BaseResponse;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.utils.JwtUtils;
import com.hdu.lease.utils.UuidUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;
import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private UserContract userContract;

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
        Contract assetContractEntity = contractMapper.selectById(3);
        Contract placeAssetContractEntity = contractMapper.selectById(4);
        Contract assetDetailContractEntity = contractMapper.selectById(5);
        Contract auditContractEntity = contractMapper.selectById(6);

        // 加载合约
        userContract = UserContract.load(userContractEntity.getContractAddress(), web3j, credentials, provider);
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
    public BaseGenericsResponse<String> create(CreateAssertRequest createAssertRequest) throws Exception {
        // 生成资产唯一标识
        String assetId = UuidUtils.createUuid();

        // 绑定自提点资产,生成List<PlaceAsset>
        List<PlaceAssetContract.PlaceAsset> placeAssetList = new ArrayList<>();
        List<Map<String, Integer>> list = createAssertRequest.getPlaceList();
        List<AssetDetailContract.AssetDetail> assetDetailList = new ArrayList<>();

        String placeId = "";
        // count:自提点绑定余量; sum:资产总量
        int count = 0, sum = 0;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Integer> map = list.get(i);
            for(String key : map.keySet()) {
                count = map.get(key);
                sum += count;
                placeId = key;

                // 生产资产明细
                // TODO 修改数据结构后，增加相应字段
                for (int j = 0; j < count; j++) {
                    String blankStr = "";
                    AssetDetailContract.AssetDetail assetDetail = new AssetDetailContract.AssetDetail(
                            UuidUtils.createUuid(),
                            blankStr,
                            assetId,
                            placeId,
                            blankStr,
                            blankStr,
                            new BigInteger("0"),
                            new BigInteger("0")
                    );
                    assetDetailList.add(assetDetail);
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
                new BigInteger(String.valueOf(count)),
                new BigInteger("0")
        );

        assertContract.createAsset(asset).send();
        assetDetailContract.insertAssetDetail(assetDetailList).send();

        return BaseGenericsResponse.successBaseResp("创建成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<AssetDTO>> getList(String token, String placeId) throws Exception {
        // 判断角色
        if (!userService.judgeRole(token, 2)) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "角色权限不足");
        }

        // 获取资产信息
        List<AssetContract.Asset> assetList = assertContract.getAssetListPlaceId(placeAssetContract.getContractAddress(), placeId).send();
        List<AssetDTO> assetDTOList = new ArrayList<>();

        for (int i = 0; i < assetList.size(); i++) {
            // 获取可用余量
            List<AssetDetailContract.AssetDetail> assetDetailList = assetDetailContract.getListByStatus(assetList.get(i).getAssetId(), new BigInteger("0")).send();
            AssetDTO assetDTO = one(assetList.get(i));
            assetDTO.setRest(assetDetailList.size());

            // TODO 获取绑定的自提点
            List<String> placeList = placeAssetContract.getPlaceListByAssetId(assetList.get(i).getAssetId()).send();
            assetDTO.setPlaceList(placeList);
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
    public BaseGenericsResponse<String> uploadPic(String token, MultipartFile picture, String assetId) {
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

//            scannedAssetDTO.setExpiredTime();
        }

        return BaseGenericsResponse.successBaseResp(scannedAssetDTO);
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
     * 上传文件
     *
     * @param multipartFile 上传文件
     * @return url
     */
    public String uploadFile(MultipartFile multipartFile, String folder) {
        return null;
    }


}
