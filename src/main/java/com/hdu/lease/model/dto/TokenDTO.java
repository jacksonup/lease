package com.hdu.lease.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jackson
 * @date 2022/4/30 20:40
 * @description: Token structure
 */
@Getter
@Setter
@ToString
public class TokenDTO {

    private Integer userId;

    private String uuid;

    private Integer role;
}
