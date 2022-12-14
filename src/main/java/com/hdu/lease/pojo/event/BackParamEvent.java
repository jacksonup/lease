package com.hdu.lease.pojo.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 扫码归还参数事件
 *
 * @author chenyb46701
 * @date 2022/12/14
 */
@Getter
@Setter
@ToString
public class BackParamEvent {
    private String assetName;

    private String placeManagerName;

    @Override
    public String toString() {
        return "【" + assetName + "】已由管理员【" + placeManagerName + "】扫码接收归还。";
    }

}
