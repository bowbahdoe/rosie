package dev.mccue.rosie;

import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link Request} which delegates to another provided
 * {@link Request}.
 *
 * <p>
 *     This is provided for the purpose of making "enriched" requests
 *     more convenient to construct via subclassing.
 * </p>
 *
 * <p>
 *     This abstract class is generic over the kind of request being wrapped
 *     so that if there is a {@link Request} subtype which has more methods,
 *     a user can wrap and expose methods from that as well.
 * </p>
 *
 * <p>
 *     In a sense, this is the counterpart to {@link IntoResponse}. While {@link IntoResponse}
 *     allows a user to define a custom type and return that from their route handlers
 *     and have it be converted to a {@link Response}, making subtypes of {@link Request}
 *     would be required to "enrich" the request object without adding new parameters to route
 *     handlers. This accomplishes the goal of making those subtypes easier to construct.
 * </p>
 *
 * @param <Req> The exact kind of request being wrapped.
 */
public abstract class DelegatingRequest<Req extends Request> implements Request {
    /**
     * Request object being delegated to. Accessible directly
     * to subclasses in case a specific subclass of {@link Request}
     * is being tracked.
     */
    protected final Req delegate;

    /**
     * Constructs a delegating request.
     * @param delegate The request to delegate to.
     */
    public DelegatingRequest(Req delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
    }

    @Override
    public int serverPort() {
        return delegate.serverPort();
    }

    @Override
    public String serverName() {
        return delegate.serverName();
    }

    @Override
    public String remoteAddr() {
        return delegate.remoteAddr();
    }

    @Override
    public String uri() {
        return delegate.uri();
    }

    @Override
    public Optional<String> queryString() {
        return delegate.queryString();
    }

    @Override
    public String scheme() {
        return delegate.scheme();
    }

    @Override
    public String requestMethod() {
        return delegate.requestMethod();
    }

    @Override
    public String protocol() {
        return delegate.protocol();
    }

    @Override
    public Map<String, String> headers() {
        return delegate.headers();
    }

    @Override
    public Optional<X509Certificate> sslClientCert() {
        return delegate.sslClientCert();
    }

    @Override
    public InputStream body() {
        return delegate.body();
    }

    @Override
    public String toString() {
        return "DelegatingRequest[delegate=" + delegate + "]";
    }
}
