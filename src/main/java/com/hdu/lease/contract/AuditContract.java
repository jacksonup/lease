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
import org.web3j.tuples.generated.Tuple11;
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
public class AuditContract extends Contract {
    public static final String BINARY = "608060405260028054905060035534801561001957600080fd5b50611a3a806100296000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806331129c88146100515780633e81b4191461006d578063756164df14610089578063d51b27b8146100b9575b600080fd5b61006b6004803603810190610066919061146d565b6100f3565b005b6100876004803603810190610082919061146d565b6102ff565b005b6100a3600480360381019061009e91906114b6565b6104f0565b6040516100b091906116bd565b60405180910390f35b6100d360048036038101906100ce91906114b6565b610a96565b6040516100ea9b9a99989796959493929190611738565b60405180910390f35b60018160000151604051610107919061185e565b908152602001604051809103902060009054906101000a900460ff166102fc57806000826000015160405161013c919061185e565b90815260200160405180910390206000820151816000019080519060200190610166929190610fce565b506020820151816001019080519060200190610183929190610fce565b5060408201518160020190805190602001906101a0929190610fce565b5060608201518160030190805190602001906101bd929190610fce565b5060808201518160040190805190602001906101da929190610fce565b5060a08201518160050190805190602001906101f7929190610fce565b5060c0820151816006019080519060200190610214929190610fce565b5060e082015181600701556101008201518160080155610120820151816009019080519060200190610247929190610fce565b5061014082015181600a019080519060200190610265929190610fce565b50905050600180826000015160405161027e919061185e565b908152602001604051809103902060006101000a81548160ff021916908315150217905550600281600001519080600181540180825580915050600190039060005260206000200160009091909190915090805190602001906102e2929190610fce565b50600360008154809291906102f6906118a4565b91905055505b50565b60018160000151604051610313919061185e565b908152602001604051809103902060009054906101000a900460ff16156104b3578060008260000151604051610349919061185e565b90815260200160405180910390206000820151816000019080519060200190610373929190610fce565b506020820151816001019080519060200190610390929190610fce565b5060408201518160020190805190602001906103ad929190610fce565b5060608201518160030190805190602001906103ca929190610fce565b5060808201518160040190805190602001906103e7929190610fce565b5060a0820151816005019080519060200190610404929190610fce565b5060c0820151816006019080519060200190610421929190610fce565b5060e082015181600701556101008201518160080155610120820151816009019080519060200190610454929190610fce565b5061014082015181600a019080519060200190610472929190610fce565b509050507f1f0b7755550dd48676926d868a91257934b9a64d5f852cad45e85274c052c6b060c86040516104a69190611932565b60405180910390a16104ed565b7f1f0b7755550dd48676926d868a91257934b9a64d5f852cad45e85274c052c6b06127116040516104e49190611988565b60405180910390a15b50565b6104f8611054565b600182604051610508919061185e565b908152602001604051809103902060009054906101000a900460ff1615610a9057600082604051610539919061185e565b908152602001604051809103902060405180610160016040529081600082018054610563906119d2565b80601f016020809104026020016040519081016040528092919081815260200182805461058f906119d2565b80156105dc5780601f106105b1576101008083540402835291602001916105dc565b820191906000526020600020905b8154815290600101906020018083116105bf57829003601f168201915b505050505081526020016001820180546105f5906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610621906119d2565b801561066e5780601f106106435761010080835404028352916020019161066e565b820191906000526020600020905b81548152906001019060200180831161065157829003601f168201915b50505050508152602001600282018054610687906119d2565b80601f01602080910402602001604051908101604052809291908181526020018280546106b3906119d2565b80156107005780601f106106d557610100808354040283529160200191610700565b820191906000526020600020905b8154815290600101906020018083116106e357829003601f168201915b50505050508152602001600382018054610719906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610745906119d2565b80156107925780601f1061076757610100808354040283529160200191610792565b820191906000526020600020905b81548152906001019060200180831161077557829003601f168201915b505050505081526020016004820180546107ab906119d2565b80601f01602080910402602001604051908101604052809291908181526020018280546107d7906119d2565b80156108245780601f106107f957610100808354040283529160200191610824565b820191906000526020600020905b81548152906001019060200180831161080757829003601f168201915b5050505050815260200160058201805461083d906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610869906119d2565b80156108b65780601f1061088b576101008083540402835291602001916108b6565b820191906000526020600020905b81548152906001019060200180831161089957829003601f168201915b505050505081526020016006820180546108cf906119d2565b80601f01602080910402602001604051908101604052809291908181526020018280546108fb906119d2565b80156109485780601f1061091d57610100808354040283529160200191610948565b820191906000526020600020905b81548152906001019060200180831161092b57829003601f168201915b505050505081526020016007820154815260200160088201548152602001600982018054610975906119d2565b80601f01602080910402602001604051908101604052809291908181526020018280546109a1906119d2565b80156109ee5780601f106109c3576101008083540402835291602001916109ee565b820191906000526020600020905b8154815290600101906020018083116109d157829003601f168201915b50505050508152602001600a82018054610a07906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610a33906119d2565b8015610a805780601f10610a5557610100808354040283529160200191610a80565b820191906000526020600020905b815481529060010190602001808311610a6357829003601f168201915b5050505050815250509050610a91565b5b919050565b600081805160208101820180518482526020830160208501208183528095505050505050600091509050806000018054610acf906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610afb906119d2565b8015610b485780601f10610b1d57610100808354040283529160200191610b48565b820191906000526020600020905b815481529060010190602001808311610b2b57829003601f168201915b505050505090806001018054610b5d906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610b89906119d2565b8015610bd65780601f10610bab57610100808354040283529160200191610bd6565b820191906000526020600020905b815481529060010190602001808311610bb957829003601f168201915b505050505090806002018054610beb906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610c17906119d2565b8015610c645780601f10610c3957610100808354040283529160200191610c64565b820191906000526020600020905b815481529060010190602001808311610c4757829003601f168201915b505050505090806003018054610c79906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610ca5906119d2565b8015610cf25780601f10610cc757610100808354040283529160200191610cf2565b820191906000526020600020905b815481529060010190602001808311610cd557829003601f168201915b505050505090806004018054610d07906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610d33906119d2565b8015610d805780601f10610d5557610100808354040283529160200191610d80565b820191906000526020600020905b815481529060010190602001808311610d6357829003601f168201915b505050505090806005018054610d95906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610dc1906119d2565b8015610e0e5780601f10610de357610100808354040283529160200191610e0e565b820191906000526020600020905b815481529060010190602001808311610df157829003601f168201915b505050505090806006018054610e23906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610e4f906119d2565b8015610e9c5780601f10610e7157610100808354040283529160200191610e9c565b820191906000526020600020905b815481529060010190602001808311610e7f57829003601f168201915b505050505090806007015490806008015490806009018054610ebd906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610ee9906119d2565b8015610f365780601f10610f0b57610100808354040283529160200191610f36565b820191906000526020600020905b815481529060010190602001808311610f1957829003601f168201915b50505050509080600a018054610f4b906119d2565b80601f0160208091040260200160405190810160405280929190818152602001828054610f77906119d2565b8015610fc45780601f10610f9957610100808354040283529160200191610fc4565b820191906000526020600020905b815481529060010190602001808311610fa757829003601f168201915b505050505090508b565b828054610fda906119d2565b90600052602060002090601f016020900481019282610ffc5760008555611043565b82601f1061101557805160ff1916838001178555611043565b82800160010185558215611043579182015b82811115611042578251825591602001919060010190611027565b5b50905061105091906110ae565b5090565b60405180610160016040528060608152602001606081526020016060815260200160608152602001606081526020016060815260200160608152602001600081526020016000815260200160608152602001606081525090565b5b808211156110c75760008160009055506001016110af565b5090565b6000604051905090565b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b61112d826110e4565b810181811067ffffffffffffffff8211171561114c5761114b6110f5565b5b80604052505050565b600061115f6110cb565b905061116b8282611124565b919050565b600080fd5b600080fd5b600080fd5b600067ffffffffffffffff82111561119a576111996110f5565b5b6111a3826110e4565b9050602081019050919050565b82818337600083830152505050565b60006111d26111cd8461117f565b611155565b9050828152602081018484840111156111ee576111ed61117a565b5b6111f98482856111b0565b509392505050565b600082601f83011261121657611215611175565b5b81356112268482602086016111bf565b91505092915050565b6000819050919050565b6112428161122f565b811461124d57600080fd5b50565b60008135905061125f81611239565b92915050565b6000610160828403121561127c5761127b6110df565b5b611287610160611155565b9050600082013567ffffffffffffffff8111156112a7576112a6611170565b5b6112b384828501611201565b600083015250602082013567ffffffffffffffff8111156112d7576112d6611170565b5b6112e384828501611201565b602083015250604082013567ffffffffffffffff81111561130757611306611170565b5b61131384828501611201565b604083015250606082013567ffffffffffffffff81111561133757611336611170565b5b61134384828501611201565b606083015250608082013567ffffffffffffffff81111561136757611366611170565b5b61137384828501611201565b60808301525060a082013567ffffffffffffffff81111561139757611396611170565b5b6113a384828501611201565b60a08301525060c082013567ffffffffffffffff8111156113c7576113c6611170565b5b6113d384828501611201565b60c08301525060e06113e784828501611250565b60e0830152506101006113fc84828501611250565b6101008301525061012082013567ffffffffffffffff81111561142257611421611170565b5b61142e84828501611201565b6101208301525061014082013567ffffffffffffffff81111561145457611453611170565b5b61146084828501611201565b6101408301525092915050565b600060208284031215611483576114826110d5565b5b600082013567ffffffffffffffff8111156114a1576114a06110da565b5b6114ad84828501611265565b91505092915050565b6000602082840312156114cc576114cb6110d5565b5b600082013567ffffffffffffffff8111156114ea576114e96110da565b5b6114f684828501611201565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b8381101561153957808201518184015260208101905061151e565b83811115611548576000848401525b50505050565b6000611559826114ff565b611563818561150a565b935061157381856020860161151b565b61157c816110e4565b840191505092915050565b6115908161122f565b82525050565b60006101608301600083015184820360008601526115b4828261154e565b915050602083015184820360208601526115ce828261154e565b915050604083015184820360408601526115e8828261154e565b91505060608301518482036060860152611602828261154e565b9150506080830151848203608086015261161c828261154e565b91505060a083015184820360a0860152611636828261154e565b91505060c083015184820360c0860152611650828261154e565b91505060e083015161166560e0860182611587565b5061010083015161167a610100860182611587565b50610120830151848203610120860152611694828261154e565b9150506101408301518482036101408601526116b0828261154e565b9150508091505092915050565b600060208201905081810360008301526116d78184611596565b905092915050565b600082825260208201905092915050565b60006116fb826114ff565b61170581856116df565b935061171581856020860161151b565b61171e816110e4565b840191505092915050565b6117328161122f565b82525050565b6000610160820190508181036000830152611753818e6116f0565b90508181036020830152611767818d6116f0565b9050818103604083015261177b818c6116f0565b9050818103606083015261178f818b6116f0565b905081810360808301526117a3818a6116f0565b905081810360a08301526117b781896116f0565b905081810360c08301526117cb81886116f0565b90506117da60e0830187611729565b6117e8610100830186611729565b8181036101208301526117fb81856116f0565b905081810361014083015261181081846116f0565b90509c9b505050505050505050505050565b600081905092915050565b6000611838826114ff565b6118428185611822565b935061185281856020860161151b565b80840191505092915050565b600061186a828461182d565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006118af8261122f565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8214156118e2576118e1611875565b5b600182019050919050565b6000819050919050565b6000819050919050565b600061191c611917611912846118ed565b6118f7565b61122f565b9050919050565b61192c81611901565b82525050565b60006020820190506119476000830184611923565b92915050565b6000819050919050565b600061197261196d6119688461194d565b6118f7565b61122f565b9050919050565b61198281611957565b82525050565b600060208201905061199d6000830184611979565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806119ea57607f821691505b602082108114156119fe576119fd6119a3565b5b5091905056fea2646970667358221220c8772a1e29dfd773b2845170c32d35e32e2e04e15e09ba90207a7db4b9148d6064736f6c634300080a0033";

