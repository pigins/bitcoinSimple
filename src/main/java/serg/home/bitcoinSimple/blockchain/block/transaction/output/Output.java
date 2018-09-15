package serg.home.bitcoinSimple.blockchain.block.transaction.output;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.blockchain.block.transaction.TxVersion;
import serg.home.bitcoinSimple.blockchain.block.transaction.script.Script;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class Output implements BinaryEncoded {
    public static Output read(ByteBuf byteBuf) {
       return new Output(byteBuf.readLongLE(), Script.read(byteBuf));
    }
    // denominated in satoshis
    private long value;
    /**
     * a cryptographic puzzle that determines the conditions required to spend the output(locking script)
     */
    private Script scriptPubKey;

    public Output(long value, Script scriptPubKey) {
        this.value = value;
        this.scriptPubKey = scriptPubKey;
    }

    @Override
    public String toString() {
        return "Output{" +
                "value=" + value +
                ", scriptPubKey=" + scriptPubKey +
                '}';
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeLongLE(value);
        scriptPubKey.write(byteBuf);
    }
}
