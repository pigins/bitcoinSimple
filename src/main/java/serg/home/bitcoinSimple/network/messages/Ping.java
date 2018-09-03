package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Does nothing. Used to check that the connection is still online. A TCP error will occur if the connection has died.
 */
public class Ping implements Payload {
    public static final String NAME = "ping";
    private long uNonce;

    public long getuNonce() {
        return uNonce;
    }

    public Ping() {
        this.uNonce = ThreadLocalRandom.current().nextLong();
    }

    public Ping(Bytes bytes) {
        decode(new ByteReader(bytes));
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void decode(ByteReader byteReader) {
        this.uNonce = byteReader.nextLong();
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
