package dev.mccue.rosie;

/**
 * Interface for code that handles a {@link Request}.
 *
 * @param <Req> The specific kind of {@link Request} to handle.
 */
public interface Handler<Req extends Request> {
    /**
     * Handles the request.
     * @param request The request.
     * @return Something which can be turned into a {@link Response}
     * @throws Exception If something goes wrong.
     */
    IntoResponse handle(Req request) throws Exception;
}
