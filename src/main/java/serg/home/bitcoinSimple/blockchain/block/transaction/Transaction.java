package serg.home.bitcoinSimple.blockchain.block.transaction;

import serg.home.bitcoinSimple.blockchain.block.transaction.input.Input;
import serg.home.bitcoinSimple.blockchain.block.transaction.output.Output;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import java.util.List;

public class Transaction implements BinaryEncoded {
    private TxVersion version;
    private int uLockTime;
    private List<Input> inputs;
    private List<Output> outputs;

    public Transaction(TxVersion version, int uLockTime, List<Input> inputs, List<Output> outputs) {
        this.version = version;
        this.uLockTime = uLockTime;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public Bytes hash() {
        return encode().doubleSha256();
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary();

        Bytes versionBytes = Bytes.fromIntLE(version.version());
        compoundBinary.add(versionBytes);

        compoundBinary.add(new VarInt(inputs.size()));
        inputs.forEach(compoundBinary::add);

        compoundBinary.add(new VarInt(outputs.size()));
        outputs.forEach(compoundBinary::add);

        Bytes lockTimeBytes = Bytes.fromInt(uLockTime);
        compoundBinary.add(lockTimeBytes);

        return compoundBinary.encode();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "version=" + version +
                ", uLockTime=" + uLockTime +
                ", inputs=" + inputs +
                ", outputs=" + outputs +
                '}';
    }
}
