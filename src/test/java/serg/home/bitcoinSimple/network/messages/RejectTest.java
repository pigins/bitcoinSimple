package serg.home.bitcoinSimple.network.messages;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

class RejectTest extends BaseTest {
    public static String rejectPayload =
                      "02" // Number of bytes in message: 2
                    + "7478" // Type of message rejected: tx
                    + "12"   // Reject code: 0x12 (duplicate)
                    + "15"   // Number of bytes in reason: 21
                    + "6261642D74786E732D696E707574732D7370656E74" // Reason: bad-txns-inputs-spent
                    + "394715FCAB51093BE7BFCA5A31005972947BAF86A31017939575FB2354222821"; // TXID


    @Test
    void decode() {
        Reject reject = Reject.read(fromHex(rejectPayload));
        assertEquals("tx", reject.getMessage());
        assertEquals(Reject.CCODES.REJECT_DUPLICATE, reject.getCcode());
        assertEquals("bad-txns-inputs-spent", reject.getReason());
//        assertEquals(new Bytes("394715FCAB51093BE7BFCA5A31005972947BAF86A31017939575FB2354222821"), reject.getData());
    }

    @Test
    void encode() {
        Reject reject = new Reject("tx", Reject.CCODES.REJECT_DUPLICATE, "bad-txns-inputs-spent",
                fromHex("394715FCAB51093BE7BFCA5A31005972947BAF86A31017939575FB2354222821")
        );
        assertEquals(rejectPayload, writeHex(reject));
    }
}