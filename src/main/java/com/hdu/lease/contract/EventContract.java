package com.hdu.lease.contract;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class EventContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50611060806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806325bf7b741461005c5780639673d6a31461007857806397402152146100ae578063afa4e8e0146100de578063c04255921461010e575b600080fd5b61007660048036038101906100719190610be5565b61012c565b005b610092600480360381019061008d9190610c2e565b6102a9565b6040516100a59796959493929190610d1d565b60405180910390f35b6100c860048036038101906100c39190610c2e565b6105a9565b6040516100d59190610dca565b60405180910390f35b6100f860048036038101906100f39190610de5565b6105df565b6040516101059190610e12565b60405180910390f35b61011661068b565b6040516101239190610f54565b60405180910390f35b600181600001516040516101409190610fb2565b908152602001604051809103902060009054906101000a900460ff166102a65780600082600001516040516101759190610fb2565b9081526020016040518091039020600082015181600001908051906020019061019f9291906107f5565b506020820151816001015560408201518160020190805190602001906101c69291906107f5565b5060608201518160030190805190602001906101e39291906107f5565b5060808201518160040190805190602001906102009291906107f5565b5060a0820151816005015560c08201518160060190805190602001906102279291906107f5565b5090505060018082600001516040516102409190610fb2565b908152602001604051809103902060006101000a81548160ff021916908315150217905550600281600001519080600181540180825580915050600190039060005260206000200160009091909190915090805190602001906102a49291906107f5565b505b50565b6000818051602081018201805184825260208301602085012081835280955050505050506000915090508060000180546102e290610ff8565b80601f016020809104026020016040519081016040528092919081815260200182805461030e90610ff8565b801561035b5780601f106103305761010080835404028352916020019161035b565b820191906000526020600020905b81548152906001019060200180831161033e57829003601f168201915b50505050509080600101549080600201805461037690610ff8565b80601f01602080910402602001604051908101604052809291908181526020018280546103a290610ff8565b80156103ef5780601f106103c4576101008083540402835291602001916103ef565b820191906000526020600020905b8154815290600101906020018083116103d257829003601f168201915b50505050509080600301805461040490610ff8565b80601f016020809104026020016040519081016040528092919081815260200182805461043090610ff8565b801561047d5780601f106104525761010080835404028352916020019161047d565b820191906000526020600020905b81548152906001019060200180831161046057829003601f168201915b50505050509080600401805461049290610ff8565b80601f01602080910402602001604051908101604052809291908181526020018280546104be90610ff8565b801561050b5780601f106104e05761010080835404028352916020019161050b565b820191906000526020600020905b8154815290600101906020018083116104ee57829003601f168201915b50505050509080600501549080600601805461052690610ff8565b80601f016020809104026020016040519081016040528092919081815260200182805461055290610ff8565b801561059f5780601f106105745761010080835404028352916020019161059f565b820191906000526020600020905b81548152906001019060200180831161058257829003601f168201915b5050505050905087565b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b600281815481106105ef57600080fd5b90600052602060002001600091509050805461060a90610ff8565b80601f016020809104026020016040519081016040528092919081815260200182805461063690610ff8565b80156106835780601f1061065857610100808354040283529160200191610683565b820191906000526020600020905b81548152906001019060200180831161066657829003601f168201915b505050505081565b61069361087b565b6040518060e001604052806040518060400160405280600181526020017f300000000000000000000000000000000000000000000000000000000000000081525081526020017fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff81526020016040518060400160405280600d81526020017f617373657444657461696c49640000000000000000000000000000000000000081525081526020016040518060400160405280600781526020017f706c61636549640000000000000000000000000000000000000000000000000081525081526020016040518060400160405280600f81526020017f6f70657261746f724163636f756e7400000000000000000000000000000000008152508152602001600081526020016040518060400160405280600781526020017f636f6e74656e7400000000000000000000000000000000000000000000000000815250815250905090565b82805461080190610ff8565b90600052602060002090601f016020900481019282610823576000855561086a565b82601f1061083c57805160ff191683800117855561086a565b8280016001018555821561086a579182015b8281111561086957825182559160200191906001019061084e565b5b50905061087791906108b8565b5090565b6040518060e00160405280606081526020016000815260200160608152602001606081526020016060815260200160008152602001606081525090565b5b808211156108d15760008160009055506001016108b9565b5090565b6000604051905090565b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610937826108ee565b810181811067ffffffffffffffff82111715610956576109556108ff565b5b80604052505050565b60006109696108d5565b9050610975828261092e565b919050565b600080fd5b600080fd5b600080fd5b600067ffffffffffffffff8211156109a4576109a36108ff565b5b6109ad826108ee565b9050602081019050919050565b82818337600083830152505050565b60006109dc6109d784610989565b61095f565b9050828152602081018484840111156109f8576109f7610984565b5b610a038482856109ba565b509392505050565b600082601f830112610a2057610a1f61097f565b5b8135610a308482602086016109c9565b91505092915050565b6000819050919050565b610a4c81610a39565b8114610a5757600080fd5b50565b600081359050610a6981610a43565b92915050565b6000819050919050565b610a8281610a6f565b8114610a8d57600080fd5b50565b600081359050610a9f81610a79565b92915050565b600060e08284031215610abb57610aba6108e9565b5b610ac560e061095f565b9050600082013567ffffffffffffffff811115610ae557610ae461097a565b5b610af184828501610a0b565b6000830152506020610b0584828501610a5a565b602083015250604082013567ffffffffffffffff811115610b2957610b2861097a565b5b610b3584828501610a0b565b604083015250606082013567ffffffffffffffff811115610b5957610b5861097a565b5b610b6584828501610a0b565b606083015250608082013567ffffffffffffffff811115610b8957610b8861097a565b5b610b9584828501610a0b565b60808301525060a0610ba984828501610a90565b60a08301525060c082013567ffffffffffffffff811115610bcd57610bcc61097a565b5b610bd984828501610a0b565b60c08301525092915050565b600060208284031215610bfb57610bfa6108df565b5b600082013567ffffffffffffffff811115610c1957610c186108e4565b5b610c2584828501610aa5565b91505092915050565b600060208284031215610c4457610c436108df565b5b600082013567ffffffffffffffff811115610c6257610c616108e4565b5b610c6e84828501610a0b565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610cb1578082015181840152602081019050610c96565b83811115610cc0576000848401525b50505050565b6000610cd182610c77565b610cdb8185610c82565b9350610ceb818560208601610c93565b610cf4816108ee565b840191505092915050565b610d0881610a39565b82525050565b610d1781610a6f565b82525050565b600060e0820190508181036000830152610d37818a610cc6565b9050610d466020830189610cff565b8181036040830152610d588188610cc6565b90508181036060830152610d6c8187610cc6565b90508181036080830152610d808186610cc6565b9050610d8f60a0830185610d0e565b81810360c0830152610da18184610cc6565b905098975050505050505050565b60008115159050919050565b610dc481610daf565b82525050565b6000602082019050610ddf6000830184610dbb565b92915050565b600060208284031215610dfb57610dfa6108df565b5b6000610e0984828501610a90565b91505092915050565b60006020820190508181036000830152610e2c8184610cc6565b905092915050565b600082825260208201905092915050565b6000610e5082610c77565b610e5a8185610e34565b9350610e6a818560208601610c93565b610e73816108ee565b840191505092915050565b610e8781610a39565b82525050565b610e9681610a6f565b82525050565b600060e0830160008301518482036000860152610eb98282610e45565b9150506020830151610ece6020860182610e7e565b5060408301518482036040860152610ee68282610e45565b91505060608301518482036060860152610f008282610e45565b91505060808301518482036080860152610f1a8282610e45565b91505060a0830151610f2f60a0860182610e8d565b5060c083015184820360c0860152610f478282610e45565b9150508091505092915050565b60006020820190508181036000830152610f6e8184610e9c565b905092915050565b600081905092915050565b6000610f8c82610c77565b610f968185610f76565b9350610fa6818560208601610c93565b80840191505092915050565b6000610fbe8284610f81565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061101057607f821691505b6020821081141561102457611023610fc9565b5b5091905056fea2646970667358221220dd78dd772d1fcfe61825a09609b179be6ebc4c0767d49f46396b1c560f239c8564736f6c634300080a0033";

    public static final String FUNC_EVENTINSERTED = "eventInserted";

    public static final String FUNC_EVENTKEY = "eventKey";

    public static final String FUNC_EVENTS = "events";

    public static final String FUNC_GETEVENTTEST = "getEventTest";

    public static final String FUNC_INSERT = "insert";

    @Deprecated
    protected EventContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EventContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EventContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EventContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> eventInserted(String param0) {
        final Function function = new Function(FUNC_EVENTINSERTED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> eventKey(BigInteger param0) {
        final Function function = new Function(FUNC_EVENTKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple7<String, BigInteger, String, String, String, BigInteger, String>> events(String param0) {
        final Function function = new Function(FUNC_EVENTS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple7<String, BigInteger, String, String, String, BigInteger, String>>(function,
                new Callable<Tuple7<String, BigInteger, String, String, String, BigInteger, String>>() {
                    @Override
                    public Tuple7<String, BigInteger, String, String, String, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, BigInteger, String, String, String, BigInteger, String>(
                                (String) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (String) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                (String) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Event> getEventTest() {
        final Function function = new Function(FUNC_GETEVENTTEST,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Event>() {}));
        return executeRemoteCallSingleValueReturn(function, Event.class);
    }

    public RemoteFunctionCall<TransactionReceipt> insert(Event _event) {
        final Function function = new Function(
                FUNC_INSERT,
                Arrays.<Type>asList(_event),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EventContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EventContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EventContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EventContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EventContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EventContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EventContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EventContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EventContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EventContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EventContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EventContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<EventContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EventContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EventContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EventContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Getter
    @Setter
    @ToString
    public static class Event extends DynamicStruct {
        public String id;

        public BigInteger eventType;

        public String assetDetailId;

        public String placeId;

        public String operatorAccount;

        public BigInteger createTime;

        public String content;

        public Event(String id, BigInteger eventType, String assetDetailId, String placeId, String operatorAccount, BigInteger createTime, String content) {
            super(new org.web3j.abi.datatypes.Utf8String(id),new org.web3j.abi.datatypes.generated.Int256(eventType),new org.web3j.abi.datatypes.Utf8String(assetDetailId),new org.web3j.abi.datatypes.Utf8String(placeId),new org.web3j.abi.datatypes.Utf8String(operatorAccount),new org.web3j.abi.datatypes.generated.Uint256(createTime),new org.web3j.abi.datatypes.Utf8String(content));
            this.id = id;
            this.eventType = eventType;
            this.assetDetailId = assetDetailId;
            this.placeId = placeId;
            this.operatorAccount = operatorAccount;
            this.createTime = createTime;
            this.content = content;
        }

        public Event(Utf8String id, Int256 eventType, Utf8String assetDetailId, Utf8String placeId, Utf8String operatorAccount, Uint256 createTime, Utf8String content) {
            super(id,eventType,assetDetailId,placeId,operatorAccount,createTime,content);
            this.id = id.getValue();
            this.eventType = eventType.getValue();
            this.assetDetailId = assetDetailId.getValue();
            this.placeId = placeId.getValue();
            this.operatorAccount = operatorAccount.getValue();
            this.createTime = createTime.getValue();
            this.content = content.getValue();
        }
    }
}
