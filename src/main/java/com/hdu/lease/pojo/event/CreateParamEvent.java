package com.hdu.lease.pojo.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 创建event
 *
 * @author chenyb46701
 * @date 2022/12/13
 */
@Getter
@Setter
@ToString
public class CreateParamEvent {
    private Boolean isBorrow;

    private String assetName;

    private String value;

    private String placeName;

    /**
     * 创建了不需要借用的【Macbook】，价值6666元，存放在中央仓库.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder createBuilder = new StringBuilder();
        createBuilder.append("创建了");

        if (isBorrow) {
            createBuilder.append("需要借用的");
        } else {
            createBuilder.append("不需要借用的");
        }

        createBuilder.append("【").append(assetName).append("】，");
        createBuilder.append("价值").append(value).append("元，");
        createBuilder.append("存放在").append(placeName).append("仓库。");
        return createBuilder.toString();

    }
}
