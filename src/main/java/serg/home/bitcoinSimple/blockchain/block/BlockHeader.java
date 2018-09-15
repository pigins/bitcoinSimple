package serg.home.bitcoinSimple.blockchain.block;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import serg.home.bitcoinSimple.network.model.Timestamp4;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import java.time.OffsetDateTime;

public class BlockHeader implements BinaryEncoded {
    public static BlockHeader read(ByteBuf byteBuf) {
        return new BlockHeader(
                BlockVersion.read(byteBuf),
                byteBuf.readBytes(32),
                byteBuf.readBytes(32),
                Timestamp4.read(byteBuf),
                Difficulty.read(byteBuf),
                byteBuf.readIntLE(),
                true
        );
    }
    private static Bytes ZERO_BYTE = new Bytes("00");


    private BlockVersion blockVersion;
    private ByteBuf previousBlockHash;
    // reversed
    private ByteBuf mercleRoot;
    private OffsetDateTime date;
    private Difficulty difficulty;
    private int uNonce;
    private boolean withZeroByte;

    public BlockHeader(BlockVersion blockVersion, ByteBuf previousBlockHash, ByteBuf mercleRoot, OffsetDateTime date, Difficulty difficulty, int uNonce, boolean withZeroByte) {
        this(blockVersion, previousBlockHash, mercleRoot, date, difficulty, uNonce);
        this.withZeroByte = withZeroByte;
    }

    public BlockHeader(BlockVersion blockVersion, ByteBuf previousBlockHash, ByteBuf mercleRoot, OffsetDateTime date, Difficulty difficulty, int uNonce) {
        this.blockVersion = blockVersion;
        this.previousBlockHash = previousBlockHash;
        this.mercleRoot = mercleRoot;
        this.date = date;
        this.difficulty = difficulty;
        this.uNonce = uNonce;
        this.withZeroByte = true;
    }

    public BlockVersion getBlockVersion() {
        return blockVersion;
    }

    public ByteBuf getPreviousBlockHash() {
        return previousBlockHash;
    }

    public ByteBuf getMercleRoot() {
        return mercleRoot;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getuNonce() {
        return uNonce;
    }

    public ByteBuf hash() {
        ByteBuf buffer = Unpooled.buffer();
        write(buffer);
        return buffer.doubleSha256();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        blockVersion.write(byteBuf);
        byteBuf.writeBytes(previousBlockHash);
        byteBuf.writeBytes(mercleRoot);
        new Timestamp4(date).write(byteBuf);
        difficulty.write(byteBuf);
        byteBuf.writeIntLE(uNonce);
        if (withZeroByte) {
            byteBuf.writeBytes(ZERO_BYTE);
        }
    }

    private Timestamp4 currentDate() {
        return new Timestamp4();
    }
}
