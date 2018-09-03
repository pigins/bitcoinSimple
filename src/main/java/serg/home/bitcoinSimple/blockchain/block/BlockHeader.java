package serg.home.bitcoinSimple.blockchain.block;

import serg.home.bitcoinSimple.network.model.Timestamp4;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class BlockHeader implements BinaryEncoded, BinaryDecoded {
    private static Bytes ZERO_BYTE = new Bytes("00");
    private BlockVersion blockVersion;
    private Bytes previousBlockHash;
    private Bytes mercleRoot;
    private Timestamp4 date;
    private Difficulty difficulty;
    private int uNonce;

    public BlockHeader(BlockVersion blockVersion, Bytes previousBlockHash, Bytes mercleRoot, Timestamp4 date, Difficulty difficulty, int uNonce) {
        this.blockVersion = blockVersion;
        this.previousBlockHash = previousBlockHash;
        this.mercleRoot = mercleRoot;
        this.date = date;
        this.difficulty = difficulty;
        this.uNonce = uNonce;
    }

    public BlockHeader(ByteReader byteReader) {
        decode(byteReader);
    }

    public BlockVersion getBlockVersion() {
        return blockVersion;
    }

    public Bytes getPreviousBlockHash() {
        return previousBlockHash;
    }

    public Bytes getMercleRoot() {
        return mercleRoot;
    }

    public Timestamp4 getDate() {
        return date;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getuNonce() {
        return uNonce;
    }

    public Bytes hash() {
        return encode().doubleSha256();
    }

    @Override
    public void decode(ByteReader byteReader) {
        this.blockVersion = BlockVersion.decode(byteReader);
        this.previousBlockHash = byteReader.next(32);
        this.mercleRoot = byteReader.next(32).flip();
        this.date = new Timestamp4(byteReader);
        this.difficulty = new Difficulty(byteReader);
        this.uNonce = byteReader.nextIntLE();
        var txCount = byteReader.nextByte();
        if (txCount != 0) {
            throw new IllegalArgumentException("tx count should be always zero");
        }
    }

    @Override
    public Bytes encode() {

        return new CompoundBinary()
                .add(blockVersion)
                .add(previousBlockHash)
                .add(mercleRoot.flip())
                .add(date)
                .add(difficulty)
                .add(Bytes.fromIntLE(uNonce))
                .add(ZERO_BYTE)
                .encode();
    }

    private Timestamp4 currentDate() {
        return new Timestamp4();
    }

    @Override
    public String toString() {
        return "BlockHeader{" +
                "blockVersion=" + blockVersion +
                ", previousBlockHash=" + previousBlockHash +
                ", mercleRoot=" + mercleRoot +
                ", date=" + date +
                ", difficulty=" + difficulty +
                ", uNonce=" + uNonce +
                '}';
    }
}
