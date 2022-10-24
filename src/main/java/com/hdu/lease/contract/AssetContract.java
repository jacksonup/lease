package com.hdu.lease.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
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
    public static final String BINARY = "608060405260028054905060035534801561001957600080fd5b5061180d806100296000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c8063232ad707146100675780632e3e1924146100975780635fd1c06f146100c75780637e3dc024146100f7578063ce34b8291461012d578063dea809d214610149575b600080fd5b610081600480360381019061007c9190610c4f565b610165565b60405161008e9190610cb3565b60405180910390f35b6100b160048036038101906100ac9190610d2c565b61019b565b6040516100be9190610fa4565b60405180910390f35b6100e160048036038101906100dc9190610ff2565b6104f4565b6040516100ee9190611069565b60405180910390f35b610111600480360381019061010c9190610c4f565b6105a0565b604051610124979695949392919061109a565b60405180910390f35b6101476004803603810190610142919061111e565b61079d565b005b610163600480360381019061015e91906112d4565b61088e565b005b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b606060008373ffffffffffffffffffffffffffffffffffffffff1663e31bcfca846040518263ffffffff1660e01b81526004016101d89190611069565b600060405180830381865afa1580156101f5573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525081019061021e9190611473565b90506000815167ffffffffffffffff81111561023d5761023c610b24565b5b60405190808252806020026020018201604052801561027657816020015b610263610a13565b81526020019060019003908161025b5790505b50905060005b82518110156104e857600083828151811061029a576102996114bc565b5b60200260200101516040516102af9190611527565b90815260200160405180910390206040518060e00160405290816000820180546102d89061156d565b80601f01602080910402602001604051908101604052809291908181526020018280546103049061156d565b80156103515780601f1061032657610100808354040283529160200191610351565b820191906000526020600020905b81548152906001019060200180831161033457829003601f168201915b5050505050815260200160018201805461036a9061156d565b80601f01602080910402602001604051908101604052809291908181526020018280546103969061156d565b80156103e35780601f106103b8576101008083540402835291602001916103e3565b820191906000526020600020905b8154815290600101906020018083116103c657829003601f168201915b505050505081526020016002820160009054906101000a900460ff161515151581526020016003820180546104179061156d565b80601f01602080910402602001604051908101604052809291908181526020018280546104439061156d565b80156104905780601f1061046557610100808354040283529160200191610490565b820191906000526020600020905b81548152906001019060200180831161047357829003601f168201915b5050505050815260200160048201548152602001600582015481526020016006820154815250508282815181106104ca576104c96114bc565b5b602002602001018190525080806104e0906115ce565b91505061027c565b50809250505092915050565b6002818154811061050457600080fd5b90600052602060002001600091509050805461051f9061156d565b80601f016020809104026020016040519081016040528092919081815260200182805461054b9061156d565b80156105985780601f1061056d57610100808354040283529160200191610598565b820191906000526020600020905b81548152906001019060200180831161057b57829003601f168201915b505050505081565b6000818051602081018201805184825260208301602085012081835280955050505050506000915090508060000180546105d99061156d565b80601f01602080910402602001604051908101604052809291908181526020018280546106059061156d565b80156106525780601f1061062757610100808354040283529160200191610652565b820191906000526020600020905b81548152906001019060200180831161063557829003601f168201915b5050505050908060010180546106679061156d565b80601f01602080910402602001604051908101604052809291908181526020018280546106939061156d565b80156106e05780601f106106b5576101008083540402835291602001916106e0565b820191906000526020600020905b8154815290600101906020018083116106c357829003601f168201915b5050505050908060020160009054906101000a900460ff16908060030180546107089061156d565b80601f01602080910402602001604051908101604052809291908181526020018280546107349061156d565b80156107815780601f1061075657610100808354040283529160200191610781565b820191906000526020600020905b81548152906001019060200180831161076457829003601f168201915b5050505050908060040154908060050154908060060154905087565b60006001836040516107af9190611527565b908152602001604051809103902060009054906101000a900460ff161561080b5760019050816000846040516107e59190611527565b90815260200160405180910390206003019080519060200190610809929190610a52565b505b801561084f577f39859739613dd11b0e780ae98a04d89e5c2230793b95ff01e96432f26036753661271060405161084291906116ce565b60405180910390a1610889565b7f39859739613dd11b0e780ae98a04d89e5c2230793b95ff01e96432f26036753661271160405161088091906117a9565b60405180910390a15b505050565b600181600001516040516108a29190611527565b908152602001604051809103902060009054906101000a900460ff16610a105780600082600001516040516108d79190611527565b90815260200160405180910390206000820151816000019080519060200190610901929190610a52565b50602082015181600101908051906020019061091e929190610a52565b5060408201518160020160006101000a81548160ff021916908315150217905550606082015181600301908051906020019061095b929190610a52565b506080820151816004015560a0820151816005015560c0820151816006015590505060018082600001516040516109929190611527565b908152602001604051809103902060006101000a81548160ff021916908315150217905550600281600001519080600181540180825580915050600190039060005260206000200160009091909190915090805190602001906109f6929190610a52565b5060036000815480929190610a0a906115ce565b91905055505b50565b6040518060e001604052806060815260200160608152602001600015158152602001606081526020016000815260200160008152602001600081525090565b828054610a5e9061156d565b90600052602060002090601f016020900481019282610a805760008555610ac7565b82601f10610a9957805160ff1916838001178555610ac7565b82800160010185558215610ac7579182015b82811115610ac6578251825591602001919060010190610aab565b5b509050610ad49190610ad8565b5090565b5b80821115610af1576000816000905550600101610ad9565b5090565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610b5c82610b13565b810181811067ffffffffffffffff82111715610b7b57610b7a610b24565b5b80604052505050565b6000610b8e610af5565b9050610b9a8282610b53565b919050565b600067ffffffffffffffff821115610bba57610bb9610b24565b5b610bc382610b13565b9050602081019050919050565b82818337600083830152505050565b6000610bf2610bed84610b9f565b610b84565b905082815260208101848484011115610c0e57610c0d610b0e565b5b610c19848285610bd0565b509392505050565b600082601f830112610c3657610c35610b09565b5b8135610c46848260208601610bdf565b91505092915050565b600060208284031215610c6557610c64610aff565b5b600082013567ffffffffffffffff811115610c8357610c82610b04565b5b610c8f84828501610c21565b91505092915050565b60008115159050919050565b610cad81610c98565b82525050565b6000602082019050610cc86000830184610ca4565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610cf982610cce565b9050919050565b610d0981610cee565b8114610d1457600080fd5b50565b600081359050610d2681610d00565b92915050565b60008060408385031215610d4357610d42610aff565b5b6000610d5185828601610d17565b925050602083013567ffffffffffffffff811115610d7257610d71610b04565b5b610d7e85828601610c21565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610dee578082015181840152602081019050610dd3565b83811115610dfd576000848401525b50505050565b6000610e0e82610db4565b610e188185610dbf565b9350610e28818560208601610dd0565b610e3181610b13565b840191505092915050565b610e4581610c98565b82525050565b6000819050919050565b610e5e81610e4b565b82525050565b600060e0830160008301518482036000860152610e818282610e03565b91505060208301518482036020860152610e9b8282610e03565b9150506040830151610eb06040860182610e3c565b5060608301518482036060860152610ec88282610e03565b9150506080830151610edd6080860182610e55565b5060a0830151610ef060a0860182610e55565b5060c0830151610f0360c0860182610e55565b508091505092915050565b6000610f1a8383610e64565b905092915050565b6000602082019050919050565b6000610f3a82610d88565b610f448185610d93565b935083602082028501610f5685610da4565b8060005b85811015610f925784840389528151610f738582610f0e565b9450610f7e83610f22565b925060208a01995050600181019050610f5a565b50829750879550505050505092915050565b60006020820190508181036000830152610fbe8184610f2f565b905092915050565b610fcf81610e4b565b8114610fda57600080fd5b50565b600081359050610fec81610fc6565b92915050565b60006020828403121561100857611007610aff565b5b600061101684828501610fdd565b91505092915050565b600082825260208201905092915050565b600061103b82610db4565b611045818561101f565b9350611055818560208601610dd0565b61105e81610b13565b840191505092915050565b600060208201905081810360008301526110838184611030565b905092915050565b61109481610e4b565b82525050565b600060e08201905081810360008301526110b4818a611030565b905081810360208301526110c88189611030565b90506110d76040830188610ca4565b81810360608301526110e98187611030565b90506110f8608083018661108b565b61110560a083018561108b565b61111260c083018461108b565b98975050505050505050565b6000806040838503121561113557611134610aff565b5b600083013567ffffffffffffffff81111561115357611152610b04565b5b61115f85828601610c21565b925050602083013567ffffffffffffffff8111156111805761117f610b04565b5b61118c85828601610c21565b9150509250929050565b600080fd5b600080fd5b6111a981610c98565b81146111b457600080fd5b50565b6000813590506111c6816111a0565b92915050565b600060e082840312156111e2576111e1611196565b5b6111ec60e0610b84565b9050600082013567ffffffffffffffff81111561120c5761120b61119b565b5b61121884828501610c21565b600083015250602082013567ffffffffffffffff81111561123c5761123b61119b565b5b61124884828501610c21565b602083015250604061125c848285016111b7565b604083015250606082013567ffffffffffffffff8111156112805761127f61119b565b5b61128c84828501610c21565b60608301525060806112a084828501610fdd565b60808301525060a06112b484828501610fdd565b60a08301525060c06112c884828501610fdd565b60c08301525092915050565b6000602082840312156112ea576112e9610aff565b5b600082013567ffffffffffffffff81111561130857611307610b04565b5b611314848285016111cc565b91505092915050565b600067ffffffffffffffff82111561133857611337610b24565b5b602082029050602081019050919050565b600080fd5b600061136161135c84610b9f565b610b84565b90508281526020810184848401111561137d5761137c610b0e565b5b611388848285610dd0565b509392505050565b600082601f8301126113a5576113a4610b09565b5b81516113b584826020860161134e565b91505092915050565b60006113d16113cc8461131d565b610b84565b905080838252602082019050602084028301858111156113f4576113f3611349565b5b835b8181101561143b57805167ffffffffffffffff81111561141957611418610b09565b5b8086016114268982611390565b855260208501945050506020810190506113f6565b5050509392505050565b600082601f83011261145a57611459610b09565b5b815161146a8482602086016113be565b91505092915050565b60006020828403121561148957611488610aff565b5b600082015167ffffffffffffffff8111156114a7576114a6610b04565b5b6114b384828501611445565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600081905092915050565b600061150182610db4565b61150b81856114eb565b935061151b818560208601610dd0565b80840191505092915050565b600061153382846114f6565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061158557607f821691505b602082108114156115995761159861153e565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006115d982610e4b565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82141561160c5761160b61159f565b5b600182019050919050565b7f6d6f64696679204173736574277320706963747572652055726c20737563636560008201527f7373000000000000000000000000000000000000000000000000000000000000602082015250565b600061167360228361101f565b915061167e82611617565b604082019050919050565b6000819050919050565b6000819050919050565b60006116b86116b36116ae84611689565b611693565b610e4b565b9050919050565b6116c88161169d565b82525050565b600060408201905081810360008301526116e781611666565b90506116f660208301846116bf565b92915050565b7f6d6f64696679206173736574277320706963747572652055726c206661696c6560008201527f6400000000000000000000000000000000000000000000000000000000000000602082015250565b600061175860218361101f565b9150611763826116fc565b604082019050919050565b6000819050919050565b600061179361178e6117898461176e565b611693565b610e4b565b9050919050565b6117a381611778565b82525050565b600060408201905081810360008301526117c28161174b565b90506117d1602083018461179a565b9291505056fea26469706673582212205d7c41f083035faa45c3620019d422b81e8c6a8b5baee37d56154b950acaac1064736f6c634300080a0033";

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

    public RemoteFunctionCall<Tuple7<String, String, Boolean, String, BigInteger, BigInteger, BigInteger>> Assets(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ASSETS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple7<String, String, Boolean, String, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple7<String, String, Boolean, String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple7<String, String, Boolean, String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, String, Boolean, String, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (Boolean) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue(),
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

    public static class Asset extends DynamicStruct {
        public String assetId;

        public String assetName;

        public Boolean isApply;

        public String picUrl;

        public BigInteger price;

        public BigInteger count;

        public BigInteger status;

        public Asset(String assetId, String assetName, Boolean isApply, String picUrl, BigInteger price, BigInteger count, BigInteger status) {
            super(new org.web3j.abi.datatypes.Utf8String(assetId),new org.web3j.abi.datatypes.Utf8String(assetName),new org.web3j.abi.datatypes.Bool(isApply),new org.web3j.abi.datatypes.Utf8String(picUrl),new org.web3j.abi.datatypes.generated.Uint256(price),new org.web3j.abi.datatypes.generated.Uint256(count),new org.web3j.abi.datatypes.generated.Uint256(status));
            this.assetId = assetId;
            this.assetName = assetName;
            this.isApply = isApply;
            this.picUrl = picUrl;
            this.price = price;
            this.count = count;
            this.status = status;
        }

        public Asset(Utf8String assetId, Utf8String assetName, Bool isApply, Utf8String picUrl, Uint256 price, Uint256 count, Uint256 status) {
            super(assetId,assetName,isApply,picUrl,price,count,status);
            this.assetId = assetId.getValue();
            this.assetName = assetName.getValue();
            this.isApply = isApply.getValue();
            this.picUrl = picUrl.getValue();
            this.price = price.getValue();
            this.count = count.getValue();
            this.status = status.getValue();
        }
    }

    public static class ModifierPicUrlEventEventResponse extends BaseEventResponse {
        public String message;

        public BigInteger value;
    }
}
