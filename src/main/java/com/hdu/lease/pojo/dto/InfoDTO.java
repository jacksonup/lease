package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 通知列表
 *
 * @author chenyb46701
 * @date 2022/11/24
 */
@Getter
@Setter
@ToString
public class InfoDTO {
    /**
     * 通知内容
     */
    private Map<String, String> content;

    /**
     * 是否已读
     */
    private Boolean read;

    /**
     * id
     */
    private Long infoId;
}
