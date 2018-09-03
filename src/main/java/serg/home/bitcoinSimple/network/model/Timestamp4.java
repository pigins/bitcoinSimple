package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Timestamp4 extends Timestamp {

    public Timestamp4(ByteReader byteReader) {
        super(byteReader);
    }

    public Timestamp4(long v) {
        super(v);
    }

    public Timestamp4(OffsetDateTime value) {
        super(value);
    }

    public Timestamp4() {
        super();
    }

    @Override
    public void decode(ByteReader byteReader) {
        value = Instant.ofEpochSecond(byteReader.nextIntLE()).atOffset(ZoneOffset.UTC);
    }

    @Override
    public Bytes encode() {
        return Bytes.fromIntLE((int)value.toEpochSecond());
    }
}
