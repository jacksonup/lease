package com.hdu.lease.pojo.response;

import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;

/**
 * @author Jackson
 * @date 2022/4/30 20:29
 * @description:
 */
@Data
@ToString(callSuper = true)
public class LoginInfoResponse {

    private String token;

    private Integer role;

    private Boolean bindPhone;

}
