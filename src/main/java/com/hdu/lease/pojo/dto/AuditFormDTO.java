package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 审批表单
 *
 * @author chenyb46701
 * @date 2022/11/20
 */
@Getter
@Setter
@ToString
public class AuditFormDTO {
    private String status;

    private String auditor;

    private String denyReason;

    private String applyName;

    private String applyNum;

    private String assetName;

    private String assetCount;

    private String assetValue;

    private String timeRange;

    private String applyReason;
}
