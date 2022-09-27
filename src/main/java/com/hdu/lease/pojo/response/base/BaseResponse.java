package com.hdu.lease.pojo.response.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName:BaseGenericsResponse
 * @Author: wangxg32387
 * @CreateTime: 2021-11-29 14:00
 * @Description 带泛型的响应类
 **/
@Getter
@Setter
@ToString
public class BaseResponse {

    /**
     * 响应状态
     */
    private String code;

    /**
     * 响应状态说明
     */
    private String msg;


    public static final String SUCCESS_STATUS = "200";

    public static final String FAIL_STATUS = "1001";
}
