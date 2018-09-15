package serg.home.bitcoinSimple.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import serg.home.bitcoinSimple.blockchain.LocalBlockchain;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.InvType;
import serg.home.bitcoinSimple.network.model.InvVector;
import serg.home.bitcoinSimple.protocol.BtcMessage;
import serg.home.bitcoinSimple.network.messages.GetBlocks;
import serg.home.bitcoinSimple.network.messages.Inv;
import serg.home.bitcoinSimple.network.model.ProtocolVersion;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class GetBlocksHandler extends SimpleChannelInboundHandler<BtcMessage> {
    private final LocalBlockchain localBlockchain;
    private final ProtocolVersion protocolVersion;

    public GetBlocksHandler(LocalBlockchain localBlockchain, ProtocolVersion protocolVersion) {
        this.localBlockchain = localBlockchain;
        this.protocolVersion = protocolVersion;
    }

    private ChannelHandlerContext ctx;
    private final BlockingQueue<List<Bytes>> answer = new LinkedBlockingQueue<>();

    public List<Bytes> getBlockHeaders() {
        ctx.writeAndFlush(new GetBlocks(protocolVersion, localBlockchain.locator(), true))
                .addListener(future -> {
            if (!future.isSuccess()) {
                future.cause().printStackTrace();
                ctx.channel().close();
            }
        });

        boolean interrupted = false;
        try {
            for (;;) {
                try {
                    return answer.take();
                } catch (InterruptedException ignore) {
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, final BtcMessage msg) {
        if (msg.getCommand().equals(Inv.NAME)) {
            Inv inv = msg.inv();
            List<Bytes> hashes = inv.invVectors().stream()
                    .filter(invVector -> invVector.type().equals(InvType.MSG_BLOCK))
                    .map(InvVector::hash)
                    .collect(Collectors.toList());

//            answer.offer(msg);
        }

    }
}
