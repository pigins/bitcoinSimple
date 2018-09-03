package serg.home.bitcoinSimple.blockchain.block.transaction.input;

import serg.home.bitcoinSimple.blockchain.block.transaction.script.Script;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

public class RegularInput extends Input {
    private Script unlockingScript;

    public RegularInput(OutputLink outputLink, Script unlockingScript, int uSequence) {
        this.outputLink = outputLink;
        this.unlockingScript = unlockingScript;
        this.uSequence = uSequence;
    }

    public RegularInput(ByteReader byteReader) {
        decode(byteReader);
    }

    @Override
    public void decode(ByteReader byteReader) {
        this.outputLink = new OutputLink(byteReader);
        this.unlockingScript = new Script(byteReader);
        this.uSequence = byteReader.nextInt();
    }

    @Override
    public Bytes encode() {
        return new CompoundBinary().add(outputLink).add(unlockingScript).add(Bytes.fromInt(uSequence)).encode();
    }
}
