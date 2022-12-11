package com.hdu.lease.contract;
import java.math.BigInteger;
import java.util.Arrays;
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
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tuples.generated.Tuple6;
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
public class NoticeContract extends Contract {
    public static final String BINARY = "608060405260028054905060035534801561001957600080fd5b50610d74806100296000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80631cc3b64e14610051578063563713b61461006f578063912ac5d91461009f578063d2a12040146100cf575b600080fd5b610059610104565b60405161006691906108c9565b60405180910390f35b6100896004803603810190610084919061092b565b6103c5565b60405161009691906109a2565b60405180910390f35b6100b960048036038101906100b49190610af9565b610471565b6040516100c69190610b51565b60405180910390f35b6100e960048036038101906100e49190610af9565b6104a7565b6040516100fb96959493929190610b7b565b60405180910390f35b61010c610726565b6000600260008154811061012357610122610bf8565b5b9060005260206000200160405161013a9190610d27565b90815260200160405180910390206040518060c001604052908160008201805461016390610c56565b80601f016020809104026020016040519081016040528092919081815260200182805461018f90610c56565b80156101dc5780601f106101b1576101008083540402835291602001916101dc565b820191906000526020600020905b8154815290600101906020018083116101bf57829003601f168201915b505050505081526020016001820180546101f590610c56565b80601f016020809104026020016040519081016040528092919081815260200182805461022190610c56565b801561026e5780601f106102435761010080835404028352916020019161026e565b820191906000526020600020905b81548152906001019060200180831161025157829003601f168201915b5050505050815260200160028201805461028790610c56565b80601f01602080910402602001604051908101604052809291908181526020018280546102b390610c56565b80156103005780601f106102d557610100808354040283529160200191610300565b820191906000526020600020905b8154815290600101906020018083116102e357829003601f168201915b50505050508152602001600382015481526020016004820160009054906101000a900460ff1615151515815260200160058201805461033e90610c56565b80601f016020809104026020016040519081016040528092919081815260200182805461036a90610c56565b80156103b75780601f1061038c576101008083540402835291602001916103b7565b820191906000526020600020905b81548152906001019060200180831161039a57829003601f168201915b505050505081525050905090565b600281815481106103d557600080fd5b9060005260206000200160009150905080546103f090610c56565b80601f016020809104026020016040519081016040528092919081815260200182805461041c90610c56565b80156104695780601f1061043e57610100808354040283529160200191610469565b820191906000526020600020905b81548152906001019060200180831161044c57829003601f168201915b505050505081565b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b6000818051602081018201805184825260208301602085012081835280955050505050506000915090508060000180546104e090610c56565b80601f016020809104026020016040519081016040528092919081815260200182805461050c90610c56565b80156105595780601f1061052e57610100808354040283529160200191610559565b820191906000526020600020905b81548152906001019060200180831161053c57829003601f168201915b50505050509080600101805461056e90610c56565b80601f016020809104026020016040519081016040528092919081815260200182805461059a90610c56565b80156105e75780601f106105bc576101008083540402835291602001916105e7565b820191906000526020600020905b8154815290600101906020018083116105ca57829003601f168201915b5050505050908060020180546105fc90610c56565b80601f016020809104026020016040519081016040528092919081815260200182805461062890610c56565b80156106755780601f1061064a57610100808354040283529160200191610675565b820191906000526020600020905b81548152906001019060200180831161065857829003601f168201915b5050505050908060030154908060040160009054906101000a900460ff16908060050180546106a390610c56565b80601f01602080910402602001604051908101604052809291908181526020018280546106cf90610c56565b801561071c5780601f106106f15761010080835404028352916020019161071c565b820191906000526020600020905b8154815290600101906020018083116106ff57829003601f168201915b5050505050905086565b6040518060c0016040528060608152602001606081526020016060815260200160008152602001600015158152602001606081525090565b600081519050919050565b600082825260208201905092915050565b60005b8381101561079857808201518184015260208101905061077d565b838111156107a7576000848401525b50505050565b6000601f19601f8301169050919050565b60006107c98261075e565b6107d38185610769565b93506107e381856020860161077a565b6107ec816107ad565b840191505092915050565b6000819050919050565b61080a816107f7565b82525050565b60008115159050919050565b61082581610810565b82525050565b600060c083016000830151848203600086015261084882826107be565b9150506020830151848203602086015261086282826107be565b9150506040830151848203604086015261087c82826107be565b91505060608301516108916060860182610801565b5060808301516108a4608086018261081c565b5060a083015184820360a08601526108bc82826107be565b9150508091505092915050565b600060208201905081810360008301526108e3818461082b565b905092915050565b6000604051905090565b600080fd5b600080fd5b610908816107f7565b811461091357600080fd5b50565b600081359050610925816108ff565b92915050565b600060208284031215610941576109406108f5565b5b600061094f84828501610916565b91505092915050565b600082825260208201905092915050565b60006109748261075e565b61097e8185610958565b935061098e81856020860161077a565b610997816107ad565b840191505092915050565b600060208201905081810360008301526109bc8184610969565b905092915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610a06826107ad565b810181811067ffffffffffffffff82111715610a2557610a246109ce565b5b80604052505050565b6000610a386108eb565b9050610a4482826109fd565b919050565b600067ffffffffffffffff821115610a6457610a636109ce565b5b610a6d826107ad565b9050602081019050919050565b82818337600083830152505050565b6000610a9c610a9784610a49565b610a2e565b905082815260208101848484011115610ab857610ab76109c9565b5b610ac3848285610a7a565b509392505050565b600082601f830112610ae057610adf6109c4565b5b8135610af0848260208601610a89565b91505092915050565b600060208284031215610b0f57610b0e6108f5565b5b600082013567ffffffffffffffff811115610b2d57610b2c6108fa565b5b610b3984828501610acb565b91505092915050565b610b4b81610810565b82525050565b6000602082019050610b666000830184610b42565b92915050565b610b75816107f7565b82525050565b600060c0820190508181036000830152610b958189610969565b90508181036020830152610ba98188610969565b90508181036040830152610bbd8187610969565b9050610bcc6060830186610b6c565b610bd96080830185610b42565b81810360a0830152610beb8184610969565b9050979650505050505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680610c6e57607f821691505b60208210811415610c8257610c81610c27565b5b50919050565b600081905092915050565b60008190508160005260206000209050919050565b60008154610cb581610c56565b610cbf8186610c88565b94506001821660008114610cda5760018114610ceb57610d1e565b60ff19831686528186019350610d1e565b610cf485610c93565b60005b83811015610d1657815481890152600182019150602081019050610cf7565b838801955050505b50505092915050565b6000610d338284610ca8565b91508190509291505056fea2646970667358221220c81bb91494bc56375e52135268d023b94b209086be9841e1bc2407b669c87c8064736f6c634300080a0033";

