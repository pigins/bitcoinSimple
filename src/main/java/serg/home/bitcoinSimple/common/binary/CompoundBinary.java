package serg.home.bitcoinSimple.common.binary;

import serg.home.bitcoinSimple.common.Bytes;

import java.util.ArrayList;
import java.util.List;

public class CompoundBinary implements BinaryEncoded {
    private List<BinaryEncoded> bytesList = new ArrayList<>();

    public CompoundBinary add(BinaryEncoded bytes) {
        bytesList.add(bytes);
        return this;
    }

    @Override
    public Bytes encode() {
        Bytes[] arrays = bytesList.stream().map(BinaryEncoded::encode).toArray(Bytes[]::new);
        return Bytes.concat(arrays);
    }
}
