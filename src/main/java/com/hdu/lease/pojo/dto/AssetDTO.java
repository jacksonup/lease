package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * 获取指定资产信息DTO
 *
 * @author chenyb46701
 * @date 2022/10/25
 */
@Getter
@Setter
@ToString
public class AssetDTO {
    private String assetId;

    private String picUrl;

    private String name;

    private Integer rest;

    private Boolean apply;

    private Map<String, String> placeList;

    private Integer value;
}
