package serg.home.bitcoinSimple.common;

import io.netty.buffer.ByteBuf;

public interface ByteBufWritable {
    void write(ByteBuf byteBuf);
}
