package com.hdu.lease.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jackson
 * @date 2022/4/30 15:59
 * @description: Base Response
 */
@Getter
@Setter
public class BaseGenericsResponse<T> {
    private Integer code;
    private String msg;

    private T data;

    public BaseGenericsResponse() {}

    public BaseGenericsResponse(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public BaseGenericsResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseGenericsResponse(StatusCode statusCode, T data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    public BaseGenericsResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
