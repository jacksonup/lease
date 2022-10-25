package com.hdu.lease.pojo.request;

import lombok.Getter;

/**
 * @author chenyb46701
 * @date 2022/10/25
 */
@Getter
public class AssetBorrowRequest {
    private String assetId;

    private String to;
}
