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
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否绑定手机号1表示绑定、0表示未绑定
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


    public User(User user) {
        super(new org.web3j.abi.datatypes.Utf8String(user.getAccount()),
                new org.web3j.abi.datatypes.Utf8String(user.getUsername()),
                new org.web3j.abi.datatypes.Utf8String(user.getPhone()),
                new org.web3j.abi.datatypes.Utf8String(user.getPassword()),
                new org.web3j.abi.datatypes.generated.Uint256(user.getIsBindPhone()),
                new org.web3j.abi.datatypes.generated.Uint256(user.getRole()),
                new org.web3j.abi.datatypes.generated.Uint256(user.getStatus()));
        this.account = user.getAccount();
        this.username = user.getUsername();
        this.phone = user.getPhone();
        this.password = user.getPassword();
        this.isBindPhone = user.getIsBindPhone();
        this.role = user.getRole();
        this.status = user.getStatus();
    }

    public User(Utf8String account, Utf8String name, Utf8String phone, Utf8String password, Uint256 isBindPhone, Uint256 role, Uint256 status) {
        super(account,name,phone,password,isBindPhone,role,status);
        this.account = account.getValue();
        this.username = name.getValue();
        this.phone = phone.getValue();
        this.password = password.getValue();
        this.isBindPhone = isBindPhone.getValue();
        this.role = role.getValue();
        this.status = status.getValue();
    }

    public User() {
    }
}
