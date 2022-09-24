package com.hdu.lease.pojo.response.base;

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
@NoArgsConstructor
@AllArgsConstructor
public class BaseGenericsResponse<T> extends BaseResponse{
    /**
     * 数据
     */
    private T data;
}
