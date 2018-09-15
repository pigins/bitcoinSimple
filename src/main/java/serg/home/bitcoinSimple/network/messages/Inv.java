package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.network.model.InvVector;
import serg.home.bitcoinSimple.network.model.VarInt;

import java.util.ArrayList;
import java.util.List;

public class Inv implements Payload {
    public static final String NAME = "inv";
    public static Inv read(ByteBuf byteBuf) {
        int count = (int)VarInt.read(byteBuf);
        List<InvVector> invVectors = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            invVectors.add(InvVector.read(byteBuf));
        }
        return new Inv(invVectors);
    }
    private List<InvVector> invVectors;

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
    public void write(ByteBuf byteBuf) {
        new VarInt(invVectors.size()).write(byteBuf);
        invVectors.forEach(invVector -> invVector.write(byteBuf));
    }

    @Override
    public String toString() {
        return "Inv{" +
                "invVectors=" + invVectors +
                '}';
    }
}


