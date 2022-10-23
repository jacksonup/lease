package com.hdu.lease.service;

import com.hdu.lease.pojo.dto.PlaceDTO;
import com.hdu.lease.pojo.request.CreatePlaceRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;

import java.util.List;

/**
 * 自提点接口
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
public interface PlaceService {

    /**
     * 创建自提点
     *
     * @param createPlaceRequest
     * @return
     */
    BaseGenericsResponse<String> createPlace(CreatePlaceRequest createPlaceRequest) throws Exception;

    /**
     * 删除自提点
     *
     * @param token
     * @param placeId
     * @return
     */
    BaseGenericsResponse<String> deletePlace(String token, String placeId) throws Exception;

    /**
     * 获取自提点列表
     *
     * @param token
     * @return
     */
    BaseGenericsResponse<List<PlaceDTO>> getAllPlaceList(String token) throws Exception;
}
