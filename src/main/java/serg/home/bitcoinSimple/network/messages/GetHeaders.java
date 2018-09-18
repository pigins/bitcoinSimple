package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;
import serg.home.bitcoinSimple.network.model.VarInt;

import java.util.ArrayList;
import java.util.List;

public class GetHeaders extends GetId {
    public static final String NAME = "getheaders";
    public static GetHeaders read(ByteBuf byteBuf) {
        ProtocolVersion protocolVersion = ProtocolVersion.read(byteBuf);
        int hashCount = (int) VarInt.read(byteBuf);
        List<ByteBuf> hashes = new ArrayList<>(hashCount);
        for (int i = 0; i < hashCount; i++) {
            hashes.add(byteBuf.readBytes(32));
        }
        boolean getAsManyAsPossible = false;
        if (byteBuf.isReadable() && byteBuf.readBytes(32).equals(GetId.STOP_HASH)) {
            getAsManyAsPossible = true;
        }
        return new GetHeaders(protocolVersion, hashes, getAsManyAsPossible);
    }

    public GetHeaders(ProtocolVersion protocolVersion, List<ByteBuf> hashes, boolean getAsManyHeadersAsPossible) {
        super(protocolVersion, hashes, getAsManyHeadersAsPossible);
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String toString() {
        return "GetHeaders{" +
                "protocolVersion=" + protocolVersion +
                ", hashes=" + hashes +
                ", getAsManyAsPossible=" + getAsManyAsPossible +
                '}';
    }
}