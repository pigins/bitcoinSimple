package serg.home.bitcoinSimple.config;

import java.nio.file.Path;

public class DbConfig {
    private int connectionPoolSize;
    private Path appDir;

    public DbConfig(int connectionPoolSize, Path appDir) {
        this.connectionPoolSize = connectionPoolSize;
        this.appDir = appDir;
    }

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public Path getDir() {
        return appDir.resolve("database");
    }
}
