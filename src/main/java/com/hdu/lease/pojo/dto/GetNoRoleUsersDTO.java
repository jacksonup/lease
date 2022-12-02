package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 获取角色1角色0用户列表DTO
 *
 * @author chenyb46701
 * @date 2022/12/2
 */
@Getter
@Setter
@ToString
public class GetNoRoleUsersDTO {
    private Integer count;

    private List<GetNoRoleUserInfoDTO> getNoRoleUserInfoDTOList;
}
