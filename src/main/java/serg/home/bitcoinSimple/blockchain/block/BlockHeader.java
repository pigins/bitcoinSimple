package serg.home.bitcoinSimple.blockchain.block;

import serg.home.bitcoinSimple.network.model.Timestamp4;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class BlockHeader implements BinaryEncoded {
    private static Bytes ZERO_BYTE = new Bytes("00");
    private BlockVersion blockVersion;
    private Bytes previousBlockHash;
    private Bytes mercleRoot;
    private Timestamp4 date;
    private Difficulty difficulty;
    private int uNonce;
    private boolean withZeroByte;

    public BlockHeader(BlockVersion blockVersion, Bytes previousBlockHash, Bytes mercleRoot, Timestamp4 date, Difficulty difficulty, int uNonce, boolean withZeroByte) {
        this(blockVersion, previousBlockHash, mercleRoot, date, difficulty, uNonce);
        this.withZeroByte = withZeroByte;
    }

    public BlockHeader(BlockVersion blockVersion, Bytes previousBlockHash, Bytes mercleRoot, Timestamp4 date, Difficulty difficulty, int uNonce) {
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
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary();
        compoundBinary
                .add(blockVersion)
                .add(previousBlockHash)
                .add(mercleRoot.flip())
                .add(date)
                .add(difficulty)
                .add(Bytes.fromIntLE(uNonce));
        if (withZeroByte) {
            compoundBinary.add(ZERO_BYTE);
        }
        return compoundBinary.encode();
    }

    private Timestamp4 currentDate() {
        return new Timestamp4();
    }

    @Override
    public String toString() {
        return "BlockHeader{" +
                "blockVersion=" + blockVersion +
                ", previousBlockHash=" + previousBlockHash.flip().getHexString().toLowerCase() +
                ", mercleRoot=" + mercleRoot +
                ", date=" + date +
                ", difficulty=" + difficulty +
                ", uNonce=" + uNonce +
                ", withZeroByte=" + withZeroByte +
                '}';
    }
}