    public static final String FUNC_AUDITS = "audits";

    public static final String FUNC_GETAUDITBYPRIMARYKEY = "getAuditByPrimaryKey";

    public static final String FUNC_INSERTAUDIT = "insertAudit";

    public static final String FUNC_UPDATEAUDITINFO = "updateAuditInfo";

    public static final Event ISUPDATESUCCESS_EVENT = new Event("isUpdateSuccess",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected AuditContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AuditContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AuditContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AuditContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteFunctionCall<Tuple11<String, String, String, String, String, String, String, BigInteger, BigInteger, String, String>> audits(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_AUDITS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple11<String, String, String, String, String, String, String, BigInteger, BigInteger, String, String>>(function,
                new Callable<Tuple11<String, String, String, String, String, String, String, BigInteger, BigInteger, String, String>>() {
                    @Override
                    public Tuple11<String, String, String, String, String, String, String, BigInteger, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple11<String, String, String, String, String, String, String, BigInteger, BigInteger, String, String>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (String) results.get(4).getValue(),
                                (String) results.get(5).getValue(),
                                (String) results.get(6).getValue(),
                                (BigInteger) results.get(7).getValue(),
                                (BigInteger) results.get(8).getValue(),
                                (String) results.get(9).getValue(),
                                (String) results.get(10).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Audit> getAuditByPrimaryKey(String _auditId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAUDITBYPRIMARYKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_auditId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Audit>() {}));
        return executeRemoteCallSingleValueReturn(function, Audit.class);
    }

    public RemoteFunctionCall<TransactionReceipt> insertAudit(Audit _audit) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INSERTAUDIT,
                Arrays.<Type>asList(_audit),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateAuditInfo(Audit _audit) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEAUDITINFO,
                Arrays.<Type>asList(_audit),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static AuditContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuditContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AuditContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuditContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AuditContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AuditContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AuditContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AuditContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AuditContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AuditContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AuditContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AuditContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AuditContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AuditContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AuditContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AuditContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Audit extends DynamicStruct {
        public String auditId;

        public String assetId;

        public String placeId;

        public String borrowerAccount;

        public String beginTime;

        public String endTime;

        public String borrowReason;

        public BigInteger count;

        public BigInteger reviewStatus;

        public String reviewReason;

        public String reviewTime;

        public Audit(String auditId, String assetId, String placeId, String borrowerAccount, String beginTime, String endTime, String borrowReason, BigInteger count, BigInteger reviewStatus, String reviewReason, String reviewTime) {
            super(new org.web3j.abi.datatypes.Utf8String(auditId),new org.web3j.abi.datatypes.Utf8String(assetId),new org.web3j.abi.datatypes.Utf8String(placeId),new org.web3j.abi.datatypes.Utf8String(borrowerAccount),new org.web3j.abi.datatypes.Utf8String(beginTime),new org.web3j.abi.datatypes.Utf8String(endTime),new org.web3j.abi.datatypes.Utf8String(borrowReason),new org.web3j.abi.datatypes.generated.Uint256(count),new org.web3j.abi.datatypes.generated.Uint256(reviewStatus),new org.web3j.abi.datatypes.Utf8String(reviewReason),new org.web3j.abi.datatypes.Utf8String(reviewTime));
            this.auditId = auditId;
            this.assetId = assetId;
            this.placeId = placeId;
            this.borrowerAccount = borrowerAccount;
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.borrowReason = borrowReason;
            this.count = count;
            this.reviewStatus = reviewStatus;
            this.reviewReason = reviewReason;
            this.reviewTime = reviewTime;
        }

        public Audit(Utf8String auditId, Utf8String assetId, Utf8String placeId, Utf8String borrowerAccount, Utf8String beginTime, Utf8String endTime, Utf8String borrowReason, Uint256 count, Uint256 reviewStatus, Utf8String reviewReason, Utf8String reviewTime) {
            super(auditId,assetId,placeId,borrowerAccount,beginTime,endTime,borrowReason,count,reviewStatus,reviewReason,reviewTime);
            this.auditId = auditId.getValue();
            this.assetId = assetId.getValue();
            this.placeId = placeId.getValue();
            this.borrowerAccount = borrowerAccount.getValue();
            this.beginTime = beginTime.getValue();
            this.endTime = endTime.getValue();
            this.borrowReason = borrowReason.getValue();
            this.count = count.getValue();
            this.reviewStatus = reviewStatus.getValue();
            this.reviewReason = reviewReason.getValue();
            this.reviewTime = reviewTime.getValue();
        }
    }

    public static class IsUpdateSuccessEventResponse extends BaseEventResponse {
        public BigInteger code;
    }
}