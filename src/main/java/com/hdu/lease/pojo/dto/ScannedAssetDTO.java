package com.hdu.lease.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 扫码物资信息DTO类
 *
 * @author chenyb46701
 * @date 2022/10/25
 */
@Setter
@Getter
@ToString
public class ScannedAssetDTO {
    /**
     * 物资图片url
     */
    private String url;

    /**
     * 物资名
     */
    private String name;

    /**
     * 价值
     */
    private int value;

    /**
     * 是否需要提交借用申请
     */
    private Boolean apply;

    /**
     * 空闲量
     */
    private Boolean isBorrow;

    /**
     * yyyy-MM-dd HH:mm:ss物资借用到期时间（若未处于借用则为空）
     */
    private String expiredTime;

    /**
     * 物资归属仓库名（若物资未被自己借用则为空）
     */
    private String place;

    /**
     * 使用者姓名（若物资正在被借用且使用者是自己则为空）
     */
    private String username;

    /**
     * 空闲量
     */
    private int free;

    /**
     * 总量
     */
    private int rest;
}
