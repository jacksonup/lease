package com.hdu.lease.pojo.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 货架操作
 *
 * @author chenyb46701
 * @date 2022/12/14
 */
@Getter
@Setter
@ToString
public class ShelfOperateParamEvent {
    private String managerName;

    private String placeName;

    private String assetName;

    private int type;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("管理员").append("【").append(managerName).append("】，");

        // 上架
        if (type == 1) {
            builder.append("上架了物资【").append(assetName).append("】，到仓库【").append(placeName).append("】中。");
        }

        // 下架
        if (type == 2) {
            builder.append("下架了仓库【").append(placeName).append("】中的物资【").append(assetName).append("】。");
        }

        return builder.toString();
    }

}
