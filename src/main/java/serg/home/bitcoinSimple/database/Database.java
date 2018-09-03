package serg.home.bitcoinSimple.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.hsqldb.jdbc.JDBCPool;
import serg.home.bitcoinSimple.config.DbConfig;
import serg.home.bitcoinSimple.database.query.Query;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;


public class Database {
    private static Logger logger = LogManager.getLogger();

    private DataSource dataSource;
    private DbConfig dbConfig;

    public Database(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public void init() {
        try {
            createDatasource();
            if (original()) {
                createStructure();
            }
            addShutdownHook();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public Query query() {
        return new Query(dataSource);
    }

    private void createDatasource() {
        String url = String.format("jdbc:hsqldb:file:/%s/db", dbConfig.getDir().toString());
        JDBCPool dataSource = new JDBCPool(dbConfig.getConnectionPoolSize());
        dataSource.setUrl(url);
        dataSource.setUser("SA");
        dataSource.setPassword("");
        this.dataSource = dataSource;
    }

    private boolean original() {
        return query()
                .sql("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'APP'")
                .empty();
    }

    private void createStructure() throws SQLException, IOException, SqlToolError {
        try(InputStream inputStream = getClass().getResourceAsStream("structure.sql")) {
            SqlFile sqlFile = new SqlFile(
                    new InputStreamReader(inputStream),
                    "init",
                    System.out,
                    "UTF-8",
                    false,
                    new File(".")
            );
            sqlFile.setConnection(dataSource.getConnection());
            sqlFile.execute();
        }
    }

    private void addShutdownHook() {
        Thread hook = new Thread(() -> query().sql("SHUTDOWN").execute());
        Runtime.getRuntime().addShutdownHook(hook);
    }
}
