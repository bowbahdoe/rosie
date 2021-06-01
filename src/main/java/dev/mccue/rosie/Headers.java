package dev.mccue.rosie;

import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Headers {
    private final Map<String, String> headers;

    private Headers(Map<String, String> headers) {
        this.headers = headers;
    }

    public static Headers fromMap(Map<String, String> headers) {
        return new Headers(new HashMap<>(headers));
    }

    @SafeVarargs
    public static Headers fromEntries(Map.Entry<String, String>... entries) {
        return new Headers(Map.ofEntries(entries));
    }

    public static Headers from(
            String k1, String v1
    ) {
        return new Headers(Map.of(k1, v1));
    }

    public static Headers from(
            String k1, String v1,
            String k2, String v2
    ) {
        return new Headers(Map.of(k1, v1, k2, v2));
    }

    public static Headers from(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3
    ) {
        return new Headers(Map.of(k1, v1, k2, v2, k3, v3));
    }

    public static Headers from(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3,
            String k4, String v4
    ) {
        return new Headers(Map.of(k1, v1, k2, v2, k3, v3, k4, v4));
    }

    public static Headers from(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3,
            String k4, String v4,
            String k5, String v5
    ) {
        return new Headers(Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5));
    }


    public static Headers from(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3,
            String k4, String v4,
            String k5, String v5,
            String k6, String v6
    ) {
        return new Headers(Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6));
    }

    public static Headers from(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3,
            String k4, String v4,
            String k5, String v5,
            String k6, String v6,
            String k7, String v7
    ) {
        return new Headers(Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7));
    }

    public static Headers from(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3,
            String k4, String v4,
            String k5, String v5,
            String k6, String v6,
            String k7, String v7,
            String k8, String v8
    ) {
        return new Headers(Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8));
    }

    public static Headers from(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3,
            String k4, String v4,
            String k5, String v5,
            String k6, String v6,
            String k7, String v7,
            String k8, String v8,
            String k9, String v9
    ) {
        return new Headers(Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9));
    }

    public static Headers from(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3,
            String k4, String v4,
            String k5, String v5,
            String k6, String v6,
            String k7, String v7,
            String k8, String v8,
            String k9, String v9,
            String k10, String v10
    ) {
        return new Headers(Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10));
    }

    void writeTo(HttpServletResponse httpServletResponse) {
        for (final var header : this.headers.entrySet()) {
            // TODO: Support multiple values for a header.
            // https://github.com/ring-clojure/ring/blob/1.9.0/ring-servlet/src/ring/util/servlet.clj#L75
            httpServletResponse.setHeader(header.getKey(), header.getValue());
        }
        if (this.headers.containsKey("Content-Type")) {
            httpServletResponse.setContentType(this.headers.get("Content-Type"));
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Headers headers
                && this.headers.equals(headers.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }

    @Override
    public String toString() {
        return "Headers[" +
                "headers=" + headers +
                ']';
    }
}