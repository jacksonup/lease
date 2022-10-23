package com.hdu.lease.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * 创建资产请求类
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
@Getter
@Setter
@ToString
public class CreateAssertRequest {
    /**
     * 资产名
     */
    private String name;

    /**
     * 是否需要借用
     */
    private Boolean apply;

    /**
     * 图片路径
     */
    private String picUrl;

    /**
     * 价值
     */
    private Double value;

    /**
     * 自提点列表
     */
    private List<Map<String, Integer>> placeList;
}
