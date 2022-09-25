package com.hdu.lease.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hdu.lease.pojo.request.BaseRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.sms.SmsUtils;
import com.hdu.lease.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * @author Jackson
 * @date 2022/5/2 15:10
 * @description: Sms service implementation.
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 发送短信验证码通用接口
     *
     * @param
     * @param templateParamSet
     * @param templateId
     * @param time
     * @return
     */
    @Override
    public BaseGenericsResponse<String> sendSms(String phone, String[] templateParamSet, String templateId, Long time) {
        String phoneNumber = "86" +
                phone;
        smsUtils.send(templateParamSet, phoneNumber, templateId);
        // put in redis
        redisTemplate.opsForValue().set(phone, templateParamSet[0], time, TimeUnit.MINUTES);
        return new BaseGenericsResponse<>("验证码发送成功");
    }
}
