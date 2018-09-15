package serg.home.bitcoinSimple.common.binary;

import io.netty.buffer.ByteBuf;

public interface BinaryEncoded {
    void write(ByteBuf byteBuf);
}
