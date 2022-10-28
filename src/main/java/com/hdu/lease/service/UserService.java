package com.hdu.lease.service;

import com.hdu.lease.pojo.dto.UserInfoDTO;
import com.hdu.lease.pojo.request.BaseRequest;
import com.hdu.lease.pojo.request.GetAllUserListRequest;
import com.hdu.lease.pojo.request.ModifyUserInfoRequest;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
     * 修改用户信息
     *
     * @param modifyUserInfoRequest
     * @return
     */
    BaseGenericsResponse<String> modifyUserInfoById(ModifyUserInfoRequest modifyUserInfoRequest) throws Exception;

    /**
     * 获取指定用户信息
     *
     * @param baseRequest
     * @return
     */
    BaseGenericsResponse<UserInfoDTO> oneInfo(BaseRequest baseRequest) throws Exception;

    /**
     * 获取所有用户
     *
     * @param getAllUserListRequest
     * @return
     */
    BaseGenericsResponse<List<UserInfoDTO>> getAllUserList(GetAllUserListRequest getAllUserListRequest) throws ExecutionException, InterruptedException;

    /**
     * 获取角色1 用户列表
     *
     * @param token
     * @return
     */
    BaseGenericsResponse<List<UserInfoDTO>> getRoleOnesUserList(String token) throws Exception;

    /**
     * 登出
     *
     * @return
     */
    BaseGenericsResponse<String> logout(BaseRequest baseRequest);

    /**
     * 判断是否是角色二
     *
     * @param token
     * @return
     */
    Boolean judgeRole(String token, int roleId) throws Exception;

    /**
     * 导入用户
     *
     * @param file
     * @return
     */
    BaseGenericsResponse<String> importUser(MultipartFile file) throws Exception;
}
