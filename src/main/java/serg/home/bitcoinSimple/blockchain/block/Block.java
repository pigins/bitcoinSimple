package serg.home.bitcoinSimple.blockchain.block;

import serg.home.bitcoinSimple.blockchain.block.transaction.Transaction;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.messages.Payload;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Block implements Payload {
    public static final String NAME = "block";

    private BlockHeader blockHeader;
    private List<Transaction> transactions;

    public Block(BlockHeader blockHeader, List<Transaction> transactions) {
        this.blockHeader = blockHeader;
        this.transactions = transactions;
        System.out.println(blockHeader.getMercleRoot());
        System.out.println(mercleRoot());
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

    private Bytes mercleRoot() {
        List<Bytes> previous = transactions.stream().map(Transaction::hash).collect(Collectors.toList());
        List<Bytes> next;
        while (previous.size() != 1) {
            next = new ArrayList<>();
            if (previous.size() % 2 != 0) {
                for (int i = 0; i < previous.size() - 1; i += 2) {
                    next.add(previous.get(i).concat(previous.get(i + 1)).doubleSha256());
                }
                Bytes lastHash = previous.get(previous.size() - 1);
                next.add(lastHash.concat(lastHash).doubleSha256());
            } else {
                for (int i = 0; i < previous.size(); i += 2) {
                    next.add(previous.get(i).concat(previous.get(i + 1)).doubleSha256());
                }
            }
            previous = next;
        }
        return previous.get(0);
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary().add(blockHeader).add(new VarInt(transactions.size()));
        transactions.forEach((compoundBinary::add));
        return compoundBinary.encode();
    }

    @Override
    public String toString() {
        return "Block{" +
                "blockHeader=" + blockHeader +
                ", transactions=" + transactions +
                '}';
    }
}
