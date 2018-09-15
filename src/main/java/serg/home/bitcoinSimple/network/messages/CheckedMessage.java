package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;

public class CheckedMessage {
    private String command;
    private ByteBuf payload;

    public CheckedMessage(String command, ByteBuf payload) {
        this.command = command;
        this.payload = payload;
    }

    public String getCommand() {
        return command;
    }

    public ByteBuf payload() {
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
