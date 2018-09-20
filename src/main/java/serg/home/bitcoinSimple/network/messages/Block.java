package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.network.model.block.BlockHeader;
import serg.home.bitcoinSimple.network.model.block.transaction.Transaction;
import serg.home.bitcoinSimple.network.model.VarInt;

import java.util.ArrayList;
import java.util.List;

public class Block implements Payload {
    public static final String NAME = "block";
    public static Block read(ByteBuf byteBuf) {
        BlockHeader blockHeader = BlockHeader.read(byteBuf);
        int txCount = (int)VarInt.read(byteBuf);
        List<Transaction> transactions = new ArrayList<>(txCount);
        transactions.add(Transaction.readCoinbaseTransaction(byteBuf));
        for (int i = 0; i < txCount - 1; i++) {
            Transaction e = Transaction.read(byteBuf);
            transactions.add(e);
        }
        return new Block(blockHeader, transactions);
    }

    private BlockHeader blockHeader;
    private List<Transaction> transactions;

    public Block(BlockHeader blockHeader, List<Transaction> transactions) {
        this.blockHeader = blockHeader;
        this.transactions = transactions;
    }

    public ByteBuf headerHash() {
        throw new UnsupportedOperationException("not implemented");
//        return blockHeader.encode().doubleSha256();
    }

    @Override
    public String name() {
        return null;
    }

    public BlockHeader header() {
        return blockHeader;
    }

    private ByteBuf mercleRoot() {
        throw new UnsupportedOperationException("not implemented");
//        List<ByteBuf> previous = transactions.stream().map(Transaction::hash).collect(Collectors.toList());
//        List<ByteBuf> next;
//        while (previous.size() != 1) {
//            next = new ArrayList<>();
//            if (previous.size() % 2 != 0) {
//                for (int i = 0; i < previous.size() - 1; i += 2) {
//                    next.add(previous.get(i).concat(previous.get(i + 1)).doubleSha256());
//                }
//                ByteBuf lastHash = previous.get(previous.size() - 1);
//                next.add(lastHash.concat(lastHash).doubleSha256());
//            } else {
//                for (int i = 0; i < previous.size(); i += 2) {
//                    next.add(previous.get(i).concat(previous.get(i + 1)).doubleSha256());
//                }
//            }
//            previous = next;
//        }
//        return previous.get(0);
    }

    @Override
    public void write(ByteBuf byteBuf) {
        blockHeader.write(byteBuf);
        new VarInt(transactions.size()).write(byteBuf);
        transactions.forEach(transaction -> transaction.write(byteBuf));
    }

    @Override
    public String toString() {
        return "Block{" +
                "blockHeader=" + blockHeader +
                ", transactions=" + transactions +
                '}';
    }
}
