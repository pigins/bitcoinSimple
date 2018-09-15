package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import java.nio.charset.StandardCharsets;

public class MessageHeader implements BinaryEncoded {
    public static MessageHeader read(ByteBuf byteBuf) {
        Network network = Network.read(byteBuf);
        String command = new String(byteBuf.readBytes(12).array(), StandardCharsets.US_ASCII).trim();
        int payloadSize = byteBuf.readIntLE();
        ByteBuf checksum = byteBuf.readBytes(4);
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
    private ByteBuf checksum;

    public MessageHeader(Network network, String command, int payloadSize, ByteBuf checksum) {
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

    public ByteBuf getChecksum() {
        return checksum;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        network.write(byteBuf);
        byteBuf.writeBytes(nullPadded(command.getBytes(StandardCharsets.US_ASCII)));
        byteBuf.writeIntLE(payloadSize);
        byteBuf.writeBytes(checksum);
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
