package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;

import java.util.List;

public class GetHeaders extends GetId {
    public static final String NAME = "getheaders";

    public GetHeaders(Bytes bytes) {
        super(bytes);
    }

    public GetHeaders(ProtocolVersion protocolVersion, List<Bytes> hashes, boolean getAsManyHeadersAsPossible) {
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