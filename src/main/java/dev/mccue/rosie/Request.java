package dev.mccue.rosie;

import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Optional;

/**
 * A request object, based on the structure Ring provides.
 * <p>
 * <a href="https://github.com/ring-clojure/ring/blob/1.9.0/ring-servlet/src/ring/util/servlet.clj#L37">...</a>
 */
public interface Request {
    int serverPort();

    String serverName();

    String remoteAddr();

    String uri();

    Optional<String> queryString();

    String scheme();

    String requestMethod();

    String protocol();

    /**
     * Gives the headers for the request as a read only map of header name to value. All the header names are lower-case
     * and if there are multiple values for a header, they are represented in the map as a comma separated
     * String like \"value1,value2,value3\".
     */
    Map<String, String> headers();

    /**
     * Will be an empty Optional
     * if the content length was not given in the request.
     */
    Optional<String> contentType();

    /**
     * Will be an empty Optional
     * if the content length was reported as negative.
     */
    Optional<Integer> contentLength();

    /**
     * Will be an empty Optional
     * if the character encoding was not given in the request.
     */
    Optional<String> characterEncoding();

    /**
     * Returns the SSL client certificate of the request, if one exists.
     */
    Optional<X509Certificate> sslClientCert();

    InputStream body();
}

