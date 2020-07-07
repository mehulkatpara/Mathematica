package org.katpara.mathematica.exceptions;

/**
 * The exception is useful when you are trying to perform an operation on
 * a matrix that is not allowed or doable.
 * For example, calculating a trace on a rectangular matrix.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class InvalidMatrixOperationException extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = 3852013709139213781L;

    /**
     * The default message in case of the specialized message is not provided.
     */
    private static final String MESSAGE = "The operation can't be performed on the matrix";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public InvalidMatrixOperationException() {
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
    public InvalidMatrixOperationException(final String message) {
        super(message);
    }
}
