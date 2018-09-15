package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;

public class Pong implements Payload {
    public static final String NAME = "pong";
    public static Pong read(ByteBuf byteBuf) {
        return new Pong(byteBuf.readLong());
    }
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
    public void write(ByteBuf byteBuf) {
        byteBuf.writeLong(uNonce);
    }

    @Override
    public String toString() {
        return "Pong{" +
                "uNonce=" + uNonce +
                '}';
    }
}
