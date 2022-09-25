package com.hdu.lease.service;

import com.hdu.lease.pojo.dto.UserInfoDTO;
import com.hdu.lease.pojo.request.BaseRequest;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;

/**
 * @author Jackson
 * @date 2022/4/30 16:03
 * @description: User service.
 */
public interface UserService {

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    BaseGenericsResponse<LoginInfoResponse> login(String account, String password) throws Exception;

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    BaseGenericsResponse<UserInfoDTO> getUserInfo(String token) throws Exception;

    /**
     * 修改手机号
     *
     * @param baseRequest
     * @return
     */
    BaseGenericsResponse<String> modifyPhone(BaseRequest baseRequest) throws Exception;

    /**
     * 非登录态修改密码
     *
     * @param baseRequest
     * @return
     */
    BaseGenericsResponse<String> modifyPasswordWithoutToken(BaseRequest baseRequest) throws Exception;

    /**
     * 登录态修改密码
     *
     * @param baseRequest
     * @return
     */
    BaseGenericsResponse<String> modifyPassword(BaseRequest baseRequest) throws Exception;

    /**
     * Update role.
     *
     * @param token
     * @param userId
     * @param role
     * @return
     */
    BaseGenericsResponse updateRole(String token, Integer userId, Integer role);

    /**
     * Update password.
     *
     * @param token
     * @param password
     * @return
     */
    BaseGenericsResponse updatePassword(String token, String password);

    /**
     * Find back the password.
     *
     * @param token
     * @param password
     * @param code
     * @return
     */
    BaseGenericsResponse findPassword(String token, String password, String code);

    /**
     * Update the wxOpenId.
     *
     * @param token
     * @param wxOpenId
     * @return
     */
    BaseGenericsResponse updateWx(String token, String wxOpenId);

    /**
     * Update the phoneNumber.
     *
     * @param token
     * @param phoneNumber
     * @param code
     * @return
     */
    BaseGenericsResponse updatePhone(String token, String phoneNumber, String code);

}
