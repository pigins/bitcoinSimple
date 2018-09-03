package serg.home.bitcoinSimple.network.exceptions;

import serg.home.bitcoinSimple.network.messages.Addr;

public class AddressCountTooLarge extends ProtocolException {
    public AddressCountTooLarge(int maxCount) {
        super(Addr.NAME, "address count exceeds " + maxCount);
    }
}
