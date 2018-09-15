package serg.home.bitcoinSimple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

public abstract class BaseTest {
    public ByteBuf fromHex(String hexString) {
        return Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hexString));
    }
}
