package com.hdu.lease.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class AssetBorrowHistoryContract extends Contract {
    public static final String BINARY = "608060405260028054905060035534801561001957600080fd5b50611a4f806100296000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806351c494aa14610067578063670b383914610097578063989425c1146100b3578063cb6e2fa1146100e3578063ee2595d314610113578063ffe8176d1461014c575b600080fd5b610081600480360381019061007c9190611219565b610168565b60405161008e919061127d565b60405180910390f35b6100b160048036038101906100ac91906114ae565b61019e565b005b6100cd60048036038101906100c891906114f7565b61038c565b6040516100da91906115ac565b60405180910390f35b6100fd60048036038101906100f89190611219565b610438565b60405161010a9190611732565b60405180910390f35b61012d60048036038101906101289190611219565b61094c565b6040516101439a99989796959493929190611763565b60405180910390f35b610166600480360381019061016191906114ae565b610df6565b005b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b600181600001516040516101b29190611873565b908152602001604051809103902060009054906101000a900460ff166103895780600082600001516040516101e79190611873565b90815260200160405180910390206000820151816000019080519060200190610211929190610fc9565b50602082015181600101908051906020019061022e929190610fc9565b50604082015181600201908051906020019061024b929190610fc9565b506060820151816003019080519060200190610268929190610fc9565b506080820151816004019080519060200190610285929190610fc9565b5060a08201518160050190805190602001906102a2929190610fc9565b5060c0820151816006015560e08201518160070190805190602001906102c9929190610fc9565b506101008201518160080190805190602001906102e7929190610fc9565b506101208201518160090155905050600180826000015160405161030b9190611873565b908152602001604051809103902060006101000a81548160ff0219169083151502179055506002816000015190806001815401808255809150506001900390600052602060002001600090919091909150908051906020019061036f929190610fc9565b5060036000815480929190610383906118b9565b91905055505b50565b6002818154811061039c57600080fd5b9060005260206000200160009150905080546103b790611931565b80601f01602080910402602001604051908101604052809291908181526020018280546103e390611931565b80156104305780601f1061040557610100808354040283529160200191610430565b820191906000526020600020905b81548152906001019060200180831161041357829003601f168201915b505050505081565b61044061104f565b6001826040516104509190611873565b908152602001604051809103902060009054906101000a900460ff1615610946576000826040516104819190611873565b9081526020016040518091039020604051806101400160405290816000820180546104ab90611931565b80601f01602080910402602001604051908101604052809291908181526020018280546104d790611931565b80156105245780601f106104f957610100808354040283529160200191610524565b820191906000526020600020905b81548152906001019060200180831161050757829003601f168201915b5050505050815260200160018201805461053d90611931565b80601f016020809104026020016040519081016040528092919081815260200182805461056990611931565b80156105b65780601f1061058b576101008083540402835291602001916105b6565b820191906000526020600020905b81548152906001019060200180831161059957829003601f168201915b505050505081526020016002820180546105cf90611931565b80601f01602080910402602001604051908101604052809291908181526020018280546105fb90611931565b80156106485780601f1061061d57610100808354040283529160200191610648565b820191906000526020600020905b81548152906001019060200180831161062b57829003601f168201915b5050505050815260200160038201805461066190611931565b80601f016020809104026020016040519081016040528092919081815260200182805461068d90611931565b80156106da5780601f106106af576101008083540402835291602001916106da565b820191906000526020600020905b8154815290600101906020018083116106bd57829003601f168201915b505050505081526020016004820180546106f390611931565b80601f016020809104026020016040519081016040528092919081815260200182805461071f90611931565b801561076c5780601f106107415761010080835404028352916020019161076c565b820191906000526020600020905b81548152906001019060200180831161074f57829003601f168201915b5050505050815260200160058201805461078590611931565b80601f01602080910402602001604051908101604052809291908181526020018280546107b190611931565b80156107fe5780601f106107d3576101008083540402835291602001916107fe565b820191906000526020600020905b8154815290600101906020018083116107e157829003601f168201915b505050505081526020016006820154815260200160078201805461082190611931565b80601f016020809104026020016040519081016040528092919081815260200182805461084d90611931565b801561089a5780601f1061086f5761010080835404028352916020019161089a565b820191906000526020600020905b81548152906001019060200180831161087d57829003601f168201915b505050505081526020016008820180546108b390611931565b80601f01602080910402602001604051908101604052809291908181526020018280546108df90611931565b801561092c5780601f106109015761010080835404028352916020019161092c565b820191906000526020600020905b81548152906001019060200180831161090f57829003601f168201915b505050505081526020016009820154815250509050610947565b5b919050565b60008180516020810182018051848252602083016020850120818352809550505050505060009150905080600001805461098590611931565b80601f01602080910402602001604051908101604052809291908181526020018280546109b190611931565b80156109fe5780601f106109d3576101008083540402835291602001916109fe565b820191906000526020600020905b8154815290600101906020018083116109e157829003601f168201915b505050505090806001018054610a1390611931565b80601f0160208091040260200160405190810160405280929190818152602001828054610a3f90611931565b8015610a8c5780601f10610a6157610100808354040283529160200191610a8c565b820191906000526020600020905b815481529060010190602001808311610a6f57829003601f168201915b505050505090806002018054610aa190611931565b80601f0160208091040260200160405190810160405280929190818152602001828054610acd90611931565b8015610b1a5780601f10610aef57610100808354040283529160200191610b1a565b820191906000526020600020905b815481529060010190602001808311610afd57829003601f168201915b505050505090806003018054610b2f90611931565b80601f0160208091040260200160405190810160405280929190818152602001828054610b5b90611931565b8015610ba85780601f10610b7d57610100808354040283529160200191610ba8565b820191906000526020600020905b815481529060010190602001808311610b8b57829003601f168201915b505050505090806004018054610bbd90611931565b80601f0160208091040260200160405190810160405280929190818152602001828054610be990611931565b8015610c365780601f10610c0b57610100808354040283529160200191610c36565b820191906000526020600020905b815481529060010190602001808311610c1957829003601f168201915b505050505090806005018054610c4b90611931565b80601f0160208091040260200160405190810160405280929190818152602001828054610c7790611931565b8015610cc45780601f10610c9957610100808354040283529160200191610cc4565b820191906000526020600020905b815481529060010190602001808311610ca757829003601f168201915b505050505090806006015490806007018054610cdf90611931565b80601f0160208091040260200160405190810160405280929190818152602001828054610d0b90611931565b8015610d585780601f10610d2d57610100808354040283529160200191610d58565b820191906000526020600020905b815481529060010190602001808311610d3b57829003601f168201915b505050505090806008018054610d6d90611931565b80601f0160208091040260200160405190810160405280929190818152602001828054610d9990611931565b8015610de65780601f10610dbb57610100808354040283529160200191610de6565b820191906000526020600020905b815481529060010190602001808311610dc957829003601f168201915b505050505090806009015490508a565b60018160000151604051610e0a9190611873565b908152602001604051809103902060009054906101000a900460ff1615610f8c578060008260000151604051610e409190611873565b90815260200160405180910390206000820151816000019080519060200190610e6a929190610fc9565b506020820151816001019080519060200190610e87929190610fc9565b506040820151816002019080519060200190610ea4929190610fc9565b506060820151816003019080519060200190610ec1929190610fc9565b506080820151816004019080519060200190610ede929190610fc9565b5060a0820151816005019080519060200190610efb929190610fc9565b5060c0820151816006015560e0820151816007019080519060200190610f22929190610fc9565b50610100820151816008019080519060200190610f40929190610fc9565b5061012082015181600901559050507f1f0b7755550dd48676926d868a91257934b9a64d5f852cad45e85274c052c6b060c8604051610f7f91906119a8565b60405180910390a1610fc6565b7f1f0b7755550dd48676926d868a91257934b9a64d5f852cad45e85274c052c6b0612711604051610fbd91906119fe565b60405180910390a15b50565b828054610fd590611931565b90600052602060002090601f016020900481019282610ff7576000855561103e565b82601f1061101057805160ff191683800117855561103e565b8280016001018555821561103e579182015b8281111561103d578251825591602001919060010190611022565b5b50905061104b91906110a2565b5090565b604051806101400160405280606081526020016060815260200160608152602001606081526020016060815260200160608152602001600081526020016060815260200160608152602001600081525090565b5b808211156110bb5760008160009055506001016110a3565b5090565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b611126826110dd565b810181811067ffffffffffffffff82111715611145576111446110ee565b5b80604052505050565b60006111586110bf565b9050611164828261111d565b919050565b600067ffffffffffffffff821115611184576111836110ee565b5b61118d826110dd565b9050602081019050919050565b82818337600083830152505050565b60006111bc6111b784611169565b61114e565b9050828152602081018484840111156111d8576111d76110d8565b5b6111e384828561119a565b509392505050565b600082601f830112611200576111ff6110d3565b5b81356112108482602086016111a9565b91505092915050565b60006020828403121561122f5761122e6110c9565b5b600082013567ffffffffffffffff81111561124d5761124c6110ce565b5b611259848285016111eb565b91505092915050565b60008115159050919050565b61127781611262565b82525050565b6000602082019050611292600083018461126e565b92915050565b600080fd5b600080fd5b6000819050919050565b6112b5816112a2565b81146112c057600080fd5b50565b6000813590506112d2816112ac565b92915050565b600061014082840312156112ef576112ee611298565b5b6112fa61014061114e565b9050600082013567ffffffffffffffff81111561131a5761131961129d565b5b611326848285016111eb565b600083015250602082013567ffffffffffffffff81111561134a5761134961129d565b5b611356848285016111eb565b602083015250604082013567ffffffffffffffff81111561137a5761137961129d565b5b611386848285016111eb565b604083015250606082013567ffffffffffffffff8111156113aa576113a961129d565b5b6113b6848285016111eb565b606083015250608082013567ffffffffffffffff8111156113da576113d961129d565b5b6113e6848285016111eb565b60808301525060a082013567ffffffffffffffff81111561140a5761140961129d565b5b611416848285016111eb565b60a08301525060c061142a848285016112c3565b60c08301525060e082013567ffffffffffffffff81111561144e5761144d61129d565b5b61145a848285016111eb565b60e08301525061010082013567ffffffffffffffff81111561147f5761147e61129d565b5b61148b848285016111eb565b610100830152506101206114a1848285016112c3565b6101208301525092915050565b6000602082840312156114c4576114c36110c9565b5b600082013567ffffffffffffffff8111156114e2576114e16110ce565b5b6114ee848285016112d8565b91505092915050565b60006020828403121561150d5761150c6110c9565b5b600061151b848285016112c3565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b8381101561155e578082015181840152602081019050611543565b8381111561156d576000848401525b50505050565b600061157e82611524565b611588818561152f565b9350611598818560208601611540565b6115a1816110dd565b840191505092915050565b600060208201905081810360008301526115c68184611573565b905092915050565b600082825260208201905092915050565b60006115ea82611524565b6115f481856115ce565b9350611604818560208601611540565b61160d816110dd565b840191505092915050565b611621816112a2565b82525050565b600061014083016000830151848203600086015261164582826115df565b9150506020830151848203602086015261165f82826115df565b9150506040830151848203604086015261167982826115df565b9150506060830151848203606086015261169382826115df565b915050608083015184820360808601526116ad82826115df565b91505060a083015184820360a08601526116c782826115df565b91505060c08301516116dc60c0860182611618565b5060e083015184820360e08601526116f482826115df565b91505061010083015184820361010086015261171082826115df565b915050610120830151611727610120860182611618565b508091505092915050565b6000602082019050818103600083015261174c8184611627565b905092915050565b61175d816112a2565b82525050565b600061014082019050818103600083015261177e818d611573565b90508181036020830152611792818c611573565b905081810360408301526117a6818b611573565b905081810360608301526117ba818a611573565b905081810360808301526117ce8189611573565b905081810360a08301526117e28188611573565b90506117f160c0830187611754565b81810360e08301526118038186611573565b90508181036101008301526118188185611573565b9050611828610120830184611754565b9b9a5050505050505050505050565b600081905092915050565b600061184d82611524565b6118578185611837565b9350611867818560208601611540565b80840191505092915050565b600061187f8284611842565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006118c4826112a2565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8214156118f7576118f661188a565b5b600182019050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061194957607f821691505b6020821081141561195d5761195c611902565b5b50919050565b6000819050919050565b6000819050919050565b600061199261198d61198884611963565b61196d565b6112a2565b9050919050565b6119a281611977565b82525050565b60006020820190506119bd6000830184611999565b92915050565b6000819050919050565b60006119e86119e36119de846119c3565b61196d565b6112a2565b9050919050565b6119f8816119cd565b82525050565b6000602082019050611a1360008301846119ef565b9291505056fea2646970667358221220ffa960004e3883455c0d3a9d63a5c9daa8e00ea33da7709f222e4384e5db44c464736f6c634300080a0033";

    public static final String FUNC_ASSETBHINSERTED = "assetBHInserted";

    public static final String FUNC_ASSETBHKEY = "assetBHKey";

    public static final String FUNC_ASSETBHS = "assetBHs";

    public static final String FUNC_GETASSETBHBYASSETBORROWAPPLYID = "getAssetBHByAssetBorrowApplyId";

    public static final String FUNC_INSERTASSERTBORROWHISTORY = "insertAssertBorrowHistory";

    public static final String FUNC_UPDATE = "update";

    public static final Event ISUPDATESUCCESS_EVENT = new Event("isUpdateSuccess",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected AssetBorrowHistoryContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AssetBorrowHistoryContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AssetBorrowHistoryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AssetBorrowHistoryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<IsUpdateSuccessEventResponse> getIsUpdateSuccessEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ISUPDATESUCCESS_EVENT, transactionReceipt);
        ArrayList<IsUpdateSuccessEventResponse> responses = new ArrayList<IsUpdateSuccessEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            IsUpdateSuccessEventResponse typedResponse = new IsUpdateSuccessEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IsUpdateSuccessEventResponse> isUpdateSuccessEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, IsUpdateSuccessEventResponse>() {
            @Override
            public IsUpdateSuccessEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ISUPDATESUCCESS_EVENT, log);
                IsUpdateSuccessEventResponse typedResponse = new IsUpdateSuccessEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IsUpdateSuccessEventResponse> isUpdateSuccessEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISUPDATESUCCESS_EVENT));
        return isUpdateSuccessEventFlowable(filter);
    }

    public RemoteFunctionCall<Boolean> assetBHInserted(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ASSETBHINSERTED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> assetBHKey(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ASSETBHKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple10<String, String, String, String, String, String, BigInteger, String, String, BigInteger>> assetBHs(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ASSETBHS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple10<String, String, String, String, String, String, BigInteger, String, String, BigInteger>>(function,
                new Callable<Tuple10<String, String, String, String, String, String, BigInteger, String, String, BigInteger>>() {
                    @Override
                    public Tuple10<String, String, String, String, String, String, BigInteger, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<String, String, String, String, String, String, BigInteger, String, String, BigInteger>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (String) results.get(4).getValue(),
                                (String) results.get(5).getValue(),
                                (BigInteger) results.get(6).getValue(),
                                (String) results.get(7).getValue(),
                                (String) results.get(8).getValue(),
                                (BigInteger) results.get(9).getValue());
                    }
                });
    }

    public RemoteFunctionCall<AssetBorrowHistory> getAssetBHByAssetBorrowApplyId(String _assetBorrowApplyId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETASSETBHBYASSETBORROWAPPLYID,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_assetBorrowApplyId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<AssetBorrowHistory>() {}));
        return executeRemoteCallSingleValueReturn(function, AssetBorrowHistory.class);
    }

    public RemoteFunctionCall<TransactionReceipt> insertAssertBorrowHistory(AssetBorrowHistory _assetBorrowHistory) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INSERTASSERTBORROWHISTORY,
                Arrays.<Type>asList(_assetBorrowHistory),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> update(AssetBorrowHistory _assetBorrowHistory) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATE,
                Arrays.<Type>asList(_assetBorrowHistory),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static AssetBorrowHistoryContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AssetBorrowHistoryContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AssetBorrowHistoryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AssetBorrowHistoryContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AssetBorrowHistoryContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AssetBorrowHistoryContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AssetBorrowHistoryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AssetBorrowHistoryContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AssetBorrowHistoryContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AssetBorrowHistoryContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AssetBorrowHistoryContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AssetBorrowHistoryContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AssetBorrowHistoryContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AssetBorrowHistoryContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AssetBorrowHistoryContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AssetBorrowHistoryContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Getter
    @Setter
    @ToString
    public static class AssetBorrowHistory extends DynamicStruct {
        public String assetBorrowApplyId;

        public String placeAssetId;

        public String borrowerAccount;

        public String beginTime;

        public String endTime;

        public String borrowReason;

        public BigInteger reviewStatus;

        public String reviewReason;

        public String reiviewTime;

        public BigInteger currentStatus;

        public AssetBorrowHistory(String assetBorrowApplyId, String placeAssetId, String borrowerAccount, String beginTime, String endTime, String borrowReason, BigInteger reviewStatus, String reviewReason, String reiviewTime, BigInteger currentStatus) {
            super(new org.web3j.abi.datatypes.Utf8String(assetBorrowApplyId),new org.web3j.abi.datatypes.Utf8String(placeAssetId),new org.web3j.abi.datatypes.Utf8String(borrowerAccount),new org.web3j.abi.datatypes.Utf8String(beginTime),new org.web3j.abi.datatypes.Utf8String(endTime),new org.web3j.abi.datatypes.Utf8String(borrowReason),new org.web3j.abi.datatypes.generated.Uint256(reviewStatus),new org.web3j.abi.datatypes.Utf8String(reviewReason),new org.web3j.abi.datatypes.Utf8String(reiviewTime),new org.web3j.abi.datatypes.generated.Uint256(currentStatus));
            this.assetBorrowApplyId = assetBorrowApplyId;
            this.placeAssetId = placeAssetId;
            this.borrowerAccount = borrowerAccount;
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.borrowReason = borrowReason;
            this.reviewStatus = reviewStatus;
            this.reviewReason = reviewReason;
            this.reiviewTime = reiviewTime;
            this.currentStatus = currentStatus;
        }

        public AssetBorrowHistory(Utf8String assetBorrowApplyId, Utf8String placeAssetId, Utf8String borrowerAccount, Utf8String beginTime, Utf8String endTime, Utf8String borrowReason, Uint256 reviewStatus, Utf8String reviewReason, Utf8String reiviewTime, Uint256 currentStatus) {
            super(assetBorrowApplyId,placeAssetId,borrowerAccount,beginTime,endTime,borrowReason,reviewStatus,reviewReason,reiviewTime,currentStatus);
            this.assetBorrowApplyId = assetBorrowApplyId.getValue();
            this.placeAssetId = placeAssetId.getValue();
            this.borrowerAccount = borrowerAccount.getValue();
            this.beginTime = beginTime.getValue();
            this.endTime = endTime.getValue();
            this.borrowReason = borrowReason.getValue();
            this.reviewStatus = reviewStatus.getValue();
            this.reviewReason = reviewReason.getValue();
            this.reiviewTime = reiviewTime.getValue();
            this.currentStatus = currentStatus.getValue();
        }
    }

    public static class IsUpdateSuccessEventResponse extends BaseEventResponse {
        public BigInteger code;
    }
}

