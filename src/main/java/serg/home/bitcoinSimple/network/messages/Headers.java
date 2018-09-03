package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.blockchain.block.BlockHeader;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;
import serg.home.bitcoinSimple.network.model.VarInt;

import java.util.ArrayList;
import java.util.List;

public class Headers implements Payload {
    public static final String NAME = "headers";
    private List<BlockHeader> messageHeaders;

    public List<BlockHeader> blockHeaders() {
        return messageHeaders;
    }

    public Headers(List<BlockHeader> messageHeaders) {
        this.messageHeaders = messageHeaders;
    }

    public Headers(Bytes bytes) {
        decode(new ByteReader(bytes));
    }

    public boolean sizeLessThenMax() {
        return messageHeaders.size() < 2000;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void decode(ByteReader byteReader) {
        int count = byteReader.nextVarInt().toInt();
        messageHeaders = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            messageHeaders.add(new BlockHeader(byteReader));
        }
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary().add(new VarInt(messageHeaders.size()));
        messageHeaders.forEach(compoundBinary::add);
        return compoundBinary.encode();
    }
}
