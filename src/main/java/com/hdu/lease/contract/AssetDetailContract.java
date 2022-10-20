package com.hdu.lease.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
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
public class AssetDetailContract extends Contract {
    public static final String BINARY = "608060405260028054905060035534801561001957600080fd5b50610f50806100296000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80630811251a146100465780632e5f4451146100645780634cd446ea14610098575b600080fd5b61004e6100b4565b60405161005b919061084e565b60405180910390f35b61007e600480360381019061007991906109b9565b6102b2565b60405161008f959493929190610a5b565b60405180910390f35b6100b260048036038101906100ad9190610cbf565b610496565b005b6100bc610646565b60006040516100ca90610d5f565b90815260200160405180910390206040518060a00160405290816000820180546100f390610da3565b80601f016020809104026020016040519081016040528092919081815260200182805461011f90610da3565b801561016c5780601f106101415761010080835404028352916020019161016c565b820191906000526020600020905b81548152906001019060200180831161014f57829003601f168201915b5050505050815260200160018201805461018590610da3565b80601f01602080910402602001604051908101604052809291908181526020018280546101b190610da3565b80156101fe5780601f106101d3576101008083540402835291602001916101fe565b820191906000526020600020905b8154815290600101906020018083116101e157829003601f168201915b5050505050815260200160028201805461021790610da3565b80601f016020809104026020016040519081016040528092919081815260200182805461024390610da3565b80156102905780601f1061026557610100808354040283529160200191610290565b820191906000526020600020905b81548152906001019060200180831161027357829003601f168201915b5050505050815260200160038201548152602001600482015481525050905090565b6000818051602081018201805184825260208301602085012081835280955050505050506000915090508060000180546102eb90610da3565b80601f016020809104026020016040519081016040528092919081815260200182805461031790610da3565b80156103645780601f1061033957610100808354040283529160200191610364565b820191906000526020600020905b81548152906001019060200180831161034757829003601f168201915b50505050509080600101805461037990610da3565b80601f01602080910402602001604051908101604052809291908181526020018280546103a590610da3565b80156103f25780601f106103c7576101008083540402835291602001916103f2565b820191906000526020600020905b8154815290600101906020018083116103d557829003601f168201915b50505050509080600201805461040790610da3565b80601f016020809104026020016040519081016040528092919081815260200182805461043390610da3565b80156104805780601f1061045557610100808354040283529160200191610480565b820191906000526020600020905b81548152906001019060200180831161046357829003601f168201915b5050505050908060030154908060040154905085565b606060005b8251811015610641578281815181106104b7576104b6610dd5565b5b60200260200101516000015191506001826040516104d59190610e35565b908152602001604051809103902060009054906101000a900460ff166106285782818151811061050857610507610dd5565b5b60200260200101516000836040516105209190610e35565b9081526020016040518091039020600082015181600001908051906020019061054a929190610675565b506020820151816001019080519060200190610567929190610675565b506040820151816002019080519060200190610584929190610675565b506060820151816003015560808201518160040155905050600180836040516105ad9190610e35565b908152602001604051809103902060006101000a81548160ff02191690831515021790555060028290806001815401808255809150506001900390600052602060002001600090919091909150908051906020019061060d929190610675565b50600160035461061d9190610e7b565b60038190555061062d565b61062e565b5b808061063990610ed1565b91505061049b565b505050565b6040518060a0016040528060608152602001606081526020016060815260200160008152602001600081525090565b82805461068190610da3565b90600052602060002090601f0160209004810192826106a357600085556106ea565b82601f106106bc57805160ff19168380011785556106ea565b828001600101855582156106ea579182015b828111156106e95782518255916020019190600101906106ce565b5b5090506106f791906106fb565b5090565b5b808211156107145760008160009055506001016106fc565b5090565b600081519050919050565b600082825260208201905092915050565b60005b83811015610752578082015181840152602081019050610737565b83811115610761576000848401525b50505050565b6000601f19601f8301169050919050565b600061078382610718565b61078d8185610723565b935061079d818560208601610734565b6107a681610767565b840191505092915050565b6000819050919050565b6107c4816107b1565b82525050565b600060a08301600083015184820360008601526107e78282610778565b915050602083015184820360208601526108018282610778565b9150506040830151848203604086015261081b8282610778565b915050606083015161083060608601826107bb565b50608083015161084360808601826107bb565b508091505092915050565b6000602082019050818103600083015261086881846107ca565b905092915050565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6108c682610767565b810181811067ffffffffffffffff821117156108e5576108e461088e565b5b80604052505050565b60006108f8610870565b905061090482826108bd565b919050565b600067ffffffffffffffff8211156109245761092361088e565b5b61092d82610767565b9050602081019050919050565b82818337600083830152505050565b600061095c61095784610909565b6108ee565b90508281526020810184848401111561097857610977610889565b5b61098384828561093a565b509392505050565b600082601f8301126109a05761099f610884565b5b81356109b0848260208601610949565b91505092915050565b6000602082840312156109cf576109ce61087a565b5b600082013567ffffffffffffffff8111156109ed576109ec61087f565b5b6109f98482850161098b565b91505092915050565b600082825260208201905092915050565b6000610a1e82610718565b610a288185610a02565b9350610a38818560208601610734565b610a4181610767565b840191505092915050565b610a55816107b1565b82525050565b600060a0820190508181036000830152610a758188610a13565b90508181036020830152610a898187610a13565b90508181036040830152610a9d8186610a13565b9050610aac6060830185610a4c565b610ab96080830184610a4c565b9695505050505050565b600067ffffffffffffffff821115610ade57610add61088e565b5b602082029050602081019050919050565b600080fd5b600080fd5b600080fd5b610b07816107b1565b8114610b1257600080fd5b50565b600081359050610b2481610afe565b92915050565b600060a08284031215610b4057610b3f610af4565b5b610b4a60a06108ee565b9050600082013567ffffffffffffffff811115610b6a57610b69610af9565b5b610b768482850161098b565b600083015250602082013567ffffffffffffffff811115610b9a57610b99610af9565b5b610ba68482850161098b565b602083015250604082013567ffffffffffffffff811115610bca57610bc9610af9565b5b610bd68482850161098b565b6040830152506060610bea84828501610b15565b6060830152506080610bfe84828501610b15565b60808301525092915050565b6000610c1d610c1884610ac3565b6108ee565b90508083825260208201905060208402830185811115610c4057610c3f610aef565b5b835b81811015610c8757803567ffffffffffffffff811115610c6557610c64610884565b5b808601610c728982610b2a565b85526020850194505050602081019050610c42565b5050509392505050565b600082601f830112610ca657610ca5610884565b5b8135610cb6848260208601610c0a565b91505092915050565b600060208284031215610cd557610cd461087a565b5b600082013567ffffffffffffffff811115610cf357610cf261087f565b5b610cff84828501610c91565b91505092915050565b600081905092915050565b7f3100000000000000000000000000000000000000000000000000000000000000600082015250565b6000610d49600183610d08565b9150610d5482610d13565b600182019050919050565b6000610d6a82610d3c565b9150819050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680610dbb57607f821691505b60208210811415610dcf57610dce610d74565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b6000610e0f82610718565b610e198185610d08565b9350610e29818560208601610734565b80840191505092915050565b6000610e418284610e04565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000610e86826107b1565b9150610e91836107b1565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff03821115610ec657610ec5610e4c565b5b828201905092915050565b6000610edc826107b1565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff821415610f0f57610f0e610e4c565b5b60018201905091905056fea2646970667358221220c6d403492102347ae1b83291442fa1e5461d3a697f0ad8424a097fba31f17fa164736f6c634300080a0033";

