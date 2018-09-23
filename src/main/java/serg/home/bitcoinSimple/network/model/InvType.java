package serg.home.bitcoinSimple.network.model;

import java.util.Arrays;

/**
 * 0	ERROR	            Any data of with this number may be ignored
 * 1	MSG_TX	            Hash is related to a transaction
 * 2	MSG_BLOCK	        Hash is related to a data block
 * 3	MSG_FILTERED_BLOCK	Hash of a block header; identical to MSG_BLOCK. Only to be used in getdata message.
 *                          Indicates the reply should be a merkleblock message rather than a block message;
 *                          this only works if a bloom filter has been set.
 * 4	MSG_CMPCT_BLOCK	    Hash of a block header; identical to MSG_BLOCK. Only to be used in getdata message.
 *                          Indicates the reply should be a cmpctblock message. See BIP 152 for more info.
 */
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
                .orElseThrow();
    }

    public int asInt() {
        return typeValue;
    }
}
