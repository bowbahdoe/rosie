package dev.mccue.rosie;

import java.io.IOException;

/**
 * Code that knows how to handle an http request.
 */
@FunctionalInterface
public interface Handler {
    /**
     * Handles the given request.
     * @param request The http request being responded to.
     * @return Something which can be converted into a Response. This includes Response itself.
     * @throws IOException If some IO the handler performs fails.
     */
    IntoResponse handle(Request request) throws IOException;
}
