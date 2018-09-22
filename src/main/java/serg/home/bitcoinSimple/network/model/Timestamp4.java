package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Standard UNIX timestamp in seconds. 4 bytes length.
 */
public class Timestamp4 extends Timestamp {
    public static Timestamp4 read(ByteBuf byteBuf) {
        return new Timestamp4(Instant.ofEpochSecond(byteBuf.readIntLE()).atOffset(ZoneOffset.UTC));
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
