package serg.home.bitcoinSimple.blockchain.block;

import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;
import serg.home.bitcoinSimple.network.messages.Block;
import serg.home.bitcoinSimple.protocol.BtcMessage;

import java.io.IOException;

class BlockTest extends BaseTest {

    @Test
    void decode() throws IOException {
        // https://www.blocktrail.com/tBTC/block/00000000763b2e54bff6b2e4a80e5aa50bcd432df2d619c0fb1407c78bcf6037
        // block hash = 00000000763b2e54bff6b2e4a80e5aa50bcd432df2d619c0fb1407c78bcf6037
        byte[] block_version3s = getClass().getResource("block_version3.bin").openStream().readAllBytes();
        Block block = new BtcMessage(Unpooled.wrappedBuffer(block_version3s)).block();
        System.out.println(block);
    }
}