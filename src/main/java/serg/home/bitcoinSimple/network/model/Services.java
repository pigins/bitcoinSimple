package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Services extends HashSet<Service> implements BinaryEncoded {

    public Services(Service... services) {
        this.addAll(Arrays.asList(services));
    }

    public Services(long services) {
        this.addAll(Arrays.stream(Service.values())
                .filter(value -> (services & (1L << Long.numberOfTrailingZeros(value.getValue()))) != 0)
                .distinct()
                .collect(Collectors.toCollection(Services::new)));
    }

    public Services(Set<Service> services) {
        this.addAll(services);
    }

    public long asLong() {
        return this.stream().mapToLong(Service::getValue).sum();
    }

    @Override
    public Bytes encode() {
        return Bytes.fromLongToLE(asLong());
    }
}
