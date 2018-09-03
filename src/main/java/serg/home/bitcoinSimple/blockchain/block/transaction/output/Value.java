package serg.home.bitcoinSimple.blockchain.block.transaction.output;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;

public class Value implements BinaryEncoded, BinaryDecoded {
    // denominated in satoshis
    private long value;

    public Value(long value) {
        this.value = value;
    }

    public Value(ByteReader byteReader) {
        decode(byteReader);
    }

    @Override
    public void decode(ByteReader byteReader) {
        value = byteReader.nextLongLE();
    }

    @Override
    public Bytes encode() {
        return Bytes.fromLongToLE(value);
    }
}
