package serg.home.bitcoinSimple.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.network.knownAddresses.KnownAddresses;
import serg.home.bitcoinSimple.network.messages.Addr;
import serg.home.bitcoinSimple.protocol.BtcMessage;
import serg.home.bitcoinSimple.network.messages.GetAddr;

public class AddrHandler extends SimpleChannelInboundHandler<BtcMessage> {
    private static Logger logger = LogManager.getLogger();

    private final KnownAddresses knownAddresses;

    public AddrHandler(KnownAddresses knownAddresses) {
        this.knownAddresses = knownAddresses;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BtcMessage msg) throws Exception {
        if (msg.getCommand().equals(Addr.NAME)) {
            Addr addr = msg.nextAddr();
            knownAddresses.put(addr.getAddrList());
            logger.trace(addr);
        } else if (msg.getCommand().equals(GetAddr.NAME)) {
            Addr addr = new Addr(knownAddresses.all());
            ctx.writeAndFlush(addr);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
