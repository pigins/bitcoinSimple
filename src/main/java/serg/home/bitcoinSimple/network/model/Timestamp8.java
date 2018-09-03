package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;

import java.time.OffsetDateTime;

public class Timestamp8 extends Timestamp {

    public Timestamp8(long v) {
        super(v);
    }

    public Timestamp8() {
        super();
    }

    public Timestamp8(OffsetDateTime value) {
        super(value);
    }

    @Override
    public Bytes encode() {
        return Bytes.fromLongToLE(value.toEpochSecond());
    }
}
