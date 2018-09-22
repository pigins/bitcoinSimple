package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

class ServicesTest extends BaseTest {
    private static final String SERVICES_AS_HEX = "0d04000000000000";
    private static final Services SERVICES = new Services(Service.NODE_BLOOM, Service.NODE_NETWORK_LIMITED, Service.NODE_WITNESS, Service.NODE_NETWORK);

    @Test
    void read() {
        assertEquals(SERVICES, Services.read(fromHex(SERVICES_AS_HEX)));
    }

    @Test
    void asLong() {
        assertEquals(1+4+8+1024, SERVICES.asLong());
    }

    @Test
    void write() {
        assertEquals(SERVICES_AS_HEX, writeHex(SERVICES));
    }
}