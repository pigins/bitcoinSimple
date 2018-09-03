package serg.home.bitcoinSimple.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.network.messages.*;

public class PongPongHandler extends SimpleChannelInboundHandler<CheckedMessage> {
    private static Logger logger = LogManager.getLogger();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CheckedMessage msg) throws Exception {
        if (msg.getCommand().equals(Ping.NAME)) {
            Ping ping = new Ping(msg.payload());
            logger.trace(ping);
            ctx.writeAndFlush(new Pong(ping));
        } else if (msg.getCommand().equals(Pong.NAME)) {
            // NOPE
        } else {
//            ctx.fireChannelRead(msg);
        }
    }
}
