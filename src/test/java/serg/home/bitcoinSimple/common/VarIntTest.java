package serg.home.bitcoinSimple.common;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.network.model.VarInt;

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
        ByteReader byteReader = new ByteReader(new Bytes("6A"));
        VarInt varInt = new VarInt(byteReader);
        assertEquals("6A", varInt.encode().getHexString());

        byteReader = new ByteReader(new Bytes("FD2602"));
        varInt = new VarInt(byteReader);
        assertEquals("FD2602", varInt.encode().getHexString());

        byteReader = new ByteReader(new Bytes("FE703A0F00"));
        varInt = new VarInt(byteReader);
        assertEquals("FE703A0F00", varInt.encode().getHexString());
    }
}