package serg.home.bitcoinSimple.network.model;

import java.util.Arrays;

public enum InvType {
    ERROR(0), MSG_TX(1), MSG_BLOCK(2), MSG_FILTERED_BLOCK(3), MSG_CMPCT_BLOCK(4);
    private final int typeValue;

    InvType(int typeValue) {
        this.typeValue = typeValue;
    }

    public static InvType fromInt(int value) {
        return Arrays.stream(InvType.values())
                .filter(type -> type.typeValue == value)
                .findAny()
                .get();
    }

    public int typeValue() {
        return typeValue;
    }
}
