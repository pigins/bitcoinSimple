package serg.home.bitcoinSimple.network.model;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryDecoded;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;
import serg.home.bitcoinSimple.common.binary.ByteReader;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import java.net.InetSocketAddress;

/**
 * https://en.bitcoin.it/wiki/Protocol_documentation#Network_address
 */
public class NetAddress implements BinaryEncoded, BinaryDecoded {
    private Services services;
    private InetSocketAddress address;
    private IpAddress ipAddress;
    private int port;


    public NetAddress(long services, int ipAddress, short port) {
        this.services = new Services(services);
        this.ipAddress = new IpAddress(ipAddress);
        this.port = Short.toUnsignedInt(port);
    }

    public NetAddress(Services services, IpAddress ipAddress, int port) {
        this.services = services;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public NetAddress(ByteReader byteReader) {
        decode(byteReader);
    }

    public Services services() {
        return services;
    }

    public IpAddress ipAddress() {
        return ipAddress;
    }

    public int port() {
        return port;
    }

    public InetSocketAddress inetSocketAddress() {
        return new InetSocketAddress(ipAddress.getIpString(), port);
    }

    @Override
    public void decode(ByteReader byteReader) {
        services = byteReader.nextServices();
        ipAddress = new IpAddress(byteReader);
        port = Short.toUnsignedInt(byteReader.nextShort());
    }

    @Override
    public Bytes encode() {
        CompoundBinary compoundBinary = new CompoundBinary();
        compoundBinary.add(services);
        if (ipAddress.isSiteLocalAddress()) {
            // Newer protocol includes the checksum now, this is from a mainline (satoshi) client during an outgoing
            // connection to another local client, notice that it does not fill out the address information at all when
            // the source or destination is "unroutable".
            compoundBinary.add(new Bytes(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}));
        } else {
            compoundBinary.add(ipAddress);
            compoundBinary.add(Bytes.fromShort((short) port));
        }
        return compoundBinary.encode();
    }



    @Override
    public String toString() {
        return "NetAddress{" +
                "services=" + services +
                ", ipAddress=" + ipAddress +
                ", port=" + port +
                '}';
    }
}
