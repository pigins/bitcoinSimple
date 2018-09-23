package serg.home.bitcoinSimple.network.model;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

class InvVectorTest extends BaseTest {
    private static String TYPE_HEX = "01000000";
    private static final String HASH_HEX = "683c6a6b51833814b3a06e172560fc57abbe86c8513a9730b2e51d5a8efabb3d";
    private static String VECTOR_AS_HEX = TYPE_HEX + HASH_HEX;

    @Test
    void read() {
        InvVector invVector = InvVector.read(fromHex(VECTOR_AS_HEX));
        assertEquals(InvType.MSG_TX, invVector.type());
        assertEquals(HASH_HEX, invVector.hash().hexDump());
    }

    @Test
    void write() {
        writeHex(new InvVector(InvType.MSG_TX, new Hash32(fromHex(HASH_HEX))));
    }
}