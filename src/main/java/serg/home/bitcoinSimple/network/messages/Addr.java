package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.Timestamp4;
import serg.home.bitcoinSimple.network.model.TimestampWithAddress;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;
import serg.home.bitcoinSimple.network.exceptions.AddressCountTooLarge;
import serg.home.bitcoinSimple.network.model.NetAddress;

import java.util.ArrayList;
import java.util.List;

public class Addr implements Payload {
    public static final String NAME = "addr";
    private static final int MAX_ADDRESSES = 1000;

    private List<TimestampWithAddress> addrList;

    public Addr(List<TimestampWithAddress> addrList) {
        checkSize(addrList.size());
        this.addrList = addrList;
    }

    public Addr(Bytes bytes) {
        decode(new ByteReader(bytes));
    }

    public List<TimestampWithAddress> getAddrList() {
        return addrList;
    }

    @Override
    public void decode(ByteReader byteReader) {
        int count = byteReader.nextVarInt().toInt();
        checkSize(count);
        addrList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            addrList.add(
                    new TimestampWithAddress(
                            new Timestamp4(byteReader).getValue(),
                            new NetAddress(byteReader)
                    )
            );
        }
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary();
        compoundBinary.add(new VarInt(addrList.size()));
        for (TimestampWithAddress timestampWithAddress: addrList) {
            compoundBinary.add(new Timestamp4(timestampWithAddress.getTimestamp()));
            compoundBinary.add(timestampWithAddress.getAddress());
        }
        return compoundBinary.encode();
    }

    @Override
    public String name() {
        return NAME;
    }

    private void checkSize(int size) {
        if (size > MAX_ADDRESSES) {
            throw new AddressCountTooLarge(MAX_ADDRESSES);
        }
    }

    @Override
    public String toString() {
        return "Addr{" +
                "addrList=" + addrList +
                '}';
    }

}
