package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.blockchain.block.transaction.TxVersion;
import serg.home.bitcoinSimple.blockchain.block.transaction.script.Script;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class RegularInput extends Input {
    public static RegularInput read(ByteBuf byteBuf) {
        return new RegularInput(OutputLink.read(byteBuf), Script.read(byteBuf), byteBuf.readInt());
    }

    private Script unlockingScript;

    public RegularInput(OutputLink outputLink, Script unlockingScript, int uSequence) {
        this.outputLink = outputLink;
        this.unlockingScript = unlockingScript;
        this.uSequence = uSequence;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        outputLink.write(byteBuf);
        unlockingScript.write(byteBuf);
        byteBuf.writeInt(uSequence);
    }

    @Override
    public String toString() {
        return "RegularInput{" +
                "unlockingScript=" + unlockingScript +
                ", outputLink=" + outputLink +
                ", uSequence=" + uSequence +
                '}';
    }
}
