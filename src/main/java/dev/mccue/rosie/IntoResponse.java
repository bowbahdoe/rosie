package dev.mccue.rosie;

/**
 * Interface for a value that can be converted into a response.
 *
 * This is convenient to have, since it would let a user have their own
 * custom types work as return types from handler functions without much fanfare, while still
 * allowing returning a response object directly.
 */
@FunctionalInterface
public interface IntoResponse {
    /**
     * @return A response derived from this object.
     */
    Response intoResponse();
}
