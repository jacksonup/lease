package com.hdu.lease.service;

import com.hdu.lease.pojo.dto.*;
import com.hdu.lease.pojo.request.*;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

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
     * @throws Exception
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
     * @throws Exception
     */
    BaseGenericsResponse<String> modifyPhone(BaseRequest baseRequest) throws Exception;

    /**
     * 非登录态修改密码
     *
     * @param baseRequest
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<String> modifyPasswordWithoutToken(BaseRequest baseRequest) throws Exception;

    /**
     * 登录态修改密码
     *
     * @param baseRequest
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<String> modifyPassword(BaseRequest baseRequest) throws Exception;


    /**
     * 修改用户信息
     *
     * @param modifyUserInfoRequest
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<String> modifyUserInfoById(ModifyUserInfoRequest modifyUserInfoRequest) throws Exception;

    /**
     * 获取指定用户信息
     *
     * @param baseRequest
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<UserInfoDTO> oneInfo(BaseRequest baseRequest) throws Exception;

    /**
     * 获取所有用户
     *
     * @param getAllUserListRequest
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<UsersDTO> getAllUserList(GetAllUserListRequest getAllUserListRequest) throws Exception;

    /**
     * 获取角色1 用户列表
     *
     * @param token
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<List<UserInfoDTO>> getRoleOnesUserList(String token) throws Exception;

    /**
     * 登出
     *
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<String> logout(BaseRequest baseRequest);

    /**
     * 判断是否是角色二
     *
     * @param token
     * @return
     * @throws Exception
     */
    Boolean judgeRole(String token, int roleId) throws Exception;

    /**
     * 导入用户
     *
     * @param file
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<String> importUser(MultipartFile file) throws Exception;

    /**
     * 驳回申请
     *
     * @param auditRequest
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<String> reject(AuditRequest auditRequest) throws Exception;

    /**
     * 同意申请
     *
     * @param auditRequest
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<String> agree(AuditRequest auditRequest) throws Exception;

    /**
     * 获取申请表单
     *
     * @param auditRequest
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<AuditFormDTO> audit(AuditRequest auditRequest) throws Exception;

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
