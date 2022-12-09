package dev.mccue.rosie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.Optional;

record InputStreamBody(InputStream value) implements Body {
    @Override
    public void writeToStream(OutputStream outputStream) {
        try (var __ = this.value) {
            value.transferTo(outputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Optional<String> defaultContentType() {
        return Optional.of("application/octet-stream");
    }
}
