package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * @see <a href="https://en.bitcoin.it/wiki/Protocol_documentation#Network_address">https://en.bitcoin.it/wiki/Protocol_documentation#Network_address</a><br>
 * it does not fill out the address information at all when the source or destination is "unroutable".
 */
public class NetAddress implements ByteBufWritable {
    private static byte[] UNROUTABLE_ADDRESS_BYTES = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

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

    public boolean unroutable() {
        return ipAddress.asInt() == 0 && port == 0;
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
            byteBuf.writeBytes(UNROUTABLE_ADDRESS_BYTES);
        } else {
            ipAddress.write(byteBuf);
            byteBuf.writeShort((short) port);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetAddress that = (NetAddress) o;
        return port == that.port &&
                Objects.equals(services, that.services) &&
                Objects.equals(ipAddress, that.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(services, ipAddress, port);
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
