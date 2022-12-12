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
    private Integer value;

    /**
     * 是否需要提交借用申请
     */
    private Boolean apply;

    /**
     * 物资当前状态
     */
    private Integer status;

    /**
     * yyyy-MM-dd HH:mm:ss物资借用到期时间（若未处于借用则为空）
     */
    private String expiredTime;

    /**
     * 使用者姓名（若物资正在被借用且使用者是自己则为空）
     */
    private String username;

    /**
     * 使用/申请者电话（若物资处于被借用或申请，且发起者是自己则填充，反之为空）
     */
    private String phone;

    /**
     * 仓库名（若有归属仓库）
     */
    private String place;

    /**
     * 仓库管理员姓名（若有归属仓库）
     */
    private String placeManager;

    /**
     * 仓库管理员电话（若有归属仓库）
     */
    private String managerPhone;
}
