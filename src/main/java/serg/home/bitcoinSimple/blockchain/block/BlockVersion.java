package serg.home.bitcoinSimple.blockchain.block;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.util.Arrays;

// https://data.bitcoinity.org/bitcoin/block_version/all?c=block_version&r=month&t=a
public enum BlockVersion implements BinaryEncoded {
    V1(1), V2(2), V3(3), V4(4);

    private int uCode;

    BlockVersion(int uCode) {
        this.uCode = uCode;
    }

    public static BlockVersion decode(ByteReader byteReader) {
        int value = byteReader.nextIntLE();
        return Arrays.stream(BlockVersion.values()).filter(blockVersion -> blockVersion.uCode == value).findAny().get();
    }

    @Override
    public Bytes encode() {
        return Bytes.fromIntLE(uCode);
    }
}
