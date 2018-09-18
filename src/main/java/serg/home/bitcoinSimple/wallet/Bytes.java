package serg.home.bitcoinSimple.wallet;

import io.netty.buffer.ByteBuf;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

class Bytes {

    public Bytes(ByteBuf byteBuf) {
        new Bytes(byteBuf.array());
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
