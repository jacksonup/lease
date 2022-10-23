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
import org.web3j.abi.datatypes.DynamicArray;
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
import org.web3j.tuples.generated.Tuple7;
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
public class AssetContract extends Contract {
    public static final String BINARY = "608060405260028054905060035534801561001957600080fd5b5061195a806100296000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c8063232ad707146100675780632d4f4337146100975780632e3e1924146100b35780635fd1c06f146100e35780637e3dc02414610113578063ce34b82914610149575b600080fd5b610081600480360381019061007c9190610d72565b610165565b60405161008e9190610dd6565b60405180910390f35b6100b160048036038101906100ac9190610f81565b61019b565b005b6100cd60048036038101906100c89190611028565b610333565b6040516100da919061129d565b60405180910390f35b6100fd60048036038101906100f891906112bf565b610714565b60405161010a9190611336565b60405180910390f35b61012d60048036038101906101289190610d72565b6107c0565b6040516101409796959493929190611367565b60405180910390f35b610163600480360381019061015e91906113f2565b610a45565b005b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b600181600001516040516101af91906114a6565b908152602001604051809103902060009054906101000a900460ff166103305780600082600001516040516101e491906114a6565b9081526020016040518091039020600082015181600001908051906020019061020e929190610b36565b50602082015181600101908051906020019061022b929190610b36565b5060408201518160020160006101000a81548160ff0219169083151502179055506060820151816003019080519060200190610268929190610b36565b506080820151816004019080519060200190610285929190610b36565b5060a0820151816005015560c0820151816006015590505060018082600001516040516102b291906114a6565b908152602001604051809103902060006101000a81548160ff02191690831515021790555060028160000151908060018154018082558091505060019003906000526020600020016000909190919091509080519060200190610316929190610b36565b506003600081548092919061032a906114ec565b91905055505b50565b606060008373ffffffffffffffffffffffffffffffffffffffff1663e31bcfca846040518263ffffffff1660e01b81526004016103709190611336565b600060405180830381865afa15801561038d573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f820116820180604052508101906103b6919061168b565b90506000815167ffffffffffffffff8111156103d5576103d4610c47565b5b60405190808252806020026020018201604052801561040e57816020015b6103fb610bbc565b8152602001906001900390816103f35790505b50905060005b8251811015610708576000838281518110610432576104316116d4565b5b602002602001015160405161044791906114a6565b90815260200160405180910390206040518060e001604052908160008201805461047090611732565b80601f016020809104026020016040519081016040528092919081815260200182805461049c90611732565b80156104e95780601f106104be576101008083540402835291602001916104e9565b820191906000526020600020905b8154815290600101906020018083116104cc57829003601f168201915b5050505050815260200160018201805461050290611732565b80601f016020809104026020016040519081016040528092919081815260200182805461052e90611732565b801561057b5780601f106105505761010080835404028352916020019161057b565b820191906000526020600020905b81548152906001019060200180831161055e57829003601f168201915b505050505081526020016002820160009054906101000a900460ff161515151581526020016003820180546105af90611732565b80601f01602080910402602001604051908101604052809291908181526020018280546105db90611732565b80156106285780601f106105fd57610100808354040283529160200191610628565b820191906000526020600020905b81548152906001019060200180831161060b57829003601f168201915b5050505050815260200160048201805461064190611732565b80601f016020809104026020016040519081016040528092919081815260200182805461066d90611732565b80156106ba5780601f1061068f576101008083540402835291602001916106ba565b820191906000526020600020905b81548152906001019060200180831161069d57829003601f168201915b50505050508152602001600582015481526020016006820154815250508282815181106106ea576106e96116d4565b5b60200260200101819052508080610700906114ec565b915050610414565b50809250505092915050565b6002818154811061072457600080fd5b90600052602060002001600091509050805461073f90611732565b80601f016020809104026020016040519081016040528092919081815260200182805461076b90611732565b80156107b85780601f1061078d576101008083540402835291602001916107b8565b820191906000526020600020905b81548152906001019060200180831161079b57829003601f168201915b505050505081565b6000818051602081018201805184825260208301602085012081835280955050505050506000915090508060000180546107f990611732565b80601f016020809104026020016040519081016040528092919081815260200182805461082590611732565b80156108725780601f1061084757610100808354040283529160200191610872565b820191906000526020600020905b81548152906001019060200180831161085557829003601f168201915b50505050509080600101805461088790611732565b80601f01602080910402602001604051908101604052809291908181526020018280546108b390611732565b80156109005780601f106108d557610100808354040283529160200191610900565b820191906000526020600020905b8154815290600101906020018083116108e357829003601f168201915b5050505050908060020160009054906101000a900460ff169080600301805461092890611732565b80601f016020809104026020016040519081016040528092919081815260200182805461095490611732565b80156109a15780601f10610976576101008083540402835291602001916109a1565b820191906000526020600020905b81548152906001019060200180831161098457829003601f168201915b5050505050908060040180546109b690611732565b80601f01602080910402602001604051908101604052809291908181526020018280546109e290611732565b8015610a2f5780601f10610a0457610100808354040283529160200191610a2f565b820191906000526020600020905b815481529060010190602001808311610a1257829003601f168201915b5050505050908060050154908060060154905087565b6000600183604051610a5791906114a6565b908152602001604051809103902060009054906101000a900460ff1615610ab3576001905081600084604051610a8d91906114a6565b90815260200160405180910390206003019080519060200190610ab1929190610b36565b505b8015610af7577f39859739613dd11b0e780ae98a04d89e5c2230793b95ff01e96432f260367536612710604051610aea919061181b565b60405180910390a1610b31565b7f39859739613dd11b0e780ae98a04d89e5c2230793b95ff01e96432f260367536612711604051610b2891906118f6565b60405180910390a15b505050565b828054610b4290611732565b90600052602060002090601f016020900481019282610b645760008555610bab565b82601f10610b7d57805160ff1916838001178555610bab565b82800160010185558215610bab579182015b82811115610baa578251825591602001919060010190610b8f565b5b509050610bb89190610bfb565b5090565b6040518060e001604052806060815260200160608152602001600015158152602001606081526020016060815260200160008152602001600081525090565b5b80821115610c14576000816000905550600101610bfc565b5090565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610c7f82610c36565b810181811067ffffffffffffffff82111715610c9e57610c9d610c47565b5b80604052505050565b6000610cb1610c18565b9050610cbd8282610c76565b919050565b600067ffffffffffffffff821115610cdd57610cdc610c47565b5b610ce682610c36565b9050602081019050919050565b82818337600083830152505050565b6000610d15610d1084610cc2565b610ca7565b905082815260208101848484011115610d3157610d30610c31565b5b610d3c848285610cf3565b509392505050565b600082601f830112610d5957610d58610c2c565b5b8135610d69848260208601610d02565b91505092915050565b600060208284031215610d8857610d87610c22565b5b600082013567ffffffffffffffff811115610da657610da5610c27565b5b610db284828501610d44565b91505092915050565b60008115159050919050565b610dd081610dbb565b82525050565b6000602082019050610deb6000830184610dc7565b92915050565b600080fd5b600080fd5b610e0481610dbb565b8114610e0f57600080fd5b50565b600081359050610e2181610dfb565b92915050565b6000819050919050565b610e3a81610e27565b8114610e4557600080fd5b50565b600081359050610e5781610e31565b92915050565b600060e08284031215610e7357610e72610df1565b5b610e7d60e0610ca7565b9050600082013567ffffffffffffffff811115610e9d57610e9c610df6565b5b610ea984828501610d44565b600083015250602082013567ffffffffffffffff811115610ecd57610ecc610df6565b5b610ed984828501610d44565b6020830152506040610eed84828501610e12565b604083015250606082013567ffffffffffffffff811115610f1157610f10610df6565b5b610f1d84828501610d44565b606083015250608082013567ffffffffffffffff811115610f4157610f40610df6565b5b610f4d84828501610d44565b60808301525060a0610f6184828501610e48565b60a08301525060c0610f7584828501610e48565b60c08301525092915050565b600060208284031215610f9757610f96610c22565b5b600082013567ffffffffffffffff811115610fb557610fb4610c27565b5b610fc184828501610e5d565b91505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610ff582610fca565b9050919050565b61100581610fea565b811461101057600080fd5b50565b60008135905061102281610ffc565b92915050565b6000806040838503121561103f5761103e610c22565b5b600061104d85828601611013565b925050602083013567ffffffffffffffff81111561106e5761106d610c27565b5b61107a85828601610d44565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600081519050919050565b600082825260208201905092915050565b60005b838110156110ea5780820151818401526020810190506110cf565b838111156110f9576000848401525b50505050565b600061110a826110b0565b61111481856110bb565b93506111248185602086016110cc565b61112d81610c36565b840191505092915050565b61114181610dbb565b82525050565b61115081610e27565b82525050565b600060e083016000830151848203600086015261117382826110ff565b9150506020830151848203602086015261118d82826110ff565b91505060408301516111a26040860182611138565b50606083015184820360608601526111ba82826110ff565b915050608083015184820360808601526111d482826110ff565b91505060a08301516111e960a0860182611147565b5060c08301516111fc60c0860182611147565b508091505092915050565b60006112138383611156565b905092915050565b6000602082019050919050565b600061123382611084565b61123d818561108f565b93508360208202850161124f856110a0565b8060005b8581101561128b578484038952815161126c8582611207565b94506112778361121b565b925060208a01995050600181019050611253565b50829750879550505050505092915050565b600060208201905081810360008301526112b78184611228565b905092915050565b6000602082840312156112d5576112d4610c22565b5b60006112e384828501610e48565b91505092915050565b600082825260208201905092915050565b6000611308826110b0565b61131281856112ec565b93506113228185602086016110cc565b61132b81610c36565b840191505092915050565b6000602082019050818103600083015261135081846112fd565b905092915050565b61136181610e27565b82525050565b600060e0820190508181036000830152611381818a6112fd565b9050818103602083015261139581896112fd565b90506113a46040830188610dc7565b81810360608301526113b681876112fd565b905081810360808301526113ca81866112fd565b90506113d960a0830185611358565b6113e660c0830184611358565b98975050505050505050565b6000806040838503121561140957611408610c22565b5b600083013567ffffffffffffffff81111561142757611426610c27565b5b61143385828601610d44565b925050602083013567ffffffffffffffff81111561145457611453610c27565b5b61146085828601610d44565b9150509250929050565b600081905092915050565b6000611480826110b0565b61148a818561146a565b935061149a8185602086016110cc565b80840191505092915050565b60006114b28284611475565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006114f782610e27565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82141561152a576115296114bd565b5b600182019050919050565b600067ffffffffffffffff8211156115505761154f610c47565b5b602082029050602081019050919050565b600080fd5b600061157961157484610cc2565b610ca7565b90508281526020810184848401111561159557611594610c31565b5b6115a08482856110cc565b509392505050565b600082601f8301126115bd576115bc610c2c565b5b81516115cd848260208601611566565b91505092915050565b60006115e96115e484611535565b610ca7565b9050808382526020820190506020840283018581111561160c5761160b611561565b5b835b8181101561165357805167ffffffffffffffff81111561163157611630610c2c565b5b80860161163e89826115a8565b8552602085019450505060208101905061160e565b5050509392505050565b600082601f83011261167257611671610c2c565b5b81516116828482602086016115d6565b91505092915050565b6000602082840312156116a1576116a0610c22565b5b600082015167ffffffffffffffff8111156116bf576116be610c27565b5b6116cb8482850161165d565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061174a57607f821691505b6020821081141561175e5761175d611703565b5b50919050565b7f6d6f64696679204173736574277320706963747572652055726c20737563636560008201527f7373000000000000000000000000000000000000000000000000000000000000602082015250565b60006117c06022836112ec565b91506117cb82611764565b604082019050919050565b6000819050919050565b6000819050919050565b60006118056118006117fb846117d6565b6117e0565b610e27565b9050919050565b611815816117ea565b82525050565b60006040820190508181036000830152611834816117b3565b9050611843602083018461180c565b92915050565b7f6d6f64696679206173736574277320706963747572652055726c206661696c6560008201527f6400000000000000000000000000000000000000000000000000000000000000602082015250565b60006118a56021836112ec565b91506118b082611849565b604082019050919050565b6000819050919050565b60006118e06118db6118d6846118bb565b6117e0565b610e27565b9050919050565b6118f0816118c5565b82525050565b6000604082019050818103600083015261190f81611898565b905061191e60208301846118e7565b9291505056fea2646970667358221220d9ab333920c0dfbac97d3c28234aca4e129d5d4d456105f885268e8380eb3cd064736f6c634300080a0033";

