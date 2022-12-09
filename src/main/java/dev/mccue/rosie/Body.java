package dev.mccue.rosie;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Taking available response forms from <a href="http://pedestal.io/reference/response-bodies">http://pedestal.io/reference/response-bodies</a>.
 */
public interface Body {
    static Body fromByteArray(byte[] value) {
        return new ByteArrayBody(value);
    }

    static Body fromString(String value) {
        return new StringBody(value);
    }

    static Body fromPath(Path value) {
        return new PathBody(value);
    }

    static Body fromInputStream(InputStream value) {
        return new InputStreamBody(value);
    }

    static Body fromReadableByteChannel(ReadableByteChannel value) {
        return new ReadableByteChannelBody(value);
    }

    static Body fromByteBuffer(ByteBuffer value) {
        return new ByteBufferBody(value);
    }

    static Body empty() {
        return EmptyBody.INSTANCE;
    }

    /**
     * Writes the body to the outputstream. If the body wraps any stateful resource,
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

