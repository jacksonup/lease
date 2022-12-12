package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author chenyb46701
 * @date 2022/9/28
 */
@Getter
@Setter
@ToString
public class ModifyUserInfoRequest extends BaseRequest{
    private BigInteger role;

    private String name;
}
