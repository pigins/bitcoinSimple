package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VarIntTest extends BaseTest {

    @Test
    void encode() {
        VarInt varInt = new VarInt(0x6AL);
        assertEquals("6A", writeHex(varInt));
        varInt = new VarInt(550L);
        assertEquals("FD2602", writeHex(varInt));
        varInt = new VarInt(998000L);
        assertEquals("FE703A0F00", writeHex(varInt));
    }

    @Test
    void readSmall() {
        long value = VarInt.read(fromHex("6A"));
        assertEquals(0x6A, value);
    }

    @Test
    void readMedium() {
        long value = VarInt.read(fromHex("FD2602"));
        assertEquals(550L, value);

    }
    @Test
    void readLong() {
        long value = VarInt.read(fromHex("FE703A0F00"));
        assertEquals(998000L, value);
    }
}