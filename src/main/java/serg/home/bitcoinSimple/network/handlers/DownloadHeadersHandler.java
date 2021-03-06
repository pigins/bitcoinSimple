package serg.home.bitcoinSimple.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import serg.home.bitcoinSimple.blockchain.LocalBlockchain;
import serg.home.bitcoinSimple.protocol.BtcMessage;
import serg.home.bitcoinSimple.network.messages.GetHeaders;
import serg.home.bitcoinSimple.network.messages.Headers;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;

import java.util.List;
// https://bitcoin.org/en/developer-guide#initial-block-download
public class DownloadHeadersHandler extends SimpleChannelInboundHandler<BtcMessage> {
    private LocalBlockchain localBlockchain;
    private ProtocolVersion protocolVersion;

    public DownloadHeadersHandler(LocalBlockchain localBlockchain, ProtocolVersion protocolVersion) {
        this.localBlockchain = localBlockchain;
        this.protocolVersion = protocolVersion;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(new GetHeaders(protocolVersion, localBlockchain.locator(), true)).addListener(future -> {
        });
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BtcMessage msg) throws Exception {
        if (msg.isHeaders()) {
            Headers headers = msg.headers();
            if (!headers.sizeLessThenMax()) {
                localBlockchain.addHeaders(headers.blockHeaders());
                List<ByteBuf> locator = localBlockchain.locator();
                ctx.channel().writeAndFlush(new GetHeaders(protocolVersion, locator, true));
            } else {
                // TODO
                System.out.println("download complete!");
                // download complete
                // send getheaders to all active connections
                // find forks and find best blockchain
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
