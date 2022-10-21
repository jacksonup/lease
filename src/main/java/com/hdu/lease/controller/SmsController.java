package com.hdu.lease.controller;

import com.hdu.lease.constant.SmsConstant;
import com.hdu.lease.contract.UserContract;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.request.BaseRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.service.SmsService;
import com.hdu.lease.utils.JwtUtils;
import com.hdu.lease.utils.RandomNumberUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import javax.annotation.PostConstruct;

/**
 * 短信控制类
 *
 * @author chenyb46701
 * @date 2022/9/25
 */
@RestController
@RequestMapping("/sms")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SmsController {

    @Setter(onMethod_ = @Autowired)
    private SmsService smsService;

    @Setter(onMethod_ = @Autowired)
    private ContractProperties contractProperties;

    @Setter(onMethod_ = @Autowired)
    private ContractMapper contractMapper;

    private UserContract usercontract;

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
        usercontract = UserContract.load(contract.getContractAddress(), web3j, credentials, provider);
    }

    /**
     * 重置密码发送验证码
     *
     * @param baseRequest
     * @return
     */
    @PostMapping("/resetPassword")
    @ResponseBody
    public BaseGenericsResponse<String> restPassword(BaseRequest baseRequest) {
        String code = RandomNumberUtils.createRandomNumber(4);
        String[] templateParamSet = {code, "5"};
        return smsService.sendSms(baseRequest.getPhone(), templateParamSet, SmsConstant.UPDATE_PASSWORD, 5L);
    }

    /**
     * 改手机号发送验证码
     *
     * @param baseRequest
     * @return
     */
    @PostMapping("/modifyPhone")
    @ResponseBody
    public BaseGenericsResponse<String> modifyPhone(BaseRequest baseRequest) {
        String code = RandomNumberUtils.createRandomNumber(4);
        String[] templateParamSet = {code};
        return smsService.sendSms(baseRequest.getPhone(), templateParamSet, SmsConstant.UPDATE_PHONE, 5L);
    }

    /**
     * 修改密码发送验证码
     *
     * @param baseRequest
     * @return
     */
    @PostMapping("/modifyPassword")
    @ResponseBody
    public BaseGenericsResponse<String> modifyPassword(BaseRequest baseRequest) throws Exception {
        String code = RandomNumberUtils.createRandomNumber(4);
        String[] templateParamSet = {code, "5"};
        // 获取token中的绑定的手机号
        // 取出account
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();
        UserContract.User user = usercontract.getUserInfo(account).send();
        return smsService.sendSms(user.getPhone(), templateParamSet, SmsConstant.UPDATE_PASSWORD, 5L);
    }
}
