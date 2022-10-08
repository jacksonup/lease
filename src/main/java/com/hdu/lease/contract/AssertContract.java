package com.hdu.lease.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
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
public class AssertContract extends Contract {
    public static final String BINARY = "608060405260028054905060035534801561001957600080fd5b506109c1806100296000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063395014ea146100515780635615d4a9146100875780635cda9e00146100b7578063757fa396146100e7575b600080fd5b61006b600480360381019061006691906104e7565b610103565b60405161007e97969594939291906105d7565b60405180910390f35b6100a1600480360381019061009c91906104e7565b61026f565b6040516100ae9190610654565b60405180910390f35b6100d160048036038101906100cc91906104e7565b610293565b6040516100de919061066f565b60405180910390f35b61010160048036038101906100fc91906108e1565b6102b3565b005b600060205280600052604060002060009150905080600001549080600101805461012c90610959565b80601f016020809104026020016040519081016040528092919081815260200182805461015890610959565b80156101a55780601f1061017a576101008083540402835291602001916101a5565b820191906000526020600020905b81548152906001019060200180831161018857829003601f168201915b5050505050908060020160009054906101000a900460ff16908060020160019054906101000a900460ff16908060030180546101e090610959565b80601f016020809104026020016040519081016040528092919081815260200182805461020c90610959565b80156102595780601f1061022e57610100808354040283529160200191610259565b820191906000526020600020905b81548152906001019060200180831161023c57829003601f168201915b5050505050908060040154908060050154905087565b6002818154811061027f57600080fd5b906000526020600020016000915090505481565b60016020528060005260406000206000915054906101000a900460ff1681565b600160008260000151815260200190815260200160002060009054906101000a900460ff166103f75780600080836000015181526020019081526020016000206000820151816000015560208201518160010190805190602001906103199291906103fa565b5060408201518160020160006101000a81548160ff02191690831515021790555060608201518160020160016101000a81548160ff02191690831515021790555060808201518160030190805190602001906103769291906103fa565b5060a0820151816004015560c0820151816005015590505060018060008360000151815260200190815260200160002060006101000a81548160ff0219169083151502179055506002816000015190806001815401808255809150506001900390600052602060002001600090919091909150556002805490506003819055505b50565b82805461040690610959565b90600052602060002090601f016020900481019282610428576000855561046f565b82601f1061044157805160ff191683800117855561046f565b8280016001018555821561046f579182015b8281111561046e578251825591602001919060010190610453565b5b50905061047c9190610480565b5090565b5b80821115610499576000816000905550600101610481565b5090565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b6104c4816104b1565b81146104cf57600080fd5b50565b6000813590506104e1816104bb565b92915050565b6000602082840312156104fd576104fc6104a7565b5b600061050b848285016104d2565b91505092915050565b61051d816104b1565b82525050565b600081519050919050565b600082825260208201905092915050565b60005b8381101561055d578082015181840152602081019050610542565b8381111561056c576000848401525b50505050565b6000601f19601f8301169050919050565b600061058e82610523565b610598818561052e565b93506105a881856020860161053f565b6105b181610572565b840191505092915050565b60008115159050919050565b6105d1816105bc565b82525050565b600060e0820190506105ec600083018a610514565b81810360208301526105fe8189610583565b905061060d60408301886105c8565b61061a60608301876105c8565b818103608083015261062c8186610583565b905061063b60a0830185610514565b61064860c0830184610514565b98975050505050505050565b60006020820190506106696000830184610514565b92915050565b600060208201905061068460008301846105c8565b92915050565b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6106c782610572565b810181811067ffffffffffffffff821117156106e6576106e561068f565b5b80604052505050565b60006106f961049d565b905061070582826106be565b919050565b600080fd5b600080fd5b600080fd5b600067ffffffffffffffff8211156107345761073361068f565b5b61073d82610572565b9050602081019050919050565b82818337600083830152505050565b600061076c61076784610719565b6106ef565b90508281526020810184848401111561078857610787610714565b5b61079384828561074a565b509392505050565b600082601f8301126107b0576107af61070f565b5b81356107c0848260208601610759565b91505092915050565b6107d2816105bc565b81146107dd57600080fd5b50565b6000813590506107ef816107c9565b92915050565b600060e0828403121561080b5761080a61068a565b5b61081560e06106ef565b90506000610825848285016104d2565b600083015250602082013567ffffffffffffffff8111156108495761084861070a565b5b6108558482850161079b565b6020830152506040610869848285016107e0565b604083015250606061087d848285016107e0565b606083015250608082013567ffffffffffffffff8111156108a1576108a061070a565b5b6108ad8482850161079b565b60808301525060a06108c1848285016104d2565b60a08301525060c06108d5848285016104d2565b60c08301525092915050565b6000602082840312156108f7576108f66104a7565b5b600082013567ffffffffffffffff811115610915576109146104ac565b5b610921848285016107f5565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061097157607f821691505b602082108114156109855761098461092a565b5b5091905056fea264697066735822122058ae427d1ce688a74937ca4adbd2253972af0d531c1818a563368791edead28f64736f6c634300080a0033";

