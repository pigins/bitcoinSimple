package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Does nothing. Used to check that the connection is still online. A TCP error will occur if the connection has died.
 */
public class Ping implements Payload {
    public static final String NAME = "ping";
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
    public Bytes encode() {
        return Bytes.fromLong(uNonce);
    }

    @Override
    public String toString() {
        return "Ping{" +
                "uNonce=" + uNonce +
                '}';
    }
}
