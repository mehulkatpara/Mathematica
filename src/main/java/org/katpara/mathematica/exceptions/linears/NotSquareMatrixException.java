package org.katpara.mathematica.exceptions.linears;

/**
 * Certain operations are only performed on a square matrix.
 * When you try to perform those operations on any non-square
 * matrix, this exception is thrown.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class NotSquareMatrixException extends RuntimeException {
    private static final long serialVersionUID = 6092069271078327984L;

    /**
     * The default message in case of the specialized message is not provided.
     */
    private static final String MESSAGE = "The matrix is not a square matrix.";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotSquareMatrixException() {
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
    public NotSquareMatrixException(final String message) {
        super(message);
    }
}
