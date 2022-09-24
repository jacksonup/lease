package com.hdu.lease.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Component
public class Web3jUtils {

    /**
     * 获取web3j对象
     *
     * @return
     */
    @Bean("Web3j")
    @Scope("prototype")
    private static Web3j getWeb3j(){
        return Web3j.build(new HttpService("http://localhost:8545"));
    }
}
