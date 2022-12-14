package com.hdu.lease.pojo.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 借用参数事件
 *
 * @author chenyb46701
 * @date 2022/12/14
 */
@Getter
@Setter
@ToString
public class BorrowParamEvent {
    private String assetName;

    private String borrowName;

    private String to;

    @Override
    public String toString() {

        return "【" +
                assetName +
                "】由" +
                borrowName +
                "扫码借用，借用事件截止到" +
                "【" +
                to +
                "】。";
    }
}
