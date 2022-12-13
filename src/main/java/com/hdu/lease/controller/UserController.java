package com.hdu.lease.controller;

import com.hdu.lease.pojo.dto.*;
import com.hdu.lease.pojo.request.*;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.service.UserService;
import com.hdu.lease.utils.QrCodeUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

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
    @ResponseBody
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
    @ResponseBody
    public BaseGenericsResponse<String> reject(AuditRequest auditRequest) throws Exception {
        return userService.reject(auditRequest);
    }

    /**
     * 同意申请
     *
     * @return
     */
    @PostMapping("/audit/yes")
    @ResponseBody
    public BaseGenericsResponse<String> agree(AuditRequest auditRequest) throws Exception {
        return userService.agree(auditRequest);
    }

    /**
     * 获取审批表单
     *
     * @return
     */
    @GetMapping("/audit/audit")
    @ResponseBody
    public BaseGenericsResponse<AuditFormDTO> audit(AuditRequest auditRequest) throws Exception {
        return userService.audit(auditRequest);
    }

    /**
     * 按类型获取审批预览列表
     *
     * @return
     */
    @GetMapping("/audit/audits")
    @ResponseBody
    public BaseGenericsResponse<List<AuditPreviewDTO>> audits(AuditPreviewRequest auditPreviewRequest) throws Exception{
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
    @ResponseBody
    public BaseGenericsResponse<String> read(String token, String infoId) throws Exception {
        return userService.read(token, infoId);
    }

    /**
     * 获取通知未读数量列表
     *
     * @param noticeCountListRequest
     * @return
     */
    @GetMapping("/info/counts")
    @ResponseBody
    public BaseGenericsResponse<List<Integer>> counts(NoticeCountListRequest noticeCountListRequest) throws Exception {
        return userService.counts(noticeCountListRequest);
    }

    /**
     * 按类型获取通知
     *
     * @param noticeInfosRequest
     * @return
     */
    @GetMapping("/info/infos")
    @ResponseBody
    public BaseGenericsResponse<List<InfoDTO>> infos(NoticeInfosRequest noticeInfosRequest) throws Exception {
        return userService.infos(noticeInfosRequest);
    }

    /**
     * 一键标为已读
     *
     * @param token
     * @param infoIds
     * @return
     */
    @PostMapping("/info/readAll")
    @ResponseBody
    public BaseGenericsResponse<String> readAll(String token, List<String> infoIds) throws Exception {
        return userService.readAll(token, infoIds);
    }

    @GetMapping("/getPlaceManager")
    @ResponseBody
    public BaseGenericsResponse<PlaceManagerDTO> getPlaceManager(String token, String placeId) throws Exception {
        return userService.getPlaceManager(token, placeId);
    }

    @GetMapping("/getNoRole2s")
    @ResponseBody
    public BaseGenericsResponse<GetNoRoleUsersDTO> getNoRole2s(String token, Integer from) throws Exception{
        return userService.getNoRole2s(token, from);
    }

    @PostMapping("/grantPlaceManager")
    @ResponseBody
    public BaseGenericsResponse<String> grantPlaceManager(GrantPlaceManagerDTO grantPlaceManagerDTO) throws Exception {
        return userService.grantPlaceManager(grantPlaceManagerDTO);
    }

    /**
     * 获取审批数目列表
     *
     * @param auditCountsRequest
     * @return
     * @throws Exception
     */
    @GetMapping("/audit/counts")
    @ResponseBody
    public BaseGenericsResponse<List<Integer>> auditCounts(AuditCountsRequest auditCountsRequest) throws Exception{
        return userService.auditCounts(auditCountsRequest);
    }

    @GetMapping(value = "/generate")
    @ResponseBody
    public void generateQR(@RequestParam("content")String content) throws IOException {
        BufferedImage image;
        String test = "底部文字" + "-" + "name" + "\r\n" + "aaaaaaa";
        System.out.println(test);
        image = QrCodeUtil.createImage(content, test, true);

        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        try
        {
            ImageIO.write(image, "png", os);
            String img = "data:image/png;base64,"+ Base64.getEncoder().encodeToString(os.toByteArray());
            System.out.println(img);
        }
        catch (final IOException ioe)
        {
            throw new UncheckedIOException(ioe);
        }

    }
}
