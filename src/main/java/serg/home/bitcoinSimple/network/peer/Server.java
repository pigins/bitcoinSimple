package serg.home.bitcoinSimple.network.peer;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import serg.home.bitcoinSimple.blockchain.LocalBlockchain;
import serg.home.bitcoinSimple.config.NetConfig;
import serg.home.bitcoinSimple.network.knownAddresses.KnownAddresses;
import serg.home.bitcoinSimple.network.peer.connection.BtcNioServerChannel;

import java.io.Closeable;

public class Server implements Runnable, Closeable {
    private final NetConfig config;
    private final EventLoopGroup acceptGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup;
    private final KnownAddresses knownAddresses;
    private final LocalBlockchain localBlockchain;

    public Server(NetConfig config, EventLoopGroup workerGroup, KnownAddresses knownAddresses, LocalBlockchain localBlockchain) {
        this.config = config;
        this.workerGroup = workerGroup;
        this.knownAddresses = knownAddresses;
        this.localBlockchain = localBlockchain;
    }

    @Override
    public void run() {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(acceptGroup, workerGroup)
                    .channel(BtcNioServerChannel.class)
                    .childHandler(new SocketChannelInitializer(config, knownAddresses, localBlockchain))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(config.networkPort()).sync();
        } catch (Exception ex) {
            this.close();
        }
    }

    @Override
    public void close() {
        acceptGroup.shutdownGracefully();
    }
}