package serg.home.bitcoinSimple.protocol;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.blockchain.block.Block;
import serg.home.bitcoinSimple.network.messages.*;

public class BtcMessage {
    private final ByteBuf payload;
    private final String command;

    public BtcMessage(String command, ByteBuf payload) {
        this.command = command;
        this.payload = payload;
    }

    public Addr addr() {
        return Addr.read(payload);
    }

    public boolean isAddr() {
        return command.equals(Addr.NAME);
    }

    public Block block() {
        return Block.read(payload);
    }

    public boolean isBlock() {
        return command.equals(Block.NAME);
    }

    public GetAddr getAddr() {
        return new GetAddr();
    }

    public boolean isGetAddr() {
        return command.equals(GetAddr.NAME);
    }

    public GetBlocks getBlocks() {
        return GetBlocks.read(payload);
    }

    public boolean isGetBlocks() {
        return command.equals(GetBlocks.NAME);
    }

    public GetHeaders getHeaders() {
        return GetHeaders.read(payload);
    }

    public boolean isGetHeaders() {
        return command.equals(GetHeaders.NAME);
    }

    public Headers headers() {
        return Headers.read(payload);
    }

    public boolean isHeaders() {
        return command.equals(Headers.NAME);
    }

    public Inv inv() {
        return Inv.read(payload);
    }

    public boolean isInv() {
        return command.equals(Inv.NAME);
    }

    public Ping ping() {
        return Ping.read(payload);
    }

    public boolean isPing() {
        return command.equals(Ping.NAME);
    }

    public Pong pong() {
        return Pong.read(payload);
    }

    public boolean isPong() {
        return command.equals(Pong.NAME);
    }

    public Reject reject() {
        return Reject.read(payload);
    }

    public boolean isReject() {
        return command.equals(Reject.NAME);
    }

    public Verack verack() {
        return new Verack();
    }

    public boolean isVerack() {
        return command.equals(Verack.NAME);
    }

    public Version version() {
        return Version.read(payload);
    }

    public boolean isVersion() {
        return command.equals(Version.NAME);
    }
}
