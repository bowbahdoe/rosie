package dev.mccue.rosie;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseTests {
    @Test
    public void defaultContentTypes() {
        assertEquals(
                Body.empty().defaultContentType(),
                Optional.empty()
        );
        assertEquals(
                Body.fromString("").defaultContentType(),
                Optional.of("text/plain")
        );
        assertEquals(
                Body.fromByteArray(new byte[0]).defaultContentType(),
                Optional.of("application/octet-stream")
        );
        assertEquals(
                Body.fromInputStream(InputStream.nullInputStream()).defaultContentType(),
                Optional.of("application/octet-stream")
        );
        assertEquals(
                Body.fromByteBuffer(ByteBuffer.wrap(new byte[0])).defaultContentType(),
                Optional.of("application/octet-stream")
        );
        assertEquals(
                Body.fromPath(Path.of("abc")).defaultContentType(),
                Optional.of("application/octet-stream")
        );
        assertEquals(
                Body.fromReadableByteChannel(new ReadableByteChannel() {
                    @Override
                    public int read(ByteBuffer dst) throws IOException {
                        return 0;
                    }

                    @Override
                    public boolean isOpen() {
                        return false;
                    }

                    @Override
                    public void close() throws IOException {

                    }
                }).defaultContentType(),
                Optional.of("application/octet-stream")
        );
    }

    @Test
    public void defaultResponseInfo() {
        assertEquals(
                new Response(Body.empty()).status(),
                200
        );
        assertEquals(
                new Response(Body.empty()).headers(),
                Map.of()
        );
        assertEquals(
                new Response(200, Body.empty()).headers(),
                Map.of()
        );

        var r = new Response(Body.empty());
        assertSame(
                r,
                r.intoResponse()
        );
    }

    @Test
    public void emptyBody() {
        var baos = new ByteArrayOutputStream();

        Body.empty().writeToStream(baos);
        assertArrayEquals(
                baos.toByteArray(),
                new byte[0]
        );
    }

    @Test
    public void stringBody() {
        var baos = new ByteArrayOutputStream();

        Body.fromString("abc").writeToStream(baos);
        assertArrayEquals(
                baos.toByteArray(),
                new byte[] { 'a', 'b', 'c' }
        );
    }

    @Test
    public void byteArrayBody() {
        var baos = new ByteArrayOutputStream();

        Body.fromByteArray(new byte[] { 'a', 'b', 'c' }).writeToStream(baos);
        assertArrayEquals(
                baos.toByteArray(),
                new byte[] { 'a', 'b', 'c' }
        );
    }

    @Test
    public void inputStreamBody() {
        var baos = new ByteArrayOutputStream();

        Body.fromInputStream(
                new ByteArrayInputStream("abc".getBytes(StandardCharsets.UTF_8))
        ).writeToStream(baos);
        assertArrayEquals(
                baos.toByteArray(),
                new byte[] { 'a', 'b', 'c' }
        );
    }

    @Test
    public void byteBufferBody() {
        var baos = new ByteArrayOutputStream();

        Body.fromByteBuffer(
                ByteBuffer.wrap(new byte[] { 'a', 'b', 'c' })
        ).writeToStream(baos);
        assertArrayEquals(
                baos.toByteArray(),
                new byte[] { 'a', 'b', 'c' }
        );
    }

    @Test
    public void pathBody() throws IOException {
        var temp = Files.createTempFile("abc", ".a");
        Files.writeString(temp, "abc");

        var baos = new ByteArrayOutputStream();

        Body.fromPath(
                temp
        ).writeToStream(baos);
        assertArrayEquals(
                baos.toByteArray(),
                new byte[] { 'a', 'b', 'c' }
        );
    }

    @Test
    public void missingPath() {
        var baos = new ByteArrayOutputStream();
        assertThrows(
                UncheckedIOException.class,
                () -> Body.fromPath(Path.of(UUID.randomUUID().toString())).writeToStream(baos)
        );
    }
}
