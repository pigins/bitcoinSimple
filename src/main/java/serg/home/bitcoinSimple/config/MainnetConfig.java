package serg.home.bitcoinSimple.config;

import serg.home.bitcoinSimple.network.messages.Block;

public class MainnetConfig {

    public Block genesis() {
        throw new UnsupportedOperationException("not implemented");
//        BlockHeader blockHeader = new BlockHeader(
//                BlockVersion.V1,
//                new Bytes("0000000000000000000000000000000000000000000000000000000000000000"),
//                new Bytes("4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b"),
//                new Timestamp4(1231006505L),
//                new Difficulty(new Bytes("1d00ffff")),
//                2083236893,
//                false
//        );
//        CoinbaseInput input = new CoinbaseInput(
//                new CoinbaseData(null, new Bytes("04FFFF001D0104455468652054696D65732030332F4A616E2F32303039204368616E63656C6C6F72206F6E206272696E6B206F66207365636F6E64206261696C6F757420666F722062616E6B73"))
//        );
//        List<Input> inputs = Collections.singletonList(input);
//        Output output = new Output(
//                new Value(5_000_000_000L),
//                new Script().__(new Bytes("04678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f")).__(OP.CHECKSIG)
//        );
//        List<Output> outputs = Collections.singletonList(output);
//        List<Transaction> transactions = Collections.singletonList(new Transaction(TxVersion.V1, 0, inputs, outputs));
//        return new Block(blockHeader, transactions);
    }
}
