package serg.home.bitcoinSimple;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.blockchain.LocalBlockchain;
import serg.home.bitcoinSimple.config.Config;
import serg.home.bitcoinSimple.database.Database;
import serg.home.bitcoinSimple.network.peer.Net;
import serg.home.bitcoinSimple.wallet.AddressVersion;
import serg.home.bitcoinSimple.wallet.Wallet;

public class App {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        System.setProperty("io.netty.tryReflectionSetAccessible", "true");
        InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);
        logger.info("start application");
        new App(new Config(args)).start();
    }

    private Config config;
    private Database database;
    private LocalBlockchain localBlockchain;
    private Wallet wallet;
    private Net net;

    private App(Config config) {
        this.config = config;
    }

    private void start() {
        initDatabase();
        initBlockchain();
        initWallet();
        initNet();
    }

    private void initDatabase() {
        database = new Database(config.database());
        database.init();
    }

    private void initBlockchain() {
        localBlockchain = new LocalBlockchain(database, config.net().genesis());
    }

    private void initWallet() {
        wallet = new Wallet(AddressVersion.TESTNET_P2SH);
    }

    private void initNet() {
        net = new Net(config.net(), database, wallet, localBlockchain);
        net.run();
    }
}
