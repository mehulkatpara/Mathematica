package org.katpara.mathematica.exceptions;

public class InvalidMatrixDimension extends RuntimeException {
    @java.io.Serial
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
    public InvalidMatrixDimension() {
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
    public InvalidMatrixDimension(final String message) {
        super(message);
    }
}
