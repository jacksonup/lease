package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用请求类
 *
 * @author chenyb46701
 * @date 2022/9/25
 */
@Getter
@Setter
public class BaseRequest {
    private String token;

    private String account;

    private String phone;

    private String password;

    private String code;
}
