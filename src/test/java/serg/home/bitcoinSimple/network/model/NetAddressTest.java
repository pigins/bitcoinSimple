package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NetAddressTest extends BaseTest {
    private String addressHex = "000000000000000000000000000000000000ffff510554a4cbd5";
    private NetAddress address = new NetAddress(new Services(), new IpAddress("81.5.84.164"), 52181);

    private String localAddressHex = "0d00000000000000000000000000000000000000000000000000";
    private NetAddress localAddress = new NetAddress(new Services(Service.NODE_NETWORK, Service.NODE_WITNESS, Service.NODE_BLOOM), new IpAddress("192.168.0.1"), 1234);

    @Test
    void read() {
        assertEquals(address, NetAddress.read(fromHex(addressHex)));
    }

    @Test
    void readLocal() {
        assertTrue(NetAddress.read(fromHex(localAddressHex)).unroutable());
    }

    @Test
    void write() {
        assertEquals(addressHex, writeHex(address));
    }

    @Test
    void writeLocal() {
        assertEquals(localAddressHex, writeHex(localAddress));
    }
}