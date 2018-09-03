package serg.home.bitcoinSimple.blockchain.block.transaction.output;

import serg.home.bitcoinSimple.blockchain.block.transaction.script.Script;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class Output implements BinaryEncoded, BinaryDecoded {
    private Value value;
    /**
     * a cryptographic puzzle that determines the conditions required to spend the output(locking script)
     */
    private Script scriptPubKey;

    public Output(Value value, Script scriptPubKey) {
        this.value = value;
        this.scriptPubKey = scriptPubKey;
    }

    public Output(ByteReader byteReader) {
        decode(byteReader);
    }

    @Override
    public void decode(ByteReader byteReader) {
        value = new Value(byteReader);
        scriptPubKey = new Script(byteReader);
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary();
        compoundBinary.add(value);
        compoundBinary.add(scriptPubKey);
        return compoundBinary.encode();
    }
}