    public static final String FUNC_GETNOTICE1 = "getNotice1";

    public static final String FUNC_NOTICEINSERTED = "noticeInserted";

    public static final String FUNC_NOTICEKEY = "noticeKey";

    public static final String FUNC_NOTICES = "notices";

    @Deprecated
    protected NoticeContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NoticeContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NoticeContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NoticeContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Notice> getNotice1() {
        final Function function = new Function(FUNC_GETNOTICE1,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Notice>() {}));
        return executeRemoteCallSingleValueReturn(function, Notice.class);
    }

    public RemoteFunctionCall<Boolean> noticeInserted(String param0) {
        final Function function = new Function(FUNC_NOTICEINSERTED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> noticeKey(BigInteger param0) {
        final Function function = new Function(FUNC_NOTICEKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple6<String, String, String, BigInteger, Boolean, String>> notices(String param0) {
        final Function function = new Function(FUNC_NOTICES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple6<String, String, String, BigInteger, Boolean, String>>(function,
                new Callable<Tuple6<String, String, String, BigInteger, Boolean, String>>() {
                    @Override
                    public Tuple6<String, String, String, BigInteger, Boolean, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, BigInteger, Boolean, String>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (Boolean) results.get(4).getValue(),
                                (String) results.get(5).getValue());
                    }
                });
    }

    @Deprecated
    public static NoticeContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NoticeContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NoticeContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NoticeContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NoticeContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NoticeContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NoticeContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NoticeContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NoticeContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NoticeContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NoticeContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NoticeContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<NoticeContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NoticeContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NoticeContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NoticeContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Getter
    @Setter
    @ToString
    public static class Notice extends DynamicStruct {
        public String id;

        public String noticeType;

        public String noticerAccount;

        public BigInteger createTime;

        public Boolean isRead;

        public String content;

        public Notice(String id, String noticeType, String noticerAccount, BigInteger createTime, Boolean isRead, String content) {
            super(new org.web3j.abi.datatypes.Utf8String(id),new org.web3j.abi.datatypes.Utf8String(noticeType),new org.web3j.abi.datatypes.Utf8String(noticerAccount),new org.web3j.abi.datatypes.generated.Uint256(createTime),new org.web3j.abi.datatypes.Bool(isRead),new org.web3j.abi.datatypes.Utf8String(content));
            this.id = id;
            this.noticeType = noticeType;
            this.noticerAccount = noticerAccount;
            this.createTime = createTime;
            this.isRead = isRead;
            this.content = content;
        }

        public Notice(Utf8String id, Utf8String noticeType, Utf8String noticerAccount, Uint256 createTime, Bool isRead, Utf8String content) {
            super(id,noticeType,noticerAccount,createTime,isRead,content);
            this.id = id.getValue();
            this.noticeType = noticeType.getValue();
            this.noticerAccount = noticerAccount.getValue();
            this.createTime = createTime.getValue();
            this.isRead = isRead.getValue();
            this.content = content.getValue();
        }
    }
}