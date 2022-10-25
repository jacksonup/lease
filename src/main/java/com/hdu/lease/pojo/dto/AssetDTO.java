package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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

    private String picUrl;

    private String name;

    private int rest;

    private Boolean apply;

    private List<String> placeList;

    private int value;
}
