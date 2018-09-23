package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

class Hash32Test extends BaseTest {
    private static String FIRST_BYTES = "4444444444";
    private static String BYTES_TO_HASH = "00010966776006953d5567439e5e39f86a0D273bee";
    private static String SINGLE_HASH = "445c7a8007a93d8733188288bb320a8fe2debd2ae1b47f0f50bc10bae845c094";
    private static String DOUBLE_HASH = "d61967f63c7dd183914a4ae452c9f6ad5d462ce3d277798075b107615c1a8a30";

    @Test
    void read() {
        ByteBuf byteBuf = fromHex(FIRST_BYTES + SINGLE_HASH);
        byteBuf.readBytes(5);
        assertEquals(SINGLE_HASH, Hash32.read(byteBuf).hexDump());
    }

    @Test
    void calcSha256() {
        Hash32 hash32 = Hash32.calcSha256(fromHex(BYTES_TO_HASH));
        assertEquals(SINGLE_HASH, hash32.hexDump());
    }

    @Test
    void calcDoubleSha256() {
        Hash32 hash32 = Hash32.calcDoubleSha256(fromHex(BYTES_TO_HASH));
        assertEquals(DOUBLE_HASH, hash32.hexDump());
    }

    @Test
    void write() {
        assertEquals(SINGLE_HASH, writeHex(new Hash32(fromHex(SINGLE_HASH))));
    }
}