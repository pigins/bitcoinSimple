package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

class ProtocolVersionTest extends BaseTest {
    private static String VERSION_HEX = "7b110100";
    private static ProtocolVersion VERSION = new ProtocolVersion(70011);

    @Test
    void min() {
        ProtocolVersion version1 = new ProtocolVersion(70013);
        ProtocolVersion version2 = new ProtocolVersion(70011);
        assertSame(version2, ProtocolVersion.min(version1, version2));
    }

    @Test
    void read() {
        assertEquals(VERSION, ProtocolVersion.read(fromHex(VERSION_HEX)));
    }

    @Test
    void asInt() {
        assertEquals(70011, VERSION.asInt());
    }

    @Test
    void write() {
        assertEquals(VERSION_HEX, writeHex(VERSION));
    }
}