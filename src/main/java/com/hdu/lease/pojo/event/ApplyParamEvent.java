package com.hdu.lease.pojo.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 提交申请
 *
 * 【用户】在【时间】对仓库【仓库】的【物资名】物资进行申请借用，共计【count】件
 * @author chenyb46701
 * @date 2022/12/14
 */
@Getter
@Setter
@ToString
public class ApplyParamEvent {
    private String applyName;

    private String time;

    private String assetName;

    private String placeName;

    private int count;

    @Override
    public String toString() {
        return applyName +
                "在" +
                time +
                "对仓库" +
                "【" +
                placeName +
                "】的【" +
                assetName +
                "】物资进行借用，共计【" +
                count +
                "】件。";
    }
}
