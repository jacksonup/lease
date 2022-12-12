package com.hdu.lease;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan("com.hdu.lease.mapper")
public class LeaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaseApplication.class, args);
    }

}
