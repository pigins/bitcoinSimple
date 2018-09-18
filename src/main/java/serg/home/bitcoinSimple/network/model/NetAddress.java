package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.net.InetSocketAddress;

/**
 * https://en.bitcoin.it/wiki/Protocol_documentation#Network_address
 */
public class NetAddress implements ByteBufWritable {
    public static NetAddress read(ByteBuf byteBuf) {
        return new NetAddress(Services.read(byteBuf), IpAddress.read(byteBuf), Short.toUnsignedInt(byteBuf.readShort()));
    }

    private Services services;
    private IpAddress ipAddress;
    private int port;

    public NetAddress(Services services, IpAddress ipAddress, int port) {
        this.services = services;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public NetAddress(long services, int ipAddress, short port) {
        this.services = new Services(services);
        this.ipAddress = new IpAddress(ipAddress);
        this.port = Short.toUnsignedInt(port);
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
    public void write(ByteBuf byteBuf) {
        services.write(byteBuf);
        if (ipAddress.isSiteLocalAddress()) {
            // Newer protocol includes the checksum now, this is from a mainline (satoshi) client during an outgoing
            // connection to another local client, notice that it does not fill out the address information at all when
            // the source or destination is "unroutable".
            byteBuf.writeBytes(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        } else {
            ipAddress.write(byteBuf);
            byteBuf.writeShort((short) port);
        }
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
