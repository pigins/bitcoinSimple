package serg.home.bitcoinSimple.network.messages;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;
import serg.home.bitcoinSimple.protocol.BtcMessage;

import static org.junit.jupiter.api.Assertions.*;

class GetHeadersTest {
    String FIRST_GET_HEADERS = "7f1101000143497fd7f826957108f4a30fd9cec3aeba79972084e90ead01ea3309000000000000000000000000000000000000000000000000000000000000000000000000";

    @Test
    void decode() {
        GetHeaders getHeaders = new BtcMessage(new Bytes(FIRST_GET_HEADERS)).nextGetHeaders();
        assertEquals(new ProtocolVersion(70015), getHeaders.protocolVersion());
        assertEquals(1, getHeaders.hashes().size());
        assertEquals(
                new Bytes("43497fd7f826957108f4a30fd9cec3aeba79972084e90ead01ea330900000000"),
                getHeaders.hashes().get(0)
        );
        assertTrue(getHeaders.getAsManyAsPossible());
    }
}