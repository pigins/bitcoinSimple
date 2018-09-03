package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;

public class Verack implements Payload {
    public static final String NAME = "verack";

    @Override
    public Bytes encode() {
        return new Bytes(new byte[0]);
    }

    @Override
    public String name() {
        return NAME;
    }
}
