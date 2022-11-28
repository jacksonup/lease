package com.hdu.lease.service;

import com.hdu.lease.contract.AssetContract;
import com.hdu.lease.contract.AuditContract;
import com.hdu.lease.contract.UserContract;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.dto.*;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.request.*;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.pojo.response.base.BaseResponse;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.utils.ExcelUtils;
import com.hdu.lease.utils.JwtUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;
import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    private AssetContract assertContract;

    private AuditContract auditContract;

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
        Contract userContractEntity = contractMapper.selectById(1);
        Contract assetContractEntity = contractMapper.selectById(3);
        Contract auditContractEntity = contractMapper.selectById(6);

        // 加载合约
        usercontract = UserContract.load(userContractEntity.getContractAddress(), web3j, credentials, provider);
        assertContract = AssetContract.load(assetContractEntity.getContractAddress(), web3j, credentials, provider);
        auditContract = AuditContract.load(
                auditContractEntity.getContractAddress(),
                web3j,
                credentials,
                provider
        );

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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.DISAPPEAR_STATUS, "学号不存在");
        }

        // 校验密码
        String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(encryptPassword);
        if(!encryptPassword.equals(user.getPassword())) {
            log.info("登录密码错误");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "密码错误");
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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token校验失败");
        }

        // 取出account
        String account = JwtUtils.getTokenInfo(token).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token已失效，请重新登录");
        }

        UserContract.User user = usercontract.getUserInfo(account).send();
        // 校验用户存在性
        if (user.getAccount().isEmpty()) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.DISAPPEAR_STATUS,"学号不存在");
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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token校验失败");
        }
        // 取出account
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token已失效，请重新登录");
        }


        // 校验验证码是否正确
        if (!Objects.equals(redisTemplate.opsForValue().get(baseRequest.getPhone()), baseRequest.getCode())) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "验证码错误");
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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS,"验证码错误");
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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token校验失败");
        }
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token已失效，请重新登录");
        }

        UserContract.User user = usercontract.getUserInfo(account).send();

        // 校验验证码是否正确
        if (!Objects.equals(redisTemplate.opsForValue().get(user.getPhone()), baseRequest.getCode())) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "验证码错误");
        }

        usercontract.modifyPasswordByAccount(user.getAccount(),
                DigestUtils.md5DigestAsHex(baseRequest.getPassword().getBytes())).send();

        return BaseGenericsResponse.successBaseResp("重置成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> modifyUserInfoById(@RequestBody ModifyUserInfoRequest modifyUserInfoRequest) throws Exception {
        // 校验token
        if (!JwtUtils.verifyToken(modifyUserInfoRequest.getToken())) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token校验失败");
        }
        String account = JwtUtils.getTokenInfo(modifyUserInfoRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token已失效，请重新登录");
        }

        // 判断角色
        if (!judgeRole(modifyUserInfoRequest.getToken(), 2)) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
        }

        // 角色2不能修改自己的角色
        if (account.equals(modifyUserInfoRequest.getAccount())) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "不允许修改自己的信息");
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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token校验失败");
        }
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token已失效，请重新登录");
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
    public BaseGenericsResponse<UsersDTO> getAllUserList(@RequestBody GetAllUserListRequest getAllUserListRequest) throws Exception {
        // 校验token
        if (!JwtUtils.verifyToken(getAllUserListRequest.getToken())) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token校验失败");
        }
        String account = JwtUtils.getTokenInfo(getAllUserListRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token已失效，请重新登录");
        }

        // 获取用户信息列表
        List<UserContract.User> userList = usercontract.getUserList(getAllUserListRequest.getFrom()).sendAsync().get();
        List<UserInfoDTO> userInfoDTOList = one(userList);

        // 获取用户总数
        int count = usercontract.count().send().intValue();

        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setCount(count);
        usersDTO.setUserInfoDTOList(userInfoDTOList);
        return BaseGenericsResponse.successBaseResp(usersDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<UserInfoDTO>> getRoleOnesUserList(String token) throws Exception {
        // 校验token
        if (!JwtUtils.verifyToken(token)) {
            log.error("token校验失败");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token校验失败");
        }
        String account = JwtUtils.getTokenInfo(token).getClaim("account").asString();
        int role = JwtUtils.getTokenInfo(token).getClaim("role").asInt();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token已失效，请重新登录");
        }

        // 判断角色
        if (role != 2) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "权限不足");
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
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token校验失败");
        }

        //获取account
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        // 校验token有效性
        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token已失效，请重新登录");
        }

        // 登出 删除key
        redisTemplate.delete(account);
        return BaseGenericsResponse.successBaseResp("登出成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean judgeRole(String token, int roleId) throws Exception {
        String account = JwtUtils.getTokenInfo(token).getClaim("account").asString();
        UserContract.User user = usercontract.getUserInfo(account).send();
        if (user == null) {
            return false;
        }
        return user.getRole().intValue() == roleId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> importUser(MultipartFile file) throws Exception {
        List<Map<Integer, List<String>>> infoList = ExcelUtils.readExcel(file);
        List<UserContract.User> userList = new ArrayList<>();
        HashSet<String> userSet = new HashSet<>();
        // 批量插入

        for (Map<Integer, List<String>> map : infoList) {
            for (Integer i : map.keySet()) {
                // 获取列的信息
                List<String> list = map.get(i);
                if (list.size() != 3) {
                    return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "请输入内容，单元格不允许为空");
                }
                // 生成用户信息
                String account = list.get(0);
                String username = list.get(1);
                String phone = list.get(2);
                String password = DigestUtils.md5DigestAsHex(account.getBytes(StandardCharsets.UTF_8));
                log.info("学号：{}, 用户名：{}, 手机号：{}", account, username, phone);
                // 校验插入的用户是否重复
                if (userSet.contains(account)) {
                    return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "用户已存在");
                }
                userSet.add(account);

                // 校验和链上的信息是否重复
                try {
                    UserContract.User userInfo = usercontract.getUserInfo(account).send();
                    if (!userInfo.getAccount().equals("")) {
                        return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "用户已存在");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                UserContract.User user = new UserContract.User(
                        account,
                        username,
                        phone,
                        password,
                        new BigInteger("1"),
                        new BigInteger("0"),
                        new BigInteger("0")
                );

                userList.add(user);
            }
        }
        usercontract.batchAddUser(userList).send();
        return BaseGenericsResponse.successBaseResp("导入成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> reject(AuditRequest auditRequest) throws Exception {
        log.info("驳回申请流程中...");
        // 获取审批单
        if (StringUtils.isEmpty(auditRequest.getAuditId())) {
            log.error("审批单号为空");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "审批单号为空");
        }

        AuditContract.Audit audit = auditContract.getAuditByPrimaryKey(auditRequest.getAuditId()).send();
        if (ObjectUtils.isEmpty(audit)) {
            log.error("审批单号错误");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "审批单号错误");
        }

        // TODO 增加字段审核人
        String auditAccount = JwtUtils.getTokenInfo(auditRequest.getToken()).getClaim("account").asString();
        if (StringUtils.isEmpty(auditAccount)) {
            log.error("token失效");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token失效");
        }
        audit.setAuditAccount(auditAccount);
        audit.setReviewStatus(new BigInteger("3"));
        audit.setReviewReason(auditRequest.getDenyReason());

        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        audit.setReviewTime(localDateTime.format(formatter));


        TransactionReceipt transactionReceipt = auditContract.updateAuditInfo(audit).send();
        int code = auditContract.getIsUpdateSuccessEvents(transactionReceipt).get(0).code.intValue();
        // 这个判断实际无效...
        if (code == 10001) {
            log.error("未找到对象 更新失败");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "审批单号为空");
        }

        return BaseGenericsResponse.successBaseResp("更新成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> agree(AuditRequest auditRequest) throws Exception {
        // 获取审批单
        if (StringUtils.isEmpty(auditRequest.getAuditId())) {
            log.error("审批单号为空");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "审批单号为空");
        }

        AuditContract.Audit audit = auditContract.getAuditByPrimaryKey(auditRequest.getAuditId()).send();
        if (ObjectUtils.isEmpty(audit)) {
            log.error("审批单号错误");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "审批单号错误");
        }

        // TODO 增加字段审核人
        String auditAccount = JwtUtils.getTokenInfo(auditRequest.getToken()).getClaim("account").asString();
        if (StringUtils.isEmpty(auditAccount)) {
            log.error("token失效");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token失效");
        }
