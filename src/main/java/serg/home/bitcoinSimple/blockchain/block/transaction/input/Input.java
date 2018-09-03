package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

public abstract class Input implements BinaryEncoded, BinaryDecoded {
    OutputLink outputLink;
    int uSequence;
}
