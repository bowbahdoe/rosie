package dev.mccue.rosie;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

final class CharArrayBody extends Body {
    private final char[] value;

    CharArrayBody(char[] value) {
        Objects.requireNonNull(value);
        this.value = Arrays.copyOf(value, value.length);
    }

    @Override
    void writeTo(HttpServletResponse response) throws IOException {
        response.getWriter().write(value);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CharArrayBody charArrayBody
                && Arrays.equals(this.value, charArrayBody.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        return "CharArrayBody[" +
                "value=" + Arrays.toString(value) +
                ']';
    }
}