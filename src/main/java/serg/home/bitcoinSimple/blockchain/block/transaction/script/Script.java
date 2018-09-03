package serg.home.bitcoinSimple.blockchain.block.transaction.script;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

import java.util.ArrayList;
import java.util.List;

public class Script implements BinaryEncoded {
    public static Script p2pkh(Bytes publicKeyHash) {
        return new Script().__(OP.DUP).__(OP.HASH160).__(publicKeyHash).__(OP.EQUALVERIFY).__(OP.CHECKSIG);
    }

    private List<Object> items = new ArrayList<>();

    public Script() {
    }
    // TODO
    public Script(List<Object> items) {
        this.items = items;
    }

    public Script __(OP op) {
        items.add(op);
        return this;
    }

    public Script __(Bytes data) {
        if (data.length() <= 75) {
            items.add((byte)data.length());
            items.add(data);
        } else {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    @Override
    public Bytes encode() {
        List<Byte> result = new ArrayList<>();
        items.forEach((item) -> {
            if (item instanceof OP) {
                result.add(((OP)item).getCode());
            } else if (item instanceof Bytes){
                for (byte aByte : ((Bytes) item).byteArray()) {
                    result.add(aByte);
                }
            } else if (item instanceof Byte) {
                result.add((Byte) item);
            }
        });
        byte[] res = new byte[result.size()];
        for(int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }
        VarInt varInt = new VarInt(res.length);
        return varInt.encode().concat(new Bytes(res));
    }

    @Override
    public String toString() {
        return "Script{" +
                "items=" + items +
                '}';
    }
}
