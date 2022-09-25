package com.hdu.lease.controller;

import com.hdu.lease.pojo.dto.UserInfoDTO;
import com.hdu.lease.pojo.request.BaseRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * Update role.
     *
     * @param token
     * @return
     */
    @PostMapping("/updateRole")
    @ResponseBody
    public BaseGenericsResponse updateRole(String token, Integer userId, Integer role) {
        return userService.updateRole(token, userId, role);
    }

    /**
     * Update password.
     *
     * @param token
     * @param password
     * @return
     */
    @PostMapping("/updatePassword")
    @ResponseBody
    public BaseGenericsResponse updatePassword(String token, String password) {
        return userService.updatePassword(token, password);
    }

    /**
     * Find back the password.
     *
     * @param token 
     * @param password
     * @param code
     * @return
     */
    @PostMapping("/findPassword")
    @ResponseBody
    public BaseGenericsResponse findPassword(String token, String password, String code) {
        return userService.findPassword(token, password, code);
    }

    /**
     * Update the wxOpenId.
     *
     * @param token
     * @param wxOpenId
     * @return
     */
    @PostMapping("/updateWx")
    @ResponseBody
    public BaseGenericsResponse updateWx(String token, String wxOpenId) {
        return userService.updateWx(token, wxOpenId);
    }

    /**
     * Update the phone number.
     *
     * @param token
     * @param phoneNumber
     * @param code
     * @return
     */
    @PostMapping("/updatePhone")
    @ResponseBody
    public BaseGenericsResponse updatePhone(String token, String phoneNumber, String code) {
        return userService.updatePhone(token, phoneNumber, code);
    }
}
