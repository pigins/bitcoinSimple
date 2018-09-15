package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

public class OutputLink implements BinaryEncoded {
    public static OutputLink read(ByteBuf byteBuf) {
        return new OutputLink(byteBuf.readBytes(32), byteBuf.readInt());
    }

    private ByteBuf txHash;
    private int uVout;

    public OutputLink(ByteBuf txHash, int uVout) {
        this.txHash = txHash;
        this.uVout = uVout;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeBytes(txHash);
        byteBuf.writeInt(uVout);
    }

    @Override
    public String toString() {
        return "OutputLink{" +
                "txHash=" + txHash +
                ", uVout=" + uVout +
                '}';
    }


}
