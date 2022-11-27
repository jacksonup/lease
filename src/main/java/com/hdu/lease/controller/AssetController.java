package com.hdu.lease.controller;

import com.hdu.lease.pojo.dto.AssetDTO;
import com.hdu.lease.pojo.dto.PlaceDTO;
import com.hdu.lease.pojo.dto.ScannedAssetDTO;
import com.hdu.lease.pojo.request.AssetApplyRequest;
import com.hdu.lease.pojo.request.AssetBorrowRequest;
import com.hdu.lease.pojo.request.CreateAssertRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.service.AssetService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public BaseGenericsResponse<String> create(CreateAssertRequest createAssertRequest) throws Exception {
        return assetService.create(createAssertRequest);
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
    public BaseGenericsResponse<String> uploadPic(String token, MultipartFile picture, String assetId) {
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
}
