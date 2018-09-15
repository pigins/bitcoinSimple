package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class InvVector implements BinaryEncoded {
    public static InvVector read(ByteBuf byteBuf) {
        return new InvVector(InvType.fromInt(byteBuf.readIntLE()), byteBuf.readBytes(32));
    }

    private InvType type;
    private ByteBuf hash;

    public InvVector(InvType type, ByteBuf hash) {
        this.type = type;
        this.hash = hash;
    }

    public InvType type() {
        return type;
    }

    public ByteBuf hash() {
        return hash;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeIntLE(type.typeValue());
        byteBuf.writeBytes(hash);
    }

    @Override
    public String toString() {
        return "InvVector{" +
                "type=" + type +
                ", hash=" + hash +
                '}';
    }
}
