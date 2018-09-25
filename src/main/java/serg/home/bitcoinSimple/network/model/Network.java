package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.util.Arrays;

/**
 * Magic value indicating message origin network, and used to seek to next message when stream state is unknown
 */
public enum Network implements ByteBufWritable {
    MAIN(0xD9B4BEF9),
    REGTEST(0xDAB5BFFA),
    TESTNET3(0x0709110B);

    private final int magic;

    Network(int magic) {
        this.magic = magic;
    }

    public static Network read(ByteBuf byteBuf) {
        int value = byteBuf.readIntLE();
        return Arrays.stream(Network.values())
                .filter(network -> network.magic == value)
                .findAny()
                .orElseThrow();
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
