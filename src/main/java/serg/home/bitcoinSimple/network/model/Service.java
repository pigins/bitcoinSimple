package serg.home.bitcoinSimple.network.model;

public enum Service {
    NODE_NETWORK        (0b0000000000000000000000000000000000000000000000000000000000000001),
    NODE_GETUTXO        (0b0000000000000000000000000000000000000000000000000000000000000010),
    NODE_BLOOM          (0b0000000000000000000000000000000000000000000000000000000000000100),
    NODE_WITNESS        (0b0000000000000000000000000000000000000000000000000000000000001000),
    NODE_NETWORK_LIMITED(0b0000000000000000000000000000000000000000000000000000010000000000);

    private long value;

    Service(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}