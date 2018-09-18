package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public abstract class Timestamp implements ByteBufWritable {
    protected OffsetDateTime value;

    public OffsetDateTime getValue() {
        return value;
    }
    public Timestamp(OffsetDateTime value) {
        this.value = value;
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
