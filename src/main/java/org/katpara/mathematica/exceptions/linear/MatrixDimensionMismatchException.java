package org.katpara.mathematica.exceptions.linear;

import org.katpara.mathematica.exceptions.ExceptionMessages;

public final class MatrixDimensionMismatchException extends RuntimeException {
    private static final long serialVersionUID = 884013835571300430L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public MatrixDimensionMismatchException() {
        super(ExceptionMessages.MATRIX_DIMENSION_MISMATCH_MESSAGE);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public MatrixDimensionMismatchException(final String message) {
        super(message);
    }
}
