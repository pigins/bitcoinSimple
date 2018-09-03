package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

public class OutputLink implements BinaryEncoded {
    private Bytes txHash;
    private int uVout;

    public OutputLink(Bytes txHash, int uVout) {
        this.txHash = txHash;
        this.uVout = uVout;
    }

    @Override
    public Bytes encode() {
        return txHash.concat(Bytes.fromInt(uVout));
    }

    @Override
    public String toString() {
        return "OutputLink{" +
                "txHash=" + txHash +
                ", uVout=" + uVout +
                '}';
    }
}
