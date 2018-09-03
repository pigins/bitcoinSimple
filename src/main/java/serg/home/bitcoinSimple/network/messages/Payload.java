package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

public interface Payload extends BinaryEncoded {
    String name();
}
