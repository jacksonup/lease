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
import org.web3j.abi.datatypes.DynamicArray;
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
public class PlaceAssetContract extends Contract {
    public static final String BINARY = "608060405260028054905060035534801561001957600080fd5b50611c94806100296000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c8063637bf51a11610066578063637bf51a146101855780637ce7f1b7146101b5578063c65612da146101e5578063e31bcfca14610203578063fd346af6146102335761009e565b806309bfe26d146100a357806317026c95146100d35780631f7b6d3214610103578063465c4105146101215780634a69a74714610151575b600080fd5b6100bd60048036038101906100b89190611255565b61024f565b6040516100ca919061131b565b60405180910390f35b6100ed60048036038101906100e89190611472565b6102fb565b6040516100fa91906115c7565b60405180910390f35b61010b6105ee565b60405161011891906115f8565b60405180910390f35b61013b60048036038101906101369190611613565b6105f4565b60405161014891906116a6565b60405180910390f35b61016b60048036038101906101669190611472565b6106d1565b60405161017c9594939291906116c1565b60405180910390f35b61019f600480360381019061019a9190611472565b6108b5565b6040516101ac91906116a6565b60405180910390f35b6101cf60048036038101906101ca9190611472565b6108eb565b6040516101dc91906116a6565b60405180910390f35b6101ed6109fa565b6040516101fa91906117bc565b60405180910390f35b61021d60048036038101906102189190611472565b610bf8565b60405161022a91906115c7565b60405180910390f35b61024d600480360381019061024891906119ae565b610f3c565b005b6002818154811061025f57600080fd5b90600052602060002001600091509050805461027a90611a26565b80601f01602080910402602001604051908101604052809291908181526020018280546102a690611a26565b80156102f35780601f106102c8576101008083540402835291602001916102f3565b820191906000526020600020905b8154815290600101906020018083116102d657829003601f168201915b505050505081565b60606000603267ffffffffffffffff81111561031a57610319611347565b5b60405190808252806020026020018201604052801561034d57816020015b60608152602001906001900390816103385790505b5090506000805b6003548110156105385761042e60006002838154811061037757610376611a58565b5b9060005260206000200160405161038e9190611b26565b908152602001604051809103902060020180546103aa90611a26565b80601f01602080910402602001604051908101604052809291908181526020018280546103d690611a26565b80156104235780601f106103f857610100808354040283529160200191610423565b820191906000526020600020905b81548152906001019060200180831161040657829003601f168201915b5050505050866105f4565b156105255760006002828154811061044957610448611a58565b5b906000526020600020016040516104609190611b26565b9081526020016040518091039020600101805461047c90611a26565b80601f01602080910402602001604051908101604052809291908181526020018280546104a890611a26565b80156104f55780601f106104ca576101008083540402835291602001916104f5565b820191906000526020600020905b8154815290600101906020018083116104d857829003601f168201915b505050505083838061050690611b6c565b94508151811061051957610518611a58565b5b60200260200101819052505b808061053090611b6c565b915050610354565b5060008167ffffffffffffffff81111561055557610554611347565b5b60405190808252806020026020018201604052801561058857816020015b60608152602001906001900390816105735790505b50905060005b828110156105e2578381815181106105a9576105a8611a58565b5b60200260200101518282815181106105c4576105c3611a58565b5b602002602001018190525080806105da90611b6c565b91505061058e565b50809350505050919050565b60035481565b60008083905060008390508051825114610613576000925050506106cb565b60005b82518110156106c35781818151811061063257610631611a58565b5b602001015160f81c60f81b7effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191683828151811061067257610671611a58565b5b602001015160f81c60f81b7effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916146106b057600093505050506106cb565b80806106bb90611b6c565b915050610616565b506001925050505b92915050565b60008180516020810182018051848252602083016020850120818352809550505050505060009150905080600001805461070a90611a26565b80601f016020809104026020016040519081016040528092919081815260200182805461073690611a26565b80156107835780601f1061075857610100808354040283529160200191610783565b820191906000526020600020905b81548152906001019060200180831161076657829003601f168201915b50505050509080600101805461079890611a26565b80601f01602080910402602001604051908101604052809291908181526020018280546107c490611a26565b80156108115780601f106107e657610100808354040283529160200191610811565b820191906000526020600020905b8154815290600101906020018083116107f457829003601f168201915b50505050509080600201805461082690611a26565b80601f016020809104026020016040519081016040528092919081815260200182805461085290611a26565b801561089f5780601f106108745761010080835404028352916020019161089f565b820191906000526020600020905b81548152906001019060200180831161088257829003601f168201915b5050505050908060030154908060040154905085565b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b6000806000905060005b6003548110156109f0576109cf60006002838154811061091857610917611a58565b5b9060005260206000200160405161092f9190611b26565b9081526020016040518091039020600101805461094b90611a26565b80601f016020809104026020016040519081016040528092919081815260200182805461097790611a26565b80156109c45780601f10610999576101008083540402835291602001916109c4565b820191906000526020600020905b8154815290600101906020018083116109a757829003601f168201915b5050505050856105f4565b156109dd57600191506109f0565b80806109e890611b6c565b9150506108f5565b5080915050919050565b610a02611139565b6000604051610a1090611c01565b90815260200160405180910390206040518060a0016040529081600082018054610a3990611a26565b80601f0160208091040260200160405190810160405280929190818152602001828054610a6590611a26565b8015610ab25780601f10610a8757610100808354040283529160200191610ab2565b820191906000526020600020905b815481529060010190602001808311610a9557829003601f168201915b50505050508152602001600182018054610acb90611a26565b80601f0160208091040260200160405190810160405280929190818152602001828054610af790611a26565b8015610b445780601f10610b1957610100808354040283529160200191610b44565b820191906000526020600020905b815481529060010190602001808311610b2757829003601f168201915b50505050508152602001600282018054610b5d90611a26565b80601f0160208091040260200160405190810160405280929190818152602001828054610b8990611a26565b8015610bd65780601f10610bab57610100808354040283529160200191610bd6565b820191906000526020600020905b815481529060010190602001808311610bb957829003601f168201915b5050505050815260200160038201548152602001600482015481525050905090565b60606000603267ffffffffffffffff811115610c1757610c16611347565b5b604051908082528060200260200182016040528015610c4a57816020015b6060815260200190600190039081610c355790505b5090506000805b600354811015610e8657600160028281548110610c7157610c70611a58565b5b90600052602060002001604051610c889190611b26565b908152602001604051809103902060009054906101000a900460ff168015610d7c5750610d7b600060028381548110610cc457610cc3611a58565b5b90600052602060002001604051610cdb9190611b26565b90815260200160405180910390206001018054610cf790611a26565b80601f0160208091040260200160405190810160405280929190818152602001828054610d2390611a26565b8015610d705780601f10610d4557610100808354040283529160200191610d70565b820191906000526020600020905b815481529060010190602001808311610d5357829003601f168201915b5050505050866105f4565b5b15610e7357600060028281548110610d9757610d96611a58565b5b90600052602060002001604051610dae9190611b26565b90815260200160405180910390206002018054610dca90611a26565b80601f0160208091040260200160405190810160405280929190818152602001828054610df690611a26565b8015610e435780601f10610e1857610100808354040283529160200191610e43565b820191906000526020600020905b815481529060010190602001808311610e2657829003601f168201915b5050505050838380610e5490611b6c565b945081518110610e6757610e66611a58565b5b60200260200101819052505b8080610e7e90611b6c565b915050610c51565b5060008167ffffffffffffffff811115610ea357610ea2611347565b5b604051908082528060200260200182016040528015610ed657816020015b6060815260200190600190039081610ec15790505b50905060005b82811015610f3057838181518110610ef757610ef6611a58565b5b6020026020010151828281518110610f1257610f11611a58565b5b60200260200101819052508080610f2890611b6c565b915050610edc565b50809350505050919050565b60005b8151811015611135576001828281518110610f5d57610f5c611a58565b5b602002602001015160000151604051610f769190611c47565b908152602001604051809103902060009054906101000a900460ff1661112257818181518110610fa957610fa8611a58565b5b60200260200101516000838381518110610fc657610fc5611a58565b5b602002602001015160000151604051610fdf9190611c47565b90815260200160405180910390206000820151816000019080519060200190611009929190611168565b506020820151816001019080519060200190611026929190611168565b506040820151816002019080519060200190611043929190611168565b50606082015181600301556080820151816004015590505060018083838151811061107157611070611a58565b5b60200260200101516000015160405161108a9190611c47565b908152602001604051809103902060006101000a81548160ff02191690831515021790555060028282815181106110c4576110c3611a58565b5b602002602001015160000151908060018154018082558091505060019003906000526020600020016000909190919091509080519060200190611108929190611168565b506003600081548092919061111c90611b6c565b91905055505b808061112d90611b6c565b915050610f3f565b5050565b6040518060a0016040528060608152602001606081526020016060815260200160008152602001600081525090565b82805461117490611a26565b90600052602060002090601f01602090048101928261119657600085556111dd565b82601f106111af57805160ff19168380011785556111dd565b828001600101855582156111dd579182015b828111156111dc5782518255916020019190600101906111c1565b5b5090506111ea91906111ee565b5090565b5b808211156112075760008160009055506001016111ef565b5090565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b6112328161121f565b811461123d57600080fd5b50565b60008135905061124f81611229565b92915050565b60006020828403121561126b5761126a611215565b5b600061127984828501611240565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b838110156112bc5780820151818401526020810190506112a1565b838111156112cb576000848401525b50505050565b6000601f19601f8301169050919050565b60006112ed82611282565b6112f7818561128d565b935061130781856020860161129e565b611310816112d1565b840191505092915050565b6000602082019050818103600083015261133581846112e2565b905092915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b61137f826112d1565b810181811067ffffffffffffffff8211171561139e5761139d611347565b5b80604052505050565b60006113b161120b565b90506113bd8282611376565b919050565b600067ffffffffffffffff8211156113dd576113dc611347565b5b6113e6826112d1565b9050602081019050919050565b82818337600083830152505050565b6000611415611410846113c2565b6113a7565b90508281526020810184848401111561143157611430611342565b5b61143c8482856113f3565b509392505050565b600082601f8301126114595761145861133d565b5b8135611469848260208601611402565b91505092915050565b60006020828403121561148857611487611215565b5b600082013567ffffffffffffffff8111156114a6576114a561121a565b5b6114b284828501611444565b91505092915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600082825260208201905092915050565b600061150382611282565b61150d81856114e7565b935061151d81856020860161129e565b611526816112d1565b840191505092915050565b600061153d83836114f8565b905092915050565b6000602082019050919050565b600061155d826114bb565b61156781856114c6565b935083602082028501611579856114d7565b8060005b858110156115b557848403895281516115968582611531565b94506115a183611545565b925060208a0199505060018101905061157d565b50829750879550505050505092915050565b600060208201905081810360008301526115e18184611552565b905092915050565b6115f28161121f565b82525050565b600060208201905061160d60008301846115e9565b92915050565b6000806040838503121561162a57611629611215565b5b600083013567ffffffffffffffff8111156116485761164761121a565b5b61165485828601611444565b925050602083013567ffffffffffffffff8111156116755761167461121a565b5b61168185828601611444565b9150509250929050565b60008115159050919050565b6116a08161168b565b82525050565b60006020820190506116bb6000830184611697565b92915050565b600060a08201905081810360008301526116db81886112e2565b905081810360208301526116ef81876112e2565b9050818103604083015261170381866112e2565b905061171260608301856115e9565b61171f60808301846115e9565b9695505050505050565b6117328161121f565b82525050565b600060a083016000830151848203600086015261175582826114f8565b9150506020830151848203602086015261176f82826114f8565b9150506040830151848203604086015261178982826114f8565b915050606083015161179e6060860182611729565b5060808301516117b16080860182611729565b508091505092915050565b600060208201905081810360008301526117d68184611738565b905092915050565b600067ffffffffffffffff8211156117f9576117f8611347565b5b602082029050602081019050919050565b600080fd5b600080fd5b600080fd5b600060a0828403121561182f5761182e61180f565b5b61183960a06113a7565b9050600082013567ffffffffffffffff81111561185957611858611814565b5b61186584828501611444565b600083015250602082013567ffffffffffffffff81111561188957611888611814565b5b61189584828501611444565b602083015250604082013567ffffffffffffffff8111156118b9576118b8611814565b5b6118c584828501611444565b60408301525060606118d984828501611240565b60608301525060806118ed84828501611240565b60808301525092915050565b600061190c611907846117de565b6113a7565b9050808382526020820190506020840283018581111561192f5761192e61180a565b5b835b8181101561197657803567ffffffffffffffff8111156119545761195361133d565b5b8086016119618982611819565b85526020850194505050602081019050611931565b5050509392505050565b600082601f8301126119955761199461133d565b5b81356119a58482602086016118f9565b91505092915050565b6000602082840312156119c4576119c3611215565b5b600082013567ffffffffffffffff8111156119e2576119e161121a565b5b6119ee84828501611980565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680611a3e57607f821691505b60208210811415611a5257611a516119f7565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600081905092915050565b60008190508160005260206000209050919050565b60008154611ab481611a26565b611abe8186611a87565b94506001821660008114611ad95760018114611aea57611b1d565b60ff19831686528186019350611b1d565b611af385611a92565b60005b83811015611b1557815481890152600182019150602081019050611af6565b838801955050505b50505092915050565b6000611b328284611aa7565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611b778261121f565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff821415611baa57611ba9611b3d565b5b600182019050919050565b7f3100000000000000000000000000000000000000000000000000000000000000600082015250565b6000611beb600183611a87565b9150611bf682611bb5565b600182019050919050565b6000611c0c82611bde565b9150819050919050565b6000611c2182611282565b611c2b8185611a87565b9350611c3b81856020860161129e565b80840191505092915050565b6000611c538284611c16565b91508190509291505056fea264697066735822122097677258e731f6060e95555a14a4017dd4adb03b31db64549d0817bba760621464736f6c634300080a0033";

