package dev.mccue.rosie;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

record StringBody(String value) implements Body {
    public StringBody(String value) {
        this.value = Objects.requireNonNull(value);;
    }

    @Override
    public void writeToStream(OutputStream outputStream) {
        new ByteArrayBody(value.getBytes(StandardCharsets.UTF_8))
                .writeToStream(outputStream);
    }

    @Override
    public Optional<String> defaultContentType() {
        return Optional.of("text/plain");
    }
}
