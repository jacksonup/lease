package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 扫码物资信息DTO类
 *
 * @author chenyb46701
 * @date 2022/10/25
 */
@Setter
@Getter
@ToString
public class ScannedAssetDTO {
    private String url;

    private String name;

    private int value;

    private Boolean apply;

    private Boolean free;

    private String expiredTime;

    private String place;

    private String username;

    private int rest;
}
