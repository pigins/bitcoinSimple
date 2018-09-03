package serg.home.bitcoinSimple.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private final String[] args;
    private final Path homeDir;
    private DbConfig dbConfig;


    public Config(String[] args) {
        this.args = args;
        homeDir = Paths.get(System.getProperty("user.home")).resolve(".bitcoin_simple");
        dbConfig = new DbConfig(8, rootDir());
    }

    public Path rootDir() {
        return homeDir.resolve(net().network().name());
    }

    public DbConfig database() {
        return dbConfig;
    }

    public TestnetConfig net() {
        return new TestnetConfig();
    }
}
