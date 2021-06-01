package dev.mccue.rosie;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * A response to an http request.
 */
public final class Response implements IntoResponse {
    private final int status;
    private final Headers headers;
    private final Body body;

    private Response(int status, Headers headers, Body body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    /**
     * Returns a basic response from the components provided.
     */
    public static Response of(int status, Headers headers, Body body) {
        return new Response(status, Objects.requireNonNull(headers), Objects.requireNonNull(body));
    }

    /**
     * Status code of response.
     */
    public int status() {
        return this.status;
    }

    /**
     * Headers for the response.
     */
    public Headers headers() {
        return this.headers;
    }

    /**
     * Body of the response.
     */
    public Body body() {
        return this.body;
    }

    @Override
    public Response intoResponse() {
        return this;
    }

    void writeTo(Response response, HttpServletResponse httpServletResponse) throws IOException {
        // TODO: Figure out when / how best to handle when no status is set.
        // https://github.com/ring-clojure/ring/blob/1.9.0/ring-servlet/src/ring/util/servlet.clj#L104
        httpServletResponse.setStatus(response.status());
        response.headers().writeTo(httpServletResponse);
        response.body().writeTo(httpServletResponse);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response response)) return false;
        return status == response.status && headers.equals(response.headers) && body.equals(response.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, headers, body);
    }

    @Override
    public String toString() {
        return "Response[" +
                "status=" + status +
                ", headers=" + headers +
                ", body=" + body +
                ']';
    }
}