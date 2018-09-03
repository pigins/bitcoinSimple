package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.*;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;
import serg.home.bitcoinSimple.network.model.*;
import serg.home.bitcoinSimple.network.peer.connection.Peer;

public class Version implements Payload {
    public static final String NAME = "version";

    private ProtocolVersion protocolVersion;
    private Services services;
    private Timestamp8 timestamp;
    private NetAddress toNetAddress;
    private NetAddress fromNetAddress;
    private long uNonce;
    private VarString userAgent;
    private int uStartHeight;
    private boolean relay;

    public Version(ProtocolVersion protocolVersion, Services services, Timestamp8 timestamp,
                   NetAddress toNetAddress, NetAddress fromNetAddress, long uNonce, VarString userAgent,
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

    public Version(Bytes bytes) {
        decode(new ByteReader(bytes));
    }

    public Version(ByteReader byteReader) {
        decode(byteReader);
    }

    public TimestampWithAddress fromAddressWithTimestamp() {
        return new TimestampWithAddress(timestamp.getValue(), fromNetAddress);
    }

    public Peer peer() {
        return new Peer(services, userAgent.getValue(), uStartHeight, relay);
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void decode(ByteReader byteReader) {
        protocolVersion = new ProtocolVersion(byteReader);
        services = new Services(byteReader);
        timestamp = new Timestamp8(byteReader);
        toNetAddress = new NetAddress(byteReader);
        fromNetAddress = new NetAddress(byteReader);
        uNonce = byteReader.nextLong();
        userAgent = byteReader.nextVarString();
        uStartHeight = byteReader.nextIntLE();
        relay = (byteReader.nextByte() == 1);
    }

    @Override
    public Bytes encode() {
        return new CompoundBinary()
                .add(protocolVersion)
                .add(services)
                .add(timestamp)
                .add(toNetAddress)
                .add(fromNetAddress)
                .add(Bytes.fromLong(uNonce))
                .add(userAgent)
                .add(Bytes.fromIntLE(uStartHeight))
                .add(Bytes.fromBoolean(relay))
                .encode();
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
