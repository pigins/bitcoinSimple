package serg.home.bitcoinSimple.network.messages;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.TimestampWithAddress;
import serg.home.bitcoinSimple.network.model.IpAddress;
import serg.home.bitcoinSimple.network.model.NetAddress;
import serg.home.bitcoinSimple.network.model.Service;
import serg.home.bitcoinSimple.network.model.Services;
import serg.home.bitcoinSimple.protocol.BtcMessage;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddrTest {
    private static String ADDR_PAYLOAD =
                     "01" // 1 address in this message
                    +"E215104D" // Mon Dec 20 21:50:10 EST 2010 (only when version is >= 31402)
                    +"0100000000000000" // 1 (NODE_NETWORK service - see version message)
                    +"00000000000000000000FFFF0A000001" // IPv4: 10.0.0.1, IPv6: ::ffff:10.0.0.1 (IPv4-mapped IPv6 address)
                    +"208D"; // port 8333


    @Test
    void decode() {
        Addr addr = new BtcMessage(new Bytes(ADDR_PAYLOAD)).nextAddr();
        assertEquals(1, addr.getAddrList().size());
        assertEquals("2010-12-21T02:50:10Z", addr.getAddrList().get(0).getTimestamp().toString());
        assertEquals("10.0.0.1", addr.getAddrList().get(0).getAddress().ipAddress().getIpString());
        assertEquals(8333, addr.getAddrList().get(0).getAddress().port());
    }

    @Test
    void encode() {
        List<TimestampWithAddress> list = new ArrayList<>();
        NetAddress netAddress = new NetAddress(new Services(Service.NODE_NETWORK), new IpAddress("10.0.0.1"), 8333);
        TimestampWithAddress res = new TimestampWithAddress(OffsetDateTime.parse("2010-12-21T02:50:10Z"), netAddress);
        list.add(res);
        Addr addr = new Addr(list);
        assertEquals(ADDR_PAYLOAD, addr.encode().getHexString());
    }
}