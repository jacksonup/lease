package com.hdu.lease.bussiness;

import com.hdu.lease.constant.BusinessConstant;
import com.hdu.lease.contract.UserContract;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class UserContractBusiness {
    String address;
    static Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));
    static String pk = BusinessConstant.PK;
    static Credentials credentials = Credentials.create(pk);
    static StaticGasProvider provider = new StaticGasProvider(new BigInteger("20000000000"),new BigInteger("6721975"));
    static UserContract contract;

    static {
        try {
            contract = UserContract.deploy(web3j,credentials,provider).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;

    static {
        try {
            contract = UserContract.deploy(web3j,credentials,provider).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String modifyPasswordByAccount(String account,String password) throws Exception {
        if(contract.isValid()){
            if(contract.userInserted(account).send()){
                TransactionReceipt receipt = contract.modifyPasswordByAccount(account,password).send();
                return "10000";
            }
        }
        return "10001";
    }

    public String setUser(String userId, String infoId, String account, String name, String salt, String phone, String password, String role) throws Exception {
        if(contract.isValid()) {
            if (!contract.userInserted(account).send()) {
                TransactionReceipt receipt = contract.setUser(new BigInteger(userId), new BigInteger(infoId), account, name, salt, phone, password, new BigInteger(role)).send();
                return "10000";
            }
        }
        return "10001";
    }

    public String modifyPhoneByAccount(String account,String phone) throws Exception {
        if(contract.isValid()){
            if(contract.userInserted(account).send()){
                TransactionReceipt receipt = contract.modifyPhoneByAccount(account,phone).send();
                return "10000";
            }
        }
        return "10001";
    }}
