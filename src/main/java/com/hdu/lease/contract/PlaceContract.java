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
import org.web3j.abi.datatypes.*;
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
import org.web3j.tuples.generated.Tuple5;
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
public class PlaceContract extends Contract {
    public static final String BINARY = "608060405260028054905060035534801561001957600080fd5b506112b8806100296000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806327ede8871461005c5780634bba74d11461008c57806374386a84146100aa578063933276c9146100de5780639e4c78f4146100fa575b600080fd5b61007660048036038101906100719190610ac3565b61012a565b6040516100839190610b89565b60405180910390f35b6100946101d6565b6040516100a19190610d51565b60405180910390f35b6100c460048036038101906100bf9190610ea8565b610577565b6040516100d5959493929190610f00565b60405180910390f35b6100f860048036038101906100f39190610f6f565b6107e3565b005b610114600480360381019061010f9190610ea8565b610971565b6040516101219190611061565b60405180910390f35b6002818154811061013a57600080fd5b906000526020600020016000915090508054610155906110ab565b80601f0160208091040260200160405190810160405280929190818152602001828054610181906110ab565b80156101ce5780601f106101a3576101008083540402835291602001916101ce565b820191906000526020600020905b8154815290600101906020018083116101b157829003601f168201915b505050505081565b6060600060035467ffffffffffffffff8111156101f6576101f5610d7d565b5b60405190808252806020026020018201604052801561022f57816020015b61021c6109a7565b8152602001906001900390816102145790505b5090506000805b60035481101561056e57600160028281548110610256576102556110dd565b5b9060005260206000200160405161026d91906111ab565b908152602001604051809103902060009054906101000a900460ff1615610555576000600282815481106102a4576102a36110dd565b5b906000526020600020016040516102bb91906111ab565b90815260200160405180910390206040518060a00160405290816000820180546102e4906110ab565b80601f0160208091040260200160405190810160405280929190818152602001828054610310906110ab565b801561035d5780601f106103325761010080835404028352916020019161035d565b820191906000526020600020905b81548152906001019060200180831161034057829003601f168201915b50505050508152602001600182018054610376906110ab565b80601f01602080910402602001604051908101604052809291908181526020018280546103a2906110ab565b80156103ef5780601f106103c4576101008083540402835291602001916103ef565b820191906000526020600020905b8154815290600101906020018083116103d257829003601f168201915b50505050508152602001600282018054610408906110ab565b80601f0160208091040260200160405190810160405280929190818152602001828054610434906110ab565b80156104815780601f1061045657610100808354040283529160200191610481565b820191906000526020600020905b81548152906001019060200180831161046457829003601f168201915b5050505050815260200160038201805461049a906110ab565b80601f01602080910402602001604051908101604052809291908181526020018280546104c6906110ab565b80156105135780601f106104e857610100808354040283529160200191610513565b820191906000526020600020905b8154815290600101906020018083116104f657829003601f168201915b50505050508152602001600482015481525050838380610532906111f1565b945081518110610545576105446110dd565b5b602002602001018190525061055a565b61055b565b5b8080610566906111f1565b915050610236565b50819250505090565b6000818051602081018201805184825260208301602085012081835280955050505050506000915090508060000180546105b0906110ab565b80601f01602080910402602001604051908101604052809291908181526020018280546105dc906110ab565b80156106295780601f106105fe57610100808354040283529160200191610629565b820191906000526020600020905b81548152906001019060200180831161060c57829003601f168201915b50505050509080600101805461063e906110ab565b80601f016020809104026020016040519081016040528092919081815260200182805461066a906110ab565b80156106b75780601f1061068c576101008083540402835291602001916106b7565b820191906000526020600020905b81548152906001019060200180831161069a57829003601f168201915b5050505050908060020180546106cc906110ab565b80601f01602080910402602001604051908101604052809291908181526020018280546106f8906110ab565b80156107455780601f1061071a57610100808354040283529160200191610745565b820191906000526020600020905b81548152906001019060200180831161072857829003601f168201915b50505050509080600301805461075a906110ab565b80601f0160208091040260200160405190810160405280929190818152602001828054610786906110ab565b80156107d35780601f106107a8576101008083540402835291602001916107d3565b820191906000526020600020905b8154815290600101906020018083116107b657829003601f168201915b5050505050908060040154905085565b6001846040516107f3919061126b565b908152602001604051809103902060009054906101000a900460ff1661096b5760006040518060a00160405280868152602001858152602001848152602001838152602001600081525090508060008260000151604051610854919061126b565b9081526020016040518091039020600082015181600001908051906020019061087e9291906109d6565b50602082015181600101908051906020019061089b9291906109d6565b5060408201518160020190805190602001906108b89291906109d6565b5060608201518160030190805190602001906108d59291906109d6565b506080820151816004015590505060018082600001516040516108f8919061126b565b908152602001604051809103902060006101000a81548160ff0219169083151502179055506002816000015190806001815401808255809150506001900390600052602060002001600090919091909150908051906020019061095c9291906109d6565b50600280549050600381905550505b50505050565b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b6040518060a0016040528060608152602001606081526020016060815260200160608152602001600081525090565b8280546109e2906110ab565b90600052602060002090601f016020900481019282610a045760008555610a4b565b82601f10610a1d57805160ff1916838001178555610a4b565b82800160010185558215610a4b579182015b82811115610a4a578251825591602001919060010190610a2f565b5b509050610a589190610a5c565b5090565b5b80821115610a75576000816000905550600101610a5d565b5090565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b610aa081610a8d565b8114610aab57600080fd5b50565b600081359050610abd81610a97565b92915050565b600060208284031215610ad957610ad8610a83565b5b6000610ae784828501610aae565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610b2a578082015181840152602081019050610b0f565b83811115610b39576000848401525b50505050565b6000601f19601f8301169050919050565b6000610b5b82610af0565b610b658185610afb565b9350610b75818560208601610b0c565b610b7e81610b3f565b840191505092915050565b60006020820190508181036000830152610ba38184610b50565b905092915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600082825260208201905092915050565b6000610bf382610af0565b610bfd8185610bd7565b9350610c0d818560208601610b0c565b610c1681610b3f565b840191505092915050565b610c2a81610a8d565b82525050565b600060a0830160008301518482036000860152610c4d8282610be8565b91505060208301518482036020860152610c678282610be8565b91505060408301518482036040860152610c818282610be8565b91505060608301518482036060860152610c9b8282610be8565b9150506080830151610cb06080860182610c21565b508091505092915050565b6000610cc78383610c30565b905092915050565b6000602082019050919050565b6000610ce782610bab565b610cf18185610bb6565b935083602082028501610d0385610bc7565b8060005b85811015610d3f5784840389528151610d208582610cbb565b9450610d2b83610ccf565b925060208a01995050600181019050610d07565b50829750879550505050505092915050565b60006020820190508181036000830152610d6b8184610cdc565b905092915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610db582610b3f565b810181811067ffffffffffffffff82111715610dd457610dd3610d7d565b5b80604052505050565b6000610de7610a79565b9050610df38282610dac565b919050565b600067ffffffffffffffff821115610e1357610e12610d7d565b5b610e1c82610b3f565b9050602081019050919050565b82818337600083830152505050565b6000610e4b610e4684610df8565b610ddd565b905082815260208101848484011115610e6757610e66610d78565b5b610e72848285610e29565b509392505050565b600082601f830112610e8f57610e8e610d73565b5b8135610e9f848260208601610e38565b91505092915050565b600060208284031215610ebe57610ebd610a83565b5b600082013567ffffffffffffffff811115610edc57610edb610a88565b5b610ee884828501610e7a565b91505092915050565b610efa81610a8d565b82525050565b600060a0820190508181036000830152610f1a8188610b50565b90508181036020830152610f2e8187610b50565b90508181036040830152610f428186610b50565b90508181036060830152610f568185610b50565b9050610f656080830184610ef1565b9695505050505050565b60008060008060808587031215610f8957610f88610a83565b5b600085013567ffffffffffffffff811115610fa757610fa6610a88565b5b610fb387828801610e7a565b945050602085013567ffffffffffffffff811115610fd457610fd3610a88565b5b610fe087828801610e7a565b935050604085013567ffffffffffffffff81111561100157611000610a88565b5b61100d87828801610e7a565b925050606085013567ffffffffffffffff81111561102e5761102d610a88565b5b61103a87828801610e7a565b91505092959194509250565b60008115159050919050565b61105b81611046565b82525050565b60006020820190506110766000830184611052565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806110c357607f821691505b602082108114156110d7576110d661107c565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600081905092915050565b60008190508160005260206000209050919050565b60008154611139816110ab565b611143818661110c565b9450600182166000811461115e576001811461116f576111a2565b60ff198316865281860193506111a2565b61117885611117565b60005b8381101561119a5781548189015260018201915060208101905061117b565b838801955050505b50505092915050565b60006111b7828461112c565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006111fc82610a8d565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82141561122f5761122e6111c2565b5b600182019050919050565b600061124582610af0565b61124f818561110c565b935061125f818560208601610b0c565b80840191505092915050565b6000611277828461123a565b91508190509291505056fea2646970667358221220a7e5df92a56a891032f67d70842182ac6528e062952ae50ac328a1270608059364736f6c634300080a0033";

