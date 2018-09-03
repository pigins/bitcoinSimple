package serg.home.bitcoinSimple.common;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.protocol.BtcMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VarIntTest {

    @Test
    void encode() {
        VarInt varInt = new VarInt(0x6AL);
        assertEquals("6A", varInt.encode().getHexString());
        varInt = new VarInt(550L);
        assertEquals("FD2602", varInt.encode().getHexString());
        varInt = new VarInt(998000L);
        assertEquals("FE703A0F00", varInt.encode().getHexString());
    }

    @Test
    void decode() {
        VarInt varInt = new BtcMessage(new Bytes("6A")).nextVarInt();
        assertEquals("6A", varInt.encode().getHexString());

        varInt = new BtcMessage(new Bytes("FD2602")).nextVarInt();
        assertEquals("FD2602", varInt.encode().getHexString());

        varInt = new BtcMessage(new Bytes("FE703A0F00")).nextVarInt();
        assertEquals("FE703A0F00", varInt.encode().getHexString());
    }
}