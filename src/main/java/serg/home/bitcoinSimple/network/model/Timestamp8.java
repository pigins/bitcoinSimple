package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.time.Instant;
import java.time.ZoneOffset;

public class Timestamp8 extends Timestamp {

    public Timestamp8(ByteReader byteReader) {
        super(byteReader);
    }

    public Timestamp8(long v) {
        super(v);
    }

    public Timestamp8() {
        super();
    }

    @Override
    public void decode(ByteReader byteReader) {
        value = Instant.ofEpochSecond(byteReader.nextLongLE()).atOffset(ZoneOffset.UTC);
    }

    @Override
    public Bytes encode() {
        return Bytes.fromLongToLE(value.toEpochSecond());
    }


}
