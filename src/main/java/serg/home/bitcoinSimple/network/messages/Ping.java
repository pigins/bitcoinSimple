package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Does nothing. Used to check that the connection is still online. A TCP error will occur if the connection has died.
 */
public class Ping implements Payload {
    public static final String NAME = "ping";
    public static Ping read(ByteBuf byteBuf) {
        return new Ping(byteBuf.readLong());
    }

    private long uNonce;

    public Ping(long uNonce) {
        this.uNonce = uNonce;
    }

    public long getuNonce() {
        return uNonce;
    }

    public Ping() {
        this.uNonce = ThreadLocalRandom.current().nextLong();
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeLong(uNonce);
    }

    @Override
    public String toString() {
        return "Ping{" +
                "uNonce=" + uNonce +
                '}';
    }
}
