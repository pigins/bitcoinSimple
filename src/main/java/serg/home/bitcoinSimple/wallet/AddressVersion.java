package serg.home.bitcoinSimple.wallet;

/**
 * https://bitcoin.org/en/developer-reference#address-conversion
 */
public enum AddressVersion {
    MAINNET_P2PKH((byte)0x00), MAINNET_P2SH((byte)0x05), TESTNET_P2PKH((byte)0x6F), TESTNET_P2SH((byte)0x4C);

    private byte code;

    AddressVersion(byte code) {
        this.code = code;
    }

    public byte code() {
        return code;
    }
}