    public static final String FUNC_ASSETDETAILS = "assetDetails";

    public static final String FUNC_GETASSETDETAIL = "getAssetDetail";

    public static final String FUNC_INSERTASSETDETAIL = "insertAssetDetail";

    @Deprecated
    protected AssetDetailContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AssetDetailContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AssetDetailContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AssetDetailContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Tuple5<String, String, String, BigInteger, BigInteger>> assetDetails(String param0) {
        final Function function = new Function(FUNC_ASSETDETAILS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<String, String, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple5<String, String, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<String, String, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<AssetDetail> getAssetDetail() {
        final Function function = new Function(FUNC_GETASSETDETAIL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<AssetDetail>() {}));
        return executeRemoteCallSingleValueReturn(function, AssetDetail.class);
    }

    public RemoteFunctionCall<TransactionReceipt> insertAssetDetail(List<AssetDetail> _assetDetailList) {
        final Function function = new Function(
                FUNC_INSERTASSETDETAIL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<AssetDetail>(AssetDetail.class, _assetDetailList)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static AssetDetailContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AssetDetailContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AssetDetailContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AssetDetailContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AssetDetailContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AssetDetailContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AssetDetailContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AssetDetailContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AssetDetailContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AssetDetailContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AssetDetailContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AssetDetailContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AssetDetailContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AssetDetailContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AssetDetailContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AssetDetailContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class AssetDetail extends DynamicStruct {
        public String assetDetailId;

        public String currentUserAccount;

        public String assetId;

        public BigInteger currentStatus;

        public BigInteger status;

        public AssetDetail(String assetDetailId, String currentUserAccount, String assetId, BigInteger currentStatus, BigInteger status) {
            super(new org.web3j.abi.datatypes.Utf8String(assetDetailId),new org.web3j.abi.datatypes.Utf8String(currentUserAccount),new org.web3j.abi.datatypes.Utf8String(assetId),new org.web3j.abi.datatypes.generated.Uint256(currentStatus),new org.web3j.abi.datatypes.generated.Uint256(status));
            this.assetDetailId = assetDetailId;
            this.currentUserAccount = currentUserAccount;
            this.assetId = assetId;
            this.currentStatus = currentStatus;
            this.status = status;
        }

        public AssetDetail(Utf8String assetDetailId, Utf8String currentUserAccount, Utf8String assetId, Uint256 currentStatus, Uint256 status) {
            super(assetDetailId,currentUserAccount,assetId,currentStatus,status);
            this.assetDetailId = assetDetailId.getValue();
            this.currentUserAccount = currentUserAccount.getValue();
            this.assetId = assetId.getValue();
            this.currentStatus = currentStatus.getValue();
            this.status = status.getValue();
        }
    }
}

