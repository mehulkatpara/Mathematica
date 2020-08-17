package org.katpara.mathematica.exceptions.linear;

import org.katpara.mathematica.exceptions.ExceptionMessages;

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
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotSquareMatrixException() {
        super(ExceptionMessages.NOT_SQUARE_MATRIX_MESSAGE);
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
