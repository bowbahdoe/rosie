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

    void writeToStream(OutputStream outputStream);

    /**
     * @return A hint to the content type that a given body type implies. Will be used to set the
     */
    default Optional<String> defaultContentType() {
        return Optional.empty();
    }
}