    public static final String FUNC_ASSERTINSERTED = "assertInserted";

    public static final String FUNC_ASSERTKEY = "assertKey";

    public static final String FUNC_ASSERTS = "asserts";

    public static final String FUNC_CREATEASSERT = "createAssert";

    @Deprecated
    protected AssertContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AssertContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AssertContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AssertContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> assertInserted(BigInteger param0) {
        final Function function = new Function(FUNC_ASSERTINSERTED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> assertKey(BigInteger param0) {
        final Function function = new Function(FUNC_ASSERTKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<BigInteger, String, Boolean, Boolean, String, BigInteger, BigInteger>> asserts(BigInteger param0) {
        final Function function = new Function(FUNC_ASSERTS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple7<BigInteger, String, Boolean, Boolean, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple7<BigInteger, String, Boolean, Boolean, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple7<BigInteger, String, Boolean, Boolean, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, Boolean, Boolean, String, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (Boolean) results.get(2).getValue(),
                                (Boolean) results.get(3).getValue(),
                                (String) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> createAssert(Assert _assert) {
        final Function function = new Function(
                FUNC_CREATEASSERT,
                Arrays.<Type>asList(_assert),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static AssertContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AssertContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AssertContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AssertContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AssertContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AssertContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AssertContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AssertContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AssertContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AssertContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AssertContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AssertContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AssertContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AssertContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AssertContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AssertContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Assert extends DynamicStruct {
        public BigInteger assertId;

        public String assertName;

        public Boolean isApply;

        public Boolean picUrl;

        public String value;

        public BigInteger count;

        public BigInteger status;

        public Assert(BigInteger assertId, String assertName, Boolean isApply, Boolean picUrl, String value, BigInteger count, BigInteger status) {
            super(new org.web3j.abi.datatypes.generated.Uint256(assertId),new org.web3j.abi.datatypes.Utf8String(assertName),new org.web3j.abi.datatypes.Bool(isApply),new org.web3j.abi.datatypes.Bool(picUrl),new org.web3j.abi.datatypes.Utf8String(value),new org.web3j.abi.datatypes.generated.Uint256(count),new org.web3j.abi.datatypes.generated.Uint256(status));
            this.assertId = assertId;
            this.assertName = assertName;
            this.isApply = isApply;
            this.picUrl = picUrl;
            this.value = value;
            this.count = count;
            this.status = status;
        }

        public Assert(Uint256 assertId, Utf8String assertName, Bool isApply, Bool picUrl, Utf8String value, Uint256 count, Uint256 status) {
            super(assertId,assertName,isApply,picUrl,value,count,status);
            this.assertId = assertId.getValue();
            this.assertName = assertName.getValue();
            this.isApply = isApply.getValue();
            this.picUrl = picUrl.getValue();
            this.value = value.getValue();
            this.count = count.getValue();
            this.status = status.getValue();
        }
    }
}
