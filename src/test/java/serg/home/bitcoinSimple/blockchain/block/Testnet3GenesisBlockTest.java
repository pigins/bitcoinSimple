package serg.home.bitcoinSimple.blockchain.block;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.blockchain.block.transaction.Transaction;
import serg.home.bitcoinSimple.blockchain.block.transaction.TxVersion;
import serg.home.bitcoinSimple.blockchain.block.transaction.input.CoinbaseData;
import serg.home.bitcoinSimple.blockchain.block.transaction.input.CoinbaseInput;
import serg.home.bitcoinSimple.blockchain.block.transaction.input.Input;
import serg.home.bitcoinSimple.blockchain.block.transaction.output.Output;
import serg.home.bitcoinSimple.blockchain.block.transaction.script.OP;
import serg.home.bitcoinSimple.blockchain.block.transaction.script.Script;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.config.TestnetConfig;
import serg.home.bitcoinSimple.network.model.Timestamp4;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Testnet3GenesisBlockTest {
    static final String genesisHeaderHash = "000000000933ea01ad0ee984209779baaec3ced90fa3f408719526f8d77f4943";

//    {
//        "status" : "success",
//            "data" : {
//        "network" : "BTCTEST",
//                "version" : 1,
//                "time" : 1296688602,
//                "sent_value" : "0.00000000",
//                "fee" : "0.00000000",
//                "mining_difficulty" : "1",
//                "nonce" : 414098458,
//                "bits" : "1d00ffff",
//                "size" : 285,
//                "blockhash" : "000000000933ea01ad0ee984209779baaec3ced90fa3f408719526f8d77f4943",
//                "merkleroot" : "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b",
//                "previous_blockhash" : null,
//                "next_blockhash" : "00000000b873e79784647a6c82962c70d228557d24a747ea4d1b8bbe878e1206",
//                "txs" : [
//        {
//            "txid" : "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b",
//                "fee" : "0.00000000",
//                "inputs" : [],
//            "outputs" : []
//        }
//    ]
//    },
//        "code" : 200,
//            "message" : ""
//    }


    static final String headerBinary = "01000000" + // version
            "0000000000000000000000000000000000000000000000000000000000000000" + //prev block
            "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b" + //merkle root
            "29AB5F49" + // timestamp
            "FFFF001D" + // bits
            "1DAC2B7C";  // nonce

    public static final String genesisBinary = headerBinary +
            "01" + // number of transactions
            "01000000" + // version
            "01" + // input
            "0000000000000000000000000000000000000000000000000000000000000000FFFFFFFF" + // prev output
            "4D" + // script length
            "04FFFF001D0104455468652054696D65732030332F4A616E2F32303039204368616E63656C6C6F72206F6E206272696E6B206F66207365636F6E64206261696C6F757420666F722062616E6B73" + // scriptsig
            "FFFFFFFF" + //sequence
            "01" + //outputs
            "00F2052A01000000" + //50 BTC
            "43" + //pk_script length
            "4104678AFDB0FE5548271967F1A67130B7105CD6A828E03909A67962E0EA1F61DEB649F6BC3F4CEF38C4F35504E51EC112DE5C384DF7BA0B8D578A4C702B6BF11D5FAC" + //pk_script
            "00000000";

    @Test
    void encode() {
        BlockHeader blockHeader = new BlockHeader(
                BlockVersion.V1,
                new Bytes("0000000000000000000000000000000000000000000000000000000000000000"),
                new Bytes("4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b"),
                new Timestamp4(1296688602L),
                new Difficulty(new Bytes("1d00ffff")),
                414098458
        );

        CoinbaseInput input = new CoinbaseInput(
                new CoinbaseData(null, new Bytes("04FFFF001D0104455468652054696D65732030332F4A616E2F32303039204368616E63656C6C6F72206F6E206272696E6B206F66207365636F6E64206261696C6F757420666F722062616E6B73"))
        );
        List<Input> inputs = Collections.singletonList(input);
        Output output = new Output(
                new Value(5_000_000_000L),
                new Script().__(new Bytes("04678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f")).__(OP.CHECKSIG)
        );
        List<Output> outputs = Collections.singletonList(output);
        List<Transaction> transactions = Collections.singletonList(new Transaction(TxVersion.V1, 0, inputs, outputs));

        Block testnet3GenesisBlock = new Block(blockHeader, transactions);
        System.out.println("mercle root");
//        "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b";
//        System.out.println(testnet3GenesisBlock.mercleRoot());

        String genesisHash = "000000000019D6689C085AE165831E934FF763AE46A2A6C172B3F1B60A8CE26F";
        Block genesis = new TestnetConfig().genesis();
        assertEquals(genesisBinary, genesis.encode().getHexString());
        assertEquals(genesisHash, new Bytes(headerBinary).doubleSha256().flip().getHexString());
    }
}