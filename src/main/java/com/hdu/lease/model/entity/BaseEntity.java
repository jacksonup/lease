package com.hdu.lease.model.entity;

import com.hdu.lease.utils.DateUtils;
import lombok.*;
import java.util.Date;

/**
 * @author Jackson
 * @date 2022/4/30 15:05
 * @description: Base entity
 */
@Getter
@Setter
@ToString
public class BaseEntity {

    /**
     * Create time.
     */
    private Date createTime;

    /**
     * Update time.
     */
    private Date updateTime;

}
