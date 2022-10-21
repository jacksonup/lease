package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 自提点DTO
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
@Getter
@Setter
@ToString
public class PlaceDTO {
    /**
     * 自提点Id
     */
    private String placeId;

    /**
     * 自提点名称
     */
    private String name;

    /**
     * 自提点地址
     */
    private String address;
}
