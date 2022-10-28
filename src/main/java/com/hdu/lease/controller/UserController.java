package com.hdu.lease.controller;

import com.hdu.lease.pojo.dto.UserInfoDTO;
import com.hdu.lease.pojo.request.BaseRequest;
import com.hdu.lease.pojo.request.GetAllUserListRequest;
import com.hdu.lease.pojo.request.ModifyUserInfoRequest;
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
    public BaseGenericsResponse<List<UserInfoDTO>> getAllUserList(GetAllUserListRequest getAllUserListRequest) throws ExecutionException, InterruptedException {
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
}
