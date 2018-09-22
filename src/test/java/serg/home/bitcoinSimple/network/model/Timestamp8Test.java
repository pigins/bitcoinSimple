package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class Timestamp8Test extends BaseTest {
    private static String HEX = "fd514b5b00000000";
    private static OffsetDateTime DATE = OffsetDateTime.of(2018, 7, 15, 13, 54, 5, 0, ZoneOffset.UTC);

    @Test
    void read() {
        assertEquals(DATE, Timestamp8.read(fromHex(HEX)).value);
    }

    @Test
    void write() {
        assertEquals(HEX, writeHex(new Timestamp8(DATE)));
    }
}