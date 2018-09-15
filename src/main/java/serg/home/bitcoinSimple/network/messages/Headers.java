package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.blockchain.block.BlockHeader;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;
import serg.home.bitcoinSimple.network.model.VarInt;

import java.util.ArrayList;
import java.util.List;

public class Headers implements Payload {
    public static final String NAME = "headers";
    public static Headers read(ByteBuf byteBuf) {
        int count = (int) VarInt.read(byteBuf);
        List<BlockHeader> messageHeaders = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            messageHeaders.add(BlockHeader.read(byteBuf));
        }
        return new Headers(messageHeaders);
    }
    private List<BlockHeader> messageHeaders;

    public List<BlockHeader> blockHeaders() {
        return messageHeaders;
    }

    public Headers(List<BlockHeader> messageHeaders) {
        this.messageHeaders = messageHeaders;
    }

    public boolean sizeLessThenMax() {
        return messageHeaders.size() < 2000;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        new VarInt(messageHeaders.size()).write(byteBuf);
        messageHeaders.forEach(messageHeader -> messageHeader.write(byteBuf));
    }
}
