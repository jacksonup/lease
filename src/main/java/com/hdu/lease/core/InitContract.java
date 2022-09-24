package com.hdu.lease.core;

import com.hdu.lease.contract.UserContract;
import com.hdu.lease.property.ContractProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

/**
 * 初始化合约
 *
 * @author chenyb46701
 * @date Ap2/9/13
 */
@Slf4j
@Component
public class InitContract implements ApplicationRunner {

    @Setter(onMethod_ = @Autowired)
    private ContractProperties contractProperties;

    /**
     * 部署合约
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
//         监听本地链
//        Web3j web3j = Web3j.build(new HttpService(contractProperties.getAddress()));
//        Credentials credentials = Credentials.create(contractProperties.getCredentials());
//
//        // 注册gasProvider
//        StaticGasProvider provider = new StaticGasProvider(
//                contractProperties.getGasPrice(),
//                contractProperties.getGasLimit());
//
//        // 部署合约
//        UserContract.deploy(web3j, credentials, provider).send();
    }
}
