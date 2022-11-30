package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取指定物资信息DTO
 *
 * @author chenyb46701
 * @date 2022/11/29
 */
@Getter
@Setter
@ToString
public class AssetInfoDTO {
    private String name;

    private Integer value;

    private Boolean apply;

    private String picUrl;
}
