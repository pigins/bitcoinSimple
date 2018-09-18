package serg.home.bitcoinSimple.blockchain.block.transaction;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.blockchain.block.transaction.input.CoinbaseInput;
import serg.home.bitcoinSimple.blockchain.block.transaction.input.Input;
import serg.home.bitcoinSimple.blockchain.block.transaction.input.RegularInput;
import serg.home.bitcoinSimple.blockchain.block.transaction.output.Output;
import serg.home.bitcoinSimple.common.ByteBufWritable;
import serg.home.bitcoinSimple.network.model.VarInt;

import java.util.ArrayList;
import java.util.List;

public class Transaction implements ByteBufWritable {
    public static Transaction read(ByteBuf byteBuf) {
        TxVersion version = TxVersion.read(byteBuf);
        int inputsCount = (int) VarInt.read(byteBuf);
        List<Input> inputs = new ArrayList<>(inputsCount);
        for (int i = 0; i < inputsCount; i++) {
            RegularInput regularInput = RegularInput.read(byteBuf);
            inputs.add(regularInput);
        }
        int outputsCount = (int) VarInt.read(byteBuf);
        List<Output> outputs = new ArrayList<>(outputsCount);
        for (int i = 0; i < outputsCount; i++) {
            outputs.add(Output.read(byteBuf));
        }
        int uLockTime = byteBuf.readInt();
        return new Transaction(version, uLockTime, inputs, outputs);
    }

    public static Transaction readCoinbaseTransaction(ByteBuf byteBuf) {
        TxVersion version = TxVersion.read(byteBuf);
        int inputsCount = (int) VarInt.read(byteBuf);
        List<Input> inputs = new ArrayList<>(inputsCount);
        CoinbaseInput coinbaseInput = CoinbaseInput.read(byteBuf);
        inputs.add(coinbaseInput);
        int outputsCount = (int) VarInt.read(byteBuf);
        List<Output> outputs = new ArrayList<>(outputsCount);
        for (int i = 0; i < inputsCount; i++) {
            outputs.add(Output.read(byteBuf));
        }
        int uLockTime = byteBuf.readInt();
        return new Transaction(version, uLockTime, inputs, outputs);
    }

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

    public ByteBuf hash() {
        throw new UnsupportedOperationException();
//        return encode().doubleSha256();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeIntLE(version.version());
        new VarInt(inputs.size()).write(byteBuf);
        inputs.forEach(input -> input.write(byteBuf));
        new VarInt(outputs.size()).write(byteBuf);
        outputs.forEach(output -> output.write(byteBuf));
        byteBuf.writeInt(uLockTime);
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
