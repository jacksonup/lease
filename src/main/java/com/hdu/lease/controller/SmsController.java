package com.hdu.lease.controller;

import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.service.SmsService;
import com.hdu.lease.utils.RandomNumberUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jackson
 * @date 2022/5/2 14:30
 * @description:
 */
@RestController
@RequestMapping("/sms")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }


    /**
     * Send sms in order to update phone.
     *
     * @param token
     * @return
     */
    @PostMapping("updatePhone")
    @ResponseBody
    public BaseGenericsResponse updatePhone(String token) {
        // Configure template params.
        // Create n bit random number.
        String code = RandomNumberUtils.createRandomNumber(4);
        String[] templateParamSet = {code};
        return null;
//        return smsService.sendSms(token, templateParamSet, SmsConstant.UPDATE_PHONE, 5L);
    }

    /**
     * Send sms in order to find back the password.
     *
     * @param token
     * @return
     */
    @PostMapping("findPassword")
    @ResponseBody
    public BaseGenericsResponse findBackPassword(String token) {
        // Configure template params.
        // Create n bit random number.
        String code = RandomNumberUtils.createRandomNumber(4);
        String[] templateParamSet = {code, "1"};
//        return smsService.sendSms(token, templateParamSet, SmsConstant.UPDATE_PASSWORD, 1L);
        return null;
    }
}
