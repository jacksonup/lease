package com.hdu.lease.service;

/**
 * @author chenyb46701
 * @date 2022/12/12
 */
public interface EventService {

    /**
     * 事件插入
     *
     * @param type
     * @param assetDetailId
     * @param placeId
     * @param operatorAccount
     * @param content
     * @throws Exception
     */
    void insert(String type, String assetDetailId, String placeId, String operatorAccount, String content) throws Exception;

    /**
     * 获取事件状态
     *
     * @param type
     * @return
     */
    String getType(String type);
}
