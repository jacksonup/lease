package com.hdu.lease.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hdu.lease.model.response.BaseGenericsResponse;
import com.hdu.lease.model.response.StatusCode;
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
     * Send sms normal interface.
     *
     * @param token
     * @param templateParamSet
     * @param templateId
     * @param time
     * @return
     */
    @Override
    public BaseGenericsResponse sendSms(String token, String[] templateParamSet, String templateId, Long time) {
        if (!JwtUtils.verifyToken(token)) {
            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
        }
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
//        User user = userRepository.getById(tokenInfo.getClaim("userId").asInt());
        // Find phone number by userId.
//        String phone = user.getPhone();
        String phone = "aa";
        // Judge whether to send repeatedly.
        String codeBefore = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(codeBefore)) {
            return new BaseGenericsResponse(StatusCode.CODE_IS_EXIST);
        }
        String phoneNumber = "86" +
                phone;
        smsUtils.send(templateParamSet, phoneNumber, templateId);
        // put in redis
        redisTemplate.opsForValue().set(phone, templateParamSet[0], time, TimeUnit.MINUTES);
        return new BaseGenericsResponse(StatusCode.SUCCESS);
    }

}
