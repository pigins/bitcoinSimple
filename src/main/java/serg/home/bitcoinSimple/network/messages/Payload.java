package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.ByteBufWritable;

public interface Payload extends ByteBufWritable {
    String name();
}
