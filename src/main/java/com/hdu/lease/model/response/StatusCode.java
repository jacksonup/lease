package com.hdu.lease.model.response;

/**
 * @author Jackson
 * @date 2022/4/30 15:59
 * @description:
 */
public enum StatusCode {
    SUCCESS(200,"成功"),
    FAIL(-1,"失败"),

    WX_OPEN_ID_IS_NOT_EXIST(403, "微信id未绑定"),

    ACCOUNT_IS_NOT_EXIST(1001, "账号不存在"),

    ACCOUNT_PASSWORD_NOT_MATCH(1002,"密码错误!"),

    TOKEN_IS_IN_VALID(1003,"登陆凭证无效!"),

    CODE_IS_EXIST(1004, "验证码已存在请勿重复发送"),

    CODE_IS_OVERDUE(1005, "验证码已过期"),

    CODE_IS_NOT_CORRECT(1006, "验证码错误");


    private Integer code;
    private String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
