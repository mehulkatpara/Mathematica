package org.katpara.mathematica.exceptions.linears;

/**
 * The exception is useful when dealing with Matrices. It is usually
 * thrown when the matrices are not on the same dimensions.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class InvalidMatrixDimensionException extends RuntimeException {
    private static final long serialVersionUID = 8141142525669921693L;

    /**
     * The default message in case of the specialized message is not provided.
     */
    private static final String MESSAGE = "The matrix dimension is invalid";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public InvalidMatrixDimensionException() {
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
    public InvalidMatrixDimensionException(final String message) {
        super(message);
    }
}
