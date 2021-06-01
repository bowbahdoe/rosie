package dev.mccue.rosie;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

final class ByteArrayBody extends Body {
    private final byte[] value;

    ByteArrayBody(byte[] value) {
        Objects.requireNonNull(value);
        this.value = Arrays.copyOf(value, value.length);
    }

    @Override
    void writeTo(HttpServletResponse response) throws IOException {
        response.getOutputStream().write(value);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ByteArrayBody byteArrayBody
                && Arrays.equals(this.value, byteArrayBody.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        return "ByteArrayBody[" +
                "value=" + Arrays.toString(value) +
                ']';
    }
}

