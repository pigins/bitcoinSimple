package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

public class InvVector implements ByteBufWritable {
    public static InvVector read(ByteBuf byteBuf) {
        return new InvVector(InvType.fromInt(byteBuf.readIntLE()), Hash32.read(byteBuf));
    }

    private InvType type;
    private Hash32 hash;

    public InvVector(InvType type, Hash32 hash) {
        this.type = type;
        this.hash = hash;
    }

    public InvType type() {
        return type;
    }

    public Hash32 hash() {
        return hash;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeIntLE(type.asInt());
        hash.write(byteBuf);
    }

    @Override
    public String toString() {
        return "InvVector{" +
                "type=" + type +
                ", hash=" + hash +
                '}';
    }
}
