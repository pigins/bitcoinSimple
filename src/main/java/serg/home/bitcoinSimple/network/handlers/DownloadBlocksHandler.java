package serg.home.bitcoinSimple.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import serg.home.bitcoinSimple.protocol.BtcMessage;

public class DownloadBlocksHandler extends SimpleChannelInboundHandler<BtcMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BtcMessage msg) throws Exception {

    }
}
