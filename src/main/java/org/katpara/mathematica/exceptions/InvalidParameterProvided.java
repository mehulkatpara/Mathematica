package org.katpara.mathematica.exceptions;

/**
 * The exception is thrown when an invalid parameter is passed as an argument.
 *
 * @since 1.0.0
 * @author Mehul Katpara
 */
public class InvalidParameterProvided extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = 3727500311644363939L;

    /**
     * The default message in case of the specialized message is not provided.
     */
    private static final String MESSAGE = "Invalid parameters";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public InvalidParameterProvided() {
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
    public InvalidParameterProvided(final String message) {
        super(message);
    }
}
