package serg.home.bitcoinSimple.blockchain.block.transaction.script;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.util.ArrayList;
import java.util.List;

public class  Script implements ByteBufWritable {
    public static Script read(ByteBuf byteBuf) {
        List<Object> items = new ArrayList<>();
        int scriptLength = (int) VarInt.read(byteBuf);
        for (int i = 0; i < scriptLength; i++) {
            byte b = byteBuf.readByte();
            if (b >= 1 && b <= 75) {
                items.add(byteBuf.readBytes(b));
                i += b;
            } else {
                items.add(OP.from(b));
            }
        }
        return new Script(items);
    }

    public static Script p2pkh(ByteBuf publicKeyHash) {
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

    public Script __(ByteBuf data) {
        // TODO
        throw new UnsupportedOperationException("not implemented");
//        if (data.length() <= 75) {
//            items.add((byte)data.length());
//            items.add(data);
//        } else {
//            throw new UnsupportedOperationException();
//        }
//        return this;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        List<Byte> result = new ArrayList<>();
        items.forEach((item) -> {
            if (item instanceof OP) {
                result.add(((OP)item).getCode());
            } else if (item instanceof ByteBuf){
                for (byte aByte : ((ByteBuf) item).array()) {
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
        varInt.write(byteBuf);
        byteBuf.writeBytes(res);
    }

    @Override
    public String toString() {
        return "Script{" +
                "items=" + items +
                '}';
    }
}
