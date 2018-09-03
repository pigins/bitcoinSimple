package serg.home.bitcoinSimple.blockchain.block.transaction.script;

import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.util.Arrays;

public enum OP {
    DUP((byte) 118),
    HASH160((byte) (169 - 256)),
    EQUALVERIFY((byte) (136 - 256)),
    CHECKSIG((byte) (172 - 256));

    public static OP decode(ByteReader byteReader) {
        byte value = byteReader.nextByte();
        return Arrays.stream(OP.values()).filter(op -> op.code == value).findAny().get();
    }
    public static OP from(byte b) {
        return Arrays.stream(OP.values()).filter(op -> op.code == b).findAny().get();
    }


    private byte code;

    OP(byte code) {
        this.code = code;
    }

    public int size() {
        return 1;
    }

    public byte getCode() {
        return code;
    }
}
