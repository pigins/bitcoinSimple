package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import static org.junit.jupiter.api.Assertions.*;

class NetAddressTest {

    @Test
    void decode() {
        String hexString = "010400000000000000000000000000000000FFFF0A000001208D";
        NetAddress netAddress = new NetAddress(new ByteReader(new Bytes(hexString)));
        assertEquals(hexString, netAddress.encode().getHexString());
    }

    @Test
    void encode() {
        Services services = new Services(Service.NODE_NETWORK);
        NetAddress netAddress = new NetAddress(services, new IpAddress("10.0.0.1"), 8333);
        assertEquals("010000000000000000000000000000000000FFFF0A000001208D", netAddress.encode().getHexString());
    }
}