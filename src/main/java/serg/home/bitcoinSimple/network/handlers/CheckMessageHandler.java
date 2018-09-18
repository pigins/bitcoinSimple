package serg.home.bitcoinSimple.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import serg.home.bitcoinSimple.network.model.Network;
import serg.home.bitcoinSimple.protocol.BtcMessage;

public class CheckMessageHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private Network network;

    public CheckMessageHandler(Network network) {
        this.network = network;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        BtcMessage btcMessage = new BtcMessage(msg);
        btcMessage.validateNetwork(network);
        btcMessage.validateChecksum();
        ctx.fireChannelRead(btcMessage);
    }
}
