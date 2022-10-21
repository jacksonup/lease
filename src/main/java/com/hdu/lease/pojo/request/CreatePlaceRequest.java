package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 创建自提点请求类
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
@Getter
@Setter
@ToString
public class CreatePlaceRequest extends BaseRequest{

    /**
     * 自提点名称
     */
    private String name;

    /**
     * 自提点管理员ID
     */
    private String managerId;

    /**
     * 自提点地址
     */
    private String address;
}
