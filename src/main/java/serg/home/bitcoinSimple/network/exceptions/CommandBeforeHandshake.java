package serg.home.bitcoinSimple.network.exceptions;

public class CommandBeforeHandshake extends ProtocolException {
    public CommandBeforeHandshake(String command) {
        super(command, "command was received before handshake");
    }
}
