package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

/**
 * 获取所有用户DTO
 *
 * @author chenyb46701
 * @date 2022/12/2
 */
@Getter
@Setter
@ToString
public class GetNoRoleUserInfoDTO {
    /**
     * 用户名
     */
    private String name;

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
