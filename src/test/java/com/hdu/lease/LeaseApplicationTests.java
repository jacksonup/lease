package com.hdu.lease;

import com.hdu.lease.contract.*;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.service.UserService;
import com.hdu.lease.property.SmsProperties;
import com.hdu.lease.sms.SmsUtils;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Ignore
@Slf4j
class LeaseApplicationTests {
    @Setter(onMethod_ = @Autowired)
    private ContractProperties contractProperties;

    @Autowired
    private ContractMapper contractMapper;

    /**
     * 部署合约
     * ganache不关闭，可以不用重新部署；ganache 重启需部署
     * ganache-cli -d "tackle frozen poet aware struggle ridge february merge pulse doll enhance air"
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

        // 部署用户合约
        UserContract userContract = UserContract.deploy(web3j, credentials, provider).send();
        log.info("UserContract合约地址：{}", userContract.getContractAddress());
        log.info("UserContract 是否可用：{}",userContract.isValid());

        // 部署自提点合约
        PlaceContract placeContract = PlaceContract.deploy(web3j, credentials, provider).send();
        log.info("PlaceContract合约地址：{}", placeContract.getContractAddress());
        log.info("PlaceContract 是否可用：{}",placeContract.isValid());

        // 部署资产合约
        AssetContract assetContract = AssetContract.deploy(web3j, credentials, provider).send();
        log.info("AssetContract合约地址：{}", assetContract.getContractAddress());
        log.info("AssetContract 是否可用：{}",assetContract.isValid());

        // 部署自提点绑定资产合约
        PlaceAssetContract placeAssetContract = PlaceAssetContract.deploy(web3j, credentials, provider).send();
        log.info("PlaceAssetContract合约地址：{}", placeAssetContract.getContractAddress());
        log.info("PlaceAssetContract 是否可用：{}",placeAssetContract.isValid());

        // 部署资产明细
        AssetDetailContract assetDetailContract = AssetDetailContract.deploy(web3j, credentials, provider).send();
        log.info("AssetDetailContract合约地址：{}", assetDetailContract.getContractAddress());
        log.info("AssetDetailContract 是否可用：{}",assetDetailContract.isValid());

        // 部署审批合约
        AuditContract auditContract = AuditContract.deploy(web3j, credentials, provider).send();
        log.info("AuditContract合约地址：{}", auditContract.getContractAddress());
        log.info("AuditContract 是否可用：{}",auditContract.isValid());

        // 部署通知合约
        NoticeContract noticeContract = NoticeContract.deploy(web3j, credentials, provider).send();
        log.info("NoticeContract合约地址：{}", noticeContract.getContractAddress());
        log.info("NoticeContract 是否可用：{}",noticeContract.isValid());

        // 部署事件合约
        EventContract eventContract = EventContract.deploy(web3j, credentials, provider).send();
        log.info("EventContract：{}", eventContract.getContractAddress());
        log.info("EventContract 是否可用：{}",eventContract.isValid());

        // 维护合约地址
        if (userContract.isValid()) {
            Contract contract = new Contract();
            contract.setId(1);
            contract.setContractName("userContract");
            contract.setContractAddress(userContract.getContractAddress());
            contractMapper.updateById(contract);
        }
        if (placeContract.isValid()) {
            Contract contract = new Contract();
            contract.setId(2);
            contract.setContractName("placeContract");
            contract.setContractAddress(placeContract.getContractAddress());
            contractMapper.updateById(contract);
        }
        if (assetContract.isValid()) {
            Contract contract = new Contract();
            contract.setId(3);
            contract.setContractName("assetContract");
            contract.setContractAddress(assetContract.getContractAddress());
            contractMapper.updateById(contract);
        }
        if (placeAssetContract.isValid()) {
            Contract contract = new Contract();
            contract.setId(4);
            contract.setContractName("placeAssetContract");
            contract.setContractAddress(placeAssetContract.getContractAddress());
            contractMapper.updateById(contract);
        }
        if (assetDetailContract.isValid()) {
            Contract contract = new Contract();
            contract.setId(5);
            contract.setContractName("assetDetailContract");
            contract.setContractAddress(assetDetailContract.getContractAddress());
            contractMapper.updateById(contract);
        }
        if (auditContract.isValid()) {
            Contract contract = new Contract();
            contract.setId(6);
            contract.setContractName("auditContract");
            contract.setContractAddress(auditContract.getContractAddress());
            contractMapper.updateById(contract);
        }
        if (noticeContract.isValid()) {
            Contract contract = new Contract();
            contract.setId(7);
            contract.setContractName("noticeContract");
            contract.setContractAddress(noticeContract.getContractAddress());
            contractMapper.updateById(contract);
        }
        if (eventContract.isValid()) {
            Contract contract = new Contract();
            contract.setId(8);
            contract.setContractName("eventContract");
            contract.setContractAddress(eventContract.getContractAddress());
            contractMapper.updateById(contract);
        }

        // admin用户
        UserContract.User user = new UserContract.User(
                "admin",
                "admin",
                "15906888912",
                "827ccb0eea8a706c4c34a16891f84e7b",
                new BigInteger("1"),
                new BigInteger("2"),
                new BigInteger("0")
        );

        // pwd 12345
        UserContract.User jzy = new UserContract.User(
                "19052238",
                "jzy",
                "15906888912",
                "827ccb0eea8a706c4c34a16891f84e7b",
                new BigInteger("1"),
                new BigInteger("0"),
                new BigInteger("0")
        );

        // pwd 12345
        UserContract.User lyl = new UserContract.User(
                "19052239",
                "lyl",
                "18106660269",
                "827ccb0eea8a706c4c34a16891f84e7b",
                new BigInteger("1"),
                new BigInteger("0"),
                new BigInteger("0")
        );

        // pwd 12345
        UserContract.User cyb = new UserContract.User(
                "19052240",
                "cyb",
                "15906888912",
                "827ccb0eea8a706c4c34a16891f84e7b",
                new BigInteger("1"),
                new BigInteger("0"),
                new BigInteger("0")
        );
        List<UserContract.User> list = new ArrayList<>();
        list.add(user);
        list.add(cyb);
        list.add(jzy);
        list.add(lyl);
        userContract.batchAddUser(list).sendAsync().get();
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
    void test() {
        String str = "1#2#";
        System.out.println(Arrays.toString(str.split("#")));
    }
}
