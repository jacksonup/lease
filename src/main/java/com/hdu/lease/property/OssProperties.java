package com.hdu.lease.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author chenyb46701
 * @date 2022/11/3
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "aliyun.oss")
@PropertySource("classpath:application.yml")
public class OssProperties {
    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 节点
     */
    private String endpoint;

    /**
     * bucketName
     */
    private String bucketName;
}
