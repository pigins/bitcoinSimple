package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;

import java.util.List;

public class GetBlocks extends GetId {
    public static final String NAME = "getblocks";

    public GetBlocks(ProtocolVersion protocolVersion, List<Bytes> hashes, boolean getAsManyBlocksAsPossible) {
        super(protocolVersion, hashes, getAsManyBlocksAsPossible);
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String toString() {
        return "GetBlocks{" +
                "protocolVersion=" + protocolVersion +
                ", hashes=" + hashes +
                ", getAsManyAsPossible=" + getAsManyAsPossible +
                '}';
    }
}
