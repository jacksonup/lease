package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chenyb46701
 * @date 2022/10/25
 */
@Getter
@Setter
@ToString
public class AssetBorrowRequest extends BaseRequest{
    private String assetId;

    private String to;
}
