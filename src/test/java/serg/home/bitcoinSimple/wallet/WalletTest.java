package serg.home.bitcoinSimple.wallet;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WalletTest {

    @Test
    void generatePrivateKey() {
        Wallet wallet = new Wallet(AddressVersion.MAINNET_P2PKH);
        wallet.generateKeyPair();
        wallet.keys().get(0).address();
        System.out.println(wallet.keys().get(0).privateKey);
        System.out.println(wallet.keys().get(0).privateKeyWif());
        System.out.println(wallet.keys().get(0).publicKey);
        System.out.println(wallet.keys().get(0).address());
    }

    @Test
    void saveWallet() {
        Path walletPath = Paths.get(System.getProperty("java.io.tmpdir")).resolve("wallet");
        Wallet wallet = new Wallet(AddressVersion.MAINNET_P2PKH);
        wallet.generateKeyPair();
        byte networkId = wallet.keys().get(0).getAddressVersion();
        String address = wallet.keys().get(0).address();
        wallet.save(walletPath);
        assertTrue(walletPath.toFile().exists());
        Wallet restored = Wallet.restore(walletPath);
        byte restoredNetworkId = restored.keys().get(0).getAddressVersion();
        String restoredAddress = wallet.keys().get(0).address();
        assertEquals(networkId, restoredNetworkId);
        assertEquals(address, restoredAddress);
        walletPath.toFile().delete();
    }
}