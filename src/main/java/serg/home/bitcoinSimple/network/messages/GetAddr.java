package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;

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
    public void decode(ByteReader byteReader) {
        /*NOPE*/
    }

    @Override
    public Bytes encode() {
        return new Bytes();
    }
}
