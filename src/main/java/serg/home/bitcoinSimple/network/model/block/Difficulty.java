package serg.home.bitcoinSimple.network.model.block;

import io.netty.buffer.ByteBuf;
import org.bouncycastle.util.Arrays;
import serg.home.bitcoinSimple.common.ByteBufWritable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Difficulty implements ByteBufWritable {
    private static BigInteger DIFFICULTY_1_TARGET_B = new BigInteger("00000000FFFF0000000000000000000000000000000000000000000000000000", 16);
    private static BigInteger DIFFICULTY_1_TARGET_P = new BigInteger("00000000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);

    public static Difficulty read(ByteBuf byteBuf) {
        return new Difficulty(byteBuf.readBytes(4));
    }

    private ByteBuf bits;

    public Difficulty(ByteBuf bits) {
        if (bits.array().length != 4) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
    }

    public BigInteger target() {
        byte[] bitsArr = Arrays.reverse(bits.array());
        int exponent = bitsArr[0];
        BigInteger coefficient = new BigInteger(new byte[]{bitsArr[1], bitsArr[2], bitsArr[3]});
        BigInteger result = BigInteger.valueOf(2);
        int power = 8 * (exponent - 3);
        return result.pow(power).multiply(coefficient);
    }

    public BigDecimal bDiff() {
        return diff(DIFFICULTY_1_TARGET_B);
    }

    public BigDecimal pDiff() {
        return diff(DIFFICULTY_1_TARGET_P);
    }

    private BigDecimal diff(BigInteger diff1target) {
        return new BigDecimal(diff1target).divide(new BigDecimal(target()), 12, RoundingMode.DOWN);
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeBytes(bits);
    }

    @Override
    public String toString() {
        return "Difficulty{" +
                "bits=" + bits +
                "bdiff="+bDiff()+
                '}';
    }
}
