package com.hdu.lease.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Jackson
 * @date 2022/5/1 10:44
 * @description: sms config
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "tencent.sms")
@PropertySource("classpath:application.yml")
public class SmsProperties {

    private String secretId;

    private String secretKey;

    private String appId;

}
