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
public class User extends DynamicStruct {
    public String account;

    public String name;

    public String phone;

    public String password;

    public BigInteger isBindPhone;

    public BigInteger role;

    public BigInteger status;

    public User(String account, String name, String phone, String password, BigInteger isBindPhone, BigInteger role, BigInteger status) {
        super(new org.web3j.abi.datatypes.Utf8String(account),new org.web3j.abi.datatypes.Utf8String(name),new org.web3j.abi.datatypes.Utf8String(phone),new org.web3j.abi.datatypes.Utf8String(password),new org.web3j.abi.datatypes.generated.Uint256(isBindPhone),new org.web3j.abi.datatypes.generated.Uint256(role),new org.web3j.abi.datatypes.generated.Uint256(status));
        this.account = account;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.isBindPhone = isBindPhone;
        this.role = role;
        this.status = status;
    }

    public User(Utf8String account, Utf8String name, Utf8String phone, Utf8String password, Uint256 isBindPhone, Uint256 role, Uint256 status) {
        super(account,name,phone,password,isBindPhone,role,status);
        this.account = account.getValue();
        this.name = name.getValue();
        this.phone = phone.getValue();
        this.password = password.getValue();
        this.isBindPhone = isBindPhone.getValue();
        this.role = role.getValue();
        this.status = status.getValue();
    }

    public User() {

    }


}