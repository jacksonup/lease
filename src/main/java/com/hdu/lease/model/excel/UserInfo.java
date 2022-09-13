package com.hdu.lease.model.excel;

import lombok.*;

/**
 * @author Jackson
 * @date 2022/5/5 20:15
 * @description:
 */
@Getter
@Setter
@ToString
public class UserInfo {


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

}
