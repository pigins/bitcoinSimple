package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;

public class Block implements Payload {
    private static final String NAME = "block";

    @Override
    public String name() {
        return NAME;
    }

//    @Override
//    public void decode(ByteReader byteReader) {
//
//    }

    @Override
    public Bytes encode() {
        return null;
    }
}
