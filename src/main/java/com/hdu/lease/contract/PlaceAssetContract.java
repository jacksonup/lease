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
    public static final String BINARY = "608060405234801561001057600080fd5b506122a1806100206000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c8063637bf51a11610071578063637bf51a1461017a5780637ce7f1b7146101aa57806382d7a6e3146101da578063c65612da1461020a578063e31bcfca14610228578063fd346af614610258576100a9565b806309bfe26d146100ae57806316166cb9146100de57806334d48e36146100fa578063465c4105146101165780634a69a74714610146575b600080fd5b6100c860048036038101906100c391906115af565b610274565b6040516100d59190611675565b60405180910390f35b6100f860048036038101906100f391906115af565b610320565b005b610114600480360381019061010f91906117cc565b6103fa565b005b610130600480360381019061012b9190611815565b6105a7565b60405161013d91906118a8565b60405180910390f35b610160600480360381019061015b91906117cc565b610684565b6040516101719594939291906118d2565b60405180910390f35b610194600480360381019061018f91906117cc565b610868565b6040516101a191906118a8565b60405180910390f35b6101c460048036038101906101bf91906117cc565b61089e565b6040516101d191906118a8565b60405180910390f35b6101f460048036038101906101ef9190611998565b6109b0565b6040516102019190611b00565b60405180910390f35b610212610c9c565b60405161021f9190611bb5565b60405180910390f35b610242600480360381019061023d91906117cc565b610e9a565b60405161024f9190611b00565b60405180910390f35b610272600480360381019061026d9190611da7565b6111e1565b005b6002818154811061028457600080fd5b90600052602060002001600091509050805461029f90611e1f565b80601f01602080910402602001604051908101604052809291908181526020018280546102cb90611e1f565b80156103185780601f106102ed57610100808354040283529160200191610318565b820191906000526020600020905b8154815290600101906020018083116102fb57829003601f168201915b505050505081565b6000600280549050905080821061033757506103f7565b60008290505b60018261034a9190611e80565b8110156103c45760026001826103609190611eb4565b8154811061037157610370611f0a565b5b906000526020600020016002828154811061038f5761038e611f0a565b5b906000526020600020019080546103a590611e1f565b6103b09291906113c6565b5080806103bc90611f39565b91505061033d565b5060028054806103d7576103d6611f82565b5b6001900381819060005260206000200160006103f39190611453565b9055505b50565b60005b6002805490508110156105a3576001600282815481106104205761041f611f0a565b5b906000526020600020016040516104379190612050565b908152602001604051809103902060009054906101000a900460ff16156105905761052860006002838154811061047157610470611f0a565b5b906000526020600020016040516104889190612050565b908152602001604051809103902060020180546104a490611e1f565b80601f01602080910402602001604051908101604052809291908181526020018280546104d090611e1f565b801561051d5780601f106104f25761010080835404028352916020019161051d565b820191906000526020600020905b81548152906001019060200180831161050057829003601f168201915b5050505050836105a7565b1561058f57600060016002838154811061054557610544611f0a565b5b9060005260206000200160405161055c9190612050565b908152602001604051809103902060006101000a81548160ff02191690831515021790555061058a81610320565b6105a3565b5b808061059b90611f39565b9150506103fd565b5050565b600080839050600083905080518251146105c65760009250505061067e565b60005b8251811015610676578181815181106105e5576105e4611f0a565b5b602001015160f81c60f81b7effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191683828151811061062557610624611f0a565b5b602001015160f81c60f81b7effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191614610663576000935050505061067e565b808061066e90611f39565b9150506105c9565b506001925050505b92915050565b6000818051602081018201805184825260208301602085012081835280955050505050506000915090508060000180546106bd90611e1f565b80601f01602080910402602001604051908101604052809291908181526020018280546106e990611e1f565b80156107365780601f1061070b57610100808354040283529160200191610736565b820191906000526020600020905b81548152906001019060200180831161071957829003601f168201915b50505050509080600101805461074b90611e1f565b80601f016020809104026020016040519081016040528092919081815260200182805461077790611e1f565b80156107c45780601f10610799576101008083540402835291602001916107c4565b820191906000526020600020905b8154815290600101906020018083116107a757829003601f168201915b5050505050908060020180546107d990611e1f565b80601f016020809104026020016040519081016040528092919081815260200182805461080590611e1f565b80156108525780601f1061082757610100808354040283529160200191610852565b820191906000526020600020905b81548152906001019060200180831161083557829003601f168201915b5050505050908060030154908060040154905085565b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b6000806000905060005b6002805490508110156109a6576109856000600283815481106108ce576108cd611f0a565b5b906000526020600020016040516108e59190612050565b9081526020016040518091039020600101805461090190611e1f565b80601f016020809104026020016040519081016040528092919081815260200182805461092d90611e1f565b801561097a5780601f1061094f5761010080835404028352916020019161097a565b820191906000526020600020905b81548152906001019060200180831161095d57829003601f168201915b5050505050856105a7565b1561099357600191506109a6565b808061099e90611f39565b9150506108a8565b5080915050919050565b60606000603267ffffffffffffffff8111156109cf576109ce6116a1565b5b604051908082528060200260200182016040528015610a0257816020015b60608152602001906001900390816109ed5790505b5090506000805b600280549050811015610be557610ae6600060028381548110610a2f57610a2e611f0a565b5b90600052602060002001604051610a469190612050565b90815260200160405180910390206002018054610a6290611e1f565b80601f0160208091040260200160405190810160405280929190818152602001828054610a8e90611e1f565b8015610adb5780601f10610ab057610100808354040283529160200191610adb565b820191906000526020600020905b815481529060010190602001808311610abe57829003601f168201915b5050505050866105a7565b15610bd2578573ffffffffffffffffffffffffffffffffffffffff1663610ed5db600060028481548110610b1d57610b1c611f0a565b5b90600052602060002001604051610b349190612050565b90815260200160405180910390206001016040518263ffffffff1660e01b8152600401610b6191906120e7565b600060405180830381865afa158015610b7e573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f82011682018060405250810190610ba79190612179565b838380610bb390611f39565b945081518110610bc657610bc5611f0a565b5b60200260200101819052505b8080610bdd90611f39565b915050610a09565b5060008167ffffffffffffffff811115610c0257610c016116a1565b5b604051908082528060200260200182016040528015610c3557816020015b6060815260200190600190039081610c205790505b50905060005b82811015610c8f57838181518110610c5657610c55611f0a565b5b6020026020010151828281518110610c7157610c70611f0a565b5b60200260200101819052508080610c8790611f39565b915050610c3b565b5080935050505092915050565b610ca4611493565b6000604051610cb29061220e565b90815260200160405180910390206040518060a0016040529081600082018054610cdb90611e1f565b80601f0160208091040260200160405190810160405280929190818152602001828054610d0790611e1f565b8015610d545780601f10610d2957610100808354040283529160200191610d54565b820191906000526020600020905b815481529060010190602001808311610d3757829003601f168201915b50505050508152602001600182018054610d6d90611e1f565b80601f0160208091040260200160405190810160405280929190818152602001828054610d9990611e1f565b8015610de65780601f10610dbb57610100808354040283529160200191610de6565b820191906000526020600020905b815481529060010190602001808311610dc957829003601f168201915b50505050508152602001600282018054610dff90611e1f565b80601f0160208091040260200160405190810160405280929190818152602001828054610e2b90611e1f565b8015610e785780601f10610e4d57610100808354040283529160200191610e78565b820191906000526020600020905b815481529060010190602001808311610e5b57829003601f168201915b5050505050815260200160038201548152602001600482015481525050905090565b60606000603267ffffffffffffffff811115610eb957610eb86116a1565b5b604051908082528060200260200182016040528015610eec57816020015b6060815260200190600190039081610ed75790505b5090506000805b60028054905081101561112b57600160028281548110610f1657610f15611f0a565b5b90600052602060002001604051610f2d9190612050565b908152602001604051809103902060009054906101000a900460ff1680156110215750611020600060028381548110610f6957610f68611f0a565b5b90600052602060002001604051610f809190612050565b90815260200160405180910390206001018054610f9c90611e1f565b80601f0160208091040260200160405190810160405280929190818152602001828054610fc890611e1f565b80156110155780601f10610fea57610100808354040283529160200191611015565b820191906000526020600020905b815481529060010190602001808311610ff857829003601f168201915b5050505050866105a7565b5b156111185760006002828154811061103c5761103b611f0a565b5b906000526020600020016040516110539190612050565b9081526020016040518091039020600201805461106f90611e1f565b80601f016020809104026020016040519081016040528092919081815260200182805461109b90611e1f565b80156110e85780601f106110bd576101008083540402835291602001916110e8565b820191906000526020600020905b8154815290600101906020018083116110cb57829003601f168201915b50505050508383806110f990611f39565b94508151811061110c5761110b611f0a565b5b60200260200101819052505b808061112390611f39565b915050610ef3565b5060008167ffffffffffffffff811115611148576111476116a1565b5b60405190808252806020026020018201604052801561117b57816020015b60608152602001906001900390816111665790505b50905060005b828110156111d55783818151811061119c5761119b611f0a565b5b60200260200101518282815181106111b7576111b6611f0a565b5b602002602001018190525080806111cd90611f39565b915050611181565b50809350505050919050565b60005b81518110156113c257600182828151811061120257611201611f0a565b5b60200260200101516000015160405161121b9190612254565b908152602001604051809103902060009054906101000a900460ff166113af5781818151811061124e5761124d611f0a565b5b6020026020010151600083838151811061126b5761126a611f0a565b5b6020026020010151600001516040516112849190612254565b908152602001604051809103902060008201518160000190805190602001906112ae9291906114c2565b5060208201518160010190805190602001906112cb9291906114c2565b5060408201518160020190805190602001906112e89291906114c2565b50606082015181600301556080820151816004015590505060018083838151811061131657611315611f0a565b5b60200260200101516000015160405161132f9190612254565b908152602001604051809103902060006101000a81548160ff021916908315150217905550600282828151811061136957611368611f0a565b5b6020026020010151600001519080600181540180825580915050600190039060005260206000200160009091909190915090805190602001906113ad9291906114c2565b505b80806113ba90611f39565b9150506111e4565b5050565b8280546113d290611e1f565b90600052602060002090601f0160209004810192826113f45760008555611442565b82601f106114055780548555611442565b8280016001018555821561144257600052602060002091601f016020900482015b82811115611441578254825591600101919060010190611426565b5b50905061144f9190611548565b5090565b50805461145f90611e1f565b6000825580601f106114715750611490565b601f01602090049060005260206000209081019061148f9190611548565b5b50565b6040518060a0016040528060608152602001606081526020016060815260200160008152602001600081525090565b8280546114ce90611e1f565b90600052602060002090601f0160209004810192826114f05760008555611537565b82601f1061150957805160ff1916838001178555611537565b82800160010185558215611537579182015b8281111561153657825182559160200191906001019061151b565b5b5090506115449190611548565b5090565b5b80821115611561576000816000905550600101611549565b5090565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b61158c81611579565b811461159757600080fd5b50565b6000813590506115a981611583565b92915050565b6000602082840312156115c5576115c461156f565b5b60006115d38482850161159a565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b838110156116165780820151818401526020810190506115fb565b83811115611625576000848401525b50505050565b6000601f19601f8301169050919050565b6000611647826115dc565b61165181856115e7565b93506116618185602086016115f8565b61166a8161162b565b840191505092915050565b6000602082019050818103600083015261168f818461163c565b905092915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6116d98261162b565b810181811067ffffffffffffffff821117156116f8576116f76116a1565b5b80604052505050565b600061170b611565565b905061171782826116d0565b919050565b600067ffffffffffffffff821115611737576117366116a1565b5b6117408261162b565b9050602081019050919050565b82818337600083830152505050565b600061176f61176a8461171c565b611701565b90508281526020810184848401111561178b5761178a61169c565b5b61179684828561174d565b509392505050565b600082601f8301126117b3576117b2611697565b5b81356117c384826020860161175c565b91505092915050565b6000602082840312156117e2576117e161156f565b5b600082013567ffffffffffffffff811115611800576117ff611574565b5b61180c8482850161179e565b91505092915050565b6000806040838503121561182c5761182b61156f565b5b600083013567ffffffffffffffff81111561184a57611849611574565b5b6118568582860161179e565b925050602083013567ffffffffffffffff81111561187757611876611574565b5b6118838582860161179e565b9150509250929050565b60008115159050919050565b6118a28161188d565b82525050565b60006020820190506118bd6000830184611899565b92915050565b6118cc81611579565b82525050565b600060a08201905081810360008301526118ec818861163c565b90508181036020830152611900818761163c565b90508181036040830152611914818661163c565b905061192360608301856118c3565b61193060808301846118c3565b9695505050505050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006119658261193a565b9050919050565b6119758161195a565b811461198057600080fd5b50565b6000813590506119928161196c565b92915050565b600080604083850312156119af576119ae61156f565b5b60006119bd85828601611983565b925050602083013567ffffffffffffffff8111156119de576119dd611574565b5b6119ea8582860161179e565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600082825260208201905092915050565b6000611a3c826115dc565b611a468185611a20565b9350611a568185602086016115f8565b611a5f8161162b565b840191505092915050565b6000611a768383611a31565b905092915050565b6000602082019050919050565b6000611a96826119f4565b611aa081856119ff565b935083602082028501611ab285611a10565b8060005b85811015611aee5784840389528151611acf8582611a6a565b9450611ada83611a7e565b925060208a01995050600181019050611ab6565b50829750879550505050505092915050565b60006020820190508181036000830152611b1a8184611a8b565b905092915050565b611b2b81611579565b82525050565b600060a0830160008301518482036000860152611b4e8282611a31565b91505060208301518482036020860152611b688282611a31565b91505060408301518482036040860152611b828282611a31565b9150506060830151611b976060860182611b22565b506080830151611baa6080860182611b22565b508091505092915050565b60006020820190508181036000830152611bcf8184611b31565b905092915050565b600067ffffffffffffffff821115611bf257611bf16116a1565b5b602082029050602081019050919050565b600080fd5b600080fd5b600080fd5b600060a08284031215611c2857611c27611c08565b5b611c3260a0611701565b9050600082013567ffffffffffffffff811115611c5257611c51611c0d565b5b611c5e8482850161179e565b600083015250602082013567ffffffffffffffff811115611c8257611c81611c0d565b5b611c8e8482850161179e565b602083015250604082013567ffffffffffffffff811115611cb257611cb1611c0d565b5b611cbe8482850161179e565b6040830152506060611cd28482850161159a565b6060830152506080611ce68482850161159a565b60808301525092915050565b6000611d05611d0084611bd7565b611701565b90508083825260208201905060208402830185811115611d2857611d27611c03565b5b835b81811015611d6f57803567ffffffffffffffff811115611d4d57611d4c611697565b5b808601611d5a8982611c12565b85526020850194505050602081019050611d2a565b5050509392505050565b600082601f830112611d8e57611d8d611697565b5b8135611d9e848260208601611cf2565b91505092915050565b600060208284031215611dbd57611dbc61156f565b5b600082013567ffffffffffffffff811115611ddb57611dda611574565b5b611de784828501611d79565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680611e3757607f821691505b60208210811415611e4b57611e4a611df0565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611e8b82611579565b9150611e9683611579565b925082821015611ea957611ea8611e51565b5b828203905092915050565b6000611ebf82611579565b9150611eca83611579565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff03821115611eff57611efe611e51565b5b828201905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b6000611f4482611579565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff821415611f7757611f76611e51565b5b600182019050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603160045260246000fd5b600081905092915050565b60008190508160005260206000209050919050565b60008154611fde81611e1f565b611fe88186611fb1565b94506001821660008114612003576001811461201457612047565b60ff19831686528186019350612047565b61201d85611fbc565b60005b8381101561203f57815481890152600182019150602081019050612020565b838801955050505b50505092915050565b600061205c8284611fd1565b915081905092915050565b6000815461207481611e1f565b61207e81866115e7565b9450600182166000811461209957600181146120ab576120de565b60ff19831686526020860193506120de565b6120b485611fbc565b60005b838110156120d6578154818901526001820191506020810190506120b7565b808801955050505b50505092915050565b600060208201905081810360008301526121018184612067565b905092915050565b600061211c6121178461171c565b611701565b9050828152602081018484840111156121385761213761169c565b5b6121438482856115f8565b509392505050565b600082601f8301126121605761215f611697565b5b8151612170848260208601612109565b91505092915050565b60006020828403121561218f5761218e61156f565b5b600082015167ffffffffffffffff8111156121ad576121ac611574565b5b6121b98482850161214b565b91505092915050565b7f3100000000000000000000000000000000000000000000000000000000000000600082015250565b60006121f8600183611fb1565b9150612203826121c2565b600182019050919050565b6000612219826121eb565b9150819050919050565b600061222e826115dc565b6122388185611fb1565b93506122488185602086016115f8565b80840191505092915050565b60006122608284612223565b91508190509291505056fea264697066735822122012f0336282f7609c5b457a98e50cd0edfac5014c42c5b55998ebcc52c154fb6164736f6c634300080a0033";

    public static final String FUNC_BINDASSET = "bindAsset";

    public static final String FUNC_DELETEBYASSETID = "deleteByAssetId";

    public static final String FUNC_GETASSETIDLISTBYPLACEID = "getAssetIdListByPlaceId";

    public static final String FUNC_GETPLACEASSET = "getPlaceAsset";

    public static final String FUNC_GETPLACELIST = "getPlaceList";

    public static final String FUNC_ISEQUAL = "isEqual";

    public static final String FUNC_ISPLACEBINDASSET = "isPlaceBindAsset";

    public static final String FUNC_PLACEASSETINSERTED = "placeAssetInserted";

    public static final String FUNC_PLACEASSETKEY = "placeAssetKey";

    public static final String FUNC_PLACEASSETS = "placeAssets";

    public static final String FUNC_REMOVEPLACEASSETKEYATINDEX = "removePlaceAssetKeyAtIndex";

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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<PlaceAsset>(PlaceAsset.class, _placeAssets)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deleteByAssetId(String _assetId) {
        final Function function = new Function(
                FUNC_DELETEBYASSETID,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_assetId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAssetIdListByPlaceId(String _placeId) {
        final Function function = new Function(FUNC_GETASSETIDLISTBYPLACEID,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_placeId)),
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

    public RemoteFunctionCall<List> getPlaceList(String _placeContract, String _assetId) {
        final Function function = new Function(FUNC_GETPLACELIST,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _placeContract),
                        new org.web3j.abi.datatypes.Utf8String(_assetId)),
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(a),
                        new org.web3j.abi.datatypes.Utf8String(b)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isPlaceBindAsset(String _placeId) {
        final Function function = new Function(FUNC_ISPLACEBINDASSET,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_placeId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> placeAssetInserted(String param0) {
        final Function function = new Function(FUNC_PLACEASSETINSERTED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> placeAssetKey(BigInteger param0) {
        final Function function = new Function(FUNC_PLACEASSETKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple5<String, String, String, BigInteger, BigInteger>> placeAssets(String param0) {
        final Function function = new Function(FUNC_PLACEASSETS,
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

    public RemoteFunctionCall<TransactionReceipt> removePlaceAssetKeyAtIndex(BigInteger index) {
        final Function function = new Function(
                FUNC_REMOVEPLACEASSETKEYATINDEX,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
            super(new org.web3j.abi.datatypes.Utf8String(placeAssetId),new org.web3j.abi.datatypes.Utf8String(placeId),new org.web3j.abi.datatypes.Utf8String(assetId),new org.web3j.abi.datatypes.generated.Uint256(count),new org.web3j.abi.datatypes.generated.Uint256(status));
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
