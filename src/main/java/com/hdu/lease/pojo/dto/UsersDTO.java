package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 获取所有用户DTO
 *
 * @author chenyb46701
 * @date 2022/11/28
 */
@Getter
@Setter
@ToString
public class UsersDTO {
    private int count;

    private List<UserInfoDTO> userInfoDTOList;
}
