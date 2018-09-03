package serg.home.bitcoinSimple.blockchain.block.transaction;

import serg.home.bitcoinSimple.blockchain.block.transaction.input.Input;
import serg.home.bitcoinSimple.blockchain.block.transaction.input.CoinbaseInput;
import serg.home.bitcoinSimple.blockchain.block.transaction.input.RegularInput;
import serg.home.bitcoinSimple.blockchain.block.transaction.output.Output;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import java.util.ArrayList;
import java.util.List;

public class Transaction implements BinaryEncoded, BinaryDecoded {
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

    public Transaction(ByteReader byteReader) {
        decode(byteReader);
    }

    public Bytes hash() {
        return encode().doubleSha256();
    }

    @Override
    public void decode(ByteReader byteReader) {
        this.version = TxVersion.decode(byteReader);
        int inputsCount = byteReader.nextVarInt().toInt();
        inputs = new ArrayList<>(inputsCount);
        CoinbaseInput coinbaseInput = byteReader.nextCoinbaseInput();
        inputs.add(coinbaseInput);
        for (int i = 0; i < inputsCount - 1; i++) {
            RegularInput regularInput = byteReader.nextRegularInput();
            inputs.add(regularInput);
        }
        int outputsCount = byteReader.nextVarInt().toInt();
        outputs = new ArrayList<>(outputsCount);
        for (int i = 0; i < inputsCount; i++) {
            outputs.add(byteReader.nextOutput());
        }
        this.uLockTime = byteReader.nextInt();
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
}
