package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  Bitfield of features to be enabled for connection.
 *  1	    NODE_NETWORK	        This node can be asked for full blocks instead of just headers.
 *  2	    NODE_GETUTXO	        See BIP 0064
 *  4	    NODE_BLOOM	            See BIP 0111
 *  8	    NODE_WITNESS	        See BIP 0144
 *  1024	NODE_NETWORK_LIMITED	See BIP 0159
 */
public class Services implements ByteBufWritable {
    public static Services read(ByteBuf byteBuf) {
        long services = byteBuf.readLongLE();
        return new Services(services);
    }

    private Set<Service> services;

    public Services(Service... services) {
        this.services = new HashSet<>();
        this.services.addAll(Arrays.asList(services));
    }

    public Services(long services) {
        this.services = Arrays.stream(Service.values())
                .filter(value -> (services & (1L << Long.numberOfTrailingZeros(value.value()))) != 0)
                .distinct()
                .collect(Collectors.toSet());
    }

    public long asLong() {
        return services.stream().mapToLong(Service::value).sum();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeLongLE(asLong());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Services services1 = (Services) o;
        return Objects.equals(services, services1.services);
    }

    @Override
    public int hashCode() {
        return Objects.hash(services);
    }

    @Override
    public String toString() {
        return "Services{" +
                "services=" + services +
                '}';
    }
}
