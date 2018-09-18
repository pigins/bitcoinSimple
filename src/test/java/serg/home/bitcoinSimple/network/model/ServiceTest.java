package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest extends BaseTest {

    @Test
    void test() {
        assertEquals(1024, Service.NODE_NETWORK_LIMITED.getValue());
    }

    @Test
    void servtest() {
        System.out.println(Services.read(fromHex("0d04000000000000")));
        System.out.println(Services.read(fromHex("0900000000000000")));
    }
}