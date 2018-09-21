package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Compact integer representation.
 * Variable length integers always precede an array/vector of a type of data that may vary in length.
 */
public class VarInt implements ByteBufWritable {
    public static long read(ByteBuf byteBuf) {
        long value;
        byte b = byteBuf.readByte();
        if (Byte.compareUnsigned(((byte) 0xfc), b) > 0) {
            value = b;
        } else if (Byte.compareUnsigned(((byte) 0xfd), b) == 0) {
            value = byteBuf.readUnsignedShortLE();
        } else if (Byte.compareUnsigned(((byte) 0xfe), b) == 0) {
            value = byteBuf.readUnsignedIntLE();
        } else {
            value = byteBuf.readLongLE();
        }
        return value;
    }

    private long value;

    public VarInt(long value) {
        this.value = value;
    }

    public void write(ByteBuf byteBuf) {
        if (value <= 0xfcL) {
            byteBuf.writeByte((int) value);
        } else if (value <= 0xffffL) {
            byteBuf.writeByte(-3);
            byteBuf.writeShortLE((int) value);
        } else if (value <= 0xffffffffL) {
            byteBuf.writeByte(-2);
            byteBuf.writeIntLE((int) value);
        } else {
            byteBuf.writeByte(-1);
            byteBuf.writeLongLE(value);
        }
    }
}