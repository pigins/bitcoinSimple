package serg.home.bitcoinSimple.network.knownAddresses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.network.model.NetAddress;
import serg.home.bitcoinSimple.network.model.TimestampWithAddress;
import serg.home.bitcoinSimple.database.Database;
import serg.home.bitcoinSimple.database.query.Row;

import java.net.InetSocketAddress;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DbKnownAddresses implements KnownAddresses {
    private static Logger logger = LogManager.getLogger();
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private Database database;
    private List<TimestampWithAddress> cash;
    private Iterator<TimestampWithAddress> cashIterator;

    private Row<TimestampWithAddress> row = rs -> new TimestampWithAddress(
            rs.getObject("last_active", OffsetDateTime.class),
            new NetAddress(
                    rs.getLong("services"),
                    rs.getInt("ip"),
                    rs.getShort("port")
            ));

    public DbKnownAddresses(Database database) {
        this.database = database;
        updateCash();
    }

    @Override
    public void put(List<TimestampWithAddress> addrList) {
        merge(addrList);
        cut();
        updateCash();
    }

    @Override
    public void put(TimestampWithAddress timestampWithAddress) {
        put(List.of(timestampWithAddress));
    }

    private void merge(List<TimestampWithAddress> addrList) {
        String values = addrList.stream()
                .filter(distinctByKey(elem -> elem.getAddress().ipAddress().asInt()))
                .map(addr -> "("
                        + addr.getAddress().ipAddress().asInt() +
                        "," + (short) addr.getAddress().port() +
                        "," + addr.getAddress().services().asLong() +
                        ",'" + addr.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxxx")) +
                        "')")
                .collect(Collectors.joining(","));
        database.query()
                .sql("MERGE INTO KNOWN_ADDRESSES t" +
                        "  USING (VALUES" + values + ") AS vals(IP, PORT, SERVICES, LAST_ACTIVE) ON t.IP = vals.IP" +
                        "  WHEN MATCHED THEN UPDATE SET t.PORT = vals.PORT, t.SERVICES = vals.SERVICES, t.LAST_ACTIVE = vals.LAST_ACTIVE" +
                        "  WHEN NOT MATCHED THEN INSERT VALUES vals.IP, vals.PORT, vals.SERVICES, vals.LAST_ACTIVE"
                ).execute();
    }

    private void updateCash() {
        this.cash = database
                .query()
                .sql("SELECT IP, PORT, SERVICES, LAST_ACTIVE FROM KNOWN_ADDRESSES ORDER BY LAST_ACTIVE DESC")
                .select(row);
        cashIterator = cash.iterator();
    }

    private void cut() {
        database.query()
                .sql("DELETE FROM KNOWN_ADDRESSES t WHERE IP IN(" +
                        "SELECT IP FROM KNOWN_ADDRESSES ORDER BY LAST_ACTIVE DESC OFFSET 1000 ROWS " +
                        ")")
                .execute();
    }

    @Override
    public List<TimestampWithAddress> all() {
        return cash;
    }

    @Override
    public boolean hasNext() {
        return cashIterator.hasNext();
    }

    @Override
    public InetSocketAddress next() {
        return cashIterator.next().getAddress().inetSocketAddress();
    }
}
