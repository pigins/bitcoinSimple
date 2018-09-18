package serg.home.bitcoinSimple.blockchain.block;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.util.Arrays;

// https://data.bitcoinity.org/bitcoin/block_version/all?c=block_version&r=month&t=a
public enum BlockVersion implements ByteBufWritable {
    V1(1), V2(2), V3(3), V4(4);

    public static BlockVersion read(ByteBuf byteBuf) {
        int value = byteBuf.readIntLE();
        return Arrays.stream(BlockVersion.values()).filter(blockVersion -> blockVersion.getuCode() == value).findAny().get();
    }

    private int uCode;

    BlockVersion(int uCode) {
        this.uCode = uCode;
    }

    public int getuCode() {
        return uCode;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeIntLE(uCode);
    }
}
