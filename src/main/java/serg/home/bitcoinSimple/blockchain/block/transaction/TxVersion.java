package serg.home.bitcoinSimple.blockchain.block.transaction;

import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.util.Arrays;

public enum TxVersion {
    V1(1), V2(2);

    private int version;

    public static TxVersion decode(ByteReader byteReader) {
        int value = byteReader.nextIntLE();
        return Arrays.stream(TxVersion.values()).filter(blockVersion -> blockVersion.version == value).findAny().get();
    }

    TxVersion(int version) {
        this.version = version;
    }


    public int version() {
        return version;
    }
}
