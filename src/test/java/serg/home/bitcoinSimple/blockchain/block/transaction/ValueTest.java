package serg.home.bitcoinSimple.blockchain.block.transaction;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.blockchain.block.transaction.output.Value;

import static org.junit.jupiter.api.Assertions.*;

class ValueTest {

    @Test
    void encode() {
        Value value = new Value(1_500_000L);
        assertEquals("60E3160000000000", value.encode().getHexString());
    }
}