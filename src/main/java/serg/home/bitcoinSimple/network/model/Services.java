package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Services extends HashSet<Service> implements BinaryEncoded, BinaryDecoded {

    public Services(long services) {
        decode(services);
    }

    public Services(Service... services) {
        this.addAll(Arrays.asList(services));
    }

    public Services(ByteReader byteReader) {
        decode(byteReader);
    }

    public long asLong() {
        return this.stream().mapToLong(Service::getValue).sum();
    }

    @Override
    public void decode(ByteReader byteReader) {
        long l = byteReader.nextLongLE();
        decode(l);
    }

    @Override
    public Bytes encode() {
        return Bytes.fromLongToLE(asLong());
    }

    private void decode(long services) {
        this.addAll(Arrays.stream(Service.values())
                .filter(value -> (services & (1L << Long.numberOfTrailingZeros(value.getValue()))) != 0)
                .collect(Collectors.toSet()));
    }
}
