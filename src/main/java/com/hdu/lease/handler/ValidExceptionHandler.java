package com.hdu.lease.handler;

import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常检查
 *
 * @author chenyb46701
 * @date 2022/12/8
 */
@Slf4j
@RestControllerAdvice
public class ValidExceptionHandler {

    @ExceptionHandler(BindException.class)
    public BaseGenericsResponse<String> validBindException(BindException bindException) {
        return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, bindException.getAllErrors().get(0).getDefaultMessage());
    }

}
