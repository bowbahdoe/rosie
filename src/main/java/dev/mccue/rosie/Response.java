package dev.mccue.rosie;

import java.util.Map;
import java.util.Objects;

/**
 * A response to an http request.
 */
public record Response(
        int status,
        Map<String, String> headers,
        Body body
) implements IntoResponse {
    public Response {
        Objects.requireNonNull(headers, "headers must not be null");
        Objects.requireNonNull(body, "body must not be null");
    }

    public Response(int status) {
        this(status, Map.of(), Body.empty());
    }

    public Response(Body body) {
        this(200, Map.of(), body);
    }

    public Response(int status, Body body) {
        this(status, Map.of(), body);
    }

    @Override
    public Response intoResponse() {
        return this;
    }
}