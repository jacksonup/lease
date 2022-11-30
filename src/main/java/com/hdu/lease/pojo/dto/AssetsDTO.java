package com.hdu.lease.pojo.dto;

import com.hdu.lease.pojo.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.List;

/**
 * 获取所有资产DTO
 *
 * @author chenyb46701
 * @date 2022/11/29
 */
@Getter
@Setter
@ToString
public class AssetsDTO {
    private String name;

    private String picUrl;

    private BigInteger value;

    private Boolean apply;

    private List<String> places;

    private Integer rest;

    private String assetId;

}
