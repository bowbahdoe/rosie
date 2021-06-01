package dev.mccue.rosie;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Taking available response forms from http://pedestal.io/reference/response-bodies.
 */
public sealed abstract class Body permits ByteArrayBody, CharArrayBody, EmptyBody, InputStreamBody, StringBody {
    public static Body fromCharArray(char[] value) {
        return new CharArrayBody(value);
    }

    public static Body fromByteArray(byte[] value) {
        return new ByteArrayBody(value);
    }

    public static Body fromString(String value) {
        return new StringBody(value);
    }

    public static Body fromInputStream(InputStream value) {
        return new InputStreamBody(value);
    }

    public static Body empty() {
        return EmptyBody.INSTANCE;
    }

    abstract void writeTo(HttpServletResponse response) throws IOException;
}

