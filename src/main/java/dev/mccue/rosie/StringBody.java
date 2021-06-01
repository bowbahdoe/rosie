package dev.mccue.rosie;


import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

final class StringBody extends Body {
    private final String value;

    StringBody(String value) {
        this.value = Objects.requireNonNull(value);;
    }

    @Override
    void writeTo(HttpServletResponse response) throws IOException {
        response.getWriter().print(value);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof StringBody stringBody
                && Objects.equals(this.value, stringBody.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "StringBody[" +
                "value='" + value + '\'' +
                ']';
    }
}
