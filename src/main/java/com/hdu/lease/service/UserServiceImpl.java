package com.hdu.lease.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hdu.lease.contract.UserContract;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.dto.TokenDTO;
import com.hdu.lease.pojo.dto.UserInfoDTO;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.request.BaseRequest;
import com.hdu.lease.pojo.request.ModifyUserInfoRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.utils.JwtUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

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

    @Setter(onMethod_ = @Autowired)
    private ContractMapper contractMapper;

    private UserContract usercontract;

    /**
     * 调用智能合约
     */
    @PostConstruct
    private void init() {
        // 监听本地链
        Web3j web3j = Web3j.build(new HttpService(contractProperties.getHttpService()));

        // 生成资格凭证
        Credentials credentials = Credentials.create(contractProperties.getCredentials());

        StaticGasProvider provider = new StaticGasProvider(
                contractProperties.getGasPrice(),
                contractProperties.getGasLimit());

        // 取合约地址
        Contract contract = contractMapper.selectById(1);

        // 加载合约
        usercontract = UserContract.load(contract.getContractAddress(), web3j, credentials, provider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<LoginInfoResponse> login(String account, String password) throws Exception {
        // 获取用户信息
        UserContract.User user = usercontract.getUserInfo(account).send();
        log.info("用户信息:{}", user);

        // 校验用户存在性
        if (user.getAccount().isEmpty()) {
            return BaseGenericsResponse.failureBaseResp("学号不存在");
        }

        // 校验密码
        String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(encryptPassword);
        if(!encryptPassword.equals(user.getPassword())) {
            log.info("登录密码错误");
            return BaseGenericsResponse.failureBaseResp("密码错误");
        }

        // 生成token
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setRole(user.getRole().intValue());
        tokenDTO.setAccount(user.getAccount());

        LoginInfoResponse loginInfoResponse = new LoginInfoResponse();
        loginInfoResponse.setRole(user.getRole().intValue());
//        loginInfoResponse.setBindPhone(user.getIsBindPhone().intValue() == 1);
        loginInfoResponse.setBindPhone(!StringUtils.isEmpty(user.getPhone()));
        loginInfoResponse.setToken(JwtUtils.createToken(tokenDTO));
        return BaseGenericsResponse.successBaseResp(loginInfoResponse);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<UserInfoDTO> getUserInfo(String token) throws Exception {
        // 校验token
        if (!JwtUtils.verifyToken(token)) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp("token校验失败");
        }

        // 取出account
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        String account = tokenInfo.getClaim("account").asString();

        UserContract.User user = usercontract.getUserInfo(account).send();
        // 校验用户存在性
        if (user.getAccount().isEmpty()) {
            return BaseGenericsResponse.failureBaseResp("学号不存在");
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(user.getName());
        userInfoDTO.setAccount(user.getAccount());
        userInfoDTO.setPhone(user.getPhone());
        userInfoDTO.setRole(user.getRole());

        return BaseGenericsResponse.successBaseResp(userInfoDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> modifyPhone(@RequestBody BaseRequest baseRequest) throws Exception {
        // 校验token
        if (!JwtUtils.verifyToken(baseRequest.getToken())) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp("token校验失败");
        }
        // 取出account
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(baseRequest.getToken());
        String account = tokenInfo.getClaim("account").asString();

        // 校验验证码是否正确
        if (!Objects.equals(redisTemplate.opsForValue().get(baseRequest.getPhone()), baseRequest.getCode())) {
            return BaseGenericsResponse.successBaseResp("验证码错误");
        }

        usercontract.modifyPhoneByAccount(account, baseRequest.getPhone()).send();
        return BaseGenericsResponse.successBaseResp("修改成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> modifyPasswordWithoutToken(@RequestBody BaseRequest baseRequest) throws Exception {
        // 校验验证码是否正确
        if (!Objects.equals(redisTemplate.opsForValue().get(baseRequest.getPhone()), baseRequest.getCode())) {
            return BaseGenericsResponse.failureBaseResp("验证码错误");
        }

        usercontract.modifyPasswordByAccount(baseRequest.getAccount(),
                DigestUtils.md5DigestAsHex(baseRequest.getPassword().getBytes())).send();
        return BaseGenericsResponse.successBaseResp("重置成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> modifyPassword(@RequestBody BaseRequest baseRequest) throws Exception {
        // 校验token
        if (!JwtUtils.verifyToken(baseRequest.getToken())) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp("token校验失败");
        }

        // 校验验证码是否正确
        if (!Objects.equals(redisTemplate.opsForValue().get(baseRequest.getPhone()), baseRequest.getCode())) {
            return BaseGenericsResponse.failureBaseResp("验证码错误");
        }

        usercontract.modifyPasswordByAccount(baseRequest.getAccount(),
                DigestUtils.md5DigestAsHex(baseRequest.getPassword().getBytes())).send();

        return BaseGenericsResponse.successBaseResp("重置成功");
    }


    /**
     * {@inheritDoc}
     */
    public BaseGenericsResponse<String> modifyUserInfoById(ModifyUserInfoRequest modifyUserInfoRequest) {
        // 判断角色
        if (modifyUserInfoRequest.getRole().intValue() != 2) {
            return BaseGenericsResponse.failureBaseResp("权限不足");
        }


        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> oneInfo(BaseRequest baseRequest) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<UserInfoDTO>> getAllUserList(BaseRequest baseRequest) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<UserInfoDTO> getRoleOnesUserList(BaseRequest baseRequest) {
        return null;
    }

}
