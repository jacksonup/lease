package com.hdu.lease.model.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author Jackson
 * @date 2022/4/30 20:29
 * @description:
 */
@Data
@ToString(callSuper = true)
public class LoginVO {

    private String token;

    private Integer role;

    private Boolean bindPhone;

}
