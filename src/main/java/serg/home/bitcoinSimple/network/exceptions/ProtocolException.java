package serg.home.bitcoinSimple.network.exceptions;

public class ProtocolException extends RuntimeException {

    private final String command;

    public ProtocolException(String command, String message) {
        super(message);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
