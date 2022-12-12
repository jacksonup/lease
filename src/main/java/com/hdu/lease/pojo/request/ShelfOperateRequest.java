package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 上架下架操作请求类
 *
 * @author chenyb46701
 * @date 2022/12/3
 */
@Getter
@Setter
@ToString
public class ShelfOperateRequest {
    private String token;

    private String assetId;

    private String placeId;
}
