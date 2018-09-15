package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.blockchain.block.transaction.TxVersion;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class CoinbaseInput extends Input {
    public static CoinbaseInput read(ByteBuf byteBuf) {
        return new CoinbaseInput(
                OutputLink.read(byteBuf), CoinbaseData.read(byteBuf), byteBuf.readInt()
        );
    }
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
    public void write(ByteBuf byteBuf) {
        outputLink.write(byteBuf);
        coinbaseData.write(byteBuf);
        byteBuf.writeInt(uSequence);
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