    public static final String FUNC_CREATEPLACE = "createPlace";

    public static final String FUNC_GETALLPLACELIST = "getAllPlaceList";

    public static final String FUNC_PLACEINSERTED = "placeInserted";

    public static final String FUNC_PLACEKEY = "placeKey";

    public static final String FUNC_PLACES = "places";

    public static final Event ISDELETESUCCESS_EVENT = new Event("isDeleteSuccess",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected PlaceContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PlaceContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PlaceContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PlaceContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<IsDeleteSuccessEventResponse> getIsDeleteSuccessEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ISDELETESUCCESS_EVENT, transactionReceipt);
        ArrayList<IsDeleteSuccessEventResponse> responses = new ArrayList<IsDeleteSuccessEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            IsDeleteSuccessEventResponse typedResponse = new IsDeleteSuccessEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IsDeleteSuccessEventResponse> isDeleteSuccessEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, IsDeleteSuccessEventResponse>() {
            @Override
            public IsDeleteSuccessEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ISDELETESUCCESS_EVENT, log);
                IsDeleteSuccessEventResponse typedResponse = new IsDeleteSuccessEventResponse();
                typedResponse.log = log;
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IsDeleteSuccessEventResponse> isDeleteSuccessEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISDELETESUCCESS_EVENT));
        return isDeleteSuccessEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> createPlace(String _placeId, String _placeName, String _placeAddress, String _placeManagerAccount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATEPLACE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_placeId),
                        new org.web3j.abi.datatypes.Utf8String(_placeName),
                        new org.web3j.abi.datatypes.Utf8String(_placeAddress),
                        new org.web3j.abi.datatypes.Utf8String(_placeManagerAccount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAllPlaceList() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETALLPLACELIST,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Place>>() {}));
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

    public RemoteFunctionCall<Boolean> placeInserted(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PLACEINSERTED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> placeKey(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PLACEKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple5<String, String, String, String, BigInteger>> places(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PLACES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<String, String, String, String, BigInteger>>(function,
                new Callable<Tuple5<String, String, String, String, BigInteger>>() {
                    @Override
                    public Tuple5<String, String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, String, String, BigInteger>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    @Deprecated
    public static PlaceContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PlaceContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PlaceContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PlaceContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PlaceContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PlaceContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PlaceContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PlaceContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PlaceContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PlaceContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PlaceContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PlaceContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<PlaceContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PlaceContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PlaceContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PlaceContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }


    public static class Place extends DynamicStruct {
        public String placeId;

        public String placeName;

        public String placeAddress;

        public String placeManagerAccount;

        public BigInteger status;

        public Place(String placeId, String placeName, String placeAddress, String placeManagerAccount, BigInteger status) {
            super(new org.web3j.abi.datatypes.Utf8String(placeId),new org.web3j.abi.datatypes.Utf8String(placeName),new org.web3j.abi.datatypes.Utf8String(placeAddress),new org.web3j.abi.datatypes.Utf8String(placeManagerAccount),new org.web3j.abi.datatypes.generated.Uint256(status));
            this.placeId = placeId;
            this.placeName = placeName;
            this.placeAddress = placeAddress;
            this.placeManagerAccount = placeManagerAccount;
            this.status = status;
        }

        public Place(Utf8String placeId, Utf8String placeName, Utf8String placeAddress, Utf8String placeManagerAccount, Uint256 status) {
            super(placeId,placeName,placeAddress,placeManagerAccount,status);
            this.placeId = placeId.getValue();
            this.placeName = placeName.getValue();
            this.placeAddress = placeAddress.getValue();
            this.placeManagerAccount = placeManagerAccount.getValue();
            this.status = status.getValue();
        }
    }
    public static class IsDeleteSuccessEventResponse extends BaseEventResponse {
        public BigInteger code;

        public String msg;
    }
}
