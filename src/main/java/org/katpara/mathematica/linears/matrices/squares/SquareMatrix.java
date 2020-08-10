package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.linears.InvalidMatrixDimensionProvidedException;
import org.katpara.mathematica.linears.matrices.AbstractMatrix;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.util.Rounding;

/**
 * An abstract class holding all the square matrices together.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public abstract class SquareMatrix extends AbstractMatrix {

    /**
     * Requires to chain down the construction
     */
    protected SquareMatrix() {
        super();
    }

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected SquareMatrix(final Number[][] d) {
        super(d);

        if (d.length != d[0].length)
            throw new InvalidMatrixDimensionProvidedException();
    }

    /**
     * The method will return true if the matrix is a
     * row vector, which is 1 x n
     *
     * @return true if it is a row vector
     */
    @Override
    public final boolean isRowVector() {
        return false;
    }

    /**
     * The method will return true if the matrix is a
     * column vector, which is n x 1.
     *
     * @return true if it is a column vector
     */
    @Override
    public final boolean isColumnVector() {
        return false;
    }

    /**
     * The method will return true if the matrix is a
     * square matrix, which is n x n.
     *
     * @return true if it is a square matrix
     */
    @Override
    public final boolean isSquareMatrix() {
        return true;
    }

    /**
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @return the trace of the square matrix
     */
    public final double getTrace() {
        return getTrace(Rounding.Decimals.FOUR);
    }

    /**
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @param decimals the decimal points of accuracy
     *
     * @return the trace of the square matrix
     */
    public final double getTrace(final Rounding.Decimals decimals) {
        var sum = 0.0;

        for (int i = 0; i < d.length; i++)
            sum += d[i][i].doubleValue();

        return Double.parseDouble(Rounding.round(sum, decimals));
    }

    /**
     * The power of an element.
     *
     * @param power the exponent
     *
     * @return the value after applying power
     */
    @Override
    public Matrix power(final double power) {
        if (power == 0) {
            return this;
        } else {
            //TODO: Implement The rest
            return null;
        }
    }

    /**
     * The absolute value of an element.
     *
     * @return the absolute value
     */
    @Override
    public final Number abs() {
        return this.getDeterminant();
    }

    /**
     * The absolute value of an element.
     *
     * @param decimals rounding to given decimal places
     *
     * @return the absolute value
     */
    @Override
    public final Number abs(final Rounding.Decimals decimals) {
        return this.getDeterminant(decimals);
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    public abstract boolean isSymmetric();

    /**
     * The method returns true if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    public abstract boolean isDiagonal();

    /**
     * The method returns true if the matrix is an identity matrix.
     *
     * @return true if it's an identity matrix
     */
    public abstract boolean isIdentity();

    /**
     * the method returns true if the matrix is a lower triangular matrix
     *
     * @return true if it's a lower triangular
     */
    public abstract boolean isLowerTriangular();

    /**
     * the method returns true if the matrix is an upper triangular matrix
     *
     * @return true if it's a upper triangular
     */
    public abstract boolean isUpperTriangular();

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @return the determinant of the square matrix
     */
    public abstract double getDeterminant();

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @param decimals the decimal decimals accuracy
     *
     * @return the determinant of the square matrix
     */
    public abstract double getDeterminant(final Rounding.Decimals decimals);
}
