package serg.home.bitcoinSimple.network.exceptions;

public class InvalidMessageChecksum extends ProtocolException {
    public InvalidMessageChecksum(String command) {
        super(command, "invalid checksum");
    }
}
