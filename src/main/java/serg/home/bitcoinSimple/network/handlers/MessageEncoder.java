package serg.home.bitcoinSimple.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.common.DigestByteBuf;
import serg.home.bitcoinSimple.network.model.MessageHeader;
import serg.home.bitcoinSimple.network.messages.Payload;
import serg.home.bitcoinSimple.network.model.Network;

public class MessageEncoder extends MessageToByteEncoder<Payload> {
    private static final Logger log = LogManager.getLogger();

    private Network network;

    public MessageEncoder(Network network) {
        this.network = network;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Payload msg, ByteBuf out) throws Exception {
        ByteBuf payloadBuffer = Unpooled.buffer();
        msg.write(payloadBuffer);
        int checksum = new DigestByteBuf(payloadBuffer).doubleSha256().readInt();
        MessageHeader messageHeader = new MessageHeader(network, msg.name(), payloadBuffer.writerIndex() + 1, checksum);
        log.trace(msg);
        messageHeader.write(out);
        out.writeBytes(payloadBuffer);
    }
}
