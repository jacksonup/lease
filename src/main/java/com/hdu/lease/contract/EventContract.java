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
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tuples.generated.Tuple8;
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
    public static final String BINARY = "608060405234801561001057600080fd5b5061288a806100206000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80639740215211610066578063974021521461015f578063afa4e8e01461018f578063b9f2d0a2146101bf578063c0425592146101dd578063cca12151146101fb57610093565b8063465c4105146100985780637280bf7d146100c85780639596c042146100f85780639673d6a314610128575b600080fd5b6100b260048036038101906100ad9190611abc565b610217565b6040516100bf9190611b4f565b60405180910390f35b6100e260048036038101906100dd9190611bc8565b6102f4565b6040516100ef9190611edd565b60405180910390f35b610112600480360381019061010d9190611eff565b6109fe565b60405161011f919061200a565b60405180910390f35b610142600480360381019061013d9190611eff565b610f76565b604051610156989796959493929190612094565b60405180910390f35b61017960048036038101906101749190611eff565b61127c565b6040516101869190611b4f565b60405180910390f35b6101a960048036038101906101a49190612161565b6112b2565b6040516101b6919061218e565b60405180910390f35b6101c761135e565b6040516101d4919061220e565b60405180910390f35b6101e561155a565b6040516101f291906122fc565b60405180910390f35b610215600480360381019061021091906124aa565b6116cc565b005b60008083905060008390508051825114610236576000925050506102ee565b60005b82518110156102e657818181518110610255576102546124f3565b5b602001015160f81c60f81b7effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916838281518110610295576102946124f3565b5b602001015160f81c60f81b7effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916146102d357600093505050506102ee565b80806102de90612551565b915050610239565b506001925050505b92915050565b6060600060028054905067ffffffffffffffff81111561031757610316611991565b5b60405190808252806020026020018201604052801561035057816020015b61033d611853565b8152602001906001900390816103355790505b5090506000805b6002805490508110156109465761043460006002838154811061037d5761037c6124f3565b5b90600052602060002001604051610394919061269a565b908152602001604051809103902060020180546103b0906125c9565b80601f01602080910402602001604051908101604052809291908181526020018280546103dc906125c9565b80156104295780601f106103fe57610100808354040283529160200191610429565b820191906000526020600020905b81548152906001019060200180831161040c57829003601f168201915b505050505088610217565b1561093357604051806060016040528060006002848154811061045a576104596124f3565b5b90600052602060002001604051610471919061269a565b90815260200160405180910390206040518061010001604052908160008201805461049b906125c9565b80601f01602080910402602001604051908101604052809291908181526020018280546104c7906125c9565b80156105145780601f106104e957610100808354040283529160200191610514565b820191906000526020600020905b8154815290600101906020018083116104f757829003601f168201915b5050505050815260200160018201548152602001600282018054610537906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054610563906125c9565b80156105b05780601f10610585576101008083540402835291602001916105b0565b820191906000526020600020905b81548152906001019060200180831161059357829003601f168201915b505050505081526020016003820180546105c9906125c9565b80601f01602080910402602001604051908101604052809291908181526020018280546105f5906125c9565b80156106425780601f1061061757610100808354040283529160200191610642565b820191906000526020600020905b81548152906001019060200180831161062557829003601f168201915b5050505050815260200160048201805461065b906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054610687906125c9565b80156106d45780601f106106a9576101008083540402835291602001916106d4565b820191906000526020600020905b8154815290600101906020018083116106b757829003601f168201915b50505050508152602001600582015481526020016006820180546106f7906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054610723906125c9565b80156107705780601f1061074557610100808354040283529160200191610770565b820191906000526020600020905b81548152906001019060200180831161075357829003601f168201915b5050505050815260200160078201548152505081526020018773ffffffffffffffffffffffffffffffffffffffff1663e6dca1136000600286815481106107ba576107b96124f3565b5b906000526020600020016040516107d1919061269a565b90815260200160405180910390206004016040518263ffffffff1660e01b81526004016107fe9190612731565b600060405180830381865afa15801561081b573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525081019061084491906127c3565b81526020018673ffffffffffffffffffffffffffffffffffffffff1663610ed5db60006002868154811061087b5761087a6124f3565b5b90600052602060002001604051610892919061269a565b90815260200160405180910390206003016040518263ffffffff1660e01b81526004016108bf9190612731565b600060405180830381865afa1580156108dc573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525081019061090591906127c3565b81525083838061091490612551565b945081518110610927576109266124f3565b5b60200260200101819052505b808061093e90612551565b915050610357565b508067ffffffffffffffff81111561096157610960611991565b5b60405190808252806020026020018201604052801561099a57816020015b610987611853565b81526020019060019003908161097f5790505b50925060005b818110156109f4578281815181106109bb576109ba6124f3565b5b60200260200101518482815181106109d6576109d56124f3565b5b602002602001018190525080806109ec90612551565b9150506109a0565b5050509392505050565b6060600060028054905067ffffffffffffffff811115610a2157610a20611991565b5b604051908082528060200260200182016040528015610a5a57816020015b610a4761187a565b815260200190600190039081610a3f5790505b5090506000805b600280549050811015610ec057610b3e600060028381548110610a8757610a866124f3565b5b90600052602060002001604051610a9e919061269a565b90815260200160405180910390206002018054610aba906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054610ae6906125c9565b8015610b335780601f10610b0857610100808354040283529160200191610b33565b820191906000526020600020905b815481529060010190602001808311610b1657829003601f168201915b505050505086610217565b15610ead57600060028281548110610b5957610b586124f3565b5b90600052602060002001604051610b70919061269a565b908152602001604051809103902060405180610100016040529081600082018054610b9a906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054610bc6906125c9565b8015610c135780601f10610be857610100808354040283529160200191610c13565b820191906000526020600020905b815481529060010190602001808311610bf657829003601f168201915b5050505050815260200160018201548152602001600282018054610c36906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054610c62906125c9565b8015610caf5780601f10610c8457610100808354040283529160200191610caf565b820191906000526020600020905b815481529060010190602001808311610c9257829003601f168201915b50505050508152602001600382018054610cc8906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054610cf4906125c9565b8015610d415780601f10610d1657610100808354040283529160200191610d41565b820191906000526020600020905b815481529060010190602001808311610d2457829003601f168201915b50505050508152602001600482018054610d5a906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054610d86906125c9565b8015610dd35780601f10610da857610100808354040283529160200191610dd3565b820191906000526020600020905b815481529060010190602001808311610db657829003601f168201915b5050505050815260200160058201548152602001600682018054610df6906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054610e22906125c9565b8015610e6f5780601f10610e4457610100808354040283529160200191610e6f565b820191906000526020600020905b815481529060010190602001808311610e5257829003601f168201915b50505050508152602001600782015481525050838380610e8e90612551565b945081518110610ea157610ea06124f3565b5b60200260200101819052505b8080610eb890612551565b915050610a61565b508067ffffffffffffffff811115610edb57610eda611991565b5b604051908082528060200260200182016040528015610f1457816020015b610f0161187a565b815260200190600190039081610ef95790505b50925060005b81811015610f6e57828181518110610f3557610f346124f3565b5b6020026020010151848281518110610f5057610f4f6124f3565b5b60200260200101819052508080610f6690612551565b915050610f1a565b505050919050565b600081805160208101820180518482526020830160208501208183528095505050505050600091509050806000018054610faf906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054610fdb906125c9565b80156110285780601f10610ffd57610100808354040283529160200191611028565b820191906000526020600020905b81548152906001019060200180831161100b57829003601f168201915b505050505090806001015490806002018054611043906125c9565b80601f016020809104026020016040519081016040528092919081815260200182805461106f906125c9565b80156110bc5780601f10611091576101008083540402835291602001916110bc565b820191906000526020600020905b81548152906001019060200180831161109f57829003601f168201915b5050505050908060030180546110d1906125c9565b80601f01602080910402602001604051908101604052809291908181526020018280546110fd906125c9565b801561114a5780601f1061111f5761010080835404028352916020019161114a565b820191906000526020600020905b81548152906001019060200180831161112d57829003601f168201915b50505050509080600401805461115f906125c9565b80601f016020809104026020016040519081016040528092919081815260200182805461118b906125c9565b80156111d85780601f106111ad576101008083540402835291602001916111d8565b820191906000526020600020905b8154815290600101906020018083116111bb57829003601f168201915b5050505050908060050154908060060180546111f3906125c9565b80601f016020809104026020016040519081016040528092919081815260200182805461121f906125c9565b801561126c5780601f106112415761010080835404028352916020019161126c565b820191906000526020600020905b81548152906001019060200180831161124f57829003601f168201915b5050505050908060070154905088565b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b600281815481106112c257600080fd5b9060005260206000200160009150905080546112dd906125c9565b80601f0160208091040260200160405190810160405280929190818152602001828054611309906125c9565b80156113565780601f1061132b57610100808354040283529160200191611356565b820191906000526020600020905b81548152906001019060200180831161133957829003601f168201915b505050505081565b611366611853565b60006040518061010001604052806040518060400160405280600181526020017f300000000000000000000000000000000000000000000000000000000000000081525081526020017fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff81526020016040518060400160405280600d81526020017f617373657444657461696c49640000000000000000000000000000000000000081525081526020016040518060400160405280600781526020017f706c61636549640000000000000000000000000000000000000000000000000081525081526020016040518060400160405280600f81526020017f6f70657261746f724163636f756e7400000000000000000000000000000000008152508152602001600081526020016040518060400160405280600781526020017f636f6e74656e740000000000000000000000000000000000000000000000000081525081526020016000815250905060405180606001604052808281526020016040518060400160405280600881526020017f757365724e616d6500000000000000000000000000000000000000000000000081525081526020016040518060400160405280600981526020017f706c6163654e616d65000000000000000000000000000000000000000000000081525081525091505090565b61156261187a565b6040518061010001604052806040518060400160405280600181526020017f300000000000000000000000000000000000000000000000000000000000000081525081526020017fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff81526020016040518060400160405280600d81526020017f617373657444657461696c49640000000000000000000000000000000000000081525081526020016040518060400160405280600781526020017f706c61636549640000000000000000000000000000000000000000000000000081525081526020016040518060400160405280600f81526020017f6f70657261746f724163636f756e7400000000000000000000000000000000008152508152602001600081526020016040518060400160405280600781526020017f636f6e74656e740000000000000000000000000000000000000000000000000081525081526020016000815250905090565b600181600001516040516116e0919061283d565b908152602001604051809103902060009054906101000a900460ff16611850578060008260000151604051611715919061283d565b9081526020016040518091039020600082015181600001908051906020019061173f9291906118bf565b506020820151816001015560408201518160020190805190602001906117669291906118bf565b5060608201518160030190805190602001906117839291906118bf565b5060808201518160040190805190602001906117a09291906118bf565b5060a0820151816005015560c08201518160060190805190602001906117c79291906118bf565b5060e0820151816007015590505060018082600001516040516117ea919061283d565b908152602001604051809103902060006101000a81548160ff0219169083151502179055506002816000015190806001815401808255809150506001900390600052602060002001600090919091909150908051906020019061184e9291906118bf565b505b50565b604051806060016040528061186661187a565b815260200160608152602001606081525090565b60405180610100016040528060608152602001600081526020016060815260200160608152602001606081526020016000815260200160608152602001600081525090565b8280546118cb906125c9565b90600052602060002090601f0160209004810192826118ed5760008555611934565b82601f1061190657805160ff1916838001178555611934565b82800160010185558215611934579182015b82811115611933578251825591602001919060010190611918565b5b5090506119419190611945565b5090565b5b8082111561195e576000816000905550600101611946565b5090565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6119c982611980565b810181811067ffffffffffffffff821117156119e8576119e7611991565b5b80604052505050565b60006119fb611962565b9050611a0782826119c0565b919050565b600067ffffffffffffffff821115611a2757611a26611991565b5b611a3082611980565b9050602081019050919050565b82818337600083830152505050565b6000611a5f611a5a84611a0c565b6119f1565b905082815260208101848484011115611a7b57611a7a61197b565b5b611a86848285611a3d565b509392505050565b600082601f830112611aa357611aa2611976565b5b8135611ab3848260208601611a4c565b91505092915050565b60008060408385031215611ad357611ad261196c565b5b600083013567ffffffffffffffff811115611af157611af0611971565b5b611afd85828601611a8e565b925050602083013567ffffffffffffffff811115611b1e57611b1d611971565b5b611b2a85828601611a8e565b9150509250929050565b60008115159050919050565b611b4981611b34565b82525050565b6000602082019050611b646000830184611b40565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000611b9582611b6a565b9050919050565b611ba581611b8a565b8114611bb057600080fd5b50565b600081359050611bc281611b9c565b92915050565b600080600060608486031215611be157611be061196c565b5b600084013567ffffffffffffffff811115611bff57611bfe611971565b5b611c0b86828701611a8e565b9350506020611c1c86828701611bb3565b9250506040611c2d86828701611bb3565b9150509250925092565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600081519050919050565b600082825260208201905092915050565b60005b83811015611c9d578082015181840152602081019050611c82565b83811115611cac576000848401525b50505050565b6000611cbd82611c63565b611cc78185611c6e565b9350611cd7818560208601611c7f565b611ce081611980565b840191505092915050565b6000819050919050565b611cfe81611ceb565b82525050565b6000819050919050565b611d1781611d04565b82525050565b6000610100830160008301518482036000860152611d3b8282611cb2565b9150506020830151611d506020860182611cf5565b5060408301518482036040860152611d688282611cb2565b91505060608301518482036060860152611d828282611cb2565b91505060808301518482036080860152611d9c8282611cb2565b91505060a0830151611db160a0860182611d0e565b5060c083015184820360c0860152611dc98282611cb2565b91505060e0830151611dde60e0860182611cf5565b508091505092915050565b60006060830160008301518482036000860152611e068282611d1d565b91505060208301518482036020860152611e208282611cb2565b91505060408301518482036040860152611e3a8282611cb2565b9150508091505092915050565b6000611e538383611de9565b905092915050565b6000602082019050919050565b6000611e7382611c37565b611e7d8185611c42565b935083602082028501611e8f85611c53565b8060005b85811015611ecb5784840389528151611eac8582611e47565b9450611eb783611e5b565b925060208a01995050600181019050611e93565b50829750879550505050505092915050565b60006020820190508181036000830152611ef78184611e68565b905092915050565b600060208284031215611f1557611f1461196c565b5b600082013567ffffffffffffffff811115611f3357611f32611971565b5b611f3f84828501611a8e565b91505092915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6000611f808383611d1d565b905092915050565b6000602082019050919050565b6000611fa082611f48565b611faa8185611f53565b935083602082028501611fbc85611f64565b8060005b85811015611ff85784840389528151611fd98582611f74565b9450611fe483611f88565b925060208a01995050600181019050611fc0565b50829750879550505050505092915050565b600060208201905081810360008301526120248184611f95565b905092915050565b600082825260208201905092915050565b600061204882611c63565b612052818561202c565b9350612062818560208601611c7f565b61206b81611980565b840191505092915050565b61207f81611ceb565b82525050565b61208e81611d04565b82525050565b60006101008201905081810360008301526120af818b61203d565b90506120be602083018a612076565b81810360408301526120d0818961203d565b905081810360608301526120e4818861203d565b905081810360808301526120f8818761203d565b905061210760a0830186612085565b81810360c0830152612119818561203d565b905061212860e0830184612076565b9998505050505050505050565b61213e81611d04565b811461214957600080fd5b50565b60008135905061215b81612135565b92915050565b6000602082840312156121775761217661196c565b5b60006121858482850161214c565b91505092915050565b600060208201905081810360008301526121a8818461203d565b905092915050565b600060608301600083015184820360008601526121cd8282611d1d565b915050602083015184820360208601526121e78282611cb2565b915050604083015184820360408601526122018282611cb2565b9150508091505092915050565b6000602082019050818103600083015261222881846121b0565b905092915050565b600061010083016000830151848203600086015261224e8282611cb2565b91505060208301516122636020860182611cf5565b506040830151848203604086015261227b8282611cb2565b915050606083015184820360608601526122958282611cb2565b915050608083015184820360808601526122af8282611cb2565b91505060a08301516122c460a0860182611d0e565b5060c083015184820360c08601526122dc8282611cb2565b91505060e08301516122f160e0860182611cf5565b508091505092915050565b600060208201905081810360008301526123168184612230565b905092915050565b600080fd5b600080fd5b61233181611ceb565b811461233c57600080fd5b50565b60008135905061234e81612328565b92915050565b6000610100828403121561236b5761236a61231e565b5b6123766101006119f1565b9050600082013567ffffffffffffffff81111561239657612395612323565b5b6123a284828501611a8e565b60008301525060206123b68482850161233f565b602083015250604082013567ffffffffffffffff8111156123da576123d9612323565b5b6123e684828501611a8e565b604083015250606082013567ffffffffffffffff81111561240a57612409612323565b5b61241684828501611a8e565b606083015250608082013567ffffffffffffffff81111561243a57612439612323565b5b61244684828501611a8e565b60808301525060a061245a8482850161214c565b60a08301525060c082013567ffffffffffffffff81111561247e5761247d612323565b5b61248a84828501611a8e565b60c08301525060e061249e8482850161233f565b60e08301525092915050565b6000602082840312156124c0576124bf61196c565b5b600082013567ffffffffffffffff8111156124de576124dd611971565b5b6124ea84828501612354565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061255c82611d04565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82141561258f5761258e612522565b5b600182019050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806125e157607f821691505b602082108114156125f5576125f461259a565b5b50919050565b600081905092915050565b60008190508160005260206000209050919050565b60008154612628816125c9565b61263281866125fb565b9450600182166000811461264d576001811461265e57612691565b60ff19831686528186019350612691565b61266785612606565b60005b838110156126895781548189015260018201915060208101905061266a565b838801955050505b50505092915050565b60006126a6828461261b565b915081905092915050565b600081546126be816125c9565b6126c8818661202c565b945060018216600081146126e357600181146126f557612728565b60ff1983168652602086019350612728565b6126fe85612606565b60005b8381101561272057815481890152600182019150602081019050612701565b808801955050505b50505092915050565b6000602082019050818103600083015261274b81846126b1565b905092915050565b600061276661276184611a0c565b6119f1565b9050828152602081018484840111156127825761278161197b565b5b61278d848285611c7f565b509392505050565b600082601f8301126127aa576127a9611976565b5b81516127ba848260208601612753565b91505092915050565b6000602082840312156127d9576127d861196c565b5b600082015167ffffffffffffffff8111156127f7576127f6611971565b5b61280384828501612795565b91505092915050565b600061281782611c63565b61282181856125fb565b9350612831818560208601611c7f565b80840191505092915050565b6000612849828461280c565b91508190509291505056fea26469706673582212203efc5927c010a8462a76c358dde253431c4c73c56101685b2f9433cdbae21dd364736f6c634300080a0033";

    public static final String FUNC_EVENTINSERTED = "eventInserted";

    public static final String FUNC_EVENTKEY = "eventKey";

    public static final String FUNC_EVENTS = "events";

    public static final String FUNC_GETEVENTDTO = "getEventDTO";

    public static final String FUNC_GETEVENTTEST = "getEventTest";

    public static final String FUNC_GETLISTBYDETAILID = "getListByDetailId";

    public static final String FUNC_GETLISTBYDETAILID2 = "getListByDetailId2";

    public static final String FUNC_INSERT = "insert";

    public static final String FUNC_ISEQUAL = "isEqual";

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

    public RemoteFunctionCall<Tuple8<String, BigInteger, String, String, String, BigInteger, String, BigInteger>> events(String param0) {
        final Function function = new Function(FUNC_EVENTS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}));
        return new RemoteFunctionCall<Tuple8<String, BigInteger, String, String, String, BigInteger, String, BigInteger>>(function,
                new Callable<Tuple8<String, BigInteger, String, String, String, BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple8<String, BigInteger, String, String, String, BigInteger, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, BigInteger, String, String, String, BigInteger, String, BigInteger>(
                                (String) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (String) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                (String) results.get(6).getValue(),
                                (BigInteger) results.get(7).getValue());
                    }
                });
    }

    public RemoteFunctionCall<EventDTO> getEventDTO() {
        final Function function = new Function(FUNC_GETEVENTDTO,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<EventDTO>() {}));
        return executeRemoteCallSingleValueReturn(function, EventDTO.class);
    }

    public RemoteFunctionCall<Event> getEventTest() {
        final Function function = new Function(FUNC_GETEVENTTEST,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Event>() {}));
        return executeRemoteCallSingleValueReturn(function, Event.class);
    }

    public RemoteFunctionCall<List> getListByDetailId(String _detailId, String _userContract, String _placeContract) {
        final Function function = new Function(FUNC_GETLISTBYDETAILID,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_detailId),
                        new org.web3j.abi.datatypes.Address(160, _userContract),
                        new org.web3j.abi.datatypes.Address(160, _placeContract)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<EventDTO>>() {}));
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

    public RemoteFunctionCall<List> getListByDetailId2(String _detailId) {
        final Function function = new Function(FUNC_GETLISTBYDETAILID2,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_detailId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Event>>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> insert(Event _event) {
        final Function function = new Function(
                FUNC_INSERT,
                Arrays.<Type>asList(_event),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isEqual(String a, String b) {
        final Function function = new Function(FUNC_ISEQUAL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(a),
                        new org.web3j.abi.datatypes.Utf8String(b)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

        public BigInteger status;

        public Event(String id, BigInteger eventType, String assetDetailId, String placeId, String operatorAccount, BigInteger createTime, String content, BigInteger status) {
            super(new org.web3j.abi.datatypes.Utf8String(id),new org.web3j.abi.datatypes.generated.Int256(eventType),new org.web3j.abi.datatypes.Utf8String(assetDetailId),new org.web3j.abi.datatypes.Utf8String(placeId),new org.web3j.abi.datatypes.Utf8String(operatorAccount),new org.web3j.abi.datatypes.generated.Uint256(createTime),new org.web3j.abi.datatypes.Utf8String(content),new org.web3j.abi.datatypes.generated.Int256(status));
            this.id = id;
            this.eventType = eventType;
            this.assetDetailId = assetDetailId;
            this.placeId = placeId;
            this.operatorAccount = operatorAccount;
            this.createTime = createTime;
            this.content = content;
            this.status = status;
        }

        public Event(Utf8String id, Int256 eventType, Utf8String assetDetailId, Utf8String placeId, Utf8String operatorAccount, Uint256 createTime, Utf8String content, Int256 status) {
            super(id,eventType,assetDetailId,placeId,operatorAccount,createTime,content,status);
            this.id = id.getValue();
            this.eventType = eventType.getValue();
            this.assetDetailId = assetDetailId.getValue();
            this.placeId = placeId.getValue();
            this.operatorAccount = operatorAccount.getValue();
            this.createTime = createTime.getValue();
            this.content = content.getValue();
            this.status = status.getValue();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class EventDTO extends DynamicStruct {
        public Event _event;

        public String userName;

        public String placeName;

        public EventDTO(Event _event, String userName, String placeName) {
            super(_event,new org.web3j.abi.datatypes.Utf8String(userName),new org.web3j.abi.datatypes.Utf8String(placeName));
            this._event = _event;
            this.userName = userName;
            this.placeName = placeName;
        }

        public EventDTO(Event _event, Utf8String userName, Utf8String placeName) {
            super(_event,userName,placeName);
            this._event = _event;
            this.userName = userName.getValue();
            this.placeName = placeName.getValue();
        }
    }
}
