package com.hdu.lease.service;

import com.hdu.lease.contract.UserContract;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.dto.TokenDTO;
import com.hdu.lease.pojo.dto.UserInfoDTO;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.request.BaseRequest;
import com.hdu.lease.pojo.request.GetAllUserListRequest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

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
            return BaseGenericsResponse.failureBaseResp(2, "学号不存在");
        }

        // 校验密码
        String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(encryptPassword);
        if(!encryptPassword.equals(user.getPassword())) {
            log.info("登录密码错误");
            return BaseGenericsResponse.failureBaseResp(1, "密码错误");
        }

        // 生成token
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setRole(user.getRole().intValue());
        tokenDTO.setAccount(user.getAccount());

        LoginInfoResponse loginInfoResponse = new LoginInfoResponse();
        loginInfoResponse.setRole(user.getRole().intValue());
        loginInfoResponse.setBindPhone(user.getIsBindPhone().intValue() == 1);
        String token = JwtUtils.createToken(tokenDTO);
        loginInfoResponse.setToken(token);

        // redis中保存登录态
        redisTemplate.opsForValue().set(account, token);

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
            return BaseGenericsResponse.failureBaseResp(1, "token校验失败");
        }

        // 取出account
        String account = JwtUtils.getTokenInfo(token).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(1, "token已失效，请重新登录");
        }

        UserContract.User user = usercontract.getUserInfo(account).send();
        // 校验用户存在性
        if (user.getAccount().isEmpty()) {
            return BaseGenericsResponse.failureBaseResp(2,"学号不存在");
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
            return BaseGenericsResponse.failureBaseResp(1, "token校验失败");
        }
        // 取出account
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(1, "token已失效，请重新登录");
        }


        // 校验验证码是否正确
        if (!Objects.equals(redisTemplate.opsForValue().get(baseRequest.getPhone()), baseRequest.getCode())) {
            return BaseGenericsResponse.failureBaseResp(1, "验证码错误");
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
            return BaseGenericsResponse.failureBaseResp(1,"验证码错误");
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
            return BaseGenericsResponse.failureBaseResp(1, "token校验失败");
        }
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(1, "token已失效，请重新登录");
        }

        // 校验验证码是否正确
        if (!Objects.equals(redisTemplate.opsForValue().get(baseRequest.getPhone()), baseRequest.getCode())) {
            return BaseGenericsResponse.failureBaseResp(1, "验证码错误");
        }

        usercontract.modifyPasswordByAccount(baseRequest.getAccount(),
                DigestUtils.md5DigestAsHex(baseRequest.getPassword().getBytes())).send();

        return BaseGenericsResponse.successBaseResp("重置成功");
    }

    /**
     * {@inheritDoc}
     */
    public BaseGenericsResponse<String> modifyUserInfoById(@RequestBody ModifyUserInfoRequest modifyUserInfoRequest) throws Exception {
        // 判断角色
        if (modifyUserInfoRequest.getRole().intValue() != 2) {
            return BaseGenericsResponse.failureBaseResp(1, "权限不足");
        }

        // 校验token
        if (!JwtUtils.verifyToken(modifyUserInfoRequest.getToken())) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp(1, "token校验失败");
        }
        String account = JwtUtils.getTokenInfo(modifyUserInfoRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(1, "token已失效，请重新登录");
        }

        // 根据account获取用户信息
        UserContract.User user = usercontract.getUserInfo(modifyUserInfoRequest.getAccount()).send();
        user.setRole(modifyUserInfoRequest.getRole());
        user.setName(modifyUserInfoRequest.getName());
        user.setPhone(modifyUserInfoRequest.getPhone());

        usercontract.modifyUserInfoById(user).send();

        return BaseGenericsResponse.successBaseResp("修改成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<UserInfoDTO> oneInfo(@RequestBody BaseRequest baseRequest) throws Exception {
        // 校验token
        if (!JwtUtils.verifyToken(baseRequest.getToken())) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp(1, "token校验失败");
        }
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(1, "token已失效，请重新登录");
        }

        // 根据account获取用户信息
        UserContract.User user = usercontract.getUserInfo(baseRequest.getAccount()).send();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setRole(user.getRole());
        userInfoDTO.setUsername(user.getName());
        userInfoDTO.setAccount(user.getAccount());
        userInfoDTO.setPhone(user.getPhone());
        return BaseGenericsResponse.successBaseResp(userInfoDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<UserInfoDTO>> getAllUserList(@RequestBody GetAllUserListRequest getAllUserListRequest) throws ExecutionException, InterruptedException {
        // 校验token
        if (!JwtUtils.verifyToken(getAllUserListRequest.getToken())) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp(1, "token校验失败");
        }
        String account = JwtUtils.getTokenInfo(getAllUserListRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(1, "token已失效，请重新登录");
        }

        List<UserContract.User> userList = usercontract.getUserList(getAllUserListRequest.getFrom()).sendAsync().get();

        return BaseGenericsResponse.successBaseResp(one(userList));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<UserInfoDTO>> getRoleOnesUserList(@RequestBody BaseRequest baseRequest) throws Exception {
        // 校验token
        if (!JwtUtils.verifyToken(baseRequest.getToken())) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp(1, "token校验失败");
        }
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();
        String role = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("role").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(1, "token已失效，请重新登录");
        }

        // 判断角色
        if (!role.equals("2")) {
            return BaseGenericsResponse.failureBaseResp(1, "权限不足");
        }

        List<UserContract.User> userList = usercontract.getRoleList().send();

        return BaseGenericsResponse.successBaseResp(one(userList));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> logout(@RequestBody BaseRequest baseRequest) {
        // 校验token
        if (!JwtUtils.verifyToken(baseRequest.getToken())) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp(1, "token校验失败");
        }

        //获取account
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        // 校验token有效性
        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(1, "token已失效，请重新登录");
        }

        // 登出 删除key
        redisTemplate.delete(account);
        return BaseGenericsResponse.successBaseResp("登出成功");
    }

    /**
     * User -> UserInfoDTO list
     *
     * @return
     */
    public List<UserInfoDTO> one(List<UserContract.User> userList) {
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        userList.forEach((user ->
        {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setRole(user.getRole());
            userInfoDTO.setUsername(user.getName());
            userInfoDTO.setAccount(user.getAccount());
            userInfoDTO.setPhone(user.getPhone());
            userInfoDTOList.add(userInfoDTO);
        }));

        return userInfoDTOList;
    }
}
