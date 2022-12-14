package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 手动修改明细物资状态
 *
 * @author chenyb46701
 * @date 2022/11/30
 */
@Getter
@Setter
@ToString
public class UpdateStatusRequest extends BaseRequest{
    private String detailId;

    private String reason;

    private int status;
}
