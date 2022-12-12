package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 事件DTO
 *
 * @author chenyb46701
 * @date 2022/12/12
 */
@Getter
@Setter
@ToString
public class EventDTO {
    private String time;

    private String name;

    private String content;

    private String status;

    private String place;

    private String operator;
}
