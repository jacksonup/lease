package com.hdu.lease.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Jackson
 * @date 2022/5/1 10:44
 * @description: sms config
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tencent.sms")
@PropertySource("classpath:application.yml")
public class SmsConfig {

    private String secretId;

    private String secretKey;

    private String appId;

}
