package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;
import serg.home.bitcoinSimple.network.peer.Net;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTest extends BaseTest {
    private static final String RAW_REGTEST = "fabfb5da";

    @Test
    void read() {
        assertEquals(Network.REGTEST, Network.read(fromHex(RAW_REGTEST)));
    }

    @Test
    void write() {
        assertEquals(RAW_REGTEST, writeHex(Network.REGTEST));
    }
}