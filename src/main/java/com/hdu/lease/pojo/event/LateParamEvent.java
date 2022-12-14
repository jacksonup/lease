package com.hdu.lease.pojo.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 超时参数事件
 *
 * @author chenyb46701
 * @date 2022/12/14
 */
@Getter
@Setter
@ToString
public class LateParamEvent {
    private String userName;

    private String placeName;

    private String assetName;

    @Override
    public String toString() {
        return "用户【" +
                userName +
                "】借用仓库【" +
                placeName +
                "】的物资" +
                "【" +
                assetName +
                "】超时。";
    }

}
