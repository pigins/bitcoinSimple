package serg.home.bitcoinSimple.blockchain.block;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;
import serg.home.bitcoinSimple.network.model.Timestamp4;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockHeaderTest extends BaseTest {

    @Test
    void encode() {
        BlockHeader blockHeader = new BlockHeader(
                BlockVersion.V1,
                fromHex("0000000000000000000000000000000000000000000000000000000000000000"),
                fromHex("4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b"),
                new Timestamp4(1231006505L),
                new Difficulty(fromHex("1d00ffff")),
                2083236893
        );
        assertEquals(
                "0100000000000000000000000000000000000000000000000000000000000000000000003BA3EDFD7A7B12B27AC72C3E67768F617FC81BC3888A51323A9FB8AA4B1E5E4A29AB5F49FFFF001D1DAC2B7C",
                writeHex(blockHeader)
        );
    }

    @Test
    void decode() {
        BlockHeader blockHeader = BlockHeader.read(fromHex("0100000000000000000000000000000000000000000000000000000000000000000000003BA3EDFD7A7B12B27AC72C3E67768F617FC81BC3888A51323A9FB8AA4B1E5E4A29AB5F49FFFF001D1DAC2B7C"));
        assertEquals(
                "0100000000000000000000000000000000000000000000000000000000000000000000003BA3EDFD7A7B12B27AC72C3E67768F617FC81BC3888A51323A9FB8AA4B1E5E4A29AB5F49FFFF001D1DAC2B7C",
                writeHex(blockHeader)
        );
    }

}