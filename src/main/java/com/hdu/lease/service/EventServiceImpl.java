package com.hdu.lease.service;

import com.hdu.lease.contract.*;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.utils.UuidUtils;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 事件实现
 *
 * @author chenyb46701
 * @date 2022/12/12
 */
@Service("eventService")
public class EventServiceImpl implements EventService{

    @Setter(onMethod_ = @Autowired)
    private ContractProperties contractProperties;

    @Setter(onMethod_ = @Autowired)
    private ContractMapper contractMapper;

    private EventContract eventContract;

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
        Contract eventContractEntity = contractMapper.selectById(8);
        Contract assetDetailContractEntity = contractMapper.selectById(5);

        // 加载合约
        eventContract = EventContract.load(eventContractEntity.getContractAddress(), web3j, credentials, provider);
        assetDetailContract = AssetDetailContract.load(
                assetDetailContractEntity.getContractAddress(),
                web3j,
                credentials,
                provider
        );
    }

    /**
     * 事件插入
     *
     * @param type
     * @param assetDetailId
     * @param placeId
     * @param operatorAccount
     * @param content
     */
    @Override
    public void insert(String type, String assetDetailId, String placeId, String operatorAccount, String content) throws Exception {
        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        EventContract.Event event = new EventContract.Event(
                UuidUtils.createUuid(),
                new BigInteger(type),
                assetDetailId,
                placeId,
                operatorAccount,
                new BigInteger(localDateTime.format(formatter)),
                content,
                assetDetailContract.getByPrimaryKey(assetDetailId).send().getCurrentStatus()
        );

        eventContract.insert(event).send();
    }

    /**
     * 获取事件状态
     *
     * @param type
     * @return
     */
    @Override
    public String getType(String type) {
        String eventStatus = "";

        switch (type) {
            case "1" : eventStatus = "状态手动更新"; break;
            case "2" : eventStatus = "创建"; break;
            case "3" : eventStatus = "上架"; break;
            case "4" : eventStatus = "下架"; break;
            case "5" : eventStatus = "扫码归还"; break;
            case "6" : eventStatus = "编辑信息"; break;
            case "7" : eventStatus = "扫码借用"; break;
            case "8" : eventStatus = "提交申请"; break;
            case "9" : eventStatus = "审批结果"; break;
            default:
        }
        return eventStatus;
    }
}
