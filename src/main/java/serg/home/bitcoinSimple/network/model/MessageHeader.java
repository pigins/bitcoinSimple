package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * All messages in the network protocol use the same container format, which provides a required multi-field message header and an optional payload.
 * @see <a href="https://bitcoin.org/en/developer-reference#message-headers">https://bitcoin.org/en/developer-reference#message-headers</a>
 */
public class MessageHeader implements ByteBufWritable {
    private static final int COMMAND_LENGTH = 12;

    public static MessageHeader read(ByteBuf byteBuf) {
        Network network = Network.read(byteBuf);
        String command = byteBuf.readCharSequence(COMMAND_LENGTH, StandardCharsets.US_ASCII).toString().trim();
        int payloadSize = byteBuf.readIntLE();
        int checksum = byteBuf.readInt();
        return new MessageHeader(network, command, payloadSize, checksum);
    }

    private Network network;
    private String command;
    private int payloadSize;
    private int checksum;

    public MessageHeader(Network network, String command, int payloadSize, int checksum) {
        this.network = network;
        this.command = command;
        this.payloadSize = payloadSize;
        this.checksum = checksum;
    }

    public boolean sameNetwork(Network network) {
        return this.network.equals(network);
    }

    public Network network() {
        return network;
    }

    public String command() {
        return command;
    }

    public int payloadSize() {
        return payloadSize;
    }

    public int checksum() {
        return checksum;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        network.write(byteBuf);
        byte[] commandBytes = command.getBytes(StandardCharsets.US_ASCII);
        int zeroBytesLength = COMMAND_LENGTH - commandBytes.length;
        byteBuf.writeBytes(commandBytes);
        byteBuf.writeZero(zeroBytesLength);
        byteBuf.writeIntLE(payloadSize);
        byteBuf.writeInt(checksum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageHeader that = (MessageHeader) o;
        return payloadSize == that.payloadSize &&
                checksum == that.checksum &&
                network == that.network &&
                Objects.equals(command, that.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(network, command, payloadSize, checksum);
    }

    @Override
    public String toString() {
        return "MessageHeader{" +
                "network=" + network +
                ", command='" + command + '\'' +
                ", payloadSize=" + payloadSize +
                ", checksum=" + checksum +
                '}';
    }
}
