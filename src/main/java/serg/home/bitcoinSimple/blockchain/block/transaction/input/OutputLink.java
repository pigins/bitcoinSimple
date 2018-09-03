package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;

public class OutputLink implements BinaryEncoded, BinaryDecoded {
    private Bytes txHash;
    private int uVout;

    public OutputLink(Bytes txHash, int uVout) {
        this.txHash = txHash;
        this.uVout = uVout;
    }

    public OutputLink(ByteReader byteReader) {
        decode(byteReader);
    }

    @Override
    public void decode(ByteReader byteReader) {
        txHash = byteReader.next(32);
        uVout = byteReader.nextInt();
    }

    @Override
    public Bytes encode() {
        return txHash.concat(Bytes.fromInt(uVout));
    }
}
