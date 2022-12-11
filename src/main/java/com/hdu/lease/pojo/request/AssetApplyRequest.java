package com.hdu.lease.pojo.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 申请借用请求类
 *
 * @author chenyb46701
 * @date 2022/10/25
 */
@Getter
@Setter
@ToString
public class AssetApplyRequest extends BaseRequest{
    private String assetType;

    private String placeId;

    private String from;

    private String to;

    private String reason;

    private int count;

}
