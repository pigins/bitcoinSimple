package serg.home.bitcoinSimple.blockchain.block.transaction.script;

import serg.home.bitcoinSimple.common.Bytes;

public class P2PKHScript extends Script {

    public P2PKHScript(Bytes publicKeyHash) {
        this.__(OP.DUP).__(OP.HASH160).__(publicKeyHash).__(OP.EQUALVERIFY).__(OP.CHECKSIG);
    }
}
