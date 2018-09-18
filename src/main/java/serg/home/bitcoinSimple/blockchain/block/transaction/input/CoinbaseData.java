package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import javax.annotation.Nullable;
import java.util.Objects;

public class CoinbaseData implements ByteBufWritable {
    public static CoinbaseData read(ByteBuf byteBuf) {
        int dataSize = (int) VarInt.read(byteBuf);
        ByteBuf data = byteBuf.readBytes(dataSize);
        // TODO add height decoding
        // array!!
        return new CoinbaseData(null, data.array());
    }
    private static final int MIN_SIZE = 2;
    private static final int MAX_SIZE = 100;
//    public CoinbaseData read(ByteBuf buf) {
//        buf.re
//        int dataSize = VarInt.read(buf);
//        ByteBuf byteBuf = buf.readRetainedSlice(dataSize);
//        int dataSize = byteReader.nextVarInt().toInt();
//        this.bytes = byteReader.next(dataSize);
//    }
    private Integer blockHeight;
    private byte[] bytes;

    public CoinbaseData(@Nullable Integer blockHeight, byte[] bytes) {
        this.blockHeight = blockHeight;
        this.bytes = bytes;
        Objects.requireNonNull(bytes);
        if (blockHeight == null) {
            if (bytes.length < MIN_SIZE || bytes.length > MAX_SIZE) {
                throw new IllegalArgumentException();
            }
        } else {
            if (bytes.length < MIN_SIZE || bytes.length > MAX_SIZE - 4) {
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public void write(ByteBuf byteBuf) {
        // should be CompositeByteBuf
        if (blockHeight == null) {
            new VarInt(bytes.length).write(byteBuf);
            byteBuf.writeBytes(bytes);
        } else {
            new VarInt(bytes.length + 4).write(byteBuf);
            byteBuf.writeInt(blockHeight);
            byteBuf.writeBytes(bytes);
        }
    }

    @Override
    public String toString() {
        return "CoinbaseData{" +
                "blockHeight=" + blockHeight +
                ", bytes=" + bytes +
                '}';
    }
}
