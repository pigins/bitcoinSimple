package serg.home.bitcoinSimple.blockchain.block;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.Bytes;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyTest {

    @Test
    void target() {
        Difficulty difficulty = new Difficulty(new Bytes("1903a30c"));
        assertEquals("22829202948393929850749706076701368331072452018388575715328", difficulty.target().toString());
    }

    @Test
    void bDiff() {
        Difficulty difficulty = new Difficulty(new Bytes("1b0404cb"));
        assertEquals("16307.420938523983", difficulty.bDiff().toString());
    }

    @Test
    void pDiff() {
        Difficulty difficulty = new Difficulty(new Bytes("1b0404cb"));
        assertEquals("16307.669773817162", difficulty.pDiff().toString());
    }
}