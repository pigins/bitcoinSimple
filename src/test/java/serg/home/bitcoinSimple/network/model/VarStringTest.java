package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

class VarStringTest extends BaseTest {
    private static String STRING_HEX = "102f5361746f7368693a302e31362e312f";
    private static String STRING = "/Satoshi:0.16.1/";

    @Test
    void read() {
        String read = VarString.read(fromHex(STRING_HEX));
        assertEquals(STRING, read);
    }

    @Test
    void write() {
        assertEquals(STRING_HEX, writeHex(new VarString(STRING)));
    }
}