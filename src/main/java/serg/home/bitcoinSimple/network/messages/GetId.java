package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;

import java.util.ArrayList;
import java.util.List;

public abstract class GetId implements Payload {
    protected static final Bytes STOP_HASH = new Bytes("0000000000000000000000000000000000000000000000000000000000000000");

    protected ProtocolVersion protocolVersion;
    protected List<Bytes> hashes;
    protected boolean getAsManyAsPossible;

    public GetId(Bytes bytes) {
        decode(new ByteReader(bytes));
    }

    public GetId(ProtocolVersion protocolVersion, List<Bytes> hashes, boolean getAsManyAsPossible) {
        this.protocolVersion = protocolVersion;
        this.hashes = hashes;
        this.getAsManyAsPossible = getAsManyAsPossible;
    }

    public ProtocolVersion protocolVersion() {
        return protocolVersion;
    }

    public List<Bytes> hashes() {
        return hashes;
    }

    public boolean getAsManyAsPossible() {
        return getAsManyAsPossible;
    }

    @Override
    public void decode(ByteReader byteReader) {
        protocolVersion = new ProtocolVersion(byteReader);
        int hashCount = byteReader.nextVarInt().toInt();
        List<Bytes> hashes = new ArrayList<>(hashCount);
        for (int i = 0; i < hashCount; i++) {
            hashes.add(byteReader.next(32));
        }
        this.hashes = hashes;
        if (byteReader.hasNext() && byteReader.next(32).equals(STOP_HASH)) {
            getAsManyAsPossible = true;
        }
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary()
                .add(protocolVersion)
                .add(new VarInt(hashes.size()));
        hashes.forEach(compoundBinary::add);
        if (getAsManyAsPossible) {
            compoundBinary.add(STOP_HASH);
        }
        return compoundBinary.encode();
    }
}
