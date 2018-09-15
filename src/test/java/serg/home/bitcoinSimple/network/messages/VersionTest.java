package serg.home.bitcoinSimple.network.messages;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.NetAddress;
import serg.home.bitcoinSimple.network.model.Timestamp8;
import serg.home.bitcoinSimple.network.model.VarString;
import serg.home.bitcoinSimple.network.model.*;
import serg.home.bitcoinSimple.protocol.BtcMessage;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {
    private static final String VERSION_PAYLOAD =
                    "72110100"
                    +"0100000000000000"
                    +"BC8F5E5400000000"
                    +"0100000000000000"
                    +"00000000000000000000FFFFC61B6409"
                    +"208D"
                    +"0100000000000000"
                    +"00000000000000000000FFFFCB0071C0"
                    +"208D"
                    +"128035CBC97953F8"
                    +"0F"
                    +"2F5361746F7368693A302E392E332F"
                    +"CF050500"
                    +"01";

    private static final String VERSION_MESSAGE1 = "FABFB5DA76657273696F6E000000000066000000E2EFD31F7F1101000D04000000000000FD514B5B0000000000000000000000000000000000000000000000000000000000000D04000000000000000000000000000000000000000000000000F92522BC2A1DA055102F5361746F7368693A302E31362E312F0000000001";
    private static final String VERSION_MESSAGE2 = "fabfb5da76657273696f6e0000000000660000001af963817f1101000d04000000000000fd514b5b0000000000000000000000000000000000000000000000000000000000000d0400000000000000000000000000000000000000000000000089c99cb1af502a15102f5361746f7368693a302e31362e312f0000000001";
    private static final String mine1 =            "d9b4bef976657273696f6e00000000000000006564cc527f11010001000000000000005c254e5b00000000000000000000000000000000000000000000ffff7f0000010000010000000000000000000000000000000000ffff7f0000010000128035cbc97953f80f2f5361746f7368693a302e392e332fcf05050001";

    private static String TESTNET_PAYLOAD = "7f1101000d04000000000000a2ae5d5b00000000090000000000000000000000000000000000ffff330f514d479d0d040000000000000000000000000000000000000000000000003b6977c3c1476b86102f5361746f7368693a302e31362e312f0000000001";
    private static String APP_PAYLOAD =     "7B1101000D04000000000000F5B85D5B00000000090000000000000000000000000000000000FFFF330F514D479D0D04000000000000000000000000000000000000000000000000185202DFC6F367D9102F5361746F7368693A302E31362E312F0000000001";

    @Test
    void compare() {
        System.out.println(new BtcMessage(new Bytes(TESTNET_PAYLOAD)).version());
        System.out.println(new BtcMessage(new Bytes(APP_PAYLOAD)).version());
    }

    @Test
    void messageDecode() {
        // send test message
        ProtocolVersion protocolVersion = new ProtocolVersion(70015);
        Services services = new Services(Service.NODE_NETWORK, Service.NODE_BLOOM, Service.NODE_WITNESS, Service.NODE_NETWORK_LIMITED);
        Timestamp8 timestamp = new Timestamp8(1531662845);
        NetAddress toAddress = new NetAddress(new Services(), new IpAddress("127.0.0.1"), 0);
        NetAddress fromAddress = new NetAddress(services, new IpAddress("127.0.0.1"), 0);
        VarString userAgent = new VarString("/Satoshi:0.16.1/");
        Version payload = new Version(
                protocolVersion,
                services,
                timestamp,
                toAddress,
                fromAddress,
                0xF92522BC2A1DA055L,
                userAgent,
                0,
                true
        );
//        assertEquals(VERSION_MESSAGE1, version.encode().getHexString());
    }

    @Test
    void payloadDecode() {
        Version payload = new BtcMessage(new Bytes(VERSION_PAYLOAD)).version();
        assertEquals(VERSION_PAYLOAD, payload.encode().getHexString());
    }

    @Test
    void payloadEncode() {
        ProtocolVersion protocolVersion = new ProtocolVersion(70002);
        Services services = new Services(Service.NODE_NETWORK);
        Timestamp8 timestamp = new Timestamp8(1415483324);
        NetAddress toAddress = new NetAddress(services, new IpAddress("198.27.100.9"), 8333);
        NetAddress fromAddress = new NetAddress(services, new IpAddress("203.0.113.192"), 8333);
        VarString userAgent = new VarString("/Satoshi:0.9.3/");
        Version payload = new Version(
                protocolVersion,
                services,
                timestamp,
                toAddress,
                fromAddress,
                0x128035cbc97953f8L,
                userAgent,
                329167,
                true
        );
        assertEquals(VERSION_PAYLOAD, payload.encode().getHexString());
    }
}