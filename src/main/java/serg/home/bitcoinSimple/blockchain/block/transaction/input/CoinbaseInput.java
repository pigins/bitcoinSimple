package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class CoinbaseInput extends Input {
    private static final OutputLink OUTPUT_LINK = new OutputLink(
            new Bytes("0000000000000000000000000000000000000000000000000000000000000000"),
            -1
    );

    private CoinbaseData coinbaseData;

    public CoinbaseInput(OutputLink outputLink, CoinbaseData coinbaseData, int uSequence) {
        this.outputLink = outputLink;
        this.coinbaseData = coinbaseData;
        this.uSequence = uSequence;
    }

    public CoinbaseInput(CoinbaseData coinbaseData) {
        this.outputLink = OUTPUT_LINK;
        this.uSequence = -1;
        this.coinbaseData = coinbaseData;
    }

    @Override
    public Bytes encode() {
        return new CompoundBinary().add(outputLink).add(coinbaseData).add(Bytes.fromInt(uSequence)).encode();
    }

    @Override
    public String toString() {
        return "CoinbaseInput{" +
                "coinbaseData=" + coinbaseData +
                ", outputLink=" + outputLink +
                ", uSequence=" + uSequence +
                '}';
    }
}
