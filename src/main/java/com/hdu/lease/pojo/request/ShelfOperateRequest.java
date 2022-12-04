package com.hdu.lease.pojo.request;

import lombok.Getter;

/**
 * 上架下架操作请求类
 *
 * @author chenyb46701
 * @date 2022/12/3
 */
@Getter
public class ShelfOperateRequest {
    private String token;

    private String assetId;

    private String placeId;
}
