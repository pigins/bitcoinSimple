package serg.home.bitcoinSimple.wallet;


import serg.home.bitcoinSimple.common.Bytes;

import java.io.*;
import java.nio.file.Path;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Wallet implements Serializable {
    public static Wallet restore(Path path) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return (Wallet) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Key> keys = new ArrayList<>();

    public AddressVersion getAddressVersion() {
        return addressVersion;
    }

    private AddressVersion addressVersion;


    public Wallet(AddressVersion addressVersion) {
        this.addressVersion = addressVersion;
    }

    public void generateKeyPair() {
        keys.add(createKey());
    }

    public List<Key> keys() {
        return Collections.unmodifiableList(keys);
    }

    public void save(Path path) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Key createKey() {
        KeyPair keyPair = createKeyPair64();
        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
        String privateHexString = privateKey.getS().toString(16).toUpperCase();
        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        String publicHexString = "04" + adjustTo64(publicKey.getW().getAffineX().toString(16).toUpperCase())
                + adjustTo64(publicKey.getW().getAffineY().toString(16).toUpperCase());
        return new Key(
                new Bytes(privateHexString),
                new Bytes(publicHexString),
                this.addressVersion
        );
    }

    private KeyPair createKeyPair64() {
        KeyPair keyPair = createKeyPair();
        String privateHexString = ((ECPrivateKey) keyPair.getPrivate()).getS().toString(16).toUpperCase();
        if (privateHexString.length() != 64) {
            keyPair = createKeyPair64();
        }
        return keyPair;
    }

    private KeyPair createKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyGen.initialize(ecSpec, new SecureRandom());
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    private String adjustTo64(String s) {
        switch (s.length()) {
            case 61:
                return "000" + s;
            case 62:
                return "00" + s;
            case 63:
                return "0" + s;
            case 64:
                return s;
            default:
                throw new IllegalArgumentException("not a valid key: " + s);
        }
    }
}
