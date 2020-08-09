package org.katpara.mathematica.exceptions.linears;

import org.katpara.mathematica.exceptions.ExceptionMessages;

public final class NotLowerTriangularMatrixException extends RuntimeException {
    private static final long serialVersionUID = 786653691413629307L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotLowerTriangularMatrixException() {
        super(ExceptionMessages.NOT_LOWER_TRIANGUALR_MATRIX_MESSAGE);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotLowerTriangularMatrixException(final String message) {
        super(message);
    }
}
