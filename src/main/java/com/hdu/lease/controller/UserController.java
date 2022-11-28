package com.hdu.lease.controller;

import com.hdu.lease.pojo.dto.*;
import com.hdu.lease.pojo.request.*;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 用户模块控制类
 *
 * @author Jackson
 * @date 2022/4/30 15:48
 * @description:
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Setter(onMethod_ = @Autowired)
    private UserService userService;

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public BaseGenericsResponse<LoginInfoResponse> login(String account , String password) throws Exception {
        return userService.login(account, password);
    }

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    @GetMapping("/getUserInfo")
    @ResponseBody
    public BaseGenericsResponse<UserInfoDTO> getUserInfo(String token) throws Exception {
        return userService.getUserInfo(token);
    }

    /**
     * 修改手机号
     *
     * @param baseRequest
     * @return
     */
    @PostMapping("/modifyPhone")
    @ResponseBody
    public BaseGenericsResponse<String> modifyPhone(BaseRequest baseRequest) throws Exception {
        return userService.modifyPhone(baseRequest);
    }

    /**
     * 非登录态修改密码
     *
     * @param baseRequest
     * @return
     */
    @PostMapping("/modifyPasswordWithoutToken")
    @ResponseBody
    public BaseGenericsResponse<String> modifyPasswordWithoutToken(BaseRequest baseRequest) throws Exception {
        return userService.modifyPasswordWithoutToken(baseRequest);
    }

    /**
     * 登录态修改密码
     *
     * @param baseRequest
     * @return
     */
    @PostMapping("/modifyPassword")
    @ResponseBody
    public BaseGenericsResponse<String> modifyPassword(BaseRequest baseRequest) throws Exception {
        return userService.modifyPassword(baseRequest);
    }

    /**
     * 更改用户信息
     *
     * @return
     */
    @PostMapping("/modifyUserInfo")
    @ResponseBody
    public BaseGenericsResponse<String> modifyUserInfoById(ModifyUserInfoRequest modifyUserInfoRequest) throws Exception {
        return userService.modifyUserInfoById(modifyUserInfoRequest);
    }

    /**
     * 获取指定用户信息
     *
     * @param baseRequest
     * @return
     */
    @GetMapping("/oneInfo")
    @ResponseBody
    public BaseGenericsResponse<UserInfoDTO> oneInfo(BaseRequest baseRequest) throws Exception {
        return userService.oneInfo(baseRequest);
    }

    /**
     * 获取所有用户
     *
     * @param getAllUserListRequest
     * @return
     */
    @GetMapping("/getAllUserList")
    @ResponseBody
    public BaseGenericsResponse<UsersDTO> getAllUserList(GetAllUserListRequest getAllUserListRequest) throws Exception {
        return userService.getAllUserList(getAllUserListRequest);
    }

    /**
     * 获取角色1 用户列表
     *
     * @param token
     * @return
     */
    @GetMapping("/getRoleOnesUserList")
    @ResponseBody
    public BaseGenericsResponse<List<UserInfoDTO>> getRoleOnesUserList(String token) throws Exception {
        return userService.getRoleOnesUserList(token);
    }

    /**
     * 导入用户
     *
     * @return
     */
    @PostMapping("/importUser")
    @ResponseBody
    public BaseGenericsResponse<String> importUser(@RequestParam("file") MultipartFile file) throws Exception {
        return userService.importUser(file);
    }

    /**
     * 登出
     *
     * @return
     */
    @PostMapping("/logout")
    public BaseGenericsResponse<String> logout(BaseRequest baseRequest) {
        return userService.logout(baseRequest);
    }

    /**
     * 审批驳回
     *
     * @param auditRequest
     * @return
     */
    @PostMapping("/audit/no")
    public BaseGenericsResponse<String> reject(AuditRequest auditRequest) throws Exception {
        return userService.reject(auditRequest);
    }

    /**
     * 同意申请
     *
     * @return
     */
    @PostMapping("/audit/yes")
    public BaseGenericsResponse<String> agree(AuditRequest auditRequest) throws Exception {
        return userService.agree(auditRequest);
    }

    /**
     * 获取审批表单
     *
     * @return
     */
    @PostMapping("/audit/audit")
    public BaseGenericsResponse<AuditFormDTO> audit(AuditRequest auditRequest) throws Exception {
        return userService.audit(auditRequest);
    }

    /**
     * 按类型获取审批预览列表
     *
     * @return
     */
    @PostMapping("/audit/audits")
    public BaseGenericsResponse<List<AuditPreviewDTO>> audits(AuditPreviewRequest auditPreviewRequest) {
        return userService.audits(auditPreviewRequest);
    }

    /**
     * 标记对应通知已读
     *
     * @param token
     * @param infoId
     * @return
     */
    @PostMapping("/info/read")
    public BaseGenericsResponse<String> read(String token, String infoId) {
        return userService.read(token, infoId);
    }

    /**
     * 获取通知未读数量列表
     *
     * @param noticeCountListRequest
     * @return
     */
    @GetMapping("/info/counts")
    public BaseGenericsResponse<List<Integer>> counts(NoticeCountListRequest noticeCountListRequest) {
        return userService.counts(noticeCountListRequest);
    }

    /**
     * 按类型获取通知
     *
     * @param noticeCountListRequest
     * @return
     */
    @GetMapping("/info/infos")
    public BaseGenericsResponse<InfoDTO> infos(NoticeCountListRequest noticeCountListRequest) {
        return userService.infos(noticeCountListRequest);
    }

    /**
     * 一键标为已读
     *
     * @param token
     * @param infoIds
     * @return
     */
    @PostMapping("/info/readAll")
    public BaseGenericsResponse<String> readAll(String token, List<Long> infoIds) {
        return userService.readAll(token, infoIds);
    }
}
