package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VarIntTest extends BaseTest {

    @Test
    void writeByte() {
        VarInt varInt = new VarInt(0x6AL);
        assertEquals("6a", writeHex(varInt));
    }

    @Test
    void writeShort() {
        VarInt varInt = new VarInt(550L);
        assertEquals("fd2602", writeHex(varInt));
    }

    @Test
    void writeInt() {
        VarInt varInt = new VarInt(998000L);
        assertEquals("fe703a0f00", writeHex(varInt));
    }

    @Test
    void readByte() {
        long value = VarInt.read(fromHex("6a"));
        assertEquals(0x6A, value);
    }

    @Test
    void readShort() {
        long value = VarInt.read(fromHex("fd2602"));
        assertEquals(550L, value);

    }

    @Test
    void readInt() {
        long value = VarInt.read(fromHex("fe703a0f00"));
        assertEquals(998000L, value);
    }
}