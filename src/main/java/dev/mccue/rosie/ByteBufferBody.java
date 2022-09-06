package dev.mccue.rosie;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.Optional;

record ByteBufferBody(ByteBuffer value) implements Body {
    @Override
    public void writeToStream(OutputStream outputStream) {
        try {
            Channels.newChannel(outputStream).write(value);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Optional<String> defaultContentType() {
        return Optional.of("application/octet-stream");
    }
}
