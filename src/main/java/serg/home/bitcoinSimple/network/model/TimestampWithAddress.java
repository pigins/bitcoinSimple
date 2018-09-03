package serg.home.bitcoinSimple.network.model;

import java.time.OffsetDateTime;

public class TimestampWithAddress {
    private OffsetDateTime timestamp;
    private NetAddress address;

    public TimestampWithAddress(OffsetDateTime timestamp, NetAddress address) {
        this.timestamp = timestamp;
        this.address = address;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public NetAddress getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "TimestampWithAddress{" +
                "timestamp=" + timestamp +
                ", address=" + address +
                '}';
    }
}
