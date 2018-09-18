package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.nio.charset.StandardCharsets;

public class MessageHeader implements ByteBufWritable {
    public static MessageHeader read(ByteBuf byteBuf) {
        Network network = Network.read(byteBuf);
        String command = new String(byteBuf.readBytes(12).array(), StandardCharsets.US_ASCII).trim();
        int payloadSize = byteBuf.readIntLE();
        int checksum = byteBuf.readInt();
        return new MessageHeader(network, command, payloadSize, checksum);
    }
    /**
     * Magic value indicating message origin network, and used to seek to next message when stream state is unknown
     */
    private Network network;
    /**
     * ASCII string identifying the packet content, NULL padded (non-NULL padding results in packet rejected)
     */
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

    public String getCommand() {
        return command;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    public int getChecksum() {
        return checksum;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        network.write(byteBuf);
        byteBuf.writeBytes(nullPadded(command.getBytes(StandardCharsets.US_ASCII)));
        byteBuf.writeIntLE(payloadSize);
        byteBuf.writeInt(checksum);
    }

    private byte[] nullPadded(byte[] in) {
        byte[] res = new byte[12];
        System.arraycopy(in, 0, res, 0, in.length);
        return res;
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
