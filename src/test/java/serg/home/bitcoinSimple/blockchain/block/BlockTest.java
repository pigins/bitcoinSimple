package serg.home.bitcoinSimple.blockchain.block;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.Bytes;

class BlockTest {

    @Test
    void decode() {
        Block block = new Block(new Bytes(MainnetGenesisBlockTest.genesisBinary));
    }
}