package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

class VarStringTest extends BaseTest {

    @Test
    void read() {
        String read = VarString.read(fromHex("102F5361746F7368693A302E31362E312F"));
        assertEquals("/Satoshi:0.16.1/", read);
    }
}