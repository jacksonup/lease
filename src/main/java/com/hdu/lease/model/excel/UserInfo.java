package com.hdu.lease.model.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jackson
 * @date 2022/5/5 20:15
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
