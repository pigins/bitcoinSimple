package serg.home.bitcoinSimple.blockchain;

import serg.home.bitcoinSimple.blockchain.block.Block;
import serg.home.bitcoinSimple.blockchain.block.BlockHeader;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.database.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LocalBlockchain {
    private final Database database;
    private final Block genesis;

    private CopyOnWriteArrayList<BlockHeader> headersChain;

    public LocalBlockchain(Database database, Block genesis) {
        this.database = database;
        this.genesis = genesis;
        this.headersChain = new CopyOnWriteArrayList<>();
        this.headersChain.add(genesis.header());
    }

    public int height() {
        return headersChain.size();
    }

    public void addHeaders(List<BlockHeader> blockHeaders) {
        // headers chain should be validated with consensus rules
        // https://bitcoin.org/en/developer-guide#block-chain-overview
        headersChain.addAll(blockHeaders);
    }

    private List<Integer> locatorIndexes(int topHeight) {
        List<Integer> indexes = new ArrayList<>();
        int step = 1;
        for (int index = topHeight; index > 0; index -= step) {
            if (indexes.size() >= 10) {
                step *= 2;
            }
            indexes.add(index);
        }
        indexes.add(0);
        System.out.println(indexes);
        return indexes;
    }

    public List<Bytes> locator() {
        List<Integer> indexes = locatorIndexes(headersChain.size() - 1);
        System.out.println(headersChain.size());
        List<Bytes> result = new ArrayList<>();
        for (Integer index : indexes) {
            result.add(headersChain.get(index).hash());
        }
        System.out.println(result);
        return result;
    }
}