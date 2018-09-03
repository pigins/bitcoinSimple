package serg.home.bitcoinSimple.network.messages;

import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.model.VarString;
import serg.home.bitcoinSimple.common.binary.CompoundBinary;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

/**
 * https://bitcoin.org/en/developer-reference#reject
 */
public class Reject implements Payload {
    public static final String NAME = "reject";

    public enum CCODES {
        REJECT_MALFORMED((byte) 0x01),
        REJECT_INVALID((byte) 0x10),
        REJECT_OBSOLETE((byte) 0x11),
        REJECT_DUPLICATE((byte) 0x12),
        REJECT_NONSTANDARD((byte) 0x40),
        REJECT_DUST((byte) 0x41),
        REJECT_INSUFFICIENTFEE((byte) 0x42),
        REJECT_CHECKPOINT((byte) 0x43);

        public static CCODES from(byte b) {
            return Arrays.stream(CCODES.values()).filter(code -> code.value == b).findAny().get();
        }

        private byte value;

        CCODES(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }

    /**
     * type of message rejected
     */
    private String message;
    /**
     * code relating to rejected message
     */
    private CCODES ccode;
    /**
     * text version of reason for rejection
     */
    private String reason;
    /**
     * Optional extra data provided by some errors. Currently, all errors which provide this field fill it with the
     * TXID or block header hash of the object being rejected, so the field is 32 bytes.
     */
    private Bytes data;

    public Reject(String message, CCODES ccode, String reason, @Nullable Bytes data) {
        this.message = message;
        this.ccode = ccode;
        this.reason = reason;
        this.data = Objects.requireNonNullElseGet(data, Bytes::new);
    }

    public String getMessage() {
        return message;
    }

    public CCODES getCcode() {
        return ccode;
    }

    public String getReason() {
        return reason;
    }

    public Bytes getData() {
        return data;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Bytes encode() {
        return new CompoundBinary()
                .add(new VarString(message))
                .add(new Bytes(ccode.getValue()))
                .add(new VarString(reason))
                .add(data)
                .encode();
    }

    @Override
    public String toString() {
        return "Reject{" +
                "message='" + message + '\'' +
                ", ccode=" + ccode +
                ", reason='" + reason + '\'' +
                ", data=" + data +
                '}';
    }
}