//        audit.setAuditAccount(auditAccount);
        audit.setReviewStatus(new BigInteger("3"));
        audit.setReviewReason("审批通过");

        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        audit.setReviewTime(localDateTime.format(formatter));


        TransactionReceipt transactionReceipt = auditContract.updateAuditInfo(audit).send();
        int code = auditContract.getIsUpdateSuccessEvents(transactionReceipt).get(0).code.intValue();
        // 这个判断实际无效...
        if (code == 10001) {
            log.error("未找到对象 更新失败");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "审批单号为空");
        }

        return BaseGenericsResponse.successBaseResp("更新成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<AuditFormDTO> audit(AuditRequest auditRequest) throws Exception {
        // 获取审批单
        if (StringUtils.isEmpty(auditRequest.getAuditId())) {
            log.error("审批单号为空");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "审批单号为空");
        }

        AuditContract.Audit audit = auditContract.getAuditByPrimaryKey(auditRequest.getAuditId()).send();
        if (ObjectUtils.isEmpty(audit)) {
            log.error("审批单号错误");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "审批单号错误");
        }

        int status = audit.getReviewStatus().intValue();
        AuditFormDTO auditFormDTO = new AuditFormDTO();
        auditFormDTO.setStatus(status);

        // 获取审批者姓名
        UserContract.User auditUser = usercontract.getUserInfo(audit.getAuditAccount()).send();
        auditFormDTO.setApplyName(auditUser.getName());

        // 获取借用者姓名
        UserContract.User applyUser = usercontract.getUserInfo(audit.getBorrowerAccount()).send();
        auditFormDTO.setApplyName(applyUser.getName());
        auditFormDTO.setApplyNum(applyUser.getAccount());
        auditFormDTO.setAssetCount(audit.getCount().intValue());

        // 获取资产信息
        AssetContract.Asset asset = assertContract.getById(audit.getAssetId()).send();
        auditFormDTO.setAssetName(asset.getAssetName());
        auditFormDTO.setAssetValue(asset.getPrice().doubleValue());

        auditFormDTO.setTimeRange(audit.getBeginTime() + " ~ " + audit.getEndTime());
        auditFormDTO.setApplyReason(audit.getBorrowReason());

        // 设置驳回理由
        if (status == 3) {
            auditFormDTO.setDenyReason(audit.getReviewReason());
        }

        return BaseGenericsResponse.successBaseResp(auditFormDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<AuditPreviewDTO>> audits(AuditPreviewRequest auditPreviewRequest) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> read(String token, String infoId) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<Integer>> counts(NoticeCountListRequest noticeCountListRequest) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<InfoDTO> infos(NoticeCountListRequest noticeCountListRequest) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> readAll(String token, List<Long> infoIds) {
        return null;
    }

    /**
     * User -> UserInfoDTO list
     *
     * @return
     */
    private List<UserInfoDTO> one(List<UserContract.User> userList) {
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        userList.forEach((user ->
        {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserId(user.getAccount());
            userInfoDTO.setRole(user.getRole());
            userInfoDTO.setUsername(user.getName());
            userInfoDTO.setAccount(user.getAccount());
            userInfoDTO.setPhone(user.getPhone());
            userInfoDTOList.add(userInfoDTO);
        }));

        return userInfoDTOList;
    }
}
