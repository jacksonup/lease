package com.hdu.lease.model.entity;

import lombok.*;

/**
 * @author Jackson
 * @date 2022/4/30 15:05
 * @description: User entity
 */
@Getter
@Setter
@ToString
public class User extends BaseEntity{


    private Integer id;

    /**
     * Account.
     */
    private String account;

    /**
     * Password.
     */
    private String password;

    /**
     * Username.
     */
    private String username;

    /**
     * Phone.
     */
    private String phone;

    /**
     * IsBindPhone.
     */
    private Integer isBindPhone;

    /**
     * WxOpenId.
     */
    private String wxOpenId;

    /**
     * Roll.
     */
    private Integer role;
}