    public static final String FUNC_BINDASSET = "bindAsset";

    public static final String FUNC_GETASSETIDLISTBYPLACEID = "getAssetIdListByPlaceId";

    public static final String FUNC_GETPLACEASSET = "getPlaceAsset";

    public static final String FUNC_GETPLACELISTBYASSETID = "getPlaceListByAssetId";

    public static final String FUNC_ISEQUAL = "isEqual";

    public static final String FUNC_ISPLACEBINDASSET = "isPlaceBindAsset";

    public static final String FUNC_LENGTH = "length";

    public static final String FUNC_PLACEASSETINSERTED = "placeAssetInserted";

    public static final String FUNC_PLACEASSETKEY = "placeAssetKey";

    public static final String FUNC_PLACEASSETS = "placeAssets";

    @Deprecated
    protected PlaceAssetContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PlaceAssetContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PlaceAssetContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PlaceAssetContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> bindAsset(List<PlaceAsset> _placeAssets) {
        final Function function = new Function(
                FUNC_BINDASSET, 
                Arrays.<Type>asList(new DynamicArray<PlaceAsset>(PlaceAsset.class, _placeAssets)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAssetIdListByPlaceId(String _placeId) {
        final Function function = new Function(FUNC_GETASSETIDLISTBYPLACEID, 
                Arrays.<Type>asList(new Utf8String(_placeId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
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

    public RemoteFunctionCall<PlaceAsset> getPlaceAsset() {
        final Function function = new Function(FUNC_GETPLACEASSET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<PlaceAsset>() {}));
        return executeRemoteCallSingleValueReturn(function, PlaceAsset.class);
    }

    public RemoteFunctionCall<List> getPlaceListByAssetId(String _assetId) {
        final Function function = new Function(FUNC_GETPLACELISTBYASSETID, 
                Arrays.<Type>asList(new Utf8String(_assetId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
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

    public RemoteFunctionCall<Boolean> isEqual(String a, String b) {
        final Function function = new Function(FUNC_ISEQUAL, 
                Arrays.<Type>asList(new Utf8String(a),
                new Utf8String(b)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isPlaceBindAsset(String _placeId) {
        final Function function = new Function(FUNC_ISPLACEBINDASSET, 
                Arrays.<Type>asList(new Utf8String(_placeId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> length() {
        final Function function = new Function(FUNC_LENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> placeAssetInserted(String param0) {
        final Function function = new Function(FUNC_PLACEASSETINSERTED, 
                Arrays.<Type>asList(new Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> placeAssetKey(BigInteger param0) {
        final Function function = new Function(FUNC_PLACEASSETKEY, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple5<String, String, String, BigInteger, BigInteger>> placeAssets(String param0) {
        final Function function = new Function(FUNC_PLACEASSETS, 
                Arrays.<Type>asList(new Utf8String(param0)),
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

    @Deprecated
    public static PlaceAssetContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PlaceAssetContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PlaceAssetContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PlaceAssetContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PlaceAssetContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PlaceAssetContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PlaceAssetContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PlaceAssetContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PlaceAssetContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PlaceAssetContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PlaceAssetContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PlaceAssetContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<PlaceAssetContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PlaceAssetContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PlaceAssetContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PlaceAssetContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Getter
    @Setter
    @ToString
    public static class PlaceAsset extends DynamicStruct {
        public String placeAssetId;

        public String placeId;

        public String assetId;

        public BigInteger count;

        public BigInteger status;

        public PlaceAsset(String placeAssetId, String placeId, String assetId, BigInteger count, BigInteger status) {
            super(new Utf8String(placeAssetId),new Utf8String(placeId),new Utf8String(assetId),new Uint256(count),new Uint256(status));
            this.placeAssetId = placeAssetId;
            this.placeId = placeId;
            this.assetId = assetId;
            this.count = count;
            this.status = status;
        }

        public PlaceAsset(Utf8String placeAssetId, Utf8String placeId, Utf8String assetId, Uint256 count, Uint256 status) {
            super(placeAssetId,placeId,assetId,count,status);
            this.placeAssetId = placeAssetId.getValue();
            this.placeId = placeId.getValue();
            this.assetId = assetId.getValue();
            this.count = count.getValue();
            this.status = status.getValue();
        }
    }
}
