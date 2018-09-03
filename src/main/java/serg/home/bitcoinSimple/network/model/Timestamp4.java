package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;

import java.time.OffsetDateTime;

public class Timestamp4 extends Timestamp {

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
    public Bytes encode() {
        return Bytes.fromIntLE((int)value.toEpochSecond());
    }
}
