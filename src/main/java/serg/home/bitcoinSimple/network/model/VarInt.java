package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
            byteBuf.writeBytes(BigInteger.valueOf(value).toByteArray());
        } else if (value <= 0xffffL) {
            byte[] res = new byte[]{-3, 0, 0};
            ByteBuffer buf = ByteBuffer.allocate(2);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.putShort((short) value);
            System.arraycopy(buf.array(), 0, res, 1, 2);
            byteBuf.writeBytes(res);
        } else if (value <= 0xffffffffL) {
            byte[] res = new byte[]{-2, 0, 0, 0, 0};
            ByteBuffer buf = ByteBuffer.allocate(4);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.putInt((int)value);
            System.arraycopy(buf.array(), 0, res, 1, 4);
            byteBuf.writeBytes(res);
        } else {
            byte[] res = new byte[]{-1, 0, 0, 0, 0, 0, 0, 0, 0};
            ByteBuffer buf = ByteBuffer.allocate(8);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.putLong(value);
            System.arraycopy(buf.array(), 0, res, 1, 8);
            byteBuf.writeBytes(res);
        }
    }
}