package org.katpara.mathematica.exceptions;

/**
 * The class is used to hold exception messages for mathematica.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class ExceptionMessages {

    /**
     * General Exception Messages
     */
    public static final String NULL_ARGUMENT_PROVIDED_MESSAGE = "Null value provided";

    /*
     * Matrix Exception Messages
     */
    public static final String ROW_OUT_OF_BOUND_MESSAGE = "Can not access the row, the row out of bound.";
    public static final String COLUMN_OUT_OF_BOUND_MESSAGE = "Can not access the column, the column out of bound.";
    public static final String MATRIX_DIMENSION_MISMATCH_MESSAGE = "The matrices dimensions mismatch.";
    public static final String INVALID_MATRIX_DIMENSION_PROVIDED_MESSAGE = "The provided dimensions are not valid.";
    public static final String NOT_SYMMETRIC_MATRIX_MESSAGE = "The data are not symmetric.";
    public static final String NOT_INVERTIBLE_MATRIX_MESSAGE = "The matrix are non-invertible.";
    public static final String NOT_IDENTITY_MATRIX_MESSAGE = "The matrix are non-identity.";
}
