package serg.home.bitcoinSimple.common.binary;

import serg.home.bitcoinSimple.blockchain.block.transaction.input.CoinbaseInput;
import serg.home.bitcoinSimple.blockchain.block.transaction.input.RegularInput;
import serg.home.bitcoinSimple.blockchain.block.transaction.output.Output;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.network.model.VarString;
import serg.home.bitcoinSimple.network.model.Services;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteReader {
    private int readCount = 0;
    private Bytes bytes;

    public ByteReader(Bytes bytes) {
        this.bytes = bytes;
    }

    public Bytes next(int size) {
        Bytes result = this.bytes.subArray(readCount, readCount + size);
        readCount+=size;
        return result;
    }

    public byte nextByte() {
        return bytes.byteArray()[readCount++];
    }

//    public VarInt nextVarInt() {
//        return new VarInt(this);
//    }

//    public CoinbaseInput nextCoinbaseInput() {
//        return new CoinbaseInput(this);
//    }

//    public RegularInput nextRegularInput() {
//        return new RegularInput(this);
//    }

//    public Output nextOutput() {
//        return new Output(this);
//    }

    public boolean hasNext() {
        return readCount < bytes.length();
    }

    public short nextShort() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        for (int i = readCount; i < readCount + 2; i++) {
            byteBuffer.put(bytes.byteArray()[i]);
        }
        byteBuffer.rewind();
        readCount+=2;
        return byteBuffer.getShort();
    }

    public int nextInt() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        for (int i = readCount; i < readCount + 4; i++) {
            byteBuffer.put(bytes.byteArray()[i]);
        }
        byteBuffer.rewind();
        readCount+=4;
        return byteBuffer.getInt();
    }

    public int nextIntLE() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = readCount; i < readCount + 4; i++) {
            byteBuffer.put(bytes.byteArray()[i]);
        }
        readCount+=4;
        byteBuffer.rewind();
        return byteBuffer.getInt();
    }


    public long nextLongLE() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = readCount; i < readCount + 8; i++) {
            byteBuffer.put(bytes.byteArray()[i]);
        }
        readCount+=8;
        byteBuffer.rewind();
        return byteBuffer.getLong();
    }

//    public Services nextServices() {
//        return new Services(this);
//    }

//    public VarString nextVarString() {
//        return new VarString(this);
//    }

    public long nextLong() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (int i = readCount; i < readCount + 8; i++) {
            byteBuffer.put(bytes.byteArray()[i]);
        }
        readCount+=8;
        byteBuffer.rewind();
        return byteBuffer.getLong();
    }
}
