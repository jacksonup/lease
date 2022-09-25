package com.hdu.lease.mapstruct;

import com.hdu.lease.pojo.dto.UserInfoDTO;
import com.hdu.lease.pojo.entity.User;
import org.mapstruct.Mapper;

/**
 * 用户信息转换
 *
 * @author chenyb46701
 * @date 2022/9/25
 */
@Mapper(componentModel = "spring")
public interface UserInfoConvert extends BaseObjectConvert<User, UserInfoDTO>{

}
