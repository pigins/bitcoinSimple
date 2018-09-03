package serg.home.bitcoinSimple.wallet;

import org.bitcoinj.core.Base58;
import serg.home.bitcoinSimple.common.Bytes;

import java.io.Serializable;
import java.util.Objects;

public class Key implements Serializable {
    public final Bytes privateKey;
    public final Bytes publicKey;
    private byte addressVersion;

    public byte getAddressVersion() {
        return addressVersion;
    }

    public Key(Bytes privateKey, Bytes publicKey, AddressVersion addressVersion) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.addressVersion = addressVersion.code();
    }

    public String privateKeyWif() {
        Bytes extendedKey = new Bytes((byte) 0x80).concat(privateKey);
        Bytes firstHash = extendedKey.sha256();
        Bytes firstBytesSecondHash = firstHash.sha256().subArray(0, 4);
        return Base58.encode(extendedKey.concat(firstBytesSecondHash).byteArray());
    }

    public String address() {
        Bytes riped = new Bytes(new byte[]{this.addressVersion}).concat(this.publicKey.sha256().ripeMD160());
        Bytes bytes32 = riped.doubleSha256();
        Bytes result = riped.concat(bytes32.subArray(0, 4));
        return Base58.encode(result.byteArray());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Objects.equals(privateKey, key.privateKey) &&
                Objects.equals(publicKey, key.publicKey) &&
                Objects.equals(addressVersion, key.addressVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(privateKey, publicKey, addressVersion);
    }
}
