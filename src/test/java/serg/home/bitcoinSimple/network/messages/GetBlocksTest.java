package serg.home.bitcoinSimple.network.messages;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetBlocksTest {
    private static String GETBLOCKS_PAYLOAD =
                      "71110100" // Protocol version: 70001
                    + "02" // Hash count: 2
                    + "D39F608A7775B537729884D4E6633BB2105E55A16A14D31B0000000000000000" // Hash #1
                    + "5C3E6403D40837110A2E8AFB602B1C01714BDA7CE23BEA0A0000000000000000" // Hash #2
                    + "0000000000000000000000000000000000000000000000000000000000000000"; // Stop hash

    String mineGetHeaders =   "0b110907676574686561646572730000450000009a5b6a207211010001478817810c6ada2c20faba48407a3e20c320e98adcb4e1529ddc96f05733c2850000000000000000000000000000000000000000000000000000000000000000";
    String theirsGetHeaders = "0b1109076765746865616465727300004500000077400fb87f1101000143497fd7f826957108f4a30fd9cec3aeba79972084e90ead01ea3309000000000000000000000000000000000000000000000000000000000000000000000000";

    @Test
    void decode() {
        GetBlocks getBlocks = new GetBlocks(new Bytes(GETBLOCKS_PAYLOAD));
        assertEquals(new ProtocolVersion(70001), getBlocks.protocolVersion());
        assertEquals(2, getBlocks.hashes().size());
        assertEquals(
                new Bytes("D39F608A7775B537729884D4E6633BB2105E55A16A14D31B0000000000000000"),
                getBlocks.hashes().get(0)
        );
        assertEquals(
                new Bytes("5C3E6403D40837110A2E8AFB602B1C01714BDA7CE23BEA0A0000000000000000"),
                getBlocks.hashes().get(1)
        );
        assertTrue(getBlocks.getAsManyAsPossible());
    }

    @Test
    void encode() {
        GetBlocks getBlocks = new GetBlocks(
                new ProtocolVersion(70001),
                List.of(new Bytes("D39F608A7775B537729884D4E6633BB2105E55A16A14D31B0000000000000000"),
                        new Bytes("5C3E6403D40837110A2E8AFB602B1C01714BDA7CE23BEA0A0000000000000000")),
                true);
        assertEquals(GETBLOCKS_PAYLOAD, getBlocks.encode().getHexString());
    }
}