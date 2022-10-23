package com.hdu.lease.service;

import com.hdu.lease.pojo.request.CreateAssertRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;

/**
 * 资产接口
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
public interface AssertService {

    /**
     * 创建资产
     *
     * @param createAssertRequest
     * @return
     */
    BaseGenericsResponse<String> createAssert(CreateAssertRequest createAssertRequest);
}
