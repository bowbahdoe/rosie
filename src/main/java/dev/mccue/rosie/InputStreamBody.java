package dev.mccue.rosie;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

final class InputStreamBody extends Body {
    private final InputStream value;

    InputStreamBody(InputStream value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    void writeTo(HttpServletResponse response) throws IOException {
        response.getOutputStream().write(value.readAllBytes());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof InputStreamBody inputStreamBody
                && Objects.equals(this.value, inputStreamBody.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "InputStreamBody[" +
                "value=" + value +
                ']';
    }
}
