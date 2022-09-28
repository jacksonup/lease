package com.hdu.lease.controller;

import com.hdu.lease.constant.SmsConstant;
import com.hdu.lease.pojo.request.BaseRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.service.SmsService;
import com.hdu.lease.utils.RandomNumberUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public BaseGenericsResponse<String> modifyPassword(BaseRequest baseRequest) {
        String code = RandomNumberUtils.createRandomNumber(4);
        String[] templateParamSet = {code, "5"};
        return smsService.sendSms(baseRequest.getPhone(), templateParamSet, SmsConstant.UPDATE_PASSWORD, 5L);
    }
}
