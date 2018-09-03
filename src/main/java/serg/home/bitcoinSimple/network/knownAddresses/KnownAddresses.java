package serg.home.bitcoinSimple.network.knownAddresses;

import serg.home.bitcoinSimple.network.model.TimestampWithAddress;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;

public interface KnownAddresses extends Iterator<InetSocketAddress> {
    void put(List<TimestampWithAddress> addrList);

    void put(TimestampWithAddress addrList);

    List<TimestampWithAddress> all();

    @Override
    boolean hasNext();

    @Override
    InetSocketAddress next();
}
