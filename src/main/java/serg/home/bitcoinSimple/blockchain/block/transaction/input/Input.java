package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import serg.home.bitcoinSimple.common.ByteBufWritable;

public abstract class Input implements ByteBufWritable {
    OutputLink outputLink;
    int uSequence;
}
