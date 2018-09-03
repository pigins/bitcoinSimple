package serg.home.bitcoinSimple.common;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import serg.home.bitcoinSimple.common.binary.BinaryEncoded;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

/**
 * wrapper for byte array with set of utility methods.
 */
public class Bytes implements Serializable, BinaryEncoded {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static Bytes concat(Bytes... arrays) {
        int totalLength = 0;
        for (Bytes array : arrays) {
            totalLength += array.length();
        }
        byte[] result = new byte[totalLength];
        int currentIndex = 0;
        for (Bytes array : arrays) {
            System.arraycopy(array.bytes, 0, result, currentIndex, array.length());
            currentIndex += array.length();
        }
        return new Bytes(result);
    }

    public static Bytes fromInt(int value) {
        return new Bytes(new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value});
    }

    public static Bytes fromBoolean(boolean value) {
        byte v = (byte) (value ? 1 : 0);
        return new Bytes(new byte[]{v});
    }

    public static Bytes fromShort(short value) {
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.putShort(value);
        return new Bytes(buffer.array());
    }

    public static Bytes fromIntLE(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(value);
        return new Bytes(buffer.array());
    }

    public static Bytes fromLongToLE(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(value);
        return new Bytes(buffer.array());
    }

    public static Bytes fromLong(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        return new Bytes(buffer.array());
    }

    protected byte[] bytes;

    public Bytes() {
        bytes = new byte[0];
    }

    public Bytes(byte b) {
        bytes = new byte[]{b};
    }

    public Bytes(byte[] bytes) {
        Objects.requireNonNull(bytes, "encode must not be null");
        this.bytes = bytes;
    }

    public Bytes(String hexString) {
        Objects.requireNonNull(hexString, "hexString must not be null");
        this.bytes = hexStringToByteArray(hexString.toUpperCase());
    }

    public Bytes doubleSha256() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digest1 = messageDigest.digest(bytes);
            byte[] digest2 = messageDigest.digest(digest1);
            return new Bytes(digest2);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no SHA-256 algorithm");
        }
    }

    public Bytes sha256() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return new Bytes(messageDigest.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no SHA-256 algorithm");
        }
    }

    public String getHexString() {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public Bytes ripeMD160() {
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(bytes, 0, bytes.length);
        byte[] result = new byte[digest.getDigestSize()];
        digest.doFinal(result, 0);
        return new Bytes(result);
    }

    public Bytes concat(Bytes bytes) {
        byte[] a = this.bytes;
        byte[] b = bytes.bytes;
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return new Bytes(c);
    }

    public Bytes subArray(int start, int end) {
        if (end > this.bytes.length) {
            throw new IllegalArgumentException();
        }
        int resultLength = end - start;
        byte[] result = new byte[resultLength];
        System.arraycopy(bytes, start, result, 0, resultLength);
        return new Bytes(result);
    }

    public Bytes flip() {
        for(int i = 0; i < bytes.length / 2; i++) {
            byte temp = bytes[i];
            bytes[i] = bytes[bytes.length - i - 1];
            bytes[bytes.length - i - 1] = temp;
        }
        return this;
    }

    public int length() {
        return bytes.length;
    }

    public byte[] byteArray() {
        return this.bytes;
    }

    public Bytes nullPadded(int length) {
        byte[] res = new byte[length];
        System.arraycopy(this.bytes, 0, res, 0, this.bytes.length);
        this.bytes = res;
        return this;
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    @Override
    public Bytes encode() {
        return this;
    }

    @Override
    public String toString() {
        return "Bytes{" +
                "encode=" + getHexString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bytes bytes1 = (Bytes) o;
        return Arrays.equals(bytes, bytes1.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }


}
