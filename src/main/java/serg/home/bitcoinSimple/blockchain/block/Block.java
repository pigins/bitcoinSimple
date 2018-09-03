package serg.home.bitcoinSimple.blockchain.block;

import serg.home.bitcoinSimple.blockchain.block.transaction.Transaction;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.messages.Payload;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import java.util.ArrayList;
import java.util.List;

public class Block implements Payload {
    public static final String NAME = "block";

    protected BlockHeader blockHeader;
    protected List<Transaction> transactions;

    public Block(Bytes raw) {
        decode(new ByteReader(raw));
    }

    public Block(BlockHeader blockHeader, List<Transaction> transactions) {
        this.blockHeader = blockHeader;
        this.transactions = transactions;
    }

    public Bytes headerHash() {
        return blockHeader.encode().doubleSha256();
    }

    @Override
    public String name() {
        return null;
    }

    public BlockHeader header() {
        return blockHeader;
    }

    public Bytes mercleRoot() {
        List<Bytes> firstIteration = new ArrayList<>((transactions.size() + 1) / 2);
        if (transactions.size() % 2 != 0) {
            for (int i = 0; i < transactions.size() - 1; i += 2) {
                firstIteration.add(transactions.get(i).hash()
                        .concat(transactions.get(i + 1).hash())
                        .doubleSha256());
            }
            Bytes lastHash = transactions.get(transactions.size() - 1).hash();
            firstIteration.add(lastHash.concat(lastHash).doubleSha256());
        } else {
            for (int i = 0; i < transactions.size(); i += 2) {
                firstIteration.add(transactions.get(i).hash()
                        .concat(transactions.get(i + 1).hash())
                        .doubleSha256());
            }
        }

        List<Bytes> previous = firstIteration;
        List<Bytes> next;
        while (previous.size() != 1) {
            next = new ArrayList<>(previous.size() / 2);
            for (int i = 0; i < previous.size(); i += 2) {
                next.add(previous.get(i).concat(previous.get(i + 1)).doubleSha256());
            }
            previous = next;
        }
        return previous.get(0);
    }

    @Override
    public void decode(ByteReader byteReader) {
        this.blockHeader = new BlockHeader(byteReader);
        int txCount = byteReader.nextVarInt().toInt();
        transactions = new ArrayList<>(txCount);
        for (int i = 0; i < txCount; i++) {
            transactions.add(new Transaction(byteReader));
        }
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary().add(blockHeader).add(new VarInt(transactions.size()));
        transactions.forEach((compoundBinary::add));
        return compoundBinary.encode();
    }


}
