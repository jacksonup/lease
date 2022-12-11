package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 审批数目请求类
 *
 * @author chenyb46701
 * @date 2022/12/10
 */
@Getter
@Setter
@ToString
public class AuditCountsRequest {
    private String token;

    private String from;

    private String to;

    private Boolean timeRange;
}
