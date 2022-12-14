package com.hdu.lease.service;

import com.hdu.lease.contract.*;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.dto.*;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.event.ApplyParamEvent;
import com.hdu.lease.pojo.event.AuditParamEvent;
import com.hdu.lease.pojo.request.*;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.LoginInfoResponse;
import com.hdu.lease.pojo.response.base.BaseResponse;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.utils.ExcelUtils;
import com.hdu.lease.utils.JwtUtils;
import com.tencentcloudapi.tem.v20201221.models.IngressInfo;
import jnr.ffi.annotations.In;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.StaticGasProvider;
import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Setter(onMethod_ = @Autowired)
    private EventService eventService;

    private UserContract usercontract;

    private PlaceContract placeContract;

    private AssetContract assertContract;

    private AssetDetailContract assetDetailContract;

    private AuditContract auditContract;

    private NoticeContract noticeContract;

    /**
     * ??????????????????
     */
    @PostConstruct
    private void init() {
        // ???????????????
        Web3j web3j = Web3j.build(new HttpService(contractProperties.getHttpService()));

        // ??????????????????
        Credentials credentials = Credentials.create(contractProperties.getCredentials());

        StaticGasProvider provider = new StaticGasProvider(
                contractProperties.getGasPrice(),
                contractProperties.getGasLimit());

        // ???????????????
        Contract userContractEntity = contractMapper.selectById(1);
        Contract placeContractEntity = contractMapper.selectById(2);
        Contract assetContractEntity = contractMapper.selectById(3);
        Contract assetDetailContractEntity = contractMapper.selectById(5);
        Contract auditContractEntity = contractMapper.selectById(6);
        Contract noticeContractEntity = contractMapper.selectById(7);

        // ????????????
        usercontract = UserContract.load(userContractEntity.getContractAddress(), web3j, credentials, provider);
        placeContract = PlaceContract.load(placeContractEntity.getContractAddress(), web3j, credentials, provider);
        assertContract = AssetContract.load(assetContractEntity.getContractAddress(), web3j, credentials, provider);
        auditContract = AuditContract.load(
                auditContractEntity.getContractAddress(),
                web3j,
                credentials,
                provider
        );
        assetDetailContract = AssetDetailContract.load(
                assetDetailContractEntity.getContractAddress(),
                web3j,
                credentials,
                provider
        );
        noticeContract = NoticeContract.load(
                noticeContractEntity.getContractAddress(),
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
        // ??????????????????
        UserContract.User user = usercontract.getUserInfo(account).send();
        log.info("????????????:{}", user);

        // ?????????????????????
        if (user.getAccount().isEmpty()) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.DISAPPEAR_STATUS, "???????????????");
        }

        // ????????????
        String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(encryptPassword);
        if(!encryptPassword.equals(user.getPassword())) {
            log.info("??????????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "????????????");
        }

        // ??????token
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setRole(user.getRole().intValue());
        tokenDTO.setAccount(user.getAccount());

        LoginInfoResponse loginInfoResponse = new LoginInfoResponse();
        loginInfoResponse.setRole(user.getRole().intValue());
        loginInfoResponse.setBindPhone(user.getIsBindPhone().intValue() == 1);
        String token = JwtUtils.createToken(tokenDTO);
        loginInfoResponse.setToken(token);

        // redis??????????????????
        redisTemplate.opsForValue().set(account, token);

        return BaseGenericsResponse.successBaseResp(loginInfoResponse);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<UserInfoDTO> getUserInfo(String token) throws Exception {
        // ??????token
        if (!JwtUtils.verifyToken(token)) {
            log.error("token????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token????????????");
        }

        // ??????account
        String account = JwtUtils.getTokenInfo(token).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token???????????????????????????");
        }

        UserContract.User user = usercontract.getUserInfo(account).send();
        // ?????????????????????
        if (user.getAccount().isEmpty()) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.DISAPPEAR_STATUS,"???????????????");
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
        // ??????token
        if (!JwtUtils.verifyToken(baseRequest.getToken())) {
            log.error("token????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token????????????");
        }

        // ??????account
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token???????????????????????????");
        }

        // ???????????????????????????
        if (!Objects.equals(redisTemplate.opsForValue().get(baseRequest.getPhone()), baseRequest.getCode())) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "???????????????");
        }

        usercontract.modifyPhoneByAccount(account, baseRequest.getPhone()).send();
        return BaseGenericsResponse.successBaseResp("????????????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> modifyPasswordWithoutToken(@RequestBody BaseRequest baseRequest) throws Exception {
        // ???????????????????????????
        if (!Objects.equals(redisTemplate.opsForValue().get(baseRequest.getPhone()), baseRequest.getCode())) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS,"???????????????");
        }

        usercontract.modifyPasswordByAccount(baseRequest.getAccount(),
                DigestUtils.md5DigestAsHex(baseRequest.getPassword().getBytes())).send();
        return BaseGenericsResponse.successBaseResp("????????????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> modifyPassword(@RequestBody BaseRequest baseRequest) throws Exception {
        // ??????token
        if (!JwtUtils.verifyToken(baseRequest.getToken())) {
            log.error("token????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token????????????");
        }
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token???????????????????????????");
        }

        UserContract.User user = usercontract.getUserInfo(account).send();

        // ???????????????????????????
        if (!Objects.equals(redisTemplate.opsForValue().get(user.getPhone()), baseRequest.getCode())) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "???????????????");
        }

        usercontract.modifyPasswordByAccount(user.getAccount(),
                DigestUtils.md5DigestAsHex(baseRequest.getPassword().getBytes())).send();

        return BaseGenericsResponse.successBaseResp("????????????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> modifyUserInfoById(@RequestBody ModifyUserInfoRequest modifyUserInfoRequest) throws Exception {
        // ??????token
        if (!JwtUtils.verifyToken(modifyUserInfoRequest.getToken())) {
            log.error("token????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token????????????");
        }
        String account = JwtUtils.getTokenInfo(modifyUserInfoRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token???????????????????????????");
        }

        // ????????????
        if (!judgeRole(modifyUserInfoRequest.getToken(), 2)) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "????????????");
        }

        // ??????2???????????????????????????
        if (account.equals(modifyUserInfoRequest.getAccount())) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "??????????????????????????????");
        }

        // ??????account??????????????????
        UserContract.User user = usercontract.getUserInfo(modifyUserInfoRequest.getAccount()).send();

        // ????????????
        BigInteger role = user.getRole();
        UserContract.User newUser = new UserContract.User(
                user.getAccount(),
                modifyUserInfoRequest.getName(),
                modifyUserInfoRequest.getPhone(),
                user.getPassword(),
                user.getIsBindPhone(),
                role.intValue() == 2 && user.getAccount().equals(account) ? role : modifyUserInfoRequest.getRole(),
                user.getStatus()
        );

        usercontract.modifyUserInfoById(newUser).send();

        return BaseGenericsResponse.successBaseResp("????????????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<UserInfoDTO> oneInfo(@RequestBody BaseRequest baseRequest) throws Exception {
        // ??????token
        if (!JwtUtils.verifyToken(baseRequest.getToken())) {
            log.error("token????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token????????????");
        }
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token???????????????????????????");
        }

        // ??????account??????????????????
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
        // ??????token
        if (!JwtUtils.verifyToken(getAllUserListRequest.getToken())) {
            log.error("token????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token????????????");
        }
        String account = JwtUtils.getTokenInfo(getAllUserListRequest.getToken()).getClaim("account").asString();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token???????????????????????????");
        }

        // ????????????????????????
        List<UserContract.User> userList = usercontract.getUserList(getAllUserListRequest.getFrom()).sendAsync().get();
        List<UserInfoDTO> userInfoDTOList = one(userList);

        // ??????????????????
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
        // ??????token
        if (!JwtUtils.verifyToken(token)) {
            log.error("token????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token????????????");
        }
        String account = JwtUtils.getTokenInfo(token).getClaim("account").asString();
        int role = JwtUtils.getTokenInfo(token).getClaim("role").asInt();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token???????????????????????????");
        }

        // ????????????
        if (role != 2) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "????????????");
        }

        List<UserContract.User> userList = usercontract.getRoleList().send();

        return BaseGenericsResponse.successBaseResp(one(userList));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> logout(@RequestBody BaseRequest baseRequest) {
        // ??????token
        if (!JwtUtils.verifyToken(baseRequest.getToken())) {
            log.error("token????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token????????????");
        }

        //??????account
        String account = JwtUtils.getTokenInfo(baseRequest.getToken()).getClaim("account").asString();

        // ??????token?????????
        if (Boolean.FALSE.equals(redisTemplate.hasKey(account))) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token???????????????????????????");
        }

        // ?????? ??????key
        redisTemplate.delete(account);
        return BaseGenericsResponse.successBaseResp("????????????");
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
     * ???????????????
     *
     * @param token
     * @param roleIds
     * @return
     * @throws Exception
     */
    @Override
    public Boolean judgeRoles(String token, int...roleIds) throws Exception {
        String account = JwtUtils.getTokenInfo(token).getClaim("account").asString();
        UserContract.User user = usercontract.getUserInfo(account).send();
        if (user == null) {
            return false;
        }

        for (int i = 0; i < roleIds.length; i++) {
            if (user.getRole().intValue() == roleIds[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> importUser(MultipartFile file) throws Exception {
        List<Map<Integer, List<String>>> infoList = ExcelUtils.readExcel(file);
        List<UserContract.User> userList = new ArrayList<>();
        HashSet<String> userSet = new HashSet<>();
        // ????????????

        for (Map<Integer, List<String>> map : infoList) {
            for (Integer i : map.keySet()) {
                // ??????????????????
                List<String> list = map.get(i);
                if (list.size() != 3) {
                    return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "??????????????????????????????????????????");
                }
                // ??????????????????
                String account = list.get(0);
                String username = list.get(1);
                String phone = list.get(2);
                String password = DigestUtils.md5DigestAsHex(account.getBytes(StandardCharsets.UTF_8));
                log.info("?????????{}, ????????????{}, ????????????{}", account, username, phone);
                // ?????????????????????????????????
                if (userSet.contains(account)) {
                    return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "???????????????");
                }
                userSet.add(account);

                // ????????????????????????????????????
                try {
                    UserContract.User userInfo = usercontract.getUserInfo(account).send();
                    if (!userInfo.getAccount().equals("")) {
                        return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "???????????????");
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
        return BaseGenericsResponse.successBaseResp("????????????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> reject(AuditRequest auditRequest) throws Exception {
        String account = JwtUtils.getTokenInfo(auditRequest.getToken()).getClaim("account").asString();
        log.info("?????????????????????...");
        // ???????????????
        if (StringUtils.isEmpty(auditRequest.getAuditId())) {
            log.error("??????????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "??????????????????");
        }

        Tuple2<AuditContract.Audit, AuditContract.AuditBeginEndTime> audits = auditContract.getAuditByPrimaryKey(auditRequest.getAuditId()).send();

        AuditContract.Audit audit = audits.getValue1();
        AuditContract.AuditBeginEndTime auditBeginEndTime = audits.getValue2();

        if (ObjectUtils.isEmpty(audit)) {
            log.error("??????????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "??????????????????");
        }

        String auditAccount = JwtUtils.getTokenInfo(auditRequest.getToken()).getClaim("account").asString();
        if (StringUtils.isEmpty(auditAccount)) {
            log.error("token??????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token??????");
        }

        // ??????????????????ID
        String assetDetailIds = audit.getReviewReason();

        if (StringUtils.isEmpty(assetDetailIds)) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "???????????????????????????????????????");
        }
        String[] split = assetDetailIds.split("#");
        log.info("????????????ID{}", Arrays.toString(split));

        String blankStr = "";
        // ????????????????????????
        for (String assetDetailId : split) {
            AssetDetailContract.AssetDetail assetDetail = assetDetailContract.getByPrimaryKey(assetDetailId).send();

            // ????????????????????????
            AssetDetailContract.AssetDetail newAssetDetail = new AssetDetailContract.AssetDetail(
                    assetDetail.getAssetDetailId(),
                    blankStr,
                    assetDetail.getAssetId(),
                    assetDetail.getPlaceId(),
                    blankStr,
                    blankStr,
                    new BigInteger("0"),
                    new BigInteger("0")
            );

            assetDetailContract.update(newAssetDetail).send();

            // ??????
            AuditParamEvent auditParamEvent = new AuditParamEvent();
            auditParamEvent.setAuditId(auditRequest.getAuditId());
            auditParamEvent.setManageName(usercontract.getUserInfo(account).send().getName());
            auditParamEvent.setType(2);
            auditParamEvent.setUserName(usercontract.getUserInfo(audit.getBorrowerAccount()).send().getName());

            eventService.insert("9",
                    assetDetail.getAssetDetailId(),
                    assetDetail.getPlaceId(),
                    account,
                    auditParamEvent.toString()
            );
        }



        // ??????????????????
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        AuditContract.Audit newAudit = new AuditContract.Audit(
                audit.getAuditId(),
                audit.getAssetId(),
                audit.getPlaceId(),
                audit.getBorrowerAccount(),
                audit.getBorrowReason(),
                audit.getCount(),
                auditAccount,
                new BigInteger("3"),
                auditRequest.getDenyReason(),
                audit.getApplyTime(),
                new BigInteger(localDateTime.format(formatter))
        );

        AuditContract.AuditBeginEndTime newAuditBeginEndTime = new AuditContract.AuditBeginEndTime(
                auditBeginEndTime.getAuditId(),
                auditBeginEndTime.getBeginTime(),
                auditBeginEndTime.getEndTime()
        );

        TransactionReceipt transactionReceipt = auditContract.updateAuditInfo(newAudit, newAuditBeginEndTime).send();
        int code = auditContract.getIsUpdateSuccessEvents(transactionReceipt).get(0).code.intValue();
        // ????????????????????????...
        if (code == 10001) {
            log.error("??????????????? ????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "??????????????????");
        }

        return BaseGenericsResponse.successBaseResp("????????????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> agree(AuditRequest auditRequest) throws Exception {
        String account = JwtUtils.getTokenInfo(auditRequest.getToken()).getClaim("account").asString();
        // ???????????????
        if (StringUtils.isEmpty(auditRequest.getAuditId())) {
            log.error("??????????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "??????????????????");
        }

        Tuple2<AuditContract.Audit, AuditContract.AuditBeginEndTime> audits = auditContract.getAuditByPrimaryKey(auditRequest.getAuditId()).send();

        AuditContract.Audit audit = audits.getValue1();
        AuditContract.AuditBeginEndTime auditBeginEndTime = audits.getValue2();

        if (ObjectUtils.isEmpty(audit)) {
            log.error("??????????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "??????????????????");
        }

        String auditAccount = JwtUtils.getTokenInfo(auditRequest.getToken()).getClaim("account").asString();
        if (StringUtils.isEmpty(auditAccount)) {
            log.error("token??????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "token??????");
        }

        // ??????????????????ID
        String assetDetailIds = audit.getReviewReason();

        if (StringUtils.isEmpty(assetDetailIds)) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "???????????????????????????????????????");
        }
        String[] split = assetDetailIds.split("#");
        log.info("????????????ID{}", Arrays.toString(split));

        // ????????????????????????
        for (String assetDetailId : split) {
            AssetDetailContract.AssetDetail assetDetail = assetDetailContract.getByPrimaryKey(assetDetailId).send();

            // ????????????????????????
            AssetDetailContract.AssetDetail newAssetDetail = new AssetDetailContract.AssetDetail(
                    assetDetail.getAssetDetailId(),
                    audit.getBorrowerAccount(),
                    assetDetail.getAssetId(),
                    assetDetail.getPlaceId(),
                    String.valueOf(auditBeginEndTime.getBeginTime()),
                    String.valueOf(auditBeginEndTime.getEndTime()),
                    new BigInteger("1"),
                    new BigInteger("0")
            );

            assetDetailContract.update(newAssetDetail).send();

            // ??????
            AuditParamEvent auditParamEvent = new AuditParamEvent();
            auditParamEvent.setAuditId(auditRequest.getAuditId());
            auditParamEvent.setManageName(usercontract.getUserInfo(account).send().getName());
            auditParamEvent.setType(1);
            auditParamEvent.setUserName(usercontract.getUserInfo(audit.getBorrowerAccount()).send().getName());

            eventService.insert("9",
                    assetDetail.getAssetDetailId(),
                    assetDetail.getPlaceId(),
                    account,
                    auditParamEvent.toString()
            );
        }

        // ??????????????????
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        AuditContract.Audit newAudit = new AuditContract.Audit(
                audit.getAuditId(),
                audit.getAssetId(),
                audit.getPlaceId(),
                audit.getBorrowerAccount(),
                audit.getBorrowReason(),
                audit.getCount(),
                auditAccount,
                new BigInteger("2"),
                audit.getReviewReason(),
                audit.getApplyTime(),
                new BigInteger(localDateTime.format(formatter))
        );

        AuditContract.AuditBeginEndTime newAuditBeginEndTime = new AuditContract.AuditBeginEndTime(
                auditBeginEndTime.getAuditId(),
                auditBeginEndTime.getBeginTime(),
                auditBeginEndTime.getEndTime()
        );

        TransactionReceipt transactionReceipt = auditContract.updateAuditInfo(newAudit, newAuditBeginEndTime).send();
        int code = auditContract.getIsUpdateSuccessEvents(transactionReceipt).get(0).code.intValue();
        // ????????????????????????...
        if (code == 10001) {
            log.error("??????????????? ????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "??????????????????");
        }

        return BaseGenericsResponse.successBaseResp("????????????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<AuditFormDTO> audit(AuditRequest auditRequest) throws Exception {
        // ???????????????
        if (StringUtils.isEmpty(auditRequest.getAuditId())) {
            log.error("??????????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "??????????????????");
        }

        Tuple2<AuditContract.Audit, AuditContract.AuditBeginEndTime> audits = auditContract.getAuditByPrimaryKey(auditRequest.getAuditId()).send();

        AuditContract.Audit audit = audits.getValue1();
        AuditContract.AuditBeginEndTime auditBeginEndTime = audits.getValue2();

        if (ObjectUtils.isEmpty(audit)) {
            log.error("??????????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "??????????????????");
        }

        int status = audit.getReviewStatus().intValue();
        AuditFormDTO auditFormDTO = new AuditFormDTO();
        auditFormDTO.setStatus(status);

        // ?????????????????????
        UserContract.User auditUser = usercontract.getUserInfo(audit.getAuditAccount()).send();
        auditFormDTO.setApplyName(auditUser.getName());

        // ?????????????????????
        UserContract.User applyUser = usercontract.getUserInfo(audit.getBorrowerAccount()).send();
        auditFormDTO.setApplyName(applyUser.getName());
        auditFormDTO.setApplyNum(applyUser.getAccount());
        auditFormDTO.setAssetCount(audit.getCount().intValue());

        // ??????????????????
        AssetContract.Asset asset = assertContract.getById(audit.getAssetId()).send();
        auditFormDTO.setAssetName(asset.getAssetName());
        auditFormDTO.setAssetValue(asset.getPrice().doubleValue());

        auditFormDTO.setTimeRange(auditBeginEndTime.getBeginTime() + " ~ " + auditBeginEndTime.getEndTime());
        auditFormDTO.setApplyReason(audit.getBorrowReason());

        // ??????????????????
        if (status == 3) {
            auditFormDTO.setDenyReason(audit.getReviewReason());
        }

        return BaseGenericsResponse.successBaseResp(auditFormDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<AuditPreviewDTO>> audits(AuditPreviewRequest auditPreviewRequest) throws Exception {
        // ????????????
        if (!judgeRole(auditPreviewRequest.getToken(), 2)) {
            log.info("????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "????????????");
        }

        // ???????????????
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedBeginDateTime = LocalDate.parse(auditPreviewRequest.getFrom(), formatter);
        LocalDate parsedEndDateTime = LocalDate.parse(auditPreviewRequest.getTo(), formatter);

        formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        BigInteger beginTime = new BigInteger(parsedBeginDateTime.format(formatter) + "000000");
        BigInteger endTime = new BigInteger(parsedEndDateTime.format(formatter) + "000000");

        List<BigInteger> typeList = new ArrayList<>();

        String type = auditPreviewRequest.getType();
        switch (type) {
            case "all" : typeList.add(new BigInteger("-1")); break;
            case "unAudit" : typeList.add(new BigInteger("1")); break;
            case "pass" : typeList.add(new BigInteger("2")); break;
            case "deny" : typeList.add(new BigInteger("3")); break;
            default: typeList.add(new BigInteger("0"));
        }

        // ??????????????????
        List<AuditContract.AuditDTO> auditDTOList = auditContract.getListByTypeAndTime(
                auditPreviewRequest.getTimeRange(),
                beginTime,
                endTime,
                typeList,
                usercontract.getContractAddress(),
                assertContract.getContractAddress()).send();

        List<AuditPreviewDTO> auditPreviewDTOList = new ArrayList<>();

        for (AuditContract.AuditDTO auditDTO : auditDTOList) {
            AuditPreviewDTO auditPreviewDTO = new AuditPreviewDTO();
            auditPreviewDTO.setAuditId(auditDTO.getAuditId());
            auditPreviewDTO.setStatus(auditDTO.getReviewStatus().intValue());
            auditPreviewDTO.setHead(auditDTO.getAssetName() + "???" + auditDTO.getBorrowerName());

            // ???????????????
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDate localDate = LocalDate.parse(auditPreviewRequest.getFrom(), dateTimeFormatter);

            dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            auditPreviewDTO.setTime(localDate.format(dateTimeFormatter));
            auditPreviewDTOList.add(auditPreviewDTO);
        }
         return BaseGenericsResponse.successBaseResp(auditPreviewDTOList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> read(String token, String infoId) throws Exception{
        NoticeContract.Notice notice = noticeContract.getById(infoId).send();
        NoticeContract.Notice newNotice = new NoticeContract.Notice(
                notice.getId(),
                notice.getNoticeType(),
                notice.getNoticerAccount(),
                notice.getCreateTime(),
                true,
                notice.getContent()
        );

        noticeContract.update(newNotice).send();

        return BaseGenericsResponse.successBaseResp("????????????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<Integer>> counts(NoticeCountListRequest noticeCountListRequest) throws Exception {
        // ???????????????
        LocalDate localDate = LocalDate.parse(noticeCountListRequest.getFrom());
        String beginTime = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        log.info("???????????????{}", beginTime);

        localDate = LocalDate.parse(noticeCountListRequest.getTo());
        String endTime = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        log.info("???????????????{}", endTime);

        List<Integer> list = new ArrayList<>();
        // ??????????????????counts
        NoticeContract.NoticeCountsDTO noticeCountsDTO = noticeContract.getTypeCounts(
                noticeCountListRequest.getTimeRange(),
                new BigInteger(beginTime),
                new BigInteger(endTime),
                false
        ).send();

        if (ObjectUtils.isEmpty(noticeCountsDTO)) {
            list = Stream.of(0, 0, 0, 0, 0, 0).collect(Collectors.toList());
            return BaseGenericsResponse.successBaseResp(list);
        }

        List<NoticeContract.Notice> noticeList = noticeContract.getListByTimeAndTypeOrIsRead(
                noticeCountListRequest.getTimeRange(),
                new BigInteger(beginTime),
                new BigInteger(endTime),
                new BigInteger("-1"),
                true,
                false
        ).send();

        // ????????????
        list.add(noticeList.size());

        // ????????????
        list.add(noticeCountsDTO.getAllCounts().intValue());

        // ?????????????????????
        list.add(noticeCountsDTO.getBorrowCounts().intValue());

        // ?????????????????????
        list.add(noticeCountsDTO.getAuditCounts().intValue());

        // ??????????????????????????????
        list.add(noticeCountsDTO.getPlaceManagerCounts().intValue());

        // ?????????????????????
        list.add(noticeCountsDTO.getNormalCounts().intValue());

        return BaseGenericsResponse.successBaseResp(list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<InfoDTO>> infos(NoticeInfosRequest noticeInfosRequest) throws Exception {
        // ???????????????
        LocalDate localDate = LocalDate.parse(noticeInfosRequest.getFrom());
        String beginTime = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        log.info("???????????????{}", beginTime);

        localDate = LocalDate.parse(noticeInfosRequest.getTo());
        String endTime = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        log.info("???????????????{}", endTime);

        String noticeType = noticeInfosRequest.getType();
        boolean isRead = false;
        boolean isType = true;

        switch (noticeType) {
            case "all" : noticeType = "-1"; break;
            case "unread" : noticeType = "0"; isRead = true; isType = false; break;
            case "borrow" : noticeType = "1"; break;
            case "audit" : noticeType = "2"; break;
            case "general" : noticeType = "3"; break;
            case "placeManager" : noticeType = "4"; break;
        }

        List<NoticeContract.Notice> noticeList = noticeContract.getListByTimeAndTypeOrIsRead(
                noticeInfosRequest.getTimeRange(),
                new BigInteger(beginTime),
                new BigInteger(endTime),
                new BigInteger(noticeType),
                isType,
                isRead
        ).send();

        List<InfoDTO> infoDTOList = new ArrayList<>();

        for (NoticeContract.Notice notice : noticeList) {
            InfoDTO infoDTO = new InfoDTO();
            infoDTO.setInfoId(notice.getId());
            infoDTO.setRead(notice.getIsRead());

            HashMap<String, String> map = new HashMap<>(6);

            // ???????????????
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime parsedBeginDateTime = LocalDateTime.parse(String.valueOf(notice.getCreateTime()), formatter);
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = parsedBeginDateTime.format(formatter);

            map.put(time, notice.getContent());
            infoDTO.setContent(map);
            infoDTOList.add(infoDTO);
        }
        return BaseGenericsResponse.successBaseResp(infoDTOList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> readAll(String token, List<String> infoIds) throws Exception {
        noticeContract.readAll(infoIds).send();
        return BaseGenericsResponse.successBaseResp("??????????????????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<PlaceManagerDTO> getPlaceManager(String token, String placeId) throws Exception{
        // ????????????
        if (!judgeRole(token, 2)) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "????????????");
        }

        PlaceManagerDTO placeManagerDTO = new PlaceManagerDTO();
        PlaceContract.Place place = placeContract.getById(placeId).send();
        placeManagerDTO.setAccount(place.getPlaceManagerAccount());

        UserContract.User user = usercontract.getUserInfo(place.getPlaceManagerAccount()).send();
        placeManagerDTO.setUsername(user.getName());

        return BaseGenericsResponse.successBaseResp(placeManagerDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<GetNoRoleUsersDTO> getNoRole2s(String token, Integer from) throws Exception {
        if (!judgeRole(token, 2)) {
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "????????????");
        }

        List<BigInteger> list = Stream.of(
                new BigInteger("0"),
                new BigInteger("1")).collect(Collectors.toList());
        int end = from + 9;

        List<UserContract.User> userList = usercontract.getListByRoleIdPage(list,
                new BigInteger(String.valueOf(from.intValue())),
                new BigInteger(String.valueOf(end))).send();

        GetNoRoleUsersDTO getNoRoleUsersDTO = new GetNoRoleUsersDTO();
        List<GetNoRoleUserInfoDTO> getNoRoleUserInfoDTOList = new ArrayList<>();

        // ????????????????????????
        getNoRoleUsersDTO.setCount(usercontract.count().send().intValue());

        if (CollectionUtils.isEmpty(userList)) {
            getNoRoleUsersDTO.setGetNoRoleUserInfoDTOList(getNoRoleUserInfoDTOList);
            return BaseGenericsResponse.successBaseResp(getNoRoleUsersDTO);
        }

        for (UserContract.User user : userList) {
            GetNoRoleUserInfoDTO getNoRoleUserInfoDTO = new GetNoRoleUserInfoDTO();
            getNoRoleUserInfoDTO.setRole(user.getRole());
            getNoRoleUserInfoDTO.setAccount(user.getAccount());
            getNoRoleUserInfoDTO.setName(user.getName());
            getNoRoleUserInfoDTO.setPhone(user.getPhone());
            getNoRoleUserInfoDTOList.add(getNoRoleUserInfoDTO);
        }

        getNoRoleUsersDTO.setGetNoRoleUserInfoDTOList(getNoRoleUserInfoDTOList);

        return BaseGenericsResponse.successBaseResp(getNoRoleUsersDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> grantPlaceManager(GrantPlaceManagerDTO grantPlaceManagerDTO) throws Exception {
        // ????????????
        if (!judgeRole(grantPlaceManagerDTO.getToken(), 2)) {
            log.info("????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "????????????");
        }

        boolean flag = false;
        String msg = "";

        // ??????
        if (grantPlaceManagerDTO.getGrant()) {
            msg = "????????????";
            flag = true;
        } else {
            // ??????
            List<BigInteger> list = Stream.of(new BigInteger("1"), new BigInteger("2")).collect(Collectors.toList());
            List<AssetDetailContract.AssetDetail> assetDetailList = assetDetailContract.getListByStatusAndPlaceId(list, grantPlaceManagerDTO.getPlaceId()).send();

            // ????????????????????????????????????????????????????????????
            if (CollectionUtils.isEmpty(assetDetailList) || assetDetailList.size() == 0) {
                flag = true;
                msg = "????????????";
            }
        }

        if (flag) {
            PlaceContract.Place place = placeContract.getById(grantPlaceManagerDTO.getPlaceId()).send();
            PlaceContract.Place newPlace = new PlaceContract.Place(
                    place.getPlaceId(),
                    place.getPlaceName(),
                    place.getPlaceAddress(),
                    grantPlaceManagerDTO.getAccount(),
                    new BigInteger("0")
            );

            // ??????
            placeContract.update(newPlace).send();
        }
        return BaseGenericsResponse.successBaseResp(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<Integer>> auditCounts(AuditCountsRequest auditCountsRequest) throws Exception {
        // ????????????
        if (!judgeRole(auditCountsRequest.getToken(), 2)) {
            log.info("????????????");
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "????????????");
        }

        // ???????????????
        LocalDate localDate = LocalDate.parse(auditCountsRequest.getFrom());
        String beginTime = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        log.info("???????????????{}", beginTime);

        localDate = LocalDate.parse(auditCountsRequest.getTo());
        String endTime = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        log.info("???????????????{}", endTime);

        Tuple2<List<AuditContract.Audit>, List<AuditContract.AuditBeginEndTime>> auditLists = auditContract.getListByTime(auditCountsRequest.getTimeRange(),
                new BigInteger(beginTime),
                new BigInteger(endTime)).send();

        List<AuditContract.Audit> auditList = auditLists.getValue1();

        List<Integer> countsList = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>(6);
        if (CollectionUtils.isEmpty(countsList) || auditList.size() == 0) {
            countsList = Stream.of(0, 0, 0, 0).collect(Collectors.toList());
            return BaseGenericsResponse.successBaseResp(countsList);
        }

        // ????????????
        countsList.add(auditList.size());

        for (AuditContract.Audit audit : auditList) {
            int statusCode = audit.getReviewStatus().intValue();
            if (map.containsKey(statusCode)) {
                map.put(statusCode, map.getOrDefault(statusCode, 0) + 1);
            } else {
                map.put(statusCode, 0);
            }
        }

        // ???????????????
        countsList.add(map.getOrDefault(1, 0));

        // ???????????????
        countsList.add(map.getOrDefault(2, 0));

        // ???????????????
        countsList.add(map.getOrDefault(3, 0));

        return BaseGenericsResponse.successBaseResp(countsList);
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
