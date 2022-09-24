package com.hdu.lease.pojo.entity;

import lombok.*;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

/**
 * @author Jackson
 * @date 2022/4/30 15:05
 * @description: User entity
 */
@Getter
@Setter
@ToString
public class User extends DynamicStruct {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否绑定手机号
     */
    private BigInteger isBindPhone;

    /**
     * 角色
     */
    private BigInteger role;

    /**
     * 是否删除
     */
    private BigInteger status;

    public User(String account, String name, String phone, String password, String salt, BigInteger role, BigInteger status) {
        super(new org.web3j.abi.datatypes.Utf8String(account),new org.web3j.abi.datatypes.Utf8String(name),new org.web3j.abi.datatypes.Utf8String(phone),new org.web3j.abi.datatypes.Utf8String(password),new org.web3j.abi.datatypes.Utf8String(salt),new org.web3j.abi.datatypes.generated.Uint256(role),new org.web3j.abi.datatypes.generated.Uint256(status));
        this.account = account;
        this.username = name;
        this.phone = phone;
        this.password = password;
        this.salt = salt;
        this.role = role;
        this.status = status;
    }

    public User(Utf8String account, Utf8String name, Utf8String phone, Utf8String password, Utf8String salt, Uint256 role, Uint256 status) {
        super(account,name,phone,password,salt,role,status);
        this.account = account.getValue();
        this.username = name.getValue();
        this.phone = phone.getValue();
        this.password = password.getValue();
        this.salt = salt.getValue();
        this.role = role.getValue();
        this.status = status.getValue();
    }

    public User() {
    }
}
