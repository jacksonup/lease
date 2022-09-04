package com.hdu.lease.repository;

import com.hdu.lease.model.entity.User;
import com.hdu.lease.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jackson
 * @date 2022/4/30 16:16
 * @description:
 */
public interface UserRepository extends BaseRepository<User, Integer> {
    /**
     * Find user by account.
     *
     * @param account
     * @return
     */
    User findByAccount(String account);

    /**
     * Find user by wxOpenId.
     *
     * @param wxOpenId
     * @return
     */
    User findByWxOpenId(String wxOpenId);

    /**
     * Update Role.
     *
     * @param user
     * @return
     */
    @Modifying
    @Transactional
    @Query("UPDATE User SET role=:#{#user.role} WHERE "
            + "id=:#{#user.id}")
    int updateRole(@Param("user") User user);

    /**
     * Update password.
     *
     * @param user
     * @return
     */
    @Modifying
    @Transactional
    @Query("UPDATE User SET password=:#{#user.password} WHERE "
            + "id=:#{#user.id}")
    int updatePassword(@Param("user") User user);

    /**
     * Update wxOpenId.
     *
     * @param user
     * @return
     */
    @Modifying
    @Transactional
    @Query("UPDATE User SET wxOpenId=:#{#user.wxOpenId} WHERE "
            + "id=:#{#user.id}")
    int updateWxOpenId(User user);

    /**
     * Update phone.
     *
     * @param user
     * @return
     */
    @Modifying
    @Transactional
    @Query("UPDATE User SET phone=:#{#user.phone}, isBindPhone=:#{#user.isBindPhone} WHERE "
            + "id=:#{#user.id}")
    int updatePhone(User user);
}
