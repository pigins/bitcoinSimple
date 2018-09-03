package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

public enum Network implements BinaryEncoded {
    MAIN("D9B4BEF9"),
    TESTNET("DAB5BFFA"),
    TESTNET3("0709110B"),
    NAMECOIN("FEB4BEF9");

    public final Bytes magicLE;

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
