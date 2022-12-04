package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 补充物资
 *
 * @author chenyb46701
 * @date 2022/12/3
 */
@Getter
@Setter
@ToString
public class SupplyRequest {
    private String token;

    private String assetId;

    private String placeId;

    private Integer count;
}
