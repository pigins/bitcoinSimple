package serg.home.bitcoinSimple.network.peer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.blockchain.LocalBlockchain;
import serg.home.bitcoinSimple.config.NetConfig;
import serg.home.bitcoinSimple.network.handlers.DownloadHeadersHandler;
import serg.home.bitcoinSimple.network.knownAddresses.KnownAddresses;
import serg.home.bitcoinSimple.network.messages.GetAddr;
import serg.home.bitcoinSimple.network.peer.connection.BtcNioChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class Clients implements Runnable {
    private static Logger logger = LogManager.getLogger();

    private final NetConfig config;
    private final KnownAddresses knownAddresses;
    private final EventLoopGroup workerGroup;
    private final LocalBlockchain localBlockchain;
    private final EventLoopGroup connectGroup = new NioEventLoopGroup();
    private ChannelGroup activeClients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    Clients(NetConfig config, EventLoopGroup workerGroup, KnownAddresses knownAddresses, LocalBlockchain localBlockchain) {
        this.config = config;
        this.knownAddresses = knownAddresses;
        this.workerGroup = workerGroup;
        this.localBlockchain = localBlockchain;
    }

    @Override
    public void run() {
        connectGroup.scheduleAtFixedRate(() -> {
            int createCount = config.clientConnectionsCount() - activeClients.size();
            for (int i = 0; i < createCount; i++) {
                startNewClient();
            }
        }, 0, 20, TimeUnit.SECONDS);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activeClients.stream().findAny().ifPresent(channel -> {
            channel.pipeline().addAfter(
                    "addr",
                    "downloadHeaders",
                    new DownloadHeadersHandler(localBlockchain, config.appProtocolVersion())
            );
            System.out.println("added DownloadHeadersHandler");
        });
    }

    private void startNewClient() {
        if (knownAddresses.hasNext()) {
            InetSocketAddress nextAddress = knownAddresses.next();
            boolean addressAlreadyInUse = activeClients.stream().anyMatch(channel -> channel.remoteAddress().equals(nextAddress));
            if (addressAlreadyInUse) {
                return;
            }
            logger.debug("try to connect to {}", nextAddress);
            Channel channel = null;
            try {
                Bootstrap b = new Bootstrap();
                b.group(workerGroup);
                b.channel(BtcNioChannel.class);
                b.handler(new SocketChannelInitializer(config, knownAddresses, localBlockchain));
                ChannelFuture connect = b.connect(nextAddress);
                channel = connect.channel();
                connect.sync();
                activeClients.add(channel);
            } catch (Exception e) {
                if (channel != null) {
                    channel.close();
                }
            }
        } else {
            logger.debug("can't find any address");
            activeClients.stream().findAny().ifPresent(channel -> channel.writeAndFlush(new GetAddr()));
        }
    }

}
