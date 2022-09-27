package com.hdu.lease.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 维护部署的合约地址
 *
 * @author chenyb46701
 * @date 2022/9/27
 */
@Setter
@Getter
@ToString
public class Contract {

    private int id;

    private String ContractAddress;
}
