package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Services implements BinaryEncoded {
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
                .filter(value -> (services & (1L << Long.numberOfTrailingZeros(value.getValue()))) != 0)
                .distinct()
                .collect(Collectors.toSet());
    }

    public long asLong() {
        return services.stream().mapToLong(Service::getValue).sum();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeLongLE(asLong());
    }
}
