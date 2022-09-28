package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author chenyb46701
 * @date 2022/9/28
 */
@Setter
@Getter
public class ModifyUserInfoRequest {
    private String token;

    private String username;

    private String phone;

    private String account;

    private BigInteger role;
}
