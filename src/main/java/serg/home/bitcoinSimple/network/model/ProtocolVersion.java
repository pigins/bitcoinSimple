package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.Arrays;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.util.Objects;

/**
 * @see <a href="https://bitcoin.org/en/developer-reference#protocol-versions">https://bitcoin.org/en/developer-reference#protocol-versions</a>
 */
public class ProtocolVersion implements ByteBufWritable, Comparable<ProtocolVersion> {
    public static Logger logger = LogManager.getLogger();

    public static ProtocolVersion min(ProtocolVersion a, ProtocolVersion b) {
        return a.compareTo(b) < 0 ? a : b;
    }

    private static int[] KNOWN_VERSIONS = new int[]{
            106, 209, 311, 31402, 31800, 60000, 60001, 60002, 70001, 70002, 70011, 70012, 70013, 70014, 70015
    };

    public static ProtocolVersion read(ByteBuf byteBuf) {
        return new ProtocolVersion(byteBuf.readIntLE());
    }

    private int version;

    public ProtocolVersion(int version) {
        if (!Arrays.contains(KNOWN_VERSIONS, version)) {
            logger.warn("unknown protocol version {}", version);
        }
        this.version = version;
    }

    public int asInt() {
        return version;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeIntLE(version);
    }

    @Override
    public int compareTo(ProtocolVersion other) {
        return Integer.compare(this.asInt(), other.asInt());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolVersion that = (ProtocolVersion) o;
        return version == that.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }

    @Override
    public String toString() {
        return "ProtocolVersion{" +
                "version=" + version +
                '}';
    }
}