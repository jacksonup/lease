package com.hdu.lease.schedule;

import com.hdu.lease.contract.*;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.service.EventService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author chenyb46701
 * @date 2022/12/14
 */
@Service
public class TimeTaskService {

    @Setter(onMethod_ = @Autowired)
    private ContractProperties contractProperties;

    @Setter(onMethod_ = @Autowired)
    private EventService eventService;

    @Setter(onMethod_ = @Autowired)
    private ContractMapper contractMapper;

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
        Contract assetDetailContractEntity = contractMapper.selectById(5);

        assetDetailContract = AssetDetailContract.load(
                assetDetailContractEntity.getContractAddress(),
                web3j,
                credentials,
                provider
        );
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void back() throws Exception {
        // 格式化时间
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        BigInteger nowTime = new BigInteger(localDateTime.format(formatter));


        // 获取所有在使用的assetDetail
        List<AssetDetailContract.AssetDetail> assetDetailList =  assetDetailContract.getByCurrentStatus(new BigInteger("1")).send();

        for (AssetDetailContract.AssetDetail assetDetail : assetDetailList) {
            // 获取截止时间
            BigInteger endTime = new BigInteger(assetDetail.getEndTime());

            int i = nowTime.compareTo(endTime);

            // 超时
            if (i > 0){
                // 事件

            }

        }

    }

}
