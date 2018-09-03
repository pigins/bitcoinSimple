package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.protocol.BtcMessage;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    @Test
    void test() {
        assertEquals(1024, Service.NODE_NETWORK_LIMITED.getValue());
    }

    @Test
    void servtest() {
        System.out.println(new BtcMessage(new Bytes("0d04000000000000")).nextServices());
        System.out.println(new BtcMessage(new Bytes("0900000000000000")).nextServices());
    }
}