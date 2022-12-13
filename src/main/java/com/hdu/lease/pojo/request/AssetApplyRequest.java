package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 申请借用请求类
 *
 * @author chenyb46701
 * @date 2022/10/25
 */
@Getter
@Setter
@ToString
public class AssetApplyRequest {
    private String token;

    private String assetType;

    private String placeId;

    private int count;

    private String from;

    private String to;

    private String reason;

}
