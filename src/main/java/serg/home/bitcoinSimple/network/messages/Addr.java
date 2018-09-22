package serg.home.bitcoinSimple.network.messages;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.network.model.NetAddress;
import serg.home.bitcoinSimple.network.model.Timestamp4;
import serg.home.bitcoinSimple.network.model.TimestampWithAddress;
import serg.home.bitcoinSimple.network.model.VarInt;
import serg.home.bitcoinSimple.network.exceptions.AddressCountTooLarge;

import java.util.ArrayList;
import java.util.List;

public class Addr implements Payload {
    public static Addr read(ByteBuf byteBuf) {
        int count = (int) VarInt.read(byteBuf);
        List<TimestampWithAddress> addrList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            addrList.add(
                    new TimestampWithAddress(
                            Timestamp4.read(byteBuf),
                            NetAddress.read(byteBuf)
                    )
            );
        }
        return new Addr(addrList);
    }

    public static final String NAME = "addr";
    private static final int MAX_ADDRESSES = 1000;

    private List<TimestampWithAddress> addrList;

    public Addr(List<TimestampWithAddress> addrList) {
        checkSize(addrList.size());
        this.addrList = addrList;
    }

    public List<TimestampWithAddress> getAddrList() {
        return addrList;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        new VarInt(addrList.size()).write(byteBuf);
        for (TimestampWithAddress timestampWithAddress : addrList) {
            new Timestamp4(timestampWithAddress.timestamp()).write(byteBuf);
            timestampWithAddress.address().write(byteBuf);
        }
    }

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
