package dev.mccue.rosie;

import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Optional;

/**
 * An object combining the information that can be derived from the contents of
 * an HTTP request and the properties of the transport it came on.
 *
 * <p>
 *     The overall purpose of this is to be a "least common denominator" for any http server,
 *     whether that be servlet-based or otherwise.
 * </p>
 *
 * <p>
 *     The reason this is an interface and not a concrete class is to allow for consumers
 *     and libraries to make and allow customized request objects which can derive more
 *     information from the data in the request. For example, parsing the body as json
 *     or using an auth header to inject information about and authenticated user.
 * </p>
 *
 * <p>
 *     For the above purpose {@link DelegatingRequest} should be of use.
 * </p>
 *
 * <p>
 *     The overall idea and shape of data in here is entirely inspired by the structure provided by
 *     Clojure's <a href="https://github.com/ring-clojure/ring/blob/1.9.0/ring-servlet/src/ring/util/servlet.clj#L37">ring</a>
 * </p>
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
     * Returns the SSL client certificate of the request, if one exists.
     */
    Optional<X509Certificate> sslClientCert();

    InputStream body();
}

