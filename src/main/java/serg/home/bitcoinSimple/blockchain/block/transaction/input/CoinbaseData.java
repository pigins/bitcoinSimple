package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import javax.annotation.Nullable;
import java.util.Objects;

public class CoinbaseData implements BinaryEncoded {
    public static final int MIN_SIZE = 2;
    public static final int MAX_SIZE = 100;

    private Integer blockHeight;
    private Bytes bytes;

    public CoinbaseData(@Nullable Integer blockHeight, Bytes bytes) {
        this.blockHeight = blockHeight;
        this.bytes = bytes;
        Objects.requireNonNull(bytes);
        if (blockHeight == null) {
            if (bytes.length() < MIN_SIZE || bytes.length() > MAX_SIZE) {
                throw new IllegalArgumentException();
            }
        } else {
            if (bytes.length() < MIN_SIZE || bytes.length() > MAX_SIZE - 4) {
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary();
        if (blockHeight == null) {
            return compoundBinary
                    .add(new VarInt(bytes.length()))
                    .add(bytes)
                    .encode();
        } else {
            return compoundBinary
                    .add(new VarInt(bytes.length() + 4))
                    .add(Bytes.fromInt(blockHeight))
                    .add(bytes)
                    .encode();
        }
    }

    @Override
    public String toString() {
        return "CoinbaseData{" +
                "blockHeight=" + blockHeight +
                ", bytes=" + bytes +
                '}';
    }
}
