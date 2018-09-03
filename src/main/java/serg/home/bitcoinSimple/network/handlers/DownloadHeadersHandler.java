package serg.home.bitcoinSimple.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import serg.home.bitcoinSimple.blockchain.LocalBlockchain;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.messages.CheckedMessage;
import serg.home.bitcoinSimple.network.messages.GetHeaders;
import serg.home.bitcoinSimple.network.messages.Headers;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;

import java.util.List;
// https://bitcoin.org/en/developer-guide#initial-block-download
public class DownloadHeadersHandler extends SimpleChannelInboundHandler<CheckedMessage> {
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
    protected void channelRead0(ChannelHandlerContext ctx, CheckedMessage msg) throws Exception {
        if (msg.getCommand().equals(Headers.NAME)) {
            Headers headers = new Headers(msg.payload());
            if (!headers.sizeLessThenMax()) {
                localBlockchain.addHeaders(headers.blockHeaders());
                List<Bytes> locator = localBlockchain.locator();
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
