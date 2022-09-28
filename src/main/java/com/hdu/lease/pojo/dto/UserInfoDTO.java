package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

/**
 * 用户信息DTO
 *
 * @author chenyb46701
 * @date 2022/9/25
 */
@Getter
@Setter
@ToString
public class UserInfoDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 学号
     */
    private String account;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色
     */
    private BigInteger role;
}
