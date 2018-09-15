package serg.home.bitcoinSimple.common.binary;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.Bytes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CompoundBinary implements BinaryEncoded {
    private List<BinaryEncoded> bytesList = new ArrayList<>();

    public CompoundBinary add(BinaryEncoded bytes) {
        bytesList.add(bytes);
        return this;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        bytesList.forEach(binaryEncoded -> binaryEncoded.write(byteBuf));
    }
}
