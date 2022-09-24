package com.hdu.lease.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hdu.lease.constant.BusinessConstant;
import com.hdu.lease.contract.UserContract;
import com.hdu.lease.exception.BaseBizException;
import com.hdu.lease.pojo.dto.TokenDTO;
import com.hdu.lease.pojo.entity.User;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.utils.JwtUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;
import javax.annotation.PostConstruct;
import java.net.BindException;

/**
 * @author Jackson
 * @date 2022/4/30 16:04
 * @description: User service implementation.
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Setter(onMethod_ = @Autowired)
    private ContractProperties contractProperties;

    private UserContract usercontract;

    @PostConstruct
    private void init() {
        // 监听本地链
        Web3j web3j = Web3j.build(new HttpService(contractProperties.getHttpService()));

        // 生成资格凭证
        Credentials credentials = Credentials.create(contractProperties.getCredentials());

        StaticGasProvider provider = new StaticGasProvider(
                contractProperties.getGasPrice(),
                contractProperties.getGasLimit());

        // 加载合约
        usercontract = UserContract.load(contractProperties.getAddress(), web3j, credentials, provider);
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public BaseGenericsResponse<LoginInfoResponse> login(String account, String password) throws Exception {
        // 获取用户信息
        User user = usercontract.getUserInfo(account).send();
        log.info("用户信息:{}", user);

        // 校验用户存在性
        if (user.getAccount().isEmpty()) {
            throw new BaseBizException("100041", "学号", account);
        }

        // 校验密码
        String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(encryptPassword);
        if(!encryptPassword.equals(user.getPassword())) {
            log.info("登录密码错误");
            throw new BaseBizException("101003");
        }

        // 生成token
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setRole(user.getRole().intValue());
        tokenDTO.setAccount(user.getAccount());

        LoginInfoResponse loginInfoResponse = new LoginInfoResponse();
        loginInfoResponse.setRole(user.getRole().intValue());
        loginInfoResponse.setBindPhone(user.getIsBindPhone().intValue() == 1);
        loginInfoResponse.setToken(JwtUtils.createToken(tokenDTO));

        return new BaseGenericsResponse<LoginInfoResponse>();

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
