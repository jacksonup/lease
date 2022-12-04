package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * 获取待上架物资DTO
 *
 * @author chenyb46701
 * @date 2022/12/3
 */
@Getter
@ToString
public class CanGroundingDTO {
    private String name;

    private String picUrl;

    private String count;

    private String assetId;
}
