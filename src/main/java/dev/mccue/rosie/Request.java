package dev.mccue.rosie;


import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.eclipse.jetty.http.HttpMethod;

/**
 * A request object, based on the structure Ring provides.
 * <p>
 * https://github.com/ring-clojure/ring/blob/1.9.0/ring-servlet/src/ring/util/servlet.clj#L37
 */
public final class Request {
    private final int serverPort;
    private final String serverName;
    private final String remoteAddr;
    private final String uri;
    private final String queryString; // nullable
    private final String scheme;
    private final HttpMethod requestMethod;
    private final String protocol;
    private final Map<String, String> headers;
    private final String contentType; // nullable
    private final Integer contentLength; // nullable
    private final String characterEncoding; // nullable
    private final X509Certificate sslClientCert; // nullable
    private final ServletInputStream body;


    private Request(HttpServletRequest httpServletRequest) throws IOException {
        this.serverPort = httpServletRequest.getServerPort();
        this.serverName = httpServletRequest.getServerName();
        this.remoteAddr = httpServletRequest.getRemoteAddr();
        this.uri = httpServletRequest.getRequestURI();
        this.queryString = httpServletRequest.getQueryString();
        this.scheme = httpServletRequest.getScheme();
        this.requestMethod = HttpMethod.fromString(httpServletRequest.getMethod().toUpperCase(Locale.ENGLISH));
        this.protocol = httpServletRequest.getProtocol();
        this.headers = Collections.unmodifiableMap(getHeaders(httpServletRequest));
        this.contentType = httpServletRequest.getContentType();
        this.contentLength = httpServletRequest.getContentLength() >= 0 ? httpServletRequest.getContentLength() : null;
        this.characterEncoding = httpServletRequest.getCharacterEncoding();
        this.sslClientCert = getClientCert(httpServletRequest);
        this.body = httpServletRequest.getInputStream();
    }

    static Request fromHttpServletRequest(HttpServletRequest httpServletRequest) throws IOException {
        return new Request(httpServletRequest);
    }

    private static Map<String, String> getHeaders(HttpServletRequest request) {
        final var headers = new HashMap<String, String>();

        final var headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final var headerName = headerNames.nextElement();
            headers.put(
                    headerName.toLowerCase(Locale.ENGLISH),
                    StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(
                                    request.getHeaders(headerName).asIterator(),
                                    Spliterator.ORDERED),
                            false)
                            .collect(Collectors.joining(","))
            );
        }

        return headers;
    }

    private static X509Certificate getClientCert(HttpServletRequest request) {
        final var certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        if (certs == null || certs.length == 0) {
            return null;
        } else {
            return certs[0];
        }
    }

    /**
     * Analogous to {@link HttpServletRequest#getServerPort()}.
     */
    public int serverPort() {
        return this.serverPort;
    }

    /**
     * Analogous to {@link HttpServletRequest#getServerName()}.
     */
    public String serverName() {
        return this.serverName;
    }

    /**
     * Analogous to {@link HttpServletRequest#getRemoteAddr()}.
     */
    public String remoteAddr() {
        return this.remoteAddr;
    }

    /**
     * Analogous to {@link HttpServletRequest#getRequestURI()}.
     */
    public String uri() {
        return this.uri;
    }

    /**
     * Analogous to {@link HttpServletRequest#getQueryString()}.
     */
    public Optional<String> queryString() {
        return Optional.ofNullable(this.queryString);
    }

    /**
     * Analogous to {@link HttpServletRequest#getScheme()}.
     */
    public String scheme() {
        return this.scheme;
    }

    /**
     * Analogous to {@link HttpServletRequest#getMethod()}, but will always be lowercase.
     */
    public HttpMethod requestMethod() {
        return this.requestMethod;
    }

    /**
     * Analogous to {@link HttpServletRequest#getProtocol()}.
     */
    public String protocol() {
        return this.protocol;
    }

    /**
     * Gives the headers for the request as a read only map of header name to value. All the header names are lower-case
     * and if there are multiple values for a header, they are represented in the map as a comma separated
     * String like \"value1,value2,value3\".
     */
    public Map<String, String> headers() {
        return this.headers;
    }

    /**
     * Analogous to {@link HttpServletRequest#getContentType()}, but will be an empty Optional
     * if the content length was not given in the request.
     */
    public Optional<String> contentType() {
        return Optional.ofNullable(this.contentType);
    }

    /**
     * Analogous to {@link HttpServletRequest#getContentLength()}, but will be an empty Optional
     * if the content length was reported as negative.
     */
    public Optional<Integer> contentLength() {
        return Optional.ofNullable(this.contentLength);
    }

    /**
     * Analogous to {@link HttpServletRequest#getCharacterEncoding()}, but will be an empty Optional
     * if the character encoding was not given in the request.
     */
    public Optional<String> characterEncoding() {
        return Optional.ofNullable(this.characterEncoding);
    }

    /**
     * Returns the SSL client certificate of the request, if one exists.
     */
    public Optional<X509Certificate> sslClientCert() {
        return Optional.ofNullable(this.sslClientCert);
    }

    /**
     * Analogous to {@link HttpServletRequest#getInputStream()}.
     */
    public ServletInputStream body() {
        return this.body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;
        return serverPort == request.serverPort
                && serverName.equals(request.serverName)
                && remoteAddr.equals(request.remoteAddr)
                && uri.equals(request.uri)
                && Objects.equals(queryString, request.queryString)
                && scheme.equals(request.scheme)
                && requestMethod.equals(request.requestMethod)
                && protocol.equals(request.protocol)
                && headers.equals(request.headers)
                && Objects.equals(contentType, request.contentType)
                && Objects.equals(contentLength, request.contentLength)
                && Objects.equals(characterEncoding, request.characterEncoding)
                && Objects.equals(sslClientCert, request.sslClientCert)
                && body.equals(request.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                serverPort,
                serverName,
                remoteAddr,
                uri,
                queryString,
                scheme,
                requestMethod,
                protocol,
                headers,
                contentType,
                contentLength,
                characterEncoding,
                sslClientCert,
                body
        );
    }

    @Override
    public String toString() {
        return "Request[" +
                "serverPort=" + serverPort +
                ", serverName='" + serverName + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", uri='" + uri + '\'' +
                ", queryString='" + queryString + '\'' +
                ", scheme='" + scheme + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", protocol='" + protocol + '\'' +
                ", headers=" + headers +
                ", contentType='" + contentType + '\'' +
                ", contentLength=" + contentLength +
                ", characterEncoding='" + characterEncoding + '\'' +
                ", sslClientCert=" + sslClientCert +
                ", body=" + body +
                ']';
    }
}

