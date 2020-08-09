package org.katpara.mathematica.exceptions.linears;

import org.katpara.mathematica.exceptions.ExceptionMessages;

public final class NonInvertibleMatrixException extends RuntimeException {
    private static final long serialVersionUID = 946985623442548507L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NonInvertibleMatrixException() {
        super(ExceptionMessages.NOT_INVERTIBLE_MATRIX_MESSAGE);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NonInvertibleMatrixException(final String message) {
        super(message);
    }
}
