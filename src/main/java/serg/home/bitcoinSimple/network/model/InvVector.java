package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class InvVector implements BinaryEncoded, BinaryDecoded {
    private InvType type;
    private Bytes hash;

    public InvVector(ByteReader byteReader) {
        decode(byteReader);
    }

    public InvVector(InvType type, Bytes hash) {
        this.type = type;
        this.hash = hash;
    }

    public InvType type() {
        return type;
    }

    public Bytes hash() {
        return hash;
    }

    @Override
    public void decode(ByteReader byteReader) {
        int value = byteReader.nextIntLE();
        type = InvType.fromInt(value);
        hash = byteReader.next(32);
    }

    @Override
    public Bytes encode() {
        return new CompoundBinary()
                .add(Bytes.fromIntLE(type.typeValue()))
                .add(hash)
                .encode();
    }

    @Override
    public String toString() {
        return "InvVector{" +
                "type=" + type +
                ", hash=" + hash +
                '}';
    }
}
