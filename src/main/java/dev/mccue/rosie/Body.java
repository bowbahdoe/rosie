package dev.mccue.rosie;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.Optional;

/**
 * The body of a request.
 *
 * <p>
 *     The available types of bodies were taken from the
 *     <a href="http://pedestal.io/reference/response-bodies">
 *         documentation for the Clojure library Pedestal.
 *     </a>
 * </p>
 */
public interface Body {
    /**
     * @param value The {@code byte[]} to wrap.
     * @return A body wrapping the provided {@code byte[]}.
     */
    static Body fromByteArray(byte[] value) {
        return new ByteArrayBody(value);
    }

    /**
     * @param value The {@link String} to wrap.
     * @return A body wrapping the provided {@link String}.
     */
    static Body fromString(String value) {
        return new StringBody(value);
    }

    /**
     * @param value The {@link Path} to wrap.
     * @return A body wrapping the provided {@link Path}.
     */
    static Body fromPath(Path value) {
        return new PathBody(value);
    }

    /**
     * @param value The {@link InputStream} to wrap.
     * @return A body wrapping the provided {@link InputStream}.
     */
    static Body fromInputStream(InputStream value) {
        return new InputStreamBody(value);
    }

    /**
     * @param value The {@link ReadableByteChannel} to wrap.
     * @return A body wrapping the provided {@link ReadableByteChannel}.
     */
    static Body fromReadableByteChannel(ReadableByteChannel value) {
        return new ReadableByteChannelBody(value);
    }

    /**
     * @param value The {@link ByteBuffer} to wrap.
     * @return A body wrapping the provided {@link ByteBuffer}.
     */
    static Body fromByteBuffer(ByteBuffer value) {
        return new ByteBufferBody(value);
    }

    /**
     * @return An empty body which sends no data back to the user.
     */
    static Body empty() {
        return EmptyBody.INSTANCE;
    }

    /**
     * Writes the body to the {@link OutputStream}. If the body wraps any stateful resource,
     * after writing that resource should be closed. This should be assumed to be only
     * callable once.
     *
     * @param outputStream The output stream to write to.
     */
    void writeToStream(OutputStream outputStream);

    /**
     * @return A hint to the content type that a given body type implies. Will be used to set the
     * {@code Content-Type} header in a response if none is set explicitly.
     */
    default Optional<String> defaultContentType() {
        return Optional.empty();
    }
}

