package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chenyb46701
 * @date 2022/10/9
 */
@Getter
@Setter
@ToString
public class JwtTokenDTO {

    private String loginName;
    private String name;
    private String email;
    private String phoneNumber;
}
