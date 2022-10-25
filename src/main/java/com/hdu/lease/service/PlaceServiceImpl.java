package com.hdu.lease.service;

import com.hdu.lease.contract.PlaceAssetContract;
import com.hdu.lease.contract.PlaceContract;
import com.hdu.lease.contract.UserContract;
import com.hdu.lease.mapper.ContractMapper;
import com.hdu.lease.pojo.dto.PlaceDTO;
import com.hdu.lease.pojo.entity.Contract;
import com.hdu.lease.pojo.request.CreatePlaceRequest;
import com.hdu.lease.pojo.response.base.BaseGenericsResponse;
import com.hdu.lease.pojo.response.base.BaseResponse;
import com.hdu.lease.property.ContractProperties;
import com.hdu.lease.utils.JwtUtils;
import com.hdu.lease.utils.UuidUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 自提点实现类
 *
 * @author chenyb46701
 * @date 2022/10/15
 */
@Slf4j
@Service("placeService")
public class PlaceServiceImpl implements PlaceService{

    @Setter(onMethod_ = @Autowired)
    private ContractProperties contractProperties;

    @Setter(onMethod_ = @Autowired)
    private ContractMapper contractMapper;

    private PlaceContract placeContract;

    private UserContract userContract;

    private PlaceAssetContract placeAssetContract;

    /**
     * 调用智能合约
     */
    @PostConstruct
    private void init() {
        // 监听本地链
        Web3j web3j = Web3j.build(new HttpService(contractProperties.getHttpService()));

        // 生成资格凭证
        Credentials credentials = Credentials.create(contractProperties.getCredentials());

        StaticGasProvider provider = new StaticGasProvider(
                contractProperties.getGasPrice(),
                contractProperties.getGasLimit());

        // 取合约地址
        Contract userContractEntity = contractMapper.selectById(1);
        Contract placeContractEntity = contractMapper.selectById(2);
        Contract placeAssetContractEntity = contractMapper.selectById(4);

        // 加载合约
        userContract = UserContract.load(userContractEntity.getContractAddress(), web3j, credentials, provider);
        placeContract = PlaceContract.load(placeContractEntity.getContractAddress(), web3j, credentials, provider);
        placeAssetContract = PlaceAssetContract.load(
                placeAssetContractEntity.getContractAddress(),
                web3j,
                credentials,
                provider
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> createPlace(CreatePlaceRequest createPlaceRequest) throws Exception {
        // 生成自提点唯一标识
        String placeId = UuidUtils.createUuid();

        // 判断name和address是否重复
        List<PlaceContract.Place> placeList = placeContract.getAllPlaceList().send();
        for (int i = 0; i < placeList.size(); i++) {
            if (placeList.get(i).getPlaceName().equals(createPlaceRequest.getName())) {
                return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "自提点名重复");
            }
            if (placeList.get(i).getPlaceAddress().equals(createPlaceRequest.getAddress())) {
                return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, "自提点地址重复");
            }
        }

        // 创建自提点对象
        PlaceContract.Place place = new PlaceContract.Place(
                placeId,
                createPlaceRequest.getName(),
                createPlaceRequest.getAddress(),
                createPlaceRequest.getManagerId(),
                new BigInteger("0")
        );

        placeContract.createPlace(place).send();
        return BaseGenericsResponse.successBaseResp("创建成功");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<String> deletePlace(String token, String placeId) throws Exception {
        String account = JwtUtils.getTokenInfo(token).getClaim("account").asString();
        TransactionReceipt deletePlaceTransactionReceipt = placeContract.deletePlace(
                userContract.getContractAddress(),
                placeAssetContract.getContractAddress(),
                account,
                placeId).send();

        PlaceContract.DeleteMsgEventResponse deleteMsgEventResponse = placeContract.getDeleteMsgEvents(deletePlaceTransactionReceipt).get(0);

        int code = deleteMsgEventResponse.getCode().intValue();
        String msg = "删除成功";
        if (code == 10001) {
            msg = "角色权限不足";
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, msg);
        } else if (code == 10002) {
            msg = "自提点已绑定物资";
            return BaseGenericsResponse.failureBaseResp(BaseResponse.FAIL_STATUS, msg);
        }
        return BaseGenericsResponse.successBaseResp(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseGenericsResponse<List<PlaceDTO>> getAllPlaceList(String token) throws Exception {
        List<PlaceContract.Place> placeList = placeContract.getAllPlaceList().send();
        List<PlaceDTO> placeDTOList = new ArrayList<>();
        placeList.forEach(place -> {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setPlaceId(place.getPlaceId());
            placeDTO.setName(place.getPlaceName());
            placeDTO.setAddress(place.getPlaceAddress());
            placeDTOList.add(placeDTO);
        });

        return BaseGenericsResponse.successBaseResp(placeDTOList);
    }
}
