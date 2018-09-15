package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;

public class Verack implements Payload {
    public static final String NAME = "verack";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        /*NOPE*/
    }
}
