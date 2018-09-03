package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;

public class Pong implements Payload {
    public static final String NAME = "pong";
    private long uNonce;

    public Pong(Ping ping) {
        this.uNonce = ping.getuNonce();
    }

    public Pong(long uNonce) {
        this.uNonce = uNonce;
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
        return "Pong{" +
                "uNonce=" + uNonce +
                '}';
    }
}
