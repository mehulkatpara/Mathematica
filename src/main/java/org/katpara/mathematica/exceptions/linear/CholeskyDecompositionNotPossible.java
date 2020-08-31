package org.katpara.mathematica.exceptions.linear;

import org.katpara.mathematica.exceptions.ExceptionMessages;

public final class CholeskyDecompositionNotPossible extends RuntimeException {
    private static final long serialVersionUID = 616804828292592251L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public CholeskyDecompositionNotPossible() {
        super(ExceptionMessages.CHOLESKY_DECOMPOSITION_NOT_POSSIBLE_MESSAGE);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CholeskyDecompositionNotPossible(final String message) {
        super(message);
    }
}
