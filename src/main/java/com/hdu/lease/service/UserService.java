package com.hdu.lease.service;

import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;

import java.util.concurrent.ExecutionException;

/**
 * @author Jackson
 * @date 2022/4/30 16:03
 * @description: User service.
 */
public interface UserService {

    /**
     * Manual login.
     *
     * @param account
     * @param password
     * @return
     */
    BaseGenericsResponse<LoginInfoResponse> login(String account, String password) throws Exception;

    /**
     * Auto login.
     *
     * @param token
     * @return
     */
    BaseGenericsResponse autoLogin(String token);

    /**
     * Sso login.
     *
     * @param wxOpenId
     * @return
     */
    BaseGenericsResponse wxLogin(String wxOpenId);

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
