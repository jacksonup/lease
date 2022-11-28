package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 审批拒绝请求类
 *
 * @author chenyb46701
 * @date 2022/11/19
 */
@Getter
@Setter
public class AuditRequest extends BaseRequest{
    /**
     * 审批单ID
     */
    private String auditId;

    /**
     * 拒绝原因
     */
    private String denyReason;
}
