package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import java.nio.charset.StandardCharsets;

public class VarString implements BinaryEncoded {
    private VarInt varInt;
    private String value;

    public VarString(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.US_ASCII);
        varInt = new VarInt(bytes.length);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Bytes encode() {
        return new CompoundBinary()
                .add(varInt)
                .add(new Bytes(value.getBytes(StandardCharsets.US_ASCII)))
                .encode();
    }

    @Override
    public String toString() {
        return "VarString{" +
                "value='" + value + '\'' +
                '}';
    }
}
