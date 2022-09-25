package com.hdu.lease.mapstruct;

import org.mapstruct.InheritInverseConfiguration;

import java.util.List;

/**
 * BasicObjectConvert包含了4个基本方法，单个和集合以及反转的单个和集合
 *
 * @author Service
 * @date 2020/07/20
 */
public interface BaseObjectConvert<ENTITY, DTO> {
    /**
     * Entity转DTO
     *
     * @param entity Entity
     * @return DTO
     */
    DTO one(ENTITY entity);

    /**
     * Entity列表转DTO列表
     *
     * @param entityList Entity列表
     * @return DTO列表
     */
    List<DTO> list(List<ENTITY> entityList);

    /**
     * DTO转Entity
     *
     * @param target DTO
     * @return Entity
     */
    @InheritInverseConfiguration
    ENTITY oneInverse(DTO target);

    /**
     * DTO列表转Entity列表
     *
     * @param targetList DTO列表
     * @return Entity列表
     */
    @InheritInverseConfiguration
    List<ENTITY> listInverse(List<DTO> targetList);
}
