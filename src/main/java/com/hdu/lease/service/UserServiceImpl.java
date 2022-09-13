package com.hdu.lease.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hdu.lease.model.dto.TokenDTO;
import com.hdu.lease.model.entity.User;
import com.hdu.lease.model.response.BaseGenericsResponse;
import com.hdu.lease.model.response.StatusCode;
import com.hdu.lease.model.vo.LoginVO;
import com.hdu.lease.utils.JwtUtils;
import com.hdu.lease.utils.UuidUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
/**
 * @author Jackson
 * @date 2022/4/30 16:04
 * @description: User service implementation.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Manual login.
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public BaseGenericsResponse login(String account, String password) {
        User user = null;
        if (user == null) {
            return new BaseGenericsResponse(StatusCode.ACCOUNT_IS_NOT_EXIST);
        }
        String s = DigestUtils.md5Hex(password);
        if (!user.getPassword().equals(s)) {
            return new BaseGenericsResponse(StatusCode.ACCOUNT_PASSWORD_NOT_MATCH);
        }
        return new BaseGenericsResponse(StatusCode.SUCCESS, createLoginVO(user));
    }

    /**
     * Auto login.
     *
     * @param token
     * @return
     */
    @Override
    public BaseGenericsResponse autoLogin(String token) {
        if (!JwtUtils.verifyToken(token)) {
            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
        }
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        User user = null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("role", user.getRole());
        map.put("bindPhone", user.getIsBindPhone() == 1);
        return new BaseGenericsResponse(StatusCode.SUCCESS, map);
    }

    /**
     * Sso login.
     *
     * @param wxOpenId
     * @return
     */
    @Override
    public BaseGenericsResponse wxLogin(String wxOpenId) {
        User user = null;
        if (user == null) {
            return new BaseGenericsResponse(StatusCode.WX_OPEN_ID_IS_NOT_EXIST);
        }
        return new BaseGenericsResponse(StatusCode.SUCCESS, createLoginVO(user));
    }

    /**
     * Update role.
     *
     * @param token
     * @param userId
     * @param role
     * @return
     */
    @Override
    public BaseGenericsResponse updateRole(String token, Integer userId, Integer role) {
        if (!JwtUtils.verifyToken(token)) {
            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
        }
        int i = 1;
        if (i > 0) {
            return new BaseGenericsResponse(StatusCode.SUCCESS);
        }
        return new BaseGenericsResponse(StatusCode.FAIL);
    }

    /**
     * Update password.
     *
     * @param token
     * @param password
     * @return
     */
    @Override
    public BaseGenericsResponse updatePassword(String token, String password) {
        if (!JwtUtils.verifyToken(token)) {
            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
        }
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        // Encode password.
        String s = DigestUtils.md5Hex(password);
        int i = 1;
        if (i > 0) {
            return new BaseGenericsResponse(StatusCode.SUCCESS);
        }
        return new BaseGenericsResponse(StatusCode.FAIL);
    }

    /**
     * Find back the password.
     *
     * @param token
     * @param password
     * @param code
     * @return
     */
    @Override
    public BaseGenericsResponse findPassword(String token, String password, String code) {
        if (!JwtUtils.verifyToken(token)) {
            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
        }
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        User user = null;
        // Find phone number by userId.
        String phone = user.getPhone();
        String codeBefore = redisTemplate.opsForValue().get(phone);
        // if the coedBefore is empty, it means the code is overdue.
        if (StringUtils.isEmpty(codeBefore)) {
            return new BaseGenericsResponse(StatusCode.CODE_IS_OVERDUE);
        }
        // Verify code whether right.
        if (!code.equals(codeBefore)) {
            return new BaseGenericsResponse(StatusCode.CODE_IS_NOT_CORRECT);
        }
        // Update password.
        BaseGenericsResponse baseResponse = updatePassword(token, password);
        // delete the code.
        Boolean flag = redisTemplate.delete(phone);
        if (Boolean.TRUE.equals(flag)) {
            return baseResponse;
        }
        return new BaseGenericsResponse(StatusCode.FAIL);
    }

    /**
     * Update the wxOpenId.
     *
     * @param token
     * @param wxOpenId
     * @return
     */
    @Override
    public BaseGenericsResponse updateWx(String token, String wxOpenId) {
        if (!JwtUtils.verifyToken(token)) {
            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
        }
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        int i = 1;
        if (i > 0) {
            return new BaseGenericsResponse(StatusCode.SUCCESS);
        }
        return new BaseGenericsResponse(StatusCode.FAIL);
    }

    /**
     * Update the phoneNumber.
     *
     * @param token
     * @param phoneNumber
     * @param code
     * @return
     */
    @Override
    public BaseGenericsResponse updatePhone(String token, String phoneNumber, String code) {
        if (!JwtUtils.verifyToken(token)) {
            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
        }
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        User user = null;
        // Find phone number by userId.
        String phone = user.getPhone();
        String codeBefore = redisTemplate.opsForValue().get(phone);
        // if the coedBefore is empty, it means the code is overdue.
        if (StringUtils.isEmpty(codeBefore)) {
            return new BaseGenericsResponse(StatusCode.CODE_IS_OVERDUE);
        }
        // Verify code whether right.
        if (!code.equals(codeBefore)) {
            return new BaseGenericsResponse(StatusCode.CODE_IS_NOT_CORRECT);
        }
        // Update password.
        user.setPhone(phoneNumber);
        user.setIsBindPhone(1);
        int i = 1;
        if (i > 0) {
            return new BaseGenericsResponse(StatusCode.SUCCESS);
        }
        return new BaseGenericsResponse(StatusCode.FAIL);
    }

    /**
     * Create loginVO, includes token and role and bindPhone.
     *
     * @param user
     * @return
     */
    private static LoginVO createLoginVO(User user) {
        // create token
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setRole(user.getRole());
        tokenDTO.setUuid(UuidUtils.createUuid());
        tokenDTO.setUserId(user.getId());
        String token = JwtUtils.createToken(tokenDTO);
        // create baseResponse
        LoginVO loginVO = new LoginVO();
        loginVO.setRole(user.getRole());
        loginVO.setBindPhone(user.getIsBindPhone() == 1);
        loginVO.setToken(token);
        return loginVO;
    }
}
