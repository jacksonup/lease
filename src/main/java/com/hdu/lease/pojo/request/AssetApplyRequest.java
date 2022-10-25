package com.hdu.lease.pojo.request;

import lombok.Getter;

/**
 * 申请借用请求类
 *
 * @author chenyb46701
 * @date 2022/10/25
 */
@Getter
public class AssetApplyRequest extends BaseRequest{
    private String assetType;

    private String from;

    private String to;

    private String reason;

    private String count;

}
