package com.hdu.lease.controller;

import com.hdu.lease.pojo.request.CreateAssertRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.service.AssertService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private AssertService assertService;

    @PostMapping("/create")
    @ResponseBody
    public BaseGenericsResponse<String> create(CreateAssertRequest createAssertRequest) throws Exception {
        return assertService.create(createAssertRequest);
    }
//
//    @GetMapping("/getList")
//    @ResponseBody
//    public BaseGenericsResponse<List<>>
}
