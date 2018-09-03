package serg.home.bitcoinSimple.common;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class BytesTest {

    @Test
    void nullPadded() {
        Bytes bytes = new Bytes("verack".getBytes(StandardCharsets.US_ASCII)).nullPadded(12);
        assertEquals("76657261636B000000000000", bytes.getHexString());
    }
}