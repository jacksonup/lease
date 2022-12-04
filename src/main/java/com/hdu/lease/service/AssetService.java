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
     * @throws Exception
     */
    BaseGenericsResponse<String> edit(EditAssetRequest editAssetRequest) throws Exception;

    /**
     * 获取所有物资
     *
     * @param token
     * @return
     * @throws Exception
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

    /**
     * 补充物资
     *
     * @param supplyRequest
     * @throws Exception
     */
    void supply(SupplyRequest supplyRequest) throws Exception;

    /**
     * 获取待上架物资
     *
     * @param token 令牌
     * @return List<CanGroundingDTO>
     * @throws Exception 异常
     */
    BaseGenericsResponse<List<CanGroundingDTO>> canGrounding(String token) throws Exception;

    /**
     * 上架
     * <p>
     *     只能角色2上架，上架指定物资的所有已下架和空闲明细物资和指定仓库绑定
     * </p>
     *
     * @param shelfOperateRequest 上架下架操作请求类
     * @return BaseGenericsResponse<String>
     * @throws Exception 异常
     */
    BaseGenericsResponse<String> grounding(ShelfOperateRequest shelfOperateRequest) throws Exception;

    /**
     * 下架
     * <p>
     *     只有角色2能下架，且仅当明细物资均处于空闲、丢失、损坏状态才能下架
     *     将指定仓库和指定物资类别对应的明细物资解除关联
     *     同时若修改空闲状态为已下架状态，对于丢失、损坏的物资状态不变。之后再获取此物资时归属仓库就会少一个
     * </p>
     *
     * @param shelfOperateRequest 上架下架操作请求类
     * @return BaseGenericsResponse<String>
     * @throws Exception 异常
     */
    BaseGenericsResponse<String> undercarriage(ShelfOperateRequest shelfOperateRequest) throws Exception;
}
