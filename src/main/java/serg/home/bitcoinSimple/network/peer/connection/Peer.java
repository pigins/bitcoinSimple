package serg.home.bitcoinSimple.network.peer.connection;

import serg.home.bitcoinSimple.network.model.Services;

public class Peer {
    private final Services services;
    private final String value;
    private final int startHeight;
    private final boolean relay;

    public Peer(Services services, String value, int startHeight, boolean relay) {
        this.services = services;
        this.value = value;
        this.startHeight = startHeight;
        this.relay = relay;
    }

    public Services getServices() {
        return services;
    }

    public String getValue() {
        return value;
    }

    public int getStartHeight() {
        return startHeight;
    }

    public boolean isRelay() {
        return relay;
    }
}
