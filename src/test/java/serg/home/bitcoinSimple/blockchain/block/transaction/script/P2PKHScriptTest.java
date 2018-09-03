package serg.home.bitcoinSimple.blockchain.block.transaction.script;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.Bytes;

import static org.junit.jupiter.api.Assertions.*;

class P2PKHScriptTest {

    @Test
    void encode() {
        Script script = new P2PKHScript(new Bytes("ab68025513c3dbd2f7b92a94e0581f5d50f654e7"));
        assertEquals("1976A914AB68025513C3DBD2F7B92A94E0581F5D50F654E788AC", script.encode().getHexString());
    }
}