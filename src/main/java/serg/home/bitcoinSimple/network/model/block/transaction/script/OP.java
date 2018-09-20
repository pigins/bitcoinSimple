package serg.home.bitcoinSimple.network.model.block.transaction.script;

import java.util.Arrays;
import java.util.function.Supplier;

public enum OP {
    DUP((byte) 118),
    HASH160((byte) (169 - 256)),
    EQUALVERIFY((byte) (136 - 256)),
    CHECKSIG((byte) (172 - 256)),
    NOT_FOUND((byte) (243-256));

    public static OP from(byte b) {
        try {
            return Arrays.stream(OP.values())
                    .filter(op -> op.code == b)
                    .findAny()
                    .orElseThrow((Supplier<Throwable>) () -> new RuntimeException("can't find OP code for[" + b + "]"));
        } catch (Throwable throwable) {
            return OP.NOT_FOUND;
        }
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
