package serg.home.bitcoinSimple.protocol;

import serg.home.bitcoinSimple.blockchain.block.Block;
import serg.home.bitcoinSimple.blockchain.block.BlockHeader;
import serg.home.bitcoinSimple.blockchain.block.BlockVersion;
import serg.home.bitcoinSimple.blockchain.block.Difficulty;
import serg.home.bitcoinSimple.blockchain.block.transaction.Transaction;
import serg.home.bitcoinSimple.blockchain.block.transaction.TxVersion;
import serg.home.bitcoinSimple.blockchain.block.transaction.input.*;
import serg.home.bitcoinSimple.blockchain.block.transaction.output.Output;
import serg.home.bitcoinSimple.blockchain.block.transaction.output.Value;
import serg.home.bitcoinSimple.blockchain.block.transaction.script.OP;
import serg.home.bitcoinSimple.blockchain.block.transaction.script.Script;
import serg.home.bitcoinSimple.common.Bytes;
import serg.home.bitcoinSimple.network.messages.*;
import serg.home.bitcoinSimple.network.model.*;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BtcMessage implements MessageReader {
    private String command;
    private int readCount = 0;
    private Bytes bytes;

    public BtcMessage(String command, Bytes bytes) {
        this.command = command;
        this.bytes = bytes;
    }

    public BtcMessage(Bytes bytes) {
        this.bytes = bytes;
    }

    @Override
    public Addr nextAddr() {
        int count = nextVarInt().toInt();
        List<TimestampWithAddress> addrList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            addrList.add(
                    new TimestampWithAddress(
                            nextTimestamp4().getValue(),
                            nextNetAddress()
                    )
            );
        }
        return new Addr(addrList);
    }

    @Override
    public Block nextBlock() {
        BlockHeader blockHeader = nextBlockHeader();
        int txCount = nextVarInt().toInt();
        List<Transaction> transactions = new ArrayList<>(txCount);
        transactions.add(nextCoinbaseTransaction());
        for (int i = 0; i < txCount - 1; i++) {
            Transaction e = nextTransaction();
            transactions.add(e);
        }
        return new Block(blockHeader, transactions);
    }

    @Override
    public GetAddr nextGetAddr() {
        return new GetAddr();
    }

    @Override
    public GetBlocks nextGetBlocks() {
        ProtocolVersion protocolVersion = nextProtocolVersion();
        int hashCount = nextVarInt().toInt();
        List<Bytes> hashes = new ArrayList<>(hashCount);
        for (int i = 0; i < hashCount; i++) {
            hashes.add(next(32));
        }
        boolean getAsManyAsPossible = false;
        if (hasNext() && next(32).equals(GetId.STOP_HASH)) {
            getAsManyAsPossible = true;
        }
        return new GetBlocks(protocolVersion, hashes, getAsManyAsPossible);
    }
    @Override
    public GetHeaders nextGetHeaders() {
        ProtocolVersion protocolVersion = nextProtocolVersion();
        int hashCount = nextVarInt().toInt();
        List<Bytes> hashes = new ArrayList<>(hashCount);
        for (int i = 0; i < hashCount; i++) {
            hashes.add(next(32));
        }
        boolean getAsManyAsPossible = false;
        if (hasNext() && next(32).equals(GetId.STOP_HASH)) {
            getAsManyAsPossible = true;
        }
        return new GetHeaders(protocolVersion, hashes, getAsManyAsPossible);
    }
    @Override
    public Headers nextHeaders() {
        int count = nextVarInt().toInt();
        List<BlockHeader> messageHeaders = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            messageHeaders.add(nextBlockHeader());
        }
        return new Headers(messageHeaders);
    }
    @Override
    public Inv nextInv() {
        int count = nextVarInt().toInt();
        List<InvVector> invVectors = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            invVectors.add(nextInvVector());
        }
        return new Inv(invVectors);
    }
    @Override
    public Ping nextPing() {
        return new Ping(nextLong());
    }

    @Override
    public Pong nextPong() {
        return new Pong(nextLong());
    }

    @Override
    public Reject nextReject() {
        return new Reject(
                nextVarString().getValue(),
                Reject.CCODES.from(nextByte()),
                nextVarString().getValue(),
                next(32)
        );
    }

    @Override
    public Verack nextVerack() {
        return new Verack();
    }

    @Override
    public Version nextVersion() {
        return new Version(
                nextProtocolVersion(),
                nextServices(),
                nextTimestamp8(),
                nextNetAddress(),
                nextNetAddress(),
                nextLong(),
                nextVarString(),
                nextIntLE(),
                nextByte() == 1
        );
    }

    private Bytes next(int size) {
        Bytes result = this.bytes.subArray(readCount, readCount + size);
        readCount += size;
        return result;
    }

    private byte nextByte() {
        return bytes.byteArray()[readCount++];
    }

    public VarInt nextVarInt() {
        long value;
        byte b = nextByte();
        if (Byte.compareUnsigned(((byte) 0xfc), b) > 0) {
            value = b;
        } else if (Byte.compareUnsigned(((byte) 0xfd), b) == 0) {
            ByteBuffer buf = ByteBuffer.allocate(2);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.put(nextByte());
            buf.put(nextByte());
            buf.rewind();
            value = buf.getShort();
        } else if (Byte.compareUnsigned(((byte) 0xfe), b) == 0) {
            ByteBuffer buf = ByteBuffer.allocate(4);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.put(nextByte());
            buf.put(nextByte());
            buf.put(nextByte());
            buf.put(nextByte());
            buf.rewind();
            value = buf.getInt();
        } else {
            ByteBuffer buf = ByteBuffer.allocate(8);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.put(nextByte());
            buf.put(nextByte());
            buf.put(nextByte());
            buf.put(nextByte());
            buf.put(nextByte());
            buf.put(nextByte());
            buf.put(nextByte());
            buf.put(nextByte());
            buf.rewind();
            value = buf.getLong();
        }
        return new VarInt(value);
    }

    private CoinbaseInput nextCoinbaseInput() {
        return new CoinbaseInput(nextOutputLink(), nextCoinbaseData(), nextInt());
    }

    private CoinbaseData nextCoinbaseData() {
        int dataSize = nextVarInt().toInt();
        Bytes data = next(dataSize);
        // TODO add height decoding
        return new CoinbaseData(null, data);
    }

    private OutputLink nextOutputLink() {
        return new OutputLink(next(32), nextInt());
    }

    private RegularInput nextRegularInput() {
        return new RegularInput(nextOutputLink(), nextScript(), nextInt());
    }

    private Output nextOutput() {
        return new Output(nextValue(), nextScript());
    }

    private Value nextValue() {
        return new Value(nextLongLE());
    }

    private boolean hasNext() {
        return readCount < bytes.length();
    }

    private Script nextScript() {
        List<Object> items = new ArrayList<>();
        int scriptLength = nextVarInt().toInt();


        for (int i = 0; i < scriptLength; i++) {
            byte b = nextByte();
            if (b >= 1 && b <= 75) {
                items.add(next(b));
                i += b;
            } else {
                items.add(OP.from(b));
            }
        }
        return new Script(items);
    }

    private short nextShort() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        for (int i = readCount; i < readCount + 2; i++) {
            byteBuffer.put(bytes.byteArray()[i]);
        }
        byteBuffer.rewind();
        readCount += 2;
        return byteBuffer.getShort();
    }

    private int nextInt() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        for (int i = readCount; i < readCount + 4; i++) {
            byteBuffer.put(bytes.byteArray()[i]);
        }
        byteBuffer.rewind();
        readCount += 4;
        return byteBuffer.getInt();
    }

    private int nextIntLE() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = readCount; i < readCount + 4; i++) {
            byteBuffer.put(bytes.byteArray()[i]);
        }
        readCount += 4;
        byteBuffer.rewind();
        return byteBuffer.getInt();
    }

    private long nextLongLE() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = readCount; i < readCount + 8; i++) {
            byteBuffer.put(bytes.byteArray()[i]);
        }
        readCount += 8;
        byteBuffer.rewind();
        return byteBuffer.getLong();
    }

    public Services nextServices() {
        long services = nextLongLE();
        return new Services(services);
    }

    public MessageHeader nextMessageHeader() {
        Network network = nextNetwork();
        String command = new String(next(12).byteArray(), StandardCharsets.US_ASCII).trim();
        int payloadSize = nextIntLE();
        Bytes checksum = next(4);
        return new MessageHeader(network, command, payloadSize, checksum);
    }

    public Network nextNetwork() {
        Bytes bytes = next(4);
        return Arrays.stream(Network.values())
                .filter(network -> network.magicLE.equals(bytes))
                .findAny()
                .get();
    }

    public NetAddress nextNetAddress() {
        return new NetAddress(nextServices(), nextIpAddress(), Short.toUnsignedInt(nextShort()));
    }

    private IpAddress nextIpAddress() {
        return new IpAddress(new BigInteger(next(16).subArray(12, 16).byteArray()).intValue());
    }

    private Timestamp4 nextTimestamp4() {
        return new Timestamp4(Instant.ofEpochSecond(nextIntLE()).atOffset(ZoneOffset.UTC));
    }

    private Timestamp8 nextTimestamp8() {
        return new Timestamp8(Instant.ofEpochSecond(nextLongLE()).atOffset(ZoneOffset.UTC));
    }

    private Transaction nextCoinbaseTransaction() {
        TxVersion version = nextTxVersion();
        int inputsCount = nextVarInt().toInt();
        List<Input> inputs = new ArrayList<>(inputsCount);
        CoinbaseInput coinbaseInput = nextCoinbaseInput();
        inputs.add(coinbaseInput);
        int outputsCount = nextVarInt().toInt();
        List<Output> outputs = new ArrayList<>(outputsCount);
        for (int i = 0; i < inputsCount; i++) {
            outputs.add(nextOutput());
        }
        int uLockTime = nextInt();
        return new Transaction(version, uLockTime, inputs, outputs);
    }

    private Transaction nextTransaction() {
        TxVersion version = nextTxVersion();
        int inputsCount = nextVarInt().toInt();
        List<Input> inputs = new ArrayList<>(inputsCount);
        for (int i = 0; i < inputsCount; i++) {
            RegularInput regularInput = nextRegularInput();
            inputs.add(regularInput);
        }
        int outputsCount = nextVarInt().toInt();
        List<Output> outputs = new ArrayList<>(outputsCount);
        for (int i = 0; i < outputsCount; i++) {
            outputs.add(nextOutput());
        }
        int uLockTime = nextInt();
        return new Transaction(version, uLockTime, inputs, outputs);
    }

    private TxVersion nextTxVersion() {
        int value = nextIntLE();
        return Arrays.stream(TxVersion.values()).filter(version -> version.version == value).findAny().get();
    }

    private ProtocolVersion nextProtocolVersion() {
        return new ProtocolVersion(nextIntLE());
    }

    public BlockHeader nextBlockHeader() {
        BlockHeader blockHeader = new BlockHeader(
                nextBlockVersion(),
                next(32),
                next(32).flip(),
                nextTimestamp4(),
                nextDifficulty(),
                nextIntLE(),
                true
        );
//        byte b = nextByte();
//        if (b != 0) {
//            throw new RuntimeException();
//        }
        return blockHeader;
    }

    private BlockVersion nextBlockVersion() {
        int value = nextIntLE();
        return Arrays.stream(BlockVersion.values()).filter(blockVersion -> blockVersion.getuCode() == value).findAny().get();
    }

    private Difficulty nextDifficulty() {
        return new Difficulty(next(4).flip());
    }

    private InvVector nextInvVector() {
        return new InvVector(InvType.fromInt(nextIntLE()), next(32));
    }

    private VarString nextVarString() {
        var varInt = nextVarInt();
        var value = new String(
                next(varInt.toInt()).byteArray(),
                StandardCharsets.US_ASCII
        );
        return new VarString(value);
    }

    private long nextLong() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (int i = readCount; i < readCount + 8; i++) {
            byteBuffer.put(bytes.byteArray()[i]);
        }
        readCount += 8;
        byteBuffer.rewind();
        return byteBuffer.getLong();
    }

    public String getCommand() {
        return command;
    }

    public Bytes payload() {
        return bytes;
    }

    @Override
    public String toString() {
        return "BtcMessage{" +
                "command='" + command + '\'' +
                ", bytes=" + bytes +
                '}';
    }
}
