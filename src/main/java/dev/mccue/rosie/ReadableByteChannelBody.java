package dev.mccue.rosie;

import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;

record ReadableByteChannelBody(ReadableByteChannel value) implements Body {
    @Override
    public void writeToStream(OutputStream outputStream) {
        Body.fromInputStream(Channels.newInputStream(value))
                .writeToStream(outputStream);
    }

    @Override
    public Optional<String> defaultContentType() {
        return Optional.of("application/octet-stream");
    }
}
