package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 审批预览请求
 *
 * @author chenyb46701
 * @date 2022/11/20
 */
@Getter
@Setter
@ToString
public class AuditPreviewRequest extends BaseRequest{
    /**
     * yyyy-MM-dd查询日期段
     */
    private String from;

    /**
     * yyyy-MM-dd查询日期段
     */
    private String to;

    /**
     * 是否需要时间段
     */
    private Boolean timeRange;

    /**
     * all、unAudit、pass、deny
     */
    private String type;
}
