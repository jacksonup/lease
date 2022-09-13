package com.hdu.lease.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
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
public class User extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506116fa806100206000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c80637c9b7fdd116100665780637c9b7fdd1461019b57806381842aa6146101cd578063be0a6780146101fd578063d02119681461022d578063ee2093091461025d5761009e565b80631459f0f4146100a35780634709161a146100d357806351911a8d14610106578063689e1c03146101365780636f1bd1a51461016b575b600080fd5b6100bd60048036038101906100b89190611128565b61028d565b6040516100ca91906112f2565b60405180910390f35b6100ed60048036038101906100e89190611314565b610602565b6040516100fd949392919061136c565b60405180910390f35b610120600480360381019061011b91906113bf565b610758565b60405161012d9190611437565b60405180910390f35b610150600480360381019061014b9190611314565b61080e565b60405161016296959493929190611452565b60405180910390f35b61018560048036038101906101809190611314565b610a80565b60405161019291906114ea565b60405180910390f35b6101b560048036038101906101b09190611314565b610ab6565b6040516101c493929190611505565b60405180910390f35b6101e760048036038101906101e29190611551565b610c83565b6040516101f491906112f2565b60405180910390f35b61021760048036038101906102129190611551565b610d2f565b60405161022491906112f2565b60405180910390f35b610247600480360381019061024291906113bf565b610ddb565b6040516102549190611437565b60405180910390f35b61027760048036038101906102729190611314565b610e61565b60405161028491906114ea565b60405180910390f35b6060610297610e97565b61029f610ecd565b6000808c8460000181815250508a8460200181905250898460400181905250868460600181905250888460800181905250858460a00181815250508360008c6040516102eb91906115ba565b908152602001604051809103902060008201518160000155602082015181600101908051906020019061031f929190610ef5565b50604082015181600201908051906020019061033c929190610ef5565b506060820151816003019080519060200190610359929190610ef5565b506080820151816004019080519060200190610376929190610ef5565b5060a082015181600501559050508b8360000181815250508a83604001819052508b8360200181815250508783606001819052508260038c6040516103bb91906115ba565b9081526020016040518091039020600082015181600001556020820151816001015560408201518160020190805190602001906103f9929190610ef5565b506060820151816003019080519060200190610416929190610ef5565b5090505060018b60405161042a91906115ba565b908152602001604051809103902060009054906101000a900460ff166104c157600191506001808c60405161045f91906115ba565b908152602001604051809103902060006101000a81548160ff02191690831515021790555060028b9080600181540180825580915050600190039060005260206000200160009091909190915090805190602001906104bf929190610ef5565b505b60048b6040516104d191906115ba565b908152602001604051809103902060009054906101000a900460ff166105695760019050600160048c60405161050791906115ba565b908152602001604051809103902060006101000a81548160ff02191690831515021790555060058b908060018154018082558091505060019003906000526020600020016000909190919091509080519060200190610567929190610ef5565b505b8180156105735750805b156105b9576040518060400160405280600581526020017f31303030300000000000000000000000000000000000000000000000000000008152509450505050506105f6565b6040518060400160405280600581526020017f31303030320000000000000000000000000000000000000000000000000000008152509450505050505b98975050505050505050565b60038180516020810182018051848252602083016020850120818352809550505050505060009150905080600001549080600101549080600201805461064790611600565b80601f016020809104026020016040519081016040528092919081815260200182805461067390611600565b80156106c05780601f10610695576101008083540402835291602001916106c0565b820191906000526020600020905b8154815290600101906020018083116106a357829003601f168201915b5050505050908060030180546106d590611600565b80601f016020809104026020016040519081016040528092919081815260200182805461070190611600565b801561074e5780601f106107235761010080835404028352916020019161074e565b820191906000526020600020905b81548152906001019060200180831161073157829003601f168201915b5050505050905084565b60008060048460405161076b91906115ba565b908152602001604051809103902060009054906101000a900460ff16156107c75760019050826003856040516107a191906115ba565b908152602001604051809103902060030190805190602001906107c5929190610ef5565b505b80610807576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107fe906116a4565b60405180910390fd5b5092915050565b60008180516020810182018051848252602083016020850120818352809550505050505060009150905080600001549080600101805461084d90611600565b80601f016020809104026020016040519081016040528092919081815260200182805461087990611600565b80156108c65780601f1061089b576101008083540402835291602001916108c6565b820191906000526020600020905b8154815290600101906020018083116108a957829003601f168201915b5050505050908060020180546108db90611600565b80601f016020809104026020016040519081016040528092919081815260200182805461090790611600565b80156109545780601f1061092957610100808354040283529160200191610954565b820191906000526020600020905b81548152906001019060200180831161093757829003601f168201915b50505050509080600301805461096990611600565b80601f016020809104026020016040519081016040528092919081815260200182805461099590611600565b80156109e25780601f106109b7576101008083540402835291602001916109e2565b820191906000526020600020905b8154815290600101906020018083116109c557829003601f168201915b5050505050908060040180546109f790611600565b80601f0160208091040260200160405190810160405280929190818152602001828054610a2390611600565b8015610a705780601f10610a4557610100808354040283529160200191610a70565b820191906000526020600020905b815481529060010190602001808311610a5357829003601f168201915b5050505050908060050154905086565b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b6060806060600484604051610acb91906115ba565b908152602001604051809103902060009054906101000a900460ff168015610b1b5750600184604051610afe91906115ba565b908152602001604051809103902060009054906101000a900460ff165b15610c7c57839250600084604051610b3391906115ba565b90815260200160405180910390206002018054610b4f90611600565b80601f0160208091040260200160405190810160405280929190818152602001828054610b7b90611600565b8015610bc85780601f10610b9d57610100808354040283529160200191610bc8565b820191906000526020600020905b815481529060010190602001808311610bab57829003601f168201915b50505050509150600384604051610bdf91906115ba565b90815260200160405180910390206003018054610bfb90611600565b80601f0160208091040260200160405190810160405280929190818152602001828054610c2790611600565b8015610c745780601f10610c4957610100808354040283529160200191610c74565b820191906000526020600020905b815481529060010190602001808311610c5757829003601f168201915b505050505090505b9193909250565b60028181548110610c9357600080fd5b906000526020600020016000915090508054610cae90611600565b80601f0160208091040260200160405190810160405280929190818152602001828054610cda90611600565b8015610d275780601f10610cfc57610100808354040283529160200191610d27565b820191906000526020600020905b815481529060010190602001808311610d0a57829003601f168201915b505050505081565b60058181548110610d3f57600080fd5b906000526020600020016000915090508054610d5a90611600565b80601f0160208091040260200160405190810160405280929190818152602001828054610d8690611600565b8015610dd35780601f10610da857610100808354040283529160200191610dd3565b820191906000526020600020905b815481529060010190602001808311610db657829003601f168201915b505050505081565b600080600184604051610dee91906115ba565b908152602001604051809103902060009054906101000a900460ff1615610e54576001905082600085604051610e2491906115ba565b90815260200160405180910390206003019080519060200190610e48929190610ef5565b50612710915050610e5b565b6127119150505b92915050565b6004818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b6040518060c001604052806000815260200160608152602001606081526020016060815260200160608152602001600081525090565b6040518060800160405280600081526020016000815260200160608152602001606081525090565b828054610f0190611600565b90600052602060002090601f016020900481019282610f235760008555610f6a565b82601f10610f3c57805160ff1916838001178555610f6a565b82800160010185558215610f6a579182015b82811115610f69578251825591602001919060010190610f4e565b5b509050610f779190610f7b565b5090565b5b80821115610f94576000816000905550600101610f7c565b5090565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b610fbf81610fac565b8114610fca57600080fd5b50565b600081359050610fdc81610fb6565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b61103582610fec565b810181811067ffffffffffffffff8211171561105457611053610ffd565b5b80604052505050565b6000611067610f98565b9050611073828261102c565b919050565b600067ffffffffffffffff82111561109357611092610ffd565b5b61109c82610fec565b9050602081019050919050565b82818337600083830152505050565b60006110cb6110c684611078565b61105d565b9050828152602081018484840111156110e7576110e6610fe7565b5b6110f28482856110a9565b509392505050565b600082601f83011261110f5761110e610fe2565b5b813561111f8482602086016110b8565b91505092915050565b600080600080600080600080610100898b03121561114957611148610fa2565b5b60006111578b828c01610fcd565b98505060206111688b828c01610fcd565b975050604089013567ffffffffffffffff81111561118957611188610fa7565b5b6111958b828c016110fa565b965050606089013567ffffffffffffffff8111156111b6576111b5610fa7565b5b6111c28b828c016110fa565b955050608089013567ffffffffffffffff8111156111e3576111e2610fa7565b5b6111ef8b828c016110fa565b94505060a089013567ffffffffffffffff8111156112105761120f610fa7565b5b61121c8b828c016110fa565b93505060c089013567ffffffffffffffff81111561123d5761123c610fa7565b5b6112498b828c016110fa565b92505060e061125a8b828c01610fcd565b9150509295985092959890939650565b600081519050919050565b600082825260208201905092915050565b60005b838110156112a4578082015181840152602081019050611289565b838111156112b3576000848401525b50505050565b60006112c48261126a565b6112ce8185611275565b93506112de818560208601611286565b6112e781610fec565b840191505092915050565b6000602082019050818103600083015261130c81846112b9565b905092915050565b60006020828403121561132a57611329610fa2565b5b600082013567ffffffffffffffff81111561134857611347610fa7565b5b611354848285016110fa565b91505092915050565b61136681610fac565b82525050565b6000608082019050611381600083018761135d565b61138e602083018661135d565b81810360408301526113a081856112b9565b905081810360608301526113b481846112b9565b905095945050505050565b600080604083850312156113d6576113d5610fa2565b5b600083013567ffffffffffffffff8111156113f4576113f3610fa7565b5b611400858286016110fa565b925050602083013567ffffffffffffffff81111561142157611420610fa7565b5b61142d858286016110fa565b9150509250929050565b600060208201905061144c600083018461135d565b92915050565b600060c082019050611467600083018961135d565b818103602083015261147981886112b9565b9050818103604083015261148d81876112b9565b905081810360608301526114a181866112b9565b905081810360808301526114b581856112b9565b90506114c460a083018461135d565b979650505050505050565b60008115159050919050565b6114e4816114cf565b82525050565b60006020820190506114ff60008301846114db565b92915050565b6000606082019050818103600083015261151f81866112b9565b9050818103602083015261153381856112b9565b9050818103604083015261154781846112b9565b9050949350505050565b60006020828403121561156757611566610fa2565b5b600061157584828501610fcd565b91505092915050565b600081905092915050565b60006115948261126a565b61159e818561157e565b93506115ae818560208601611286565b80840191505092915050565b60006115c68284611589565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061161857607f821691505b6020821081141561162c5761162b6115d1565b5b50919050565b7f6661696c656420746f206d6f6469667950686f6e652c7468652075736572206960008201527f736e277420657869737421000000000000000000000000000000000000000000602082015250565b600061168e602b83611275565b915061169982611632565b604082019050919050565b600060208201905081810360008301526116bd81611681565b905091905056fea2646970667358221220fb8e961bc5be45340cc89a7f5818b43d5fd5915597673bb81c7e0f93b185cc9f64736f6c634300080a0033";

    public static final String FUNC_GETUSERINFO = "getUserInfo";

    public static final String FUNC_MODIFYPASSWORDBYACCOUNT = "modifyPasswordByAccount";

    public static final String FUNC_MODIFYPHONEBYACCOUNT = "modifyPhoneByAccount";

    public static final String FUNC_SETUSER = "setUser";

    public static final String FUNC_USERINFOINSERTED = "userInfoInserted";

    public static final String FUNC_USERINFOKEY = "userInfoKey";

    public static final String FUNC_USERINFOS = "userInfos";

    public static final String FUNC_USERINSERTED = "userInserted";

    public static final String FUNC_USERKEY = "userKey";

    public static final String FUNC_USERS = "users";

    @Deprecated
    protected User(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected User(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected User(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected User(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> getUserInfo(String _account) {
        final Function function = new Function(
                FUNC_GETUSERINFO,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_account)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> modifyPasswordByAccount(String _account, String _password) {
        final Function function = new Function(
                FUNC_MODIFYPASSWORDBYACCOUNT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_account),
                        new org.web3j.abi.datatypes.Utf8String(_password)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> modifyPhoneByAccount(String _account, String _phone) {
        final Function function = new Function(
                FUNC_MODIFYPHONEBYACCOUNT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_account),
                        new org.web3j.abi.datatypes.Utf8String(_phone)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setUser(BigInteger _userId, BigInteger _infoId, String _account, String _name, String _salt, String _phone, String _password, BigInteger _role) {
        final Function function = new Function(
                FUNC_SETUSER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_userId),
                        new org.web3j.abi.datatypes.generated.Uint256(_infoId),
                        new org.web3j.abi.datatypes.Utf8String(_account),
                        new org.web3j.abi.datatypes.Utf8String(_name),
                        new org.web3j.abi.datatypes.Utf8String(_salt),
                        new org.web3j.abi.datatypes.Utf8String(_phone),
                        new org.web3j.abi.datatypes.Utf8String(_password),
                        new org.web3j.abi.datatypes.generated.Uint256(_role)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> userInfoInserted(String param0) {
        final Function function = new Function(FUNC_USERINFOINSERTED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> userInfoKey(BigInteger param0) {
        final Function function = new Function(FUNC_USERINFOKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple4<BigInteger, BigInteger, String, String>> userInfos(String param0) {
        final Function function = new Function(FUNC_USERINFOS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, BigInteger, String, String>>(function,
                new Callable<Tuple4<BigInteger, BigInteger, String, String>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, String, String>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> userInserted(String param0) {
        final Function function = new Function(FUNC_USERINSERTED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> userKey(BigInteger param0) {
        final Function function = new Function(FUNC_USERKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple6<BigInteger, String, String, String, String, BigInteger>> users(String param0) {
        final Function function = new Function(FUNC_USERS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, String, String, String, String, BigInteger>>(function,
                new Callable<Tuple6<BigInteger, String, String, String, String, BigInteger>>() {
                    @Override
                    public Tuple6<BigInteger, String, String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, String, String, String, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (String) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    @Deprecated
    public static User load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new User(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static User load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new User(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static User load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new User(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static User load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new User(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<User> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(User.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<User> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(User.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<User> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(User.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<User> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(User.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
