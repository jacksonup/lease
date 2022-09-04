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
public class BaseGenericsResponse {
    private Integer code;
    private String msg;

    private Object data;

    public BaseGenericsResponse() {}

    public BaseGenericsResponse(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public BaseGenericsResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseGenericsResponse(StatusCode statusCode, Object data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    public BaseGenericsResponse(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
