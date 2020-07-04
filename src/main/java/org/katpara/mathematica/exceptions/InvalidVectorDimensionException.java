package org.katpara.mathematica.exceptions;

/**
 * The exception is thrown when the program wants to create a vector
 * with invalid dimensions, or when two or more vectors are interacting
 * with each other and they are in different dimensions.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class InvalidVectorDimensionException extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = 2151811694623431211L;

    /**
     * The default message in case of the specialized message is not provided.
     */
    private static final String MESSAGE = "The vector dimension is invalid";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public InvalidVectorDimensionException() {
        super(MESSAGE);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidVectorDimensionException(final String message) {
        super(message);
    }
}
