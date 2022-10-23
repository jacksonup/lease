package com.hdu.lease.service;

import com.hdu.lease.contract.AssetContract;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.request.CreateAssertRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.property.ContractProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import javax.annotation.PostConstruct;

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
        Contract contract = contractMapper.selectById(1);

        // 加载合约
        assertContract = AssetContract.load(contract.getContractAddress(), web3j, credentials, provider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> createAssert(CreateAssertRequest createAssertRequest) {
        // 创建资产
        // 是否需要借用审核、需要改数据类型、
//        AssertContract.Assert anAssert = new AssertContract.Assert(
//                UuidUtils.createUuid(),
//                createAssertRequest.getName(),
//                createAssertRequest.getApply(),
//
//        );
//        assertContract.createAssert();

        // 绑定自提点资产


        // 生成资产明细

        return null;
    }
}
