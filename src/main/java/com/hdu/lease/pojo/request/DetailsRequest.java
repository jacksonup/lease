package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

/**
 * 分页获取指定物资请求类
 *
 * @author chenyb46701
 * @date 2022/11/30
 */
@Getter
@Setter
@ToString
public class DetailsRequest extends BaseRequest{
    private String assetId;

    private BigInteger fromIndex;

    private String placeId;

    private String status;
}
