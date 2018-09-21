package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.nio.charset.StandardCharsets;

/**
 * Variable length string can be stored using a variable length integer followed by the string itself.
 */
public class VarString implements ByteBufWritable {
    public static String read(ByteBuf byteBuf) {
        long stringLength = VarInt.read(byteBuf);
        return (String) byteBuf.readCharSequence((int) stringLength, StandardCharsets.US_ASCII);
    }

    private String value;

    public VarString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byte[] bytes = value.getBytes(StandardCharsets.US_ASCII);
        new VarInt(bytes.length).write(byteBuf);
        byteBuf.writeBytes(bytes);
    }

    @Override
    public String toString() {
        return "VarString{" +
                "value='" + value + '\'' +
                '}';
    }
}
