package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VarInt implements BinaryEncoded, BinaryDecoded {
    private long value;

    public VarInt(long value) {
        this.value = value;
    }

    public VarInt(ByteReader byteReader) {
        decode(byteReader);
    }

    public int toInt() {
        return (int)value;
    }

    @Override
    public void decode(ByteReader byteReader) {
        byte b = byteReader.nextByte();
        if (Byte.compareUnsigned(((byte) 0xfc), b) > 0) {
            value = b;
        } else if (Byte.compareUnsigned(((byte) 0xfd), b) == 0) {
            ByteBuffer buf = ByteBuffer.allocate(2);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.rewind();
            value = buf.getShort();
        } else if (Byte.compareUnsigned(((byte) 0xfe), b) == 0) {
            ByteBuffer buf = ByteBuffer.allocate(4);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.rewind();
            value = buf.getInt();
        } else {
            ByteBuffer buf = ByteBuffer.allocate(8);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.put(byteReader.nextByte());
            buf.rewind();
            value = buf.getLong();
        }
    }

    @Override
    public Bytes encode() {
        if (value <= 0xfcL) {
            return new Bytes(BigInteger.valueOf(value).toByteArray());
        } else if (value <= 0xffffL) {
            byte[] res = new byte[]{-3, 0, 0};
            ByteBuffer buf = ByteBuffer.allocate(2);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.putShort((short) value);
            System.arraycopy(buf.array(), 0, res, 1, 2);
            return new Bytes(res);
        } else if (value <= 0xffffffffL) {
            byte[] res = new byte[]{-2, 0, 0, 0, 0};
            ByteBuffer buf = ByteBuffer.allocate(4);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.putInt((int)value);
            System.arraycopy(buf.array(), 0, res, 1, 4);
            return new Bytes(res);
        } else {
            byte[] res = new byte[]{-1, 0, 0, 0, 0, 0, 0, 0, 0};
            ByteBuffer buf = ByteBuffer.allocate(8);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.putLong(value);
            System.arraycopy(buf.array(), 0, res, 1, 8);
            return new Bytes(res);
        }
    }
}