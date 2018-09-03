package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public abstract class Timestamp implements BinaryEncoded, BinaryDecoded {
    protected OffsetDateTime value;

    public OffsetDateTime getValue() {
        return value;
    }
    public Timestamp(OffsetDateTime value) {
        this.value = value;
    }
    public Timestamp(ByteReader byteReader) {
        decode(byteReader);
    }

    public Timestamp(long v) {
        this.value = OffsetDateTime.ofInstant(Instant.ofEpochSecond(v), ZoneId.of("UTC"));
    }

    public Timestamp() {
        value = OffsetDateTime.now(ZoneOffset.UTC);
    }

    @Override
    public String toString() {
        return "Timestamp{" +
                "value=" + value +
                '}';
    }
}
