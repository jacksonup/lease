package com.hdu.lease.service;

import com.hdu.lease.contract.AssetContract;
import com.hdu.lease.contract.AssetDetailContract;
import com.hdu.lease.contract.PlaceAssetContract;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.request.CreateAssertRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.utils.UuidUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
@Service("assertService")
public class AssertServiceImpl implements AssertService{

    @Setter(onMethod_ = @Autowired)
    private ContractProperties contractProperties;

    @Setter(onMethod_ = @Autowired)
    private ContractMapper contractMapper;

    private AssetContract assertContract;

    private PlaceAssetContract placeAssetContract;

    private AssetDetailContract assetDetailContract;

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
        Contract placeAssetContractEntity = contractMapper.selectById(4);
        Contract assetDetailContractEntity = contractMapper.selectById(5);

        // 加载合约
        assertContract = AssetContract.load(userContractEntity.getContractAddress(), web3j, credentials, provider);
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
        String placeId = "";
        // count:自提点绑定余量; sum:资产总量
        int count = 0, sum = 0;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Integer> map = list.get(i);
            for(String key : map.keySet()) {
                count = map.get(key);
                sum += count;
                placeId = key;
            }
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
        // TODO 1、价值是double类型
        AssetContract.Asset asset = new AssetContract.Asset(
                assetId,
                createAssertRequest.getName(),
                createAssertRequest.getApply(),
                createAssertRequest.getPicUrl(),
                String.valueOf(createAssertRequest.getValue()),
                new BigInteger(String.valueOf(count)),
                new BigInteger("0")
        );

        assertContract.createAsset(asset).send();

        // 生成资产明细
        List<AssetDetailContract.AssetDetail> assetDetailList = new ArrayList<>();
        for (int i = 0; i < sum; i++) {
            AssetDetailContract.AssetDetail assetDetail = new AssetDetailContract.AssetDetail(
                    UuidUtils.createUuid(),
                    "",
                    assetId,
                    new BigInteger("0"),
                    new BigInteger("0")
            );
            assetDetailList.add(assetDetail);
        }

        assetDetailContract.insertAssetDetail(assetDetailList).send();

        return BaseGenericsResponse.successBaseResp("创建成功");
    }
}
