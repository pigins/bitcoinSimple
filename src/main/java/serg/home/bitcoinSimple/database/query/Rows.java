package serg.home.bitcoinSimple.database.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Rows<T> {
    List<T> map(ResultSet rs) throws SQLException;
}
