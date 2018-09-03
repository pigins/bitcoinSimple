package serg.home.bitcoinSimple.blockchain.block.transaction.output;

import serg.home.bitcoinSimple.blockchain.block.transaction.script.Script;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class Output implements BinaryEncoded {
    private Value value;
    /**
     * a cryptographic puzzle that determines the conditions required to spend the output(locking script)
     */
    private Script scriptPubKey;

    public Output(Value value, Script scriptPubKey) {
        this.value = value;
        this.scriptPubKey = scriptPubKey;
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary();
        compoundBinary.add(value);
        compoundBinary.add(scriptPubKey);
        return compoundBinary.encode();
    }

    @Override
    public String toString() {
        return "Output{" +
                "value=" + value +
                ", scriptPubKey=" + scriptPubKey +
                '}';
    }
}
