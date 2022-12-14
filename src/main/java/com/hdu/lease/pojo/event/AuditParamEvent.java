package com.hdu.lease.pojo.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 审批事件
 *
 * 管理员【】审批通过了用户【】提交的借用物资申请。申请单号为【】
 * @author chenyb46701
 * @date 2022/12/14
 */
@Getter
@Setter
@ToString
public class AuditParamEvent {
    private String manageName;

    private String userName;

    private String auditId;

    private int type;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("管理员").append(manageName).append("审批");

        if (type == 1) {
            builder.append("通过了");
        } if (type == 2) {
            builder.append("拒绝了");
        }

        builder.append("用户【").append(userName).append("】提交的借用物资申请。申请单号为【").append(auditId).append("】。");
        return builder.toString();
    }

}
