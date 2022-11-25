package com.hdu.lease.service;

import com.hdu.lease.pojo.dto.AuditFormDTO;
import com.hdu.lease.pojo.dto.AuditPreviewDTO;
import com.hdu.lease.pojo.dto.InfoDTO;
import com.hdu.lease.pojo.dto.UserInfoDTO;
import com.hdu.lease.pojo.request.*;
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

    /**
     * 驳回申请
     *
     * @param auditRequest
     * @return
     */
    BaseGenericsResponse<String> reject(AuditRequest auditRequest);

    /**
     * 同意申请
     *
     * @param auditRequest
     * @return
     */
    BaseGenericsResponse<String> agree(AuditRequest auditRequest);

    /**
     * 获取申请表单
     *
     * @param auditRequest
     * @return
     */
    BaseGenericsResponse<AuditFormDTO> audit(AuditRequest auditRequest);

    /**
     * 按类型获取审批预览列表
     *
     * @param auditPreviewRequest
     * @return
     */
    BaseGenericsResponse<List<AuditPreviewDTO>> audits(AuditPreviewRequest auditPreviewRequest);

    /**
     * 标记对应通知已读
     *
     * @param token
     * @param infoId
     * @return
     */
    BaseGenericsResponse<String> read(String token, String infoId);

    /**
     * 获取通知未读数量列表
     *
     * @param noticeCountListRequest
     * @return
     */
    BaseGenericsResponse<List<Integer>> counts(NoticeCountListRequest noticeCountListRequest);

    /**
     * 按类型获取通知
     *
     * @param noticeCountListRequest
     * @return
     */
    BaseGenericsResponse<InfoDTO> infos(NoticeCountListRequest noticeCountListRequest);

    /**
     * 一键标为已读
     *
     * @param token
     * @param infoIds
     * @return
     */
    BaseGenericsResponse<String> readAll(String token, List<Long> infoIds);
}
