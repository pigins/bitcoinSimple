package serg.home.bitcoinSimple.network.handlers;

import io.netty.channel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.blockchain.LocalBlockchain;
import serg.home.bitcoinSimple.protocol.BtcMessage;
import serg.home.bitcoinSimple.network.model.NetAddress;
import serg.home.bitcoinSimple.network.model.Timestamp8;
import serg.home.bitcoinSimple.network.model.VarString;
import serg.home.bitcoinSimple.network.peer.connection.BtcNioChannel;
import serg.home.bitcoinSimple.network.peer.connection.Peer;
import serg.home.bitcoinSimple.network.exceptions.CommandBeforeHandshake;
import serg.home.bitcoinSimple.network.exceptions.HandshakeAlreadyDone;
import serg.home.bitcoinSimple.network.exceptions.Obsolete;
import serg.home.bitcoinSimple.network.messages.Verack;
import serg.home.bitcoinSimple.network.messages.Version;
import serg.home.bitcoinSimple.network.model.*;

import java.net.InetSocketAddress;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

public class HandshakeHandler extends SimpleChannelInboundHandler<BtcMessage> {
    private static Logger logger = LogManager.getLogger();

    private ProtocolVersion appProtocolVersion;
    private ProtocolVersion minSupportedProtocolVersion;
    private final LocalBlockchain localBlockchain;
    private Handshake handshake;
    private Peer remotePeer;

    public HandshakeHandler(ProtocolVersion appProtocolVersion, ProtocolVersion minSupportedProtocolVersion, LocalBlockchain localBlockchain) {
        this.appProtocolVersion = appProtocolVersion;
        this.minSupportedProtocolVersion = minSupportedProtocolVersion;
        this.localBlockchain = localBlockchain;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Services services = new Services(
                Service.NODE_NETWORK,
                Service.NODE_BLOOM,
                Service.NODE_WITNESS,
                Service.NODE_NETWORK_LIMITED
        );
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        NetAddress toAddress = new NetAddress(
                new Services(Service.NODE_WITNESS, Service.NODE_NETWORK),
                new IpAddress(remoteAddress.getAddress().getHostAddress()), remoteAddress.getPort()
        );
        InetSocketAddress localAddress = (InetSocketAddress) ctx.channel().localAddress();
        NetAddress fromAddress = new NetAddress(
                services,
                new IpAddress(localAddress.getAddress().getHostAddress()), localAddress.getPort()
        );
        Version payload = new Version(
                appProtocolVersion,
                services,
                new Timestamp8(),
                toAddress,
                fromAddress,
                ThreadLocalRandom.current().nextLong(),
                "/Satoshi:0.16.1/",
                localBlockchain.height(),
                true
        );
        handshake = new Handshake(appProtocolVersion, minSupportedProtocolVersion);

        ChannelFuture channelFuture = ctx.channel().writeAndFlush(payload);
        channelFuture.addListener(future -> handshake.versionSended());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BtcMessage msg) throws Exception {
        logger.trace(msg);
        if (handshake.success() && !msg.isHandshakeCommand()) {
            ctx.fireChannelRead(msg);
        } else if (!handshake.success() && msg.isHandshakeCommand()) {
            if (msg.isVersion()) {
                Version versionPayload = msg.version();
                versionPayload.protocolVersion();
                remotePeer = versionPayload.peer();
                handshake.remoteVersionReceived(versionPayload.protocolVersion());
                ctx.writeAndFlush(new Verack()).addListener(future -> {
                    handshake.verackSended();
                    if (handshake.success()) {
                        handshakeSuccess(ctx);
                    }
                });
            } else if (msg.isVerack()) {
                handshake.remoteVerackReceived();
                if (handshake.success()) {
                    handshakeSuccess(ctx);
                }
            }
        } else if (!handshake.success() && !msg.isHandshakeCommand()) {
            throw new CommandBeforeHandshake(msg.command());
        } else if (handshake.success() && msg.isHandshakeCommand()) {
            throw new HandshakeAlreadyDone(msg.command());
        }
    }

    private void handshakeSuccess(ChannelHandlerContext ctx) {
        BtcNioChannel channel = (BtcNioChannel) ctx.channel();
        channel.completeHandshake();
        channel.connectionVersion(handshake.connectionVersion());
        channel.remotePeer(remotePeer);
        ctx.fireUserEventTriggered(new HandshakeEvent());
    }

    static class Handshake {
        private boolean versionSend = false;
        private boolean remoteVersionReceived = false;
        private boolean verackSend = false;
        private boolean remoteVerackReceived = false;
        private ProtocolVersion local;
        private ProtocolVersion minSupported;
        private ProtocolVersion remote;

        Handshake(ProtocolVersion local, ProtocolVersion minSupported) {
            this.local = local;
            this.minSupported = minSupported;
        }

        void versionSended() {
            versionSend = true;
        }

        void remoteVersionReceived(ProtocolVersion remoteVersion) {
            if (remoteVersion.compareTo(minSupported) < 0) {
                throw new Obsolete();
            }
            this.remote = remoteVersion;
            remoteVersionReceived = true;
        }

        void verackSended() {
            verackSend = true;
        }

        void remoteVerackReceived() {
            remoteVerackReceived = true;
        }

        boolean success() {
            return versionSend && remoteVersionReceived && verackSend && remoteVerackReceived;
        }

        ProtocolVersion connectionVersion() {
            if (success()) {
                return ProtocolVersion.min(local, remote);
            } else {
                throw new RuntimeException("handshake in process");
            }
        }
    }
}
