package serg.home.bitcoinSimple.network.messages;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;
import serg.home.bitcoinSimple.network.model.Hash32;
import serg.home.bitcoinSimple.network.model.InvType;
import serg.home.bitcoinSimple.network.model.InvVector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvTest extends BaseTest {
    private static String GETBLOCKS_PAYLOAD =
                      "02" // Count: 2
                    + "01000000" // Type: MSG_TX
                    + "DE55FFD709AC1F5DC509A0925D0B1FC442CA034F224732E429081DA1B621F55A" // Hash
                    + "01000000" // Type: MSG_TX
                    + "91D36D997037E08018262978766F24B8A055AAF1D872E94AE85E9817B2C68DC7"; // Hash

    @Test
    void decode() {
        Inv inv = Inv.read(fromHex(GETBLOCKS_PAYLOAD));
        assertEquals(2, inv.invVectors().size());
        assertEquals(inv.invVectors().get(0).type(), InvType.MSG_TX);
        assertEquals(
                inv.invVectors().get(0).hash(),
                fromHex("DE55FFD709AC1F5DC509A0925D0B1FC442CA034F224732E429081DA1B621F55A")
        );
        assertEquals(inv.invVectors().get(1).type(), InvType.MSG_TX);
        assertEquals(
                inv.invVectors().get(1).hash(),
                fromHex("91D36D997037E08018262978766F24B8A055AAF1D872E94AE85E9817B2C68DC7")
        );
    }

    @Test
    void encode() {
        List<InvVector> invVectors = new ArrayList<>();
        invVectors.add(new InvVector(InvType.MSG_TX, new Hash32(fromHex("DE55FFD709AC1F5DC509A0925D0B1FC442CA034F224732E429081DA1B621F55A"))));
        invVectors.add(new InvVector(InvType.MSG_TX, new Hash32(fromHex("91D36D997037E08018262978766F24B8A055AAF1D872E94AE85E9817B2C68DC7"))));
        assertEquals(GETBLOCKS_PAYLOAD, writeHex(new Inv(invVectors)));
    }
}