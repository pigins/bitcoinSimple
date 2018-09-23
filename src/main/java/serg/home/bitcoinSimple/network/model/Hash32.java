package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash32 implements ByteBufWritable {
    public static final Logger logger = LogManager.getLogger();
    private static MessageDigest sha256Digest;

    static {
        try {
            sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        }
    }

    public static Hash32 read(ByteBuf byteBuf) {
        return new Hash32(byteBuf.slice(byteBuf.readerIndex(), 32));
    }

    public static Hash32 calcSha256(ByteBuf byteBuf) {
        sha256Digest.update(byteBuf.nioBuffer());
        return new Hash32(Unpooled.wrappedBuffer(sha256Digest.digest()));
    }

    public static Hash32 calcDoubleSha256(ByteBuf byteBuf) {
        sha256Digest.update(byteBuf.nioBuffer());
        byte[] digest = sha256Digest.digest();
        sha256Digest.update(digest);
        return new Hash32(Unpooled.wrappedBuffer(sha256Digest.digest()));
    }

    private ByteBuf hash;

    public Hash32(ByteBuf hash) {
        this.hash = hash;
    }

    public String hexDump() {
        return ByteBufUtil.hexDump(hash);
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeBytes(hash);
    }

    @Override
    public String toString() {
        return "Hash32=" + ByteBufUtil.hexDump(hash);
    }
}
