package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.util.Arrays;

public enum Network implements BinaryEncoded {
    MAIN("D9B4BEF9"),
    TESTNET("DAB5BFFA"),
    TESTNET3("0709110B"),
    NAMECOIN("FEB4BEF9");

    private Bytes magicLE;

    public static Network decode(ByteReader byteReader) {
        Bytes bytes = byteReader.next(4);
        return Arrays.stream(Network.values())
                .filter(network -> network.magicLE.equals(bytes))
                .findAny()
                .get();
    }

    Network(String magic) {
        this.magicLE = new Bytes(magic).flip();
    }

    @Override
    public Bytes encode() {
        return this.magicLE;
    }

    @Override
    public String toString() {
        return "Network{" +
                this.name()+
                '}';
    }
}
