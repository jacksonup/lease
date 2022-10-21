package com.hdu.lease.controller;

import com.hdu.lease.pojo.dto.PlaceDTO;
import com.hdu.lease.pojo.request.CreatePlaceRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.service.PlaceService;
import com.hdu.lease.service.PlaceServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 自提点控制器
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
@RestController
@RequestMapping("place")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PlaceController {

    @Setter(onMethod_ = @Autowired)
    private PlaceService placeService;

    /**
     * 创建自提点
     *
     * @param createPlaceRequest
     * @return
     */
    @PostMapping("create")
    @ResponseBody
    public BaseGenericsResponse<String> createPlace(CreatePlaceRequest createPlaceRequest) throws Exception {
        return placeService.createPlace(createPlaceRequest);
    }

    /**
     * 删除自提点
     *
     * @param token
     * @param placeId
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public BaseGenericsResponse<String> deletePlace(String token, String placeId) throws Exception {
        return placeService.deletePlace(token, placeId);
    }

    /**
     * 获取自提点
     *
     * @param token
     * @return
     * @throws Exception
     */
    @GetMapping("getAllPlaceList")
    @ResponseBody
    public BaseGenericsResponse<List<PlaceDTO>> getAllPlaceList(String token) throws Exception {
        return placeService.getAllPlaceList(token);
    }
}
