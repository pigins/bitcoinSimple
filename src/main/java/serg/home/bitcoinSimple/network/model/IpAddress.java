package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

import java.math.BigInteger;
import java.util.BitSet;

public class IpAddress implements BinaryEncoded {
    public static IpAddress read(ByteBuf byteBuf) {
        byteBuf.readerIndex(byteBuf.readerIndex() + 12);
        return new IpAddress(byteBuf.readInt());
    }

    static int toInt(String dottedDecimal) {
        int byte1 = 0;
        int value = 0;
        for (char c : dottedDecimal.toCharArray()) {
            if (c != '.') {
                byte1 = 10 * byte1 + (c - '0');
            } else {
                value <<= 8;
                value += byte1;
                byte1 = 0;
            }
        }
        value <<= 8;
        value += byte1;
        return value;
    }

    private int value;

    public IpAddress(int value) {
        this.value = value;
    }

    public IpAddress(String value) {
        this.value = toInt(value);
    }

    @Override
    public void write(ByteBuf byteBuf) {
        BitSet bits = new BitSet(80 + 16);
        bits.flip(80, 96);
        byteBuf.writeBytes(bits.toByteArray());
        byteBuf.writeInt(value);
    }

    public String getIpString() {
        return String.format("%d.%d.%d.%d", (value >> 24 & 0xff), (value >> 16 & 0xff), (value >> 8 & 0xff), (value & 0xff));
    }

    public int asInt() {
        return value;
    }

    public boolean isSiteLocalAddress() {
        return (((value >>> 24) & 0xFF) == 10)
                || ((((value >>> 24) & 0xFF) == 172)
                && (((value >>> 16) & 0xF0) == 16))
                || ((((value >>> 24) & 0xFF) == 192)
                && (((value >>> 16) & 0xFF) == 168));
    }

    @Override
    public String toString() {
        return "IpAddress{" +
                "value=" + getIpString() +
                '}';
    }
}
