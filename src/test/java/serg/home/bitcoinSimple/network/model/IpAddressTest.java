package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

class IpAddressTest extends BaseTest {
    private static String IP_AS_HEX = "00000000000000000000ffff2ee5a591";
    private static String IP_HUMAN_STRING = "46.229.165.145";
    private static int IP_AS_INT = 786802065;

    @Test
    void read() {
        IpAddress ipAddress = IpAddress.read(fromHex(IP_AS_HEX));
        assertEquals(IP_HUMAN_STRING, ipAddress.getIpString());
    }

    @Test
    void asInt() {
        IpAddress ipAddress = IpAddress.read(fromHex(IP_AS_HEX));
        assertEquals(IP_AS_INT, ipAddress.asInt());
    }

    @Test
    void write() {
        assertEquals(IP_AS_HEX, writeHex(new IpAddress(IP_AS_INT)));
    }

    @Test
    void getIpString() {
        assertEquals(IP_HUMAN_STRING, new IpAddress(IP_AS_INT).getIpString());
    }

    @Test
    void isSiteLocalAddress() {
        assertTrue(new IpAddress("192.168.1.1").isSiteLocalAddress());
        assertTrue(new IpAddress("10.0.0.1").isSiteLocalAddress());
        assertFalse(new IpAddress(IP_HUMAN_STRING).isSiteLocalAddress());
    }
}