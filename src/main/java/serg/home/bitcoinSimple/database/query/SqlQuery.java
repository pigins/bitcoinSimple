package serg.home.bitcoinSimple.database.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlQuery {
    private final Query query;
    private final String sql;

    SqlQuery(Query query, String sql) {
        this.query = query;
        this.sql = sql;
    }

    public boolean empty() {
        try (Connection conn = this.query.dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)
        ) {
            return !resultSet.next();
        } catch (SQLException e) {
            throw new QueryException(e);
        }
    }

    public boolean notEmpty() {
        return !empty();
    }

    public <T> List<T> select(Rows<T> rows) {
        try (Connection conn = this.query.dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)
        ) {
            return rows.map(resultSet);
        } catch (SQLException e) {
            throw new QueryException(e);
        }
    }

    public <T> List<T> select(Row<T> row) {
        try (Connection conn = this.query.dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)
        ) {
            List<T> res = new ArrayList<>();
            while (resultSet.next()) {
                res.add(row.mapRow(resultSet));
            }
            return res;
        } catch (SQLException e) {
            throw new QueryException(e);
        }
    }

    public void execute() {
        try (Connection conn = this.query.dataSource.getConnection();
             Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new QueryException(e);
        }
    }

    public BatchSqlQuery batch() {
        return new BatchSqlQuery(this.query, this.sql);
    }

    public BatchSqlQuery batch(int chunkSize) {
        return new BatchSqlQuery(this.query, this.sql, chunkSize);
    }
}
