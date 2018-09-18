package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NetAddressTest extends BaseTest {

    @Test
    void decode() {
        String hexString = "010400000000000000000000000000000000FFFF0A000001208D";
        NetAddress netAddress = NetAddress.read(fromHex(hexString));
        assertEquals(hexString, writeHex(netAddress));
    }

    @Test
    void encode() {
        Services services = new Services(Service.NODE_NETWORK);
        NetAddress netAddress = new NetAddress(services, new IpAddress("10.0.0.1"), 8333);
        assertEquals("010000000000000000000000000000000000FFFF0A000001208D", writeHex(netAddress));
    }
}