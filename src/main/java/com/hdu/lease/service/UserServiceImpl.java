package com.hdu.lease.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hdu.lease.constant.BusinessConstant;
import com.hdu.lease.contract.UserContract;
import com.hdu.lease.pojo.dto.TokenDTO;
import com.hdu.lease.pojo.entity.User;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.utils.JwtUtils;
import com.hdu.lease.utils.UuidUtils;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Jackson
 * @date 2022/4/30 16:04
 * @description: User service implementation.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    private void init() throws Exception {
        // 部署合约
//        contract.load()
        List<User> user = new ArrayList<>();
        User user1 = new User("19052240","lyl","198xxxxxxx","12345","salt",new BigInteger("1"),new BigInteger("0"));
        user.add(user1);
        user1 = new User("19052241","cyb","198xxxxxxx","12345","salt",new BigInteger("1"),new BigInteger("0"));
        user.add(user1);

//        contract.batchAddUser(user).send();
    }

    /**
     * Manual login.
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public BaseGenericsResponse<LoginInfoResponse> login(String account, String password) throws ExecutionException, InterruptedException {
        // 获取用户信息
//        User user= contract.getUserInfo(account).sendAsync().get();
        System.out.println(1);
        return null;

    }

    /**
     * Auto login.
     *
     * @param token
     * @return
     */
    @Override
    public BaseGenericsResponse autoLogin(String token) {
//        if (!JwtUtils.verifyToken(token)) {
//            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
//        }
//        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
//        User user = null;
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("role", user.getRole());
//        map.put("bindPhone", user.getIsBindPhone() == 1);
//        return new BaseGenericsResponse(StatusCode.SUCCESS, map);
        return null;
    }

    /**
     * Sso login.
     *
     * @param wxOpenId
     * @return
     */
    @Override
    public BaseGenericsResponse wxLogin(String wxOpenId) {
//        User user = null;
//        if (user == null) {
//            return new BaseGenericsResponse(StatusCode.WX_OPEN_ID_IS_NOT_EXIST);
//        }
//        return new BaseGenericsResponse(StatusCode.SUCCESS, createLoginVO(user));
        return null;
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
        return null;
//        if (!JwtUtils.verifyToken(token)) {
//            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
//        }
//        int i = 1;
//        if (i > 0) {
//            return new BaseGenericsResponse(StatusCode.SUCCESS);
//        }
//        return new BaseGenericsResponse(StatusCode.FAIL);
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
        return null;
//        if (!JwtUtils.verifyToken(token)) {
//            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
//        }
//        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
//        // Encode password.
//        String s = DigestUtils.md5Hex(password);
//        int i = 1;
//        if (i > 0) {
//            return new BaseGenericsResponse(StatusCode.SUCCESS);
//        }
//        return new BaseGenericsResponse(StatusCode.FAIL);
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
        return null;
//        if (!JwtUtils.verifyToken(token)) {
//            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
//        }
//        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
//        User user = null;
//        // Find phone number by userId.
//        String phone = user.getPhone();
//        String codeBefore = redisTemplate.opsForValue().get(phone);
//        // if the coedBefore is empty, it means the code is overdue.
//        if (StringUtils.isEmpty(codeBefore)) {
//            return new BaseGenericsResponse(StatusCode.CODE_IS_OVERDUE);
//        }
//        // Verify code whether right.
//        if (!code.equals(codeBefore)) {
//            return new BaseGenericsResponse(StatusCode.CODE_IS_NOT_CORRECT);
//        }
//        // Update password.
//        BaseGenericsResponse baseResponse = updatePassword(token, password);
//        // delete the code.
//        Boolean flag = redisTemplate.delete(phone);
//        if (Boolean.TRUE.equals(flag)) {
//            return baseResponse;
//        }
//        return new BaseGenericsResponse(StatusCode.FAIL);
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
        return null;
//        if (!JwtUtils.verifyToken(token)) {
//            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
//        }
//        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
//        int i = 1;
//        if (i > 0) {
//            return new BaseGenericsResponse(StatusCode.SUCCESS);
//        }
//        return new BaseGenericsResponse(StatusCode.FAIL);
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
        return null;
//        if (!JwtUtils.verifyToken(token)) {
//            return new BaseGenericsResponse(StatusCode.TOKEN_IS_IN_VALID);
//        }
//        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
//        User user = null;
//        // Find phone number by userId.
//        String phone = user.getPhone();
//        String codeBefore = redisTemplate.opsForValue().get(phone);
//        // if the coedBefore is empty, it means the code is overdue.
//        if (StringUtils.isEmpty(codeBefore)) {
//            return new BaseGenericsResponse(StatusCode.CODE_IS_OVERDUE);
//        }
//        // Verify code whether right.
//        if (!code.equals(codeBefore)) {
//            return new BaseGenericsResponse(StatusCode.CODE_IS_NOT_CORRECT);
//        }
//        // Update password.
//        user.setPhone(phoneNumber);
//        user.setIsBindPhone(1);
//        int i = 1;
//        if (i > 0) {
//            return new BaseGenericsResponse(StatusCode.SUCCESS);
//        }
//        return new BaseGenericsResponse(StatusCode.FAIL);
    }

    /**
     * Create loginVO, includes token and role and bindPhone.
     *
     * @param user
     * @return
     */
    private static LoginInfoResponse createLoginVO(User user) {
        return null;
//        // create token
//        TokenDTO tokenDTO = new TokenDTO();
//        tokenDTO.setRole(user.getRole());
//        tokenDTO.setUuid(UuidUtils.createUuid());
//        tokenDTO.setUserId(user.getId());
//        String token = JwtUtils.createToken(tokenDTO);
//        // create baseResponse
//        LoginInfoResponse loginVO = new LoginInfoResponse();
//        loginVO.setRole(user.getRole());
//        loginVO.setBindPhone(user.getIsBindPhone() == 1);
//        loginVO.setToken(token);
//        return loginVO;
    }
}
