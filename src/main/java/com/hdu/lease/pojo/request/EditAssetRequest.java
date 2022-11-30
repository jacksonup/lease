package com.hdu.lease.pojo.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.Base64;

/**
 * 编辑资产请求
 *
 * @author chenyb46701
 * @date 2022/11/29
 */
@Getter
@Setter
@ToString
public class EditAssetRequest extends BaseRequest {
    private String assetId;

    private String name;

    private Boolean apply;

    private String picUrl;

    private BigInteger value;
}
