package serg.home.bitcoinSimple.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import serg.home.bitcoinSimple.network.exceptions.ProtocolException;
import serg.home.bitcoinSimple.network.messages.Reject;

public class InboundExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        if (cause instanceof ProtocolException) {
            ProtocolException ex = (ProtocolException)cause;
            ctx.writeAndFlush(new Reject(ex.getMessage(), Reject.CCODES.REJECT_MALFORMED, ex.getMessage(), null));
        }
    }
}
