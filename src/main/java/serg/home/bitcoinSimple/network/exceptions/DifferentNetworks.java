package serg.home.bitcoinSimple.network.exceptions;

public class DifferentNetworks extends ProtocolException {
    public DifferentNetworks(String command) {
        super(command, "different networks");
    }
}
