package serg.home.bitcoinSimple.network.model;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.Arrays;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

import java.util.Objects;

public class ProtocolVersion implements BinaryEncoded, Comparable<ProtocolVersion> {
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
    private int uVersion;

    public ProtocolVersion(int uVersion) {
        if (!Arrays.contains(KNOWN_VERSIONS, uVersion)) {
            logger.warn("unknown protocol version {}", uVersion);
        }
        this.uVersion = uVersion;
    }

    public int getVersion() {
        return uVersion;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeIntLE(uVersion);
    }

    @Override
    public int compareTo(ProtocolVersion other) {
        return Integer.compare(this.getVersion(), other.getVersion());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolVersion that = (ProtocolVersion) o;
        return uVersion == that.uVersion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uVersion);
    }

    @Override
    public String toString() {
        return "ProtocolVersion{" +
                "uVersion=" + uVersion +
                '}';
    }
}


