package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

import java.util.Arrays;

public enum Network implements BinaryEncoded {
    MAIN(0xD9B4BEF9),
    TESTNET(0xDAB5BFFA),
    TESTNET3(0x0709110B),
    NAMECOIN(0xFEB4BEF9);

    public final int magic;

    Network(int magic) {
        this.magic = magic;
    }

    public static Network read(ByteBuf byteBuf) {
        int value = byteBuf.readIntLE();
        return Arrays.stream(Network.values())
                .filter(network -> network.magic == value)
                .findAny()
                .get();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeIntLE(this.magic);
    }

    @Override
    public String toString() {
        return "Network{" +
                this.name()+
                '}';
    }
}
