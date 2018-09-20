package serg.home.bitcoinSimple.network.model.block.transaction.script;

import io.netty.buffer.ByteBuf;

// TODO remove inheritance
public class P2PKHScript extends Script {

    public P2PKHScript(ByteBuf publicKeyHash) {
        this.__(OP.DUP).__(OP.HASH160).__(publicKeyHash).__(OP.EQUALVERIFY).__(OP.CHECKSIG);
    }
}
