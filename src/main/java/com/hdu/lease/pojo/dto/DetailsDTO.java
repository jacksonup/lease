package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 指定明细物资列表
 *
 * @author chenyb46701
 * @date 2022/11/30
 */
@Getter
@Setter
@ToString
public class DetailsDTO {
    private Integer count;

    private final DetailInfo detailInfo = new DetailInfo();

    /**
     * 指定明细物资列表
     *
     * @author chenyb46701
     * @date 2022/11/30
     */
    public static class DetailInfo {
        private String detailId;

        private Integer status;
    }
}
