package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Timestamp4 extends Timestamp {

    public static OffsetDateTime read(ByteBuf byteBuf) {
        return Instant.ofEpochSecond(byteBuf.readIntLE()).atOffset(ZoneOffset.UTC);
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
    public void write(ByteBuf byteBuf) {
        byteBuf.writeIntLE((int)value.toEpochSecond());
    }
}
