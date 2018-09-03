package serg.home.bitcoinSimple.blockchain.block.transaction;

public enum TxVersion {
    V1(1), V2(2);
    public final int version;
    TxVersion(int version) {
        this.version = version;
    }
    public int version() {
        return version;
    }
}
