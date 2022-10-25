package com.hdu.lease.service;

import com.hdu.lease.pojo.dto.AssetDTO;
import com.hdu.lease.pojo.dto.PlaceDTO;
import com.hdu.lease.pojo.dto.ScannedAssetDTO;
import com.hdu.lease.pojo.request.AssetApplyRequest;
import com.hdu.lease.pojo.request.AssetBorrowRequest;
import com.hdu.lease.pojo.request.CreateAssertRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;

import java.util.List;

/**
 * 资产接口
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
public interface AssetService {

    /**
     * 创建资产
     *
     * @param createAssertRequest
     * @return
     */
    BaseGenericsResponse<String> create(CreateAssertRequest createAssertRequest) throws Exception;

    /**
     * 获取指定资产信息
     *
     * @param token
     * @param placeId
     * @return
     */
    BaseGenericsResponse<List<AssetDTO>> getList(String token, String placeId) throws Exception;

    /**
     * 收回物资
     *
     * @param token
     * @param assetId
     * @return
     */
    BaseGenericsResponse<String> back(String token, String assetId);

    /**
     * 上传图片
     *
     * @param token
     * @param picture
     * @param assetId
     * @return
     */
    BaseGenericsResponse<String> uploadPic(String token, String picture, String assetId);

    /**
     * 申请借用
     *
     * @param assetApplyRequest
     * @return
     */
    BaseGenericsResponse<String> apply(AssetApplyRequest assetApplyRequest);

    BaseGenericsResponse<String> borrow(AssetBorrowRequest assetBorrowRequest);

    BaseGenericsResponse<ScannedAssetDTO> scanned(String token, String assetId);
}