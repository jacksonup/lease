package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 审批预览
 *
 * @author chenyb46701
 * @date 2022/11/20
 */
@Getter
@Setter
@ToString
public class AuditPreviewDTO {
    /**
     * 物资名：借用者姓名
     */
    private String head;

    /**
     * 申请理由
     */
    private String reason;

    /**
     * id
     */
    private String auditId;

    /**
     * 审批状态
     */
    private String status;
}