    public static final String FUNC_ASSETS = "Assets";

    public static final String FUNC_ASSETINSERTED = "assetInserted";

    public static final String FUNC_ASSETKEY = "assetKey";

    public static final String FUNC_CREATEASSET = "createAsset";

    public static final String FUNC_GETASSETLISTPLACEID = "getAssetListPlaceId";

    public static final String FUNC_MODIFYPICTUREURL = "modifyPictureUrl";

    public static final Event MODIFIERPICURLEVENT_EVENT = new Event("modifierPicUrlEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected AssetContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AssetContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AssetContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AssetContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ModifierPicUrlEventEventResponse> getModifierPicUrlEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MODIFIERPICURLEVENT_EVENT, transactionReceipt);
        ArrayList<ModifierPicUrlEventEventResponse> responses = new ArrayList<ModifierPicUrlEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ModifierPicUrlEventEventResponse typedResponse = new ModifierPicUrlEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.message = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ModifierPicUrlEventEventResponse> modifierPicUrlEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ModifierPicUrlEventEventResponse>() {
            @Override
            public ModifierPicUrlEventEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MODIFIERPICURLEVENT_EVENT, log);
                ModifierPicUrlEventEventResponse typedResponse = new ModifierPicUrlEventEventResponse();
                typedResponse.log = log;
                typedResponse.message = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ModifierPicUrlEventEventResponse> modifierPicUrlEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MODIFIERPICURLEVENT_EVENT));
        return modifierPicUrlEventEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple7<String, String, Boolean, String, String, BigInteger, BigInteger>> Assets(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ASSETS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple7<String, String, Boolean, String, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple7<String, String, Boolean, String, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple7<String, String, Boolean, String, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, String, Boolean, String, String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (Boolean) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (String) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> assetInserted(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ASSETINSERTED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> assetKey(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ASSETKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> createAsset(Asset _asset) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATEASSET,
                Arrays.<Type>asList(_asset),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAssetListPlaceId(String _placeAssetContract, String _placeId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETASSETLISTPLACEID,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _placeAssetContract),
                        new org.web3j.abi.datatypes.Utf8String(_placeId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Asset>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> modifyPictureUrl(String _assetId, String _pictureUrl) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MODIFYPICTUREURL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_assetId),
                        new org.web3j.abi.datatypes.Utf8String(_pictureUrl)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static AssetContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AssetContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AssetContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AssetContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AssetContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AssetContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AssetContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AssetContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AssetContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AssetContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AssetContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AssetContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AssetContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AssetContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AssetContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AssetContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Getter
    @Setter
    @ToString
    public static class Asset extends DynamicStruct {
        public String assetId;

        public String assetName;

        public Boolean isApply;

        public String picUrl;

        public String value;

        public BigInteger count;

        public BigInteger status;

        public Asset(String assetId, String assetName, Boolean isApply, String picUrl, String value, BigInteger count, BigInteger status) {
            super(new org.web3j.abi.datatypes.Utf8String(assetId),new org.web3j.abi.datatypes.Utf8String(assetName),new org.web3j.abi.datatypes.Bool(isApply),new org.web3j.abi.datatypes.Utf8String(picUrl),new org.web3j.abi.datatypes.Utf8String(value),new org.web3j.abi.datatypes.generated.Uint256(count),new org.web3j.abi.datatypes.generated.Uint256(status));
            this.assetId = assetId;
            this.assetName = assetName;
            this.isApply = isApply;
            this.picUrl = picUrl;
            this.value = value;
            this.count = count;
            this.status = status;
        }

        public Asset(Utf8String assetId, Utf8String assetName, Bool isApply, Utf8String picUrl, Utf8String value, Uint256 count, Uint256 status) {
            super(assetId,assetName,isApply,picUrl,value,count,status);
            this.assetId = assetId.getValue();
            this.assetName = assetName.getValue();
            this.isApply = isApply.getValue();
            this.picUrl = picUrl.getValue();
            this.value = value.getValue();
            this.count = count.getValue();
            this.status = status.getValue();
        }
    }

    public static class ModifierPicUrlEventEventResponse extends BaseEventResponse {
        public String message;

        public BigInteger value;
    }
}
