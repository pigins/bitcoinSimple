package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;

public class CheckedMessage {
    private String command;
    private Bytes payload;

    public CheckedMessage(String command, Bytes payload) {
        this.command = command;
        this.payload = payload;
    }

    public String getCommand() {
        return command;
    }

    public Bytes payload() {
        return payload;
    }

    @Override
    public String toString() {
        return "CheckedMessage{" +
                "command='" + command + '\'' +
                ", payload=" + payload +
                '}';
    }
}
