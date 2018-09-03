package serg.home.bitcoinSimple.network.peer.connection;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;

import java.nio.channels.SocketChannel;

public class BtcNioChannel extends NioSocketChannel implements Channel {
    private volatile boolean handshakeComplete;
    private volatile ProtocolVersion protocolVersion;

    public Peer getRemotePeer() {
        return remotePeer;
    }

    private volatile Peer remotePeer;

    public BtcNioChannel() {
    }

    public BtcNioChannel(NioServerSocketChannel btcNioServerChannel, SocketChannel ch) {
        super(btcNioServerChannel, ch);
    }

    public void remotePeer(Peer remotePeer) {
        this.remotePeer = remotePeer;
    }

    public void completeHandshake() {
        handshakeComplete = true;
    }

    public void connectionVersion(ProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public boolean active() {
        return this.isActive() && handshakeComplete;
    }
}
