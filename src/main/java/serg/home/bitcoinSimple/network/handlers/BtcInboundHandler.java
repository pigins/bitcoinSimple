package serg.home.bitcoinSimple.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import serg.home.bitcoinSimple.protocol.BtcMessage;
import serg.home.bitcoinSimple.network.peer.connection.BtcNioChannel;

public abstract class BtcInboundHandler extends SimpleChannelInboundHandler<BtcMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BtcMessage msg) throws Exception {
        btcMessage(ctx, msg, (BtcNioChannel) ctx.channel());
    }
    abstract void btcMessage(ChannelHandlerContext ctx, BtcMessage msg, BtcNioChannel channel) throws Exception;

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        btcChannelActive(ctx, (BtcNioChannel) ctx.channel());
//    }
//
//    void btcChannelActive(ChannelHandlerContext ctx, BtcNioChannel channel) throws Exception{
//        super.channelActive(ctx);
//    }
//
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof HandshakeEvent) {
//            completeHandshake(ctx, (HandshakeEvent)evt, (BtcNioChannel) ctx.channel());
//        }
//        super.userEventTriggered(ctx, evt);
//    }
//
//    void completeHandshake(ChannelHandlerContext ctx, HandshakeEvent evt, BtcNioChannel channel) {
//
//    }
}
