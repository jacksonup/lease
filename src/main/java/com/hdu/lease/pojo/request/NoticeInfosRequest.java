package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 按类型获取通知请求类
 *
 * @author chenyb46701
 * @date 2022/12/11
 */
@Getter
@Setter
@ToString
public class NoticeInfosRequest extends NoticeCountListRequest{
    private String type;
}
