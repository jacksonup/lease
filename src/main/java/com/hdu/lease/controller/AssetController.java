package com.hdu.lease.controller;

import com.hdu.lease.pojo.dto.*;
import com.hdu.lease.pojo.request.*;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.service.AssetService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 资产控制器
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
@RestController
@RequestMapping("asset")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssetController {

    @Setter(onMethod_ = @Autowired)
    private AssetService assetService;

    @PostMapping("/create")
    @ResponseBody
    public BaseGenericsResponse<Map<String, List<String>>> create(CreateAssertRequest createAssertRequest) throws Exception {
        return assetService.create(createAssertRequest);
    }

    @GetMapping("/info")
    @ResponseBody
    public BaseGenericsResponse<AssetInfoDTO> info(String token, String assetId) throws Exception {
        return assetService.info(token, assetId);
    }

    @GetMapping("/getList")
    @ResponseBody
    public BaseGenericsResponse<List<AssetDTO>> getList(String token, String placeId) throws Exception {
        return assetService.getList(token, placeId);
    }

    @PostMapping("/back")
    @ResponseBody
    public BaseGenericsResponse<String> back(String token, String assetId) throws Exception {
        return assetService.back(token, assetId);
    }

    @PostMapping("/uploadPic")
    @ResponseBody
    public BaseGenericsResponse<String> uploadPic(@RequestParam("file") MultipartFile picture,String token, String assetId) throws Exception {
        return assetService.uploadPic(token, picture, assetId);
    }

    @PostMapping("/apply")
    @ResponseBody
    public BaseGenericsResponse<String> apply(AssetApplyRequest assetApplyRequest) throws Exception {
        return assetService.apply(assetApplyRequest);
    }

    @PostMapping("/borrow")
    @ResponseBody
    public BaseGenericsResponse<String> borrow(AssetBorrowRequest assetBorrowRequest) {
        return assetService.borrow(assetBorrowRequest);
    }

    @GetMapping("/scanned")
    @ResponseBody
    public BaseGenericsResponse<ScannedAssetDTO> scanned(String token, String assetId) throws Exception {
        return assetService.scanned(token, assetId);
    }

    @PostMapping("/edit")
    @ResponseBody
    public BaseGenericsResponse<String> edit(EditAssetRequest editAssetRequest) throws Exception {
        return assetService.edit(editAssetRequest);
    }

    @GetMapping("/all")
    @ResponseBody
    public BaseGenericsResponse<List<AssetsDTO>> all(String token) throws Exception {
        return assetService.all(token);
    }

    @GetMapping("/details")
    @ResponseBody
    public BaseGenericsResponse<DetailsDTO> details(DetailsRequest detailsRequest) throws Exception {
        return assetService.details(detailsRequest);
    }

    @PostMapping("/updateStatus")
    @ResponseBody
    public BaseGenericsResponse<String> updateStatus(UpdateStatusRequest updateStatusRequest) throws Exception{
        return assetService.updateStatus(updateStatusRequest);
    }

    @PostMapping("/supply")
    @ResponseBody
    public void supply(SupplyRequest supplyRequest) throws Exception {
        assetService.supply(supplyRequest);
    }

    /**
     * 获取待上架物资
     *
     * @param token 令牌
     * @return List<CanGroundingDTO>
     * @throws Exception 异常
     */
    @GetMapping("/canGrounding")
    @ResponseBody
    public BaseGenericsResponse<List<CanGroundingDTO>> canGrounding(String token) throws Exception {
        return assetService.canGrounding(token);
    }

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
    @PostMapping("/grounding")
    @ResponseBody
    public BaseGenericsResponse<String> grounding(ShelfOperateRequest shelfOperateRequest) throws Exception {
        return assetService.grounding(shelfOperateRequest);
    }

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
    @PostMapping("/undercarriage")
    @ResponseBody
    public BaseGenericsResponse<String> undercarriage(ShelfOperateRequest shelfOperateRequest) throws Exception {
        return assetService.undercarriage(shelfOperateRequest);
    }


}
