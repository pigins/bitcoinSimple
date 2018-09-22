package serg.home.bitcoinSimple.network.model;

import java.time.OffsetDateTime;

public class TimestampWithAddress {
    private OffsetDateTime timestamp;
    private NetAddress address;

    public TimestampWithAddress(Timestamp timestamp, NetAddress address) {
        this.timestamp = timestamp.getValue();
        this.address = address;
    }

    public TimestampWithAddress(OffsetDateTime timestamp, NetAddress address) {
        this.timestamp = timestamp;
        this.address = address;
    }

    public OffsetDateTime timestamp() {
        return timestamp;
    }

    public NetAddress address() {
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
