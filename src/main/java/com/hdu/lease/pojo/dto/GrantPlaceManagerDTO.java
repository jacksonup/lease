package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * 撤销管理员DTO
 *
 * @author chenyb46701
 * @date 2022/12/8
 */
@Getter
@Setter
@ToString
public class GrantPlaceManagerDTO {
    private String token;

    private Boolean grant;

    private String placeId;

    private String account;
}
