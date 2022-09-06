package dev.mccue.rosie;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;

record ReadableByteChannelBody(ReadableByteChannel value) implements Body {
    @Override
    public void writeToStream(OutputStream outputStream) {
        try {
            Channels.newInputStream(value).transferTo(outputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Optional<String> defaultContentType() {
        return Optional.of("application/octet-stream");
    }
}
