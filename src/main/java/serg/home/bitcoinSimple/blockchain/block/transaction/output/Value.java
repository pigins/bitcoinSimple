package serg.home.bitcoinSimple.blockchain.block.transaction.output;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

public class Value implements BinaryEncoded {
    // denominated in satoshis
    private long value;

    public Value(long value) {
        this.value = value;
    }

    @Override
    public Bytes encode() {
        return Bytes.fromLongToLE(value);
    }

    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                '}';
    }
}
