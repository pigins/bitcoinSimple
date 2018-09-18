package serg.home.bitcoinSimple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import serg.home.bitcoinSimple.common.ByteBufWritable;
import serg.home.bitcoinSimple.common.DigestByteBuf;

public abstract class BaseTest {

    public ByteBuf fromHex(String hexString) {
        return Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hexString));
    }

    public String writeHex(ByteBufWritable byteBufWritable) {
        ByteBuf buffer = Unpooled.buffer();
        byteBufWritable.write(buffer);
        return ByteBufUtil.hexDump(buffer);
    }
}
