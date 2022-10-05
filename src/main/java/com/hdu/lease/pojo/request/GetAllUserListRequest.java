package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

/**
 * @author chenyb46701
 * @date 2022/10/2
 */
@Getter
@Setter
@ToString
public class GetAllUserListRequest extends BaseRequest{
    private BigInteger from;
}
