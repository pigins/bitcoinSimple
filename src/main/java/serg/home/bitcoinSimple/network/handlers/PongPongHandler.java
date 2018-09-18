package serg.home.bitcoinSimple.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.network.messages.*;
import serg.home.bitcoinSimple.protocol.BtcMessage;

public class PongPongHandler extends SimpleChannelInboundHandler<BtcMessage> {
    private static Logger logger = LogManager.getLogger();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BtcMessage msg) throws Exception {
        if (msg.isPing()) {
            Ping ping = msg.ping();
            logger.trace(ping);
            ctx.writeAndFlush(new Pong(ping));
        } else if (msg.isPong()) {
            // NOPE
        } else {
//            ctx.fireChannelRead(msg);
        }
    }
}
