package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.network.model.*;
import serg.home.bitcoinSimple.network.peer.connection.Peer;

import java.time.OffsetDateTime;

public class Version implements Payload {
    public static final String NAME = "version";

    public static Version read(ByteBuf byteBuf) {
        return new Version(
                ProtocolVersion.read(byteBuf),
                Services.read(byteBuf),
                Timestamp8.read(byteBuf),
                NetAddress.read(byteBuf),
                NetAddress.read(byteBuf),
                byteBuf.readLong(),
                VarString.read(byteBuf),
                byteBuf.readIntLE(),
                byteBuf.readByte() == 1
        );
    }

    private ProtocolVersion protocolVersion;
    private Services services;
    private Timestamp8 timestamp;
    private NetAddress toNetAddress;
    private NetAddress fromNetAddress;
    private long uNonce;
    private String userAgent;
    private int uStartHeight;
    private boolean relay;

    public Version(ProtocolVersion protocolVersion, Services services, Timestamp8 timestamp,
                   NetAddress toNetAddress, NetAddress fromNetAddress, long uNonce, String userAgent,
                   int uStartHeight, boolean relay) {
        this.protocolVersion = protocolVersion;
        this.services = services;
        this.timestamp = timestamp;
        this.toNetAddress = toNetAddress;
        this.fromNetAddress = fromNetAddress;
        this.uNonce = uNonce;
        this.userAgent = userAgent;
        this.uStartHeight = uStartHeight;
        this.relay = relay;
    }

    public ProtocolVersion protocolVersion() {
        return protocolVersion;
    }

    public TimestampWithAddress fromAddressWithTimestamp() {
        return new TimestampWithAddress(timestamp, fromNetAddress);
    }

    public Peer peer() {
        return new Peer(services, userAgent, uStartHeight, relay);
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        protocolVersion.write(byteBuf);
        services.write(byteBuf);
        timestamp.write(byteBuf);
        toNetAddress.write(byteBuf);
        fromNetAddress.write(byteBuf);
        byteBuf.writeLong(uNonce);
        new VarString(userAgent).write(byteBuf);
        byteBuf.writeIntLE(uStartHeight);
        byteBuf.writeBoolean(relay);
    }

    @Override
    public String toString() {
        return "Payload{" +
                "protocolVersion=" + protocolVersion +
                ", services=" + services +
                ", timestamp=" + timestamp +
                ", toNetAddress=" + toNetAddress +
                ", fromNetAddress=" + fromNetAddress +
                ", uNonce=" + uNonce +
                ", userAgent=" + userAgent +
                ", uStartHeight=" + uStartHeight +
                ", relay=" + relay +
                '}';
    }
}
