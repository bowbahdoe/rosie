package dev.mccue.rosie;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;

record PathBody(Path value) implements Body {
    @Override
    public void writeToStream(OutputStream outputStream) {
        try (var fileInputStream = new FileInputStream(value.toFile())) {
            fileInputStream.transferTo(outputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Optional<String> defaultContentType() {
        return Optional.of("application/octet-stream");
    }
}
