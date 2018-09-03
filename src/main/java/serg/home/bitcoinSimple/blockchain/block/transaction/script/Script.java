package serg.home.bitcoinSimple.blockchain.block.transaction.script;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;

import java.util.ArrayList;
import java.util.List;

public class Script implements BinaryEncoded, BinaryDecoded {
    private List<Object> items = new ArrayList<>();

    public Script() {
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

    public Script(ByteReader byteReader) {
        decode(byteReader);
    }

    @Override
    public void decode(ByteReader byteReader) {
        int scriptLength = byteReader.nextVarInt().toInt();
        for (int i = 0; i < scriptLength; i++) {
            byte b = byteReader.nextByte();
            if (b >= 1 && b <= 75) {
                items.add(byteReader.next(b));
                i+=b;
            } else {
                items.add(OP.from(b));
            }
        }
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
}
