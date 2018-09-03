package serg.home.bitcoinSimple.protocol;

import serg.home.bitcoinSimple.blockchain.block.Block;
import serg.home.bitcoinSimple.network.messages.*;

public interface MessageReader {
    Addr nextAddr();

    Block nextBlock();

    GetAddr nextGetAddr();

    GetBlocks nextGetBlocks();

    GetHeaders nextGetHeaders();

    Headers nextHeaders();

    Inv nextInv();

    Ping nextPing();

    Pong nextPong();

    Reject nextReject();

    Verack nextVerack();

    Version nextVersion();
}
