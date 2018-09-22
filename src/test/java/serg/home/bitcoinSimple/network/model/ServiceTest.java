package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest extends BaseTest {

    @Test
    void value() {
        assertEquals(1, Service.NODE_NETWORK.value());
        assertEquals(2, Service.NODE_GETUTXO.value());
        assertEquals(4, Service.NODE_BLOOM.value());
        assertEquals(8, Service.NODE_WITNESS.value());
        assertEquals(1024, Service.NODE_NETWORK_LIMITED.value());
    }
}