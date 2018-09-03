package serg.home.bitcoinSimple.network.knownAddresses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.common.ChainIterator;
import serg.home.bitcoinSimple.network.model.TimestampWithAddress;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ConfigKnownAddresses implements KnownAddresses {
    private static Logger logger = LogManager.getLogger();

    private KnownAddresses wrappee;
    private final int port;
    private ChainIterator<InetSocketAddress> chainIterator;

    public ConfigKnownAddresses(KnownAddresses wrappee, String seedIp, int port, List<String> hosts) {
        this.wrappee = wrappee;
        this.port = port;
        HostAddressIterator hostsIterator = new HostAddressIterator(hosts);
        SeedAddressIterator seedIterator = new SeedAddressIterator(seedIp);
        chainIterator = new ChainIterator<>(seedIterator, wrappee, hostsIterator);
    }

    @Override
    public void put(List<TimestampWithAddress> addrList) {
        wrappee.put(addrList);
    }

    @Override
    public void put(TimestampWithAddress addrList) {
        wrappee.put(addrList);
    }

    @Override
    public List<TimestampWithAddress> all() {
        return wrappee.all();
    }

    @Override
    public boolean hasNext() {
        return chainIterator.hasNext();
    }

    @Override
    public InetSocketAddress next() {
        return chainIterator.next();
    }

    private class SeedAddressIterator implements Iterator<InetSocketAddress> {
        InetSocketAddress current;
        String seed;

        SeedAddressIterator(String seed) {
            this.seed = seed;
        }

        @Override
        public boolean hasNext() {
            if (seed == null) {
                return false;
            }
            if (current != null) {
                return true;
            }
            try {
                current = new InetSocketAddress(seed, port);
                return true;
            } catch (Exception e) {
                logger.warn(e);
                return false;
            }
        }

        @Override
        public InetSocketAddress next() {
            return current;
        }
    }

    private class HostAddressIterator implements Iterator<InetSocketAddress> {
        InetSocketAddress current;
        Queue<String> hostsQueue;

        HostAddressIterator(List<String> hosts) {
            this.hostsQueue = new LinkedList<>(hosts);
        }

        @Override
        public boolean hasNext() {
            if (hostsQueue.size() == 0) {
                return false;
            }
            try {
                InetAddress address = InetAddress.getByName(hostsQueue.poll());
                current = new InetSocketAddress(address, port);
                return true;
            } catch (Exception e) {
                logger.warn(e);
                return hasNext();
            }
        }

        @Override
        public InetSocketAddress next() {
            return current;
        }
    }
}
