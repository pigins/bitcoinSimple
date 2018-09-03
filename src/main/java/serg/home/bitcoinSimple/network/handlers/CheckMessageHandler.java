package serg.home.bitcoinSimple.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.protocol.BtcMessage;
import serg.home.bitcoinSimple.network.exceptions.DifferentNetworks;
import serg.home.bitcoinSimple.network.exceptions.InvalidMessageChecksum;
import serg.home.bitcoinSimple.network.model.MessageHeader;
import serg.home.bitcoinSimple.network.model.*;

public class CheckMessageHandler extends SimpleChannelInboundHandler<ByteBuf> {


    private Network network;

    public CheckMessageHandler(Network network) {
        this.network = network;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] headerBytes = new byte[24];
        msg.readBytes(headerBytes);

        // FIXME
        BtcMessage btcMessage = new BtcMessage(new Bytes(headerBytes));
        MessageHeader messageHeader = btcMessage.nextMessageHeader();
        if (!messageHeader.sameNetwork(network)) {
            throw new DifferentNetworks(messageHeader.getCommand());
        }

        byte[] payloadBytes = new byte[messageHeader.getPayloadSize()];
        msg.readBytes(payloadBytes);
        Bytes payload = new Bytes(payloadBytes);
        Bytes checksum = payload.doubleSha256().subArray(0, 4);
        if (!checksum.equals(messageHeader.getChecksum())) {
            throw new InvalidMessageChecksum(messageHeader.getCommand());
        }
        ctx.fireChannelRead(new BtcMessage(messageHeader.getCommand(), payload));
    }
}
