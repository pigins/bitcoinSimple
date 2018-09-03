package serg.home.bitcoinSimple.network.peer;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.blockchain.LocalBlockchain;
import serg.home.bitcoinSimple.config.TestnetConfig;
import serg.home.bitcoinSimple.database.Database;
import serg.home.bitcoinSimple.network.knownAddresses.ConfigKnownAddresses;
import serg.home.bitcoinSimple.network.knownAddresses.DbKnownAddresses;
import serg.home.bitcoinSimple.network.knownAddresses.KnownAddresses;
import serg.home.bitcoinSimple.wallet.Wallet;

import java.io.Closeable;

public class Net implements Runnable, Closeable {
    private static Logger logger = LogManager.getLogger();

    private final TestnetConfig config;
    private final Database database;
    private final Wallet wallet;
    private KnownAddresses knownAddresses;
    private LocalBlockchain localBlockchain;
    private Server server;
    private Clients clients;
    private EventLoopGroup workerGroup;

    public Net(TestnetConfig config, Database database, Wallet wallet, LocalBlockchain localBlockchain) {
        this.config = config;
        this.database = database;
        this.wallet = wallet;
        this.localBlockchain = localBlockchain;
    }

    @Override
    public void run() {
        knownAddresses = new ConfigKnownAddresses(
                new DbKnownAddresses(database), config.seedIp(), config.networkPort(), config.networkHosts()
        );
        workerGroup = new NioEventLoopGroup();

        clients = new Clients(config, workerGroup, knownAddresses, localBlockchain);
        clients.run();

        this.server = new Server(config, workerGroup, knownAddresses, localBlockchain);
        this.server.run();
        logger.info("server started on {}", this.config.networkPort());
    }

    @Override
    public void close() {
        workerGroup.shutdownGracefully();
    }
}
