package com.hdu.lease.model.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author Jackson
 * @date 2022/4/30 15:05
 * @description: User entity
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Account.
     */
    @Column(name = "account", nullable = false)
    private String account;

    /**
     * Password.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Username.
     */
    @Column(name = "username")
    private String username;

    /**
     * Phone.
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * IsBindPhone.
     */
    @Column(name = "is_bind_phone")
    private Integer isBindPhone;

    /**
     * WxOpenId.
     */
    @Column(name = "wx_open_id")
    private String wxOpenId;

    /**
     * Roll.
     */
    @Column(name = "role")
    private Integer role;

    @Override
    public void prePersist() {
        super.prePersist();

        if (phone == null) {
            phone = "";
        }

        if (isBindPhone == null) {
            isBindPhone = 0;
        }

        if (role == null) {
            role = 0;
        }
    }
}
