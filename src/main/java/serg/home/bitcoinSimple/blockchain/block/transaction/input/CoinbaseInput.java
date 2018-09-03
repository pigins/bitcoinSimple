package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class CoinbaseInput extends Input {
    private static final OutputLink OUTPUT_LINK = new OutputLink(
            new Bytes("0000000000000000000000000000000000000000000000000000000000000000"),
            -1
    );

    private CoinbaseData coinbaseData;

    public CoinbaseInput(CoinbaseData coinbaseData) {
        this.outputLink = OUTPUT_LINK;
        this.uSequence = -1;
        this.coinbaseData = coinbaseData;
    }

    public CoinbaseInput(ByteReader byteReader) {
        decode(byteReader);
    }

    @Override
    public void decode(ByteReader byteReader) {
        this.outputLink = new OutputLink(byteReader);
        this.coinbaseData = new CoinbaseData(byteReader);
        this.uSequence = byteReader.nextInt();
    }

    @Override
    public Bytes encode() {
        return new CompoundBinary().add(outputLink).add(coinbaseData).add(Bytes.fromInt(uSequence)).encode();
    }
}
