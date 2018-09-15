package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Timestamp8 extends Timestamp {

    public static OffsetDateTime read(ByteBuf byteBuf) {
        return Instant.ofEpochSecond(byteBuf.readLongLE()).atOffset(ZoneOffset.UTC);
    }

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
    public void write(ByteBuf byteBuf) {
        byteBuf.writeLongLE(value.toEpochSecond());
    }
}
