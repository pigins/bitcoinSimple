package serg.home.bitcoinSimple.network.handlers;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

public class MessageDecoder extends LengthFieldBasedFrameDecoder {
    public MessageDecoder() {
        super(
                ByteOrder.LITTLE_ENDIAN,
                1024 * 1024, // max block size = 1mb
                16, // magic and command bytes
                4,
                4, // skip checksum bytes
                0,
                true);
    }
}
