package com.hdu.lease.controller;

import com.hdu.lease.model.response.BaseGenericsResponse;
import com.hdu.lease.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jackson
 * @date 2022/4/30 15:48
 * @description:
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Manual login.
     *
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public BaseGenericsResponse login(String account , String password) {
        return userService.login(account, password);
    }

    /**
     * Auto login.
     *
     * @param token
     * @return
     */
    @PostMapping("/autoLogin/token")
    @ResponseBody
    public BaseGenericsResponse autoLogin(String token) {
        return userService.autoLogin(token);
    }

    /**
     * Sso login.
     *
     * @return
     */
    @PostMapping("/autoLogin/wx")
    @ResponseBody
    public BaseGenericsResponse wxLogin(String wxOpenId) {
        return userService.wxLogin(wxOpenId);
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
