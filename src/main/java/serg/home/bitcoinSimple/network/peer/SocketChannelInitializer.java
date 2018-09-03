package serg.home.bitcoinSimple.network.peer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import serg.home.bitcoinSimple.blockchain.LocalBlockchain;
import serg.home.bitcoinSimple.config.NetConfig;
import serg.home.bitcoinSimple.network.knownAddresses.KnownAddresses;
import serg.home.bitcoinSimple.network.handlers.*;
import serg.home.bitcoinSimple.network.peer.connection.BtcNioChannel;

public class SocketChannelInitializer extends ChannelInitializer<BtcNioChannel> {
    static final EventExecutorGroup longRunningTaskGroup = new NioEventLoopGroup();

    private final NetConfig netConfig;
    private final KnownAddresses knownAddresses;
    private final LocalBlockchain localBlockchain;

    public SocketChannelInitializer(NetConfig netConfig, KnownAddresses knownAddresses, LocalBlockchain localBlockchain) {
        this.netConfig = netConfig;
        this.knownAddresses = knownAddresses;
        this.localBlockchain = localBlockchain;
    }

    @Override
    public void initChannel(BtcNioChannel ch) throws Exception {
        // outbounds
        ch.pipeline().addLast(new MessageEncoder(netConfig.network()));
        // inbounds
        ch.pipeline().addLast(new MessageDecoder());
        ch.pipeline().addLast(new CheckMessageHandler(netConfig.network()));
        ch.pipeline().addLast("handshake", new HandshakeHandler(netConfig.appProtocolVersion(), netConfig.minSupportedProtocolVersion(), localBlockchain));
        ch.pipeline().addLast(longRunningTaskGroup, "addr", new AddrHandler(knownAddresses));
        ch.pipeline().addLast(new PongPongHandler());
        ch.pipeline().addLast(new InboundExceptionHandler());
    }
}
