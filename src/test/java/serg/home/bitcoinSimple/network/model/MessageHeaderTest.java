package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;
import serg.home.bitcoinSimple.network.messages.Version;

import static org.junit.jupiter.api.Assertions.*;

class MessageHeaderTest extends BaseTest {

    private static String HEADER_HEX = "0b11090776657273696f6e0000000000660000005a717e08";
    private static MessageHeader HEADER = new MessageHeader(Network.TESTNET3, Version.NAME, 102, 0x5a717e08);

    @Test
    void read() {
        assertEquals(HEADER, MessageHeader.read(fromHex(HEADER_HEX)));
    }

    @Test
    void sameNetwork() {
        assertTrue(HEADER.sameNetwork(Network.TESTNET3));
        assertFalse(HEADER.sameNetwork(Network.MAIN));
    }

    @Test
    void write() {
        assertEquals(HEADER_HEX, writeHex(HEADER));
    }
}