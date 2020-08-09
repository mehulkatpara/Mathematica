package org.katpara.mathematica.exceptions.linears;

import org.katpara.mathematica.exceptions.ExceptionMessages;

public final class RowOutOfBoundException extends RuntimeException {
    private static final long serialVersionUID = 841718963340856695L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public RowOutOfBoundException() {
        super(ExceptionMessages.ROW_OUT_OF_BOUND_MESSAGE);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RowOutOfBoundException(final String message) {
        super(message);
    }
}
