package com.hdu.lease.pojo.response.base;

import com.hdu.lease.exception.BaseBizException;
import lombok.*;

/**
 * 通用泛型响应，用于承载一些基本类型
 *
 * @param <T> data的类型
 * @author zhouty26900
 * @date 2021/07/29
 */
@Getter
@Setter
@ToString
public class BaseGenericsResponse<T> extends BaseResponse{
    /**
     * 数据
     */
    private T data;

    public static <T> BaseGenericsResponse<T> successBaseResp(T data) {
        BaseGenericsResponse<T> BaseGenericsResponse = new BaseGenericsResponse<>();
        BaseGenericsResponse.success(data);
        return BaseGenericsResponse;
    }

    public BaseGenericsResponse<T> success(T data) {
        this.setCode(SUCCESS_STATUS);
        this.setMsg("ok");
        this.data = data;
        return this;
    }

    public static <T> BaseGenericsResponse<T> failureBaseResp(String info) {
        BaseGenericsResponse<T> BaseGenericsResponse = new BaseGenericsResponse();
        BaseGenericsResponse.failureMsg(info);
        return BaseGenericsResponse;
    }

    public static <T> BaseGenericsResponse<T> failureBaseResp(BaseBizException e) {
        BaseGenericsResponse<T> BaseGenericsResponse = new BaseGenericsResponse<>();
        BaseGenericsResponse.failureMsg(e.getMessage());
        BaseGenericsResponse.setCode(e.getErrorCode());
        return BaseGenericsResponse;
    }

    public BaseGenericsResponse failureMsg(String info) {
        this.setCode(FAIL_STATUS);
        this.setMsg(info);
        return this;
    }
}
