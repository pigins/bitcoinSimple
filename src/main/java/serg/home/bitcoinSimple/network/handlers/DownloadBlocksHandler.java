package serg.home.bitcoinSimple.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import serg.home.bitcoinSimple.network.messages.CheckedMessage;

public class DownloadBlocksHandler extends SimpleChannelInboundHandler<CheckedMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CheckedMessage msg) throws Exception {

    }
}
