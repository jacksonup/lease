package com.hdu.lease;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.hdu.lease.contract.UserContract;
import com.hdu.lease.pojo.entity.User;
import com.hdu.lease.pojo.excel.UserInfo;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.service.UserService;
import com.hdu.lease.property.SmsProperties;
import com.hdu.lease.sms.SmsUtils;
import com.hdu.lease.utils.ExcelUtils;
import com.hdu.lease.utils.RandomNumberUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Ignore
@Slf4j
class LeaseApplicationTests {

    private UserService userService;

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties smsConfig;

    @Setter(onMethod_ = @Autowired)
    private ContractProperties contractProperties;

    /**
     * 部署合约
     *
     * @throws Exception
     */
    @Test
    void deployContract() throws Exception {
        // 监听本地链
        Web3j web3j = Web3j.build(new HttpService(contractProperties.getHttpService()));

        // 生成资格凭证
        Credentials credentials = Credentials.create(contractProperties.getCredentials());

        StaticGasProvider provider = new StaticGasProvider(
                contractProperties.getGasPrice(),
                contractProperties.getGasLimit());

        // 部署合约
        UserContract userContract = UserContract.deploy(web3j, credentials, provider).send();
        log.info("UserContract合约地址：{}", userContract.getContractAddress());
    }

    @Test
    void addUser() throws Exception {
        // 监听本地链
        Web3j web3j = Web3j.build(new HttpService(contractProperties.getHttpService()));

        // 生成资格凭证
        Credentials credentials = Credentials.create(contractProperties.getCredentials());

        StaticGasProvider provider = new StaticGasProvider(
                contractProperties.getGasPrice(),
                contractProperties.getGasLimit());

        // 加载合约
        UserContract usercontract = UserContract.load(contractProperties.getAddress(), web3j, credentials, provider);
//        BigInteger userId,infoId,role;
//        String account,name,salt,phone,password;
//        userId = new BigInteger("0");
//        infoId = new BigInteger("0");
//        role = new BigInteger("0");
//        account = "19052240";
//        name = "陈宇彬";
//        salt = "salt";
//        phone = "15906888912";
//        password = "827ccb0eea8a706c4c34a16891f84e7b";
//        TransactionReceipt receipt = usercontract.setUser(account,name,salt,phone,password,role).sendAsync().get();
        User user = new User("19052241","陈宇彬","15906888911","827ccb0eea8a706c4c34a16891f84e7b","salt",new BigInteger("0"),new BigInteger("0"));
//        user.setAccount("19052241");
//        user.setPhone("15906888911");
//        user.setUsername("陈宇彬");
//        user.setIsBindPhone(new BigInteger("1"));
//        user.setPassword("827ccb0eea8a706c4c34a16891f84e7b");
//        user.setRole(new BigInteger("0"));
//        user.setStatus(new BigInteger("0"));
        List<User> list = new ArrayList<>();
        list.add(user);
        usercontract.batchAddUser(list).sendAsync().get();
    }


