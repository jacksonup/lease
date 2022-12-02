package com.hdu.lease.service;

import com.hdu.lease.pojo.dto.*;
import com.hdu.lease.pojo.request.*;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
    BaseGenericsResponse<Map<String, List<String>>> create(CreateAssertRequest createAssertRequest) throws Exception;

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
    BaseGenericsResponse<String> back(String token, String assetId) throws Exception;

    /**
     * 上传图片
     *
     * @param token
     * @param picture
     * @param assetId
     * @return
     */
    BaseGenericsResponse<String> uploadPic(String token, MultipartFile picture, String assetId) throws Exception;

    /**
     * 申请借用
     *
     * @param assetApplyRequest
     * @return
     */
    BaseGenericsResponse<String> apply(AssetApplyRequest assetApplyRequest) throws Exception;

    /**
     * 立即借用
     *
     * @param assetBorrowRequest
     * @return
     */
    BaseGenericsResponse<String> borrow(AssetBorrowRequest assetBorrowRequest);

    /**
     * 获取扫码物资信息
     *
     * @param token
     * @param assetId
     * @return
     */
    BaseGenericsResponse<ScannedAssetDTO> scanned(String token, String assetId) throws Exception;

    /**
     * 编辑物资信息
     *
     * @param editAssetRequest
     * @return
     */
    BaseGenericsResponse<String> edit(EditAssetRequest editAssetRequest) throws Exception;

    /**
     * 获取所有物资
     *
     * @param token
     * @return
     */
    BaseGenericsResponse<List<AssetsDTO>> all(String token) throws Exception;

    /**
     * 获取指定物资信息
     *
     * @param token
     * @param assetId
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<AssetInfoDTO> info(String token, String assetId) throws Exception;

    /**
     * 手动修改明细物资状态
     *
     * @param updateStatusRequest
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<String> updateStatus(UpdateStatusRequest updateStatusRequest) throws Exception;

    /**
     * 分页获取指定明细物资列表
     *
     * @param detailsRequest
     * @return
     * @throws Exception
     */
    BaseGenericsResponse<DetailsDTO> details(DetailsRequest detailsRequest) throws Exception;
}
