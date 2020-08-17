package org.katpara.mathematica.exceptions.linear.dep;

/**
 * The exception is thrown when the operation is valid for a given vector.
 * For example, multiplying with a matrix whose number of rows is different
 * then the vector dimensions.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class InvalidVectorOperationException extends RuntimeException {
    private static final long serialVersionUID = 783855621323807062L;

    /**
     * The default message in case of the specialized message is not provided.
     */
    private static final String MESSAGE = "The vector operation is invalid";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public InvalidVectorOperationException() {
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
    public InvalidVectorOperationException(final String message) {
        super(message);
    }
}
