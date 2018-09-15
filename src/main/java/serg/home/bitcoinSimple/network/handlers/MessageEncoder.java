package serg.home.bitcoinSimple.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.common.Bytes;
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
        Bytes encodedMessage = msg.encode();
        encodedMessage.length();
        Bytes checkum = encodedMessage.doubleSha256().subArray(0, 4);
        MessageHeader messageHeader = new MessageHeader(network, msg.name(), encodedMessage.length(), checkum);
        log.trace(msg);
        log.trace(encodedMessage);
        out.writeBytes(messageHeader.encode().byteArray());
        out.writeBytes(encodedMessage.byteArray());
    }
}
