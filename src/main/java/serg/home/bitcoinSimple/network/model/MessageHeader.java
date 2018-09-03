package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import java.nio.charset.StandardCharsets;

public class MessageHeader implements BinaryEncoded, BinaryDecoded {

    /**
     * Magic value indicating message origin network, and used to seek to next message when stream state is unknown
     */
    private Network network;
    /**
     * ASCII string identifying the packet content, NULL padded (non-NULL padding results in packet rejected)
     */
    private String command;
    private int payloadSize;
    private Bytes checksum;

    public MessageHeader(ByteReader byteReader) {
        decode(byteReader);
    }

    public MessageHeader(Network network, String command, Bytes payload) {
        this.network = network;
        this.command = command;
        this.payloadSize = payload.length();
        this.checksum = payload.doubleSha256().subArray(0, 4);
    }

    public Network getNetwork() {
        return network;
    }

    public String getCommand() {
        return command;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    public Bytes getChecksum() {
        return checksum;
    }

    @Override
    public void decode(ByteReader byteReader) {
        this.network = Network.decode(byteReader);
        this.command = new String(byteReader.next(12).byteArray(), StandardCharsets.US_ASCII).trim();
        this.payloadSize = byteReader.nextIntLE();
        this.checksum = byteReader.next(4);
    }

    @Override
    public Bytes encode() {
        return new CompoundBinary()
                .add(network)
                .add(new Bytes(command.getBytes(StandardCharsets.US_ASCII)).nullPadded(12))
                .add(Bytes.fromIntLE(payloadSize))
                .add(checksum)
                .encode();
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
