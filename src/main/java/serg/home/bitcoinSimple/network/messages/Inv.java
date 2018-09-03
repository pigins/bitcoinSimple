package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.InvVector;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import java.util.ArrayList;
import java.util.List;

public class Inv implements Payload {
    public static final String NAME = "inv";

    private List<InvVector> invVectors;

    public Inv(Bytes bytes) {
        decode(new ByteReader(bytes));
    }

    public Inv(List<InvVector> invVectors) {
        this.invVectors = invVectors;
    }

    public List<InvVector> invVectors() {
        return invVectors;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void decode(ByteReader byteReader) {
        int count = byteReader.nextVarInt().toInt();
        invVectors = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            invVectors.add(new InvVector(byteReader));
        }
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary();
        compoundBinary.add(new VarInt(invVectors.size()));
        invVectors.forEach(compoundBinary::add);
        return compoundBinary.encode();
    }

    @Override
    public String toString() {
        return "Inv{" +
                "invVectors=" + invVectors +
                '}';
    }
}


