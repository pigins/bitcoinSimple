package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class Timestamp4Test extends BaseTest {
    private static String HEX = "9638845b";
    private static OffsetDateTime DATE = OffsetDateTime.of(2018, 8, 27, 17, 44, 54, 0, ZoneOffset.UTC);

    @Test
    void read() {
        assertEquals(DATE, Timestamp4.read(fromHex(HEX)).value);
    }

    @Test
    void write() {
        assertEquals(HEX, writeHex(new Timestamp4(DATE)));
    }
}