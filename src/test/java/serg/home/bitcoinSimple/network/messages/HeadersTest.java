package serg.home.bitcoinSimple.network.messages;

import org.junit.jupiter.api.Test;
import serg.home.bitcoinSimple.common.Bytes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class HeadersTest {

    @Test
    void decode() throws IOException {
        String exampleHeadersAsHexString = new String(getClass().getResource("headers_example.txt").openStream().readAllBytes(), StandardCharsets.UTF_8);
        Headers headers = new Headers(new Bytes(exampleHeadersAsHexString));
        assertEquals(8, headers.blockHeaders().size());
    }

    @Test
    void encode() {
    }
}