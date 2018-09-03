package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;

public class Pong implements Payload {
    public static final String NAME = "pong";
    private long uNonce;

    public Pong(Ping ping) {
        this.uNonce = ping.getuNonce();
    }

    public Pong(Bytes bytes) {
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
        return "Pong{" +
                "uNonce=" + uNonce +
                '}';
    }
}
