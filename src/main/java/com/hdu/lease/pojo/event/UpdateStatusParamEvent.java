package com.hdu.lease.pojo.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 更新状态事件
 *
 * 管理员【】手动更新仓库【】中的物资【】状态，改为【】。更改原因：
 * @author chenyb46701
 * @date 2022/12/14
 */
@Getter
@Setter
@ToString
public class UpdateStatusParamEvent {

    private String manageName;

    private String placeName;

    private String assetName;

    private int status;

    private String reason;

    @Override
    public String toString() {
        String statusName = "";

        if (status == 1) {
            statusName = "使用中";
        } else if(status == 2) {
            statusName = "审核中";
        } else if (status == 3) {
            statusName = "丢失";
        } else if(status == 4) {
            statusName = "损坏";
        } else if (status == 5) {
            statusName = "已下架";
        } else if (status == 0) {
            statusName = "空闲";
        }

        return "管理员【" + manageName +
                "】手动更新仓库【" + placeName +
                "】中的物资【" + assetName +
                "】状态，改为【" + statusName +
                "】。更改原因：" + reason;
    }
}
