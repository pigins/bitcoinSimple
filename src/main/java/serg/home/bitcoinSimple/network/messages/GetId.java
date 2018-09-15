package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;

import java.util.List;

public abstract class GetId implements Payload {
    public static final ByteBuf STOP_HASH = Unpooled.copiedBuffer(ByteBufUtil.decodeHexDump("0000000000000000000000000000000000000000000000000000000000000000"));

    protected ProtocolVersion protocolVersion;
    protected List<ByteBuf> hashes;
    protected boolean getAsManyAsPossible;

    public GetId(ProtocolVersion protocolVersion, List<ByteBuf> hashes, boolean getAsManyAsPossible) {
        this.protocolVersion = protocolVersion;
        this.hashes = hashes;
        this.getAsManyAsPossible = getAsManyAsPossible;
    }

    public ProtocolVersion protocolVersion() {
        return protocolVersion;
    }

    public List<ByteBuf> hashes() {
        return hashes;
    }

    public boolean getAsManyAsPossible() {
        return getAsManyAsPossible;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        protocolVersion.write(byteBuf);
        new VarInt(hashes.size()).write(byteBuf);
        hashes.forEach(byteBuf::writeBytes);
        if (getAsManyAsPossible) {
            byteBuf.writeBytes(STOP_HASH);
        }
    }
}