    @Test
    void contextLoads() {
        try {
            /* 必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
             * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
             * 你也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
             * 以免泄露密钥对危及你的财产安全。
             * SecretId、SecretKey 查询: https://console.cloud.tencent.com/cam/capi */
            Credential cred = new Credential("AKID8FGc0ELTVOyOcdS9ew3x0tZ4SkqmUxBC", "vd6NTvBN4buzPehgXCdtwjKuGOIyQdcx");

            // 实例化一个http选项，可选，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            // 设置代理（无需要直接忽略）
            // httpProfile.setProxyHost("真实代理ip");
            // httpProfile.setProxyPort(真实代理端口);
            /* SDK默认使用POST方法。
             * 如果你一定要使用GET方法，可以在这里设置。GET方法无法处理一些较大的请求 */
            httpProfile.setReqMethod("POST");
            /* SDK有默认的超时时间，非必要请不要进行调整
             * 如有需要请在代码中查阅以获取最新地默认值 */
            httpProfile.setConnTimeout(60);
            /* 指定接入地域域名，默认就近地域接入域名为 sms.tencentcloudapi.com ，也支持指定地域域名访问，例如广州地域的域名为 sms.ap-guangzhou.tencentcloudapi.com */
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            /* 非必要步骤:
             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
            /* SDK默认用TC3-HMAC-SHA256进行签名
             * 非必要请不要修改这个字段 */
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化要请求产品(以sms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，支持的地域列表参考 https://cloud.tencent.com/document/api/382/52071#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8 */
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 你可以直接查询SDK源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
            SendSmsRequest req = new SendSmsRequest();

            /* 填充请求参数,这里request对象的成员变量即对应接口的入参
             * 你可以通过官网接口文档或跳转到request对象的定义处查看请求参数的定义
             * 基本类型的设置:
             * 帮助链接：
             * 短信控制台: https://console.cloud.tencent.com/smsv2
             * 腾讯云短信小助手: https://cloud.tencent.com/document/product/382/3773#.E6.8A.80.E6.9C.AF.E4.BA.A4.E6.B5.81 */

            /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666 */
            // 应用 ID 可前往 [短信控制台](https://console.cloud.tencent.com/smsv2/app-manage) 查看
            String sdkAppId = "1400671597";
            req.setSmsSdkAppId(sdkAppId);

            /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名 */
            // 签名信息可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-sign) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-sign) 的签名管理查看
            String signName = "Jackson陈公众号";
            req.setSignName(signName);

            /* 模板 ID: 必须填写已审核通过的模板 ID */
            // 模板 ID 可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-template) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-template) 的正文模板管理查看
            String templateId = "1389231";
            req.setTemplateId(templateId);

            /* 模板参数: 模板参数的个数需要与 TemplateId 对应模板的变量个数保持一致，若无模板参数，则设置为空 */
            String[] templateParamSet = {"1234", "2"};
            req.setTemplateParamSet(templateParamSet);

            /* 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号]
             * 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号 */
            String[] phoneNumberSet = {"+8615906888912"};
            req.setPhoneNumberSet(phoneNumberSet);

            /* 用户的 session 内容（无需要可忽略）: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
            String sessionContext = "";
            req.setSessionContext(sessionContext);

            /* 短信码号扩展号（无需要可忽略）: 默认未开通，如需开通请联系 [腾讯云短信小助手] */
            String extendCode = "";
            req.setExtendCode(extendCode);

            /* 国际/港澳台短信 SenderId（无需要可忽略）: 国内短信填空，默认未开通，如需开通请联系 [腾讯云短信小助手] */
            String senderid = "";
            req.setSenderId(senderid);

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);

            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(res));

            // 也可以取出单个值，你可以通过官网接口文档或跳转到response对象的定义处查看返回字段的定义
            // System.out.println(res.getRequestId());

            /* 当出现以下错误码时，快速解决方案参考
             * [FailedOperation.SignatureIncorrectOrUnapproved](https://cloud.tencent.com/document/product/382/9558#.E7.9F.AD.E4.BF.A1.E5.8F.91.E9.80.81.E6.8F.90.E7.A4.BA.EF.BC.9Afailedoperation.signatureincorrectorunapproved-.E5.A6.82.E4.BD.95.E5.A4.84.E7.90.86.EF.BC.9F)
             * [FailedOperation.TemplateIncorrectOrUnapproved](https://cloud.tencent.com/document/product/382/9558#.E7.9F.AD.E4.BF.A1.E5.8F.91.E9.80.81.E6.8F.90.E7.A4.BA.EF.BC.9Afailedoperation.templateincorrectorunapproved-.E5.A6.82.E4.BD.95.E5.A4.84.E7.90.86.EF.BC.9F)
             * [UnauthorizedOperation.SmsSdkAppIdVerifyFail](https://cloud.tencent.com/document/product/382/9558#.E7.9F.AD.E4.BF.A1.E5.8F.91.E9.80.81.E6.8F.90.E7.A4.BA.EF.BC.9Aunauthorizedoperation.smssdkappidverifyfail-.E5.A6.82.E4.BD.95.E5.A4.84.E7.90.86.EF.BC.9F)
             * [UnsupportedOperation.ContainDomesticAndInternationalPhoneNumber](https://cloud.tencent.com/document/product/382/9558#.E7.9F.AD.E4.BF.A1.E5.8F.91.E9.80.81.E6.8F.90.E7.A4.BA.EF.BC.9Aunsupportedoperation.containdomesticandinternationalphonenumber-.E5.A6.82.E4.BD.95.E5.A4.84.E7.90.86.EF.BC.9F)
             * 更多错误，可咨询[腾讯云助手](https://tccc.qcloud.com/web/im/index.html#/chat?webAppId=8fa15978f85cb41f7e2ea36920cb3ae1&title=Sms)
             */

        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testSmsConfig() {
        System.out.println(smsConfig.getSecretId());
        System.out.println(smsConfig.getSecretKey());
    }

    @Test
    void testSmsSend() {
        String[] templateParamSet = {"231113"};
        smsUtils.send(templateParamSet, "8615906888912", "1390135");
    }

    @Test
    void testRandomNumber() {
        System.out.println(RandomNumberUtils.createRandomNumber(4));
    }

    @Test
    void testJwt() {

//        User user = null;
//        // create token
//        TokenDTO tokenDTO = new TokenDTO();
//        tokenDTO.setRole(user.getRole());
//        tokenDTO.setUuid(UuidUtils.createUuid());
//        tokenDTO.setUserId(user.getId());
//        String token = JwtUtils.createToken(tokenDTO);
//        System.out.println(JwtUtils.getTokenInfo(token).getClaim("uuid").asString());
//        System.out.println(JwtUtils.getTokenInfo(token).getClaim("userId").asInt());
//        System.out.println(JwtUtils.getTokenInfo(token).getClaim("role").asInt());
    }

    @Test
    void testExcel() {
        System.out.println(ExcelUtils.getPath());
        // 匿名内部类 不用额外写一个DemoDataListener
        String fileName = ExcelUtils.getPath() +
                "com" + File.separator +
                "hdu" + File.separator +
                "lease" + File.separator +
                "excel" + File.separator +
                "userInfo.xlsx";
        System.out.println(fileName);

//         这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, UserInfo.class, new ReadListener<UserInfo>() {

            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;
            /**
             * 临时存储
             */
            private List<UserInfo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(UserInfo data, AnalysisContext context) {
                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
//                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                for (UserInfo userInfo : cachedDataList) {
                    log.info(userInfo.toString());
                    User byAccount = null;
                    if (byAccount == null) {
                        User user = new User();
                        user.setUsername(userInfo.getUsername());
                        user.setAccount(userInfo.getAccount());
                        user.setPassword(DigestUtils.md5Hex(userInfo.getPassword()));
                        user.setPhone(userInfo.getPhone());
//                        user.setIsBindPhone(StringUtils.isEmpty(userInfo.getPhone()) ? 0 : 1);
//                        User save = userRepository.save(user);
                    }
                }
                log.info("存储数据库成功！");
            }
        }).sheet().doRead();
    }

}
