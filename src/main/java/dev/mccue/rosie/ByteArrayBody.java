package dev.mccue.rosie;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

record ByteArrayBody(byte[] value) implements Body {
    ByteArrayBody(byte[] value) {
        Objects.requireNonNull(value);
        this.value = Arrays.copyOf(value, value.length);
    }

    @Override
    public void writeToStream(OutputStream outputStream) {
        Body.fromInputStream(new ByteArrayInputStream(value))
                .writeToStream(outputStream);
    }

    @Override
    public Optional<String> defaultContentType() {
        return Optional.of("application/octet-stream");
    }
}

