package org.katpara.mathematica.exceptions;

/**
 * The exception is thrown when the program is processing on a null
 * argument passed where the system doesn't expect a null argument.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class NotInvertibleException extends RuntimeException {
    private static final long serialVersionUID = 1392183545974749989L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotInvertibleException() {
        super(ExceptionMessages.NOT_INVERTIBLE_MESSAGE);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotInvertibleException(final String message) {
        super(message);
    }
}
