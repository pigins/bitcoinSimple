package serg.home.bitcoinSimple.network.exceptions;

public class HandshakeAlreadyDone extends ProtocolException {
    public HandshakeAlreadyDone(String command) {
        super(command, "handshake already done");
    }
}
