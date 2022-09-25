package com.hdu.lease.service;

import com.hdu.lease.pojo.request.BaseRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;

/**
 * @author Jackson
 * @date 2022/5/2 15:09
 * @description: Sms service.
 */
public interface SmsService {

    /**
     * Send sms normal interface.
     *
     * @param token
     * @param templateParamSet
     * @param templateId
     * @param timeUnit
     * @return
     */
    BaseGenericsResponse<String> sendSms(String token, String[] templateParamSet, String templateId, Long timeUnit);

}
