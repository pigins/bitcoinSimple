package serg.home.bitcoinSimple.blockchain.block.transaction;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;

public enum TxVersion {
    V1(1), V2(2);
    public static TxVersion read(ByteBuf byteBuf) {
        int value = byteBuf.readIntLE();
        return Arrays.stream(TxVersion.values()).filter(version -> version.version == value).findAny().get();
    }
    public final int version;
    TxVersion(int version) {
        this.version = version;
    }
    public int version() {
        return version;
    }
}
