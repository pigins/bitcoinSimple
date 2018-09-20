package serg.home.bitcoinSimple.network.model.block;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.BaseTest;
import serg.home.bitcoinSimple.network.messages.Block;
import serg.home.bitcoinSimple.config.MainnetConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainnetGenesisBlockTest extends BaseTest {
    static final String headerBinary = "01000000" + // version
            "0000000000000000000000000000000000000000000000000000000000000000" + //prev block
            "3BA3EDFD7A7B12B27AC72C3E67768F617FC81BC3888A51323A9FB8AA4B1E5E4A" + //merkle root
            "29AB5F49" + // timestamp
            "FFFF001D" + // bits
            "1DAC2B7C";  // nonce

    public static final String genesisBinary = headerBinary +
            "01" + // number of transactions
            "01000000" + // version
            "01" + // input
            "0000000000000000000000000000000000000000000000000000000000000000FFFFFFFF" + // prev output
            "4D" + // script length
            "04FFFF001D0104455468652054696D65732030332F4A616E2F32303039204368616E63656C6C6F72206F6E206272696E6B206F66207365636F6E64206261696C6F757420666F722062616E6B73" + // scriptsig
            "FFFFFFFF" + //sequence
            "01" + //outputs
            "00F2052A01000000" + //50 BTC
            "43" + //pk_script length
            "4104678AFDB0FE5548271967F1A67130B7105CD6A828E03909A67962E0EA1F61DEB649F6BC3F4CEF38C4F35504E51EC112DE5C384DF7BA0B8D578A4C702B6BF11D5FAC" + //pk_script
            "00000000";
    @Test
    void encode() {
        String genesisHash = "000000000019D6689C085AE165831E934FF763AE46A2A6C172B3F1B60A8CE26F";
        Block genesisBlock = new MainnetConfig().genesis();
        assertEquals(genesisBinary, writeHex(genesisBlock));
//        assertEquals(genesisHash, new Bytes(headerBinary).doubleSha256().flip().getHexString());
    }
}