package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;

/**
 * https://en.bitcoin.it/wiki/Satoshi_Client_Node_Discovery
 */
public class GetAddr implements Payload {
    public static final String NAME = "getaddr";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        /*NOPE*/
    }
}
