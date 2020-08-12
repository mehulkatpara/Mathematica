package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.linears.dep.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.AbstractMatrix;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.util.Rounding;

/**
 * An abstract class holding all the square matrices together.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public abstract class SquareMatrix extends AbstractMatrix {
    private static final long serialVersionUID = 876020393125503520L;

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected SquareMatrix(final double[][] d) {
        super(d);

        if (s[0] != s[1])
            throw new NotSquareMatrixException();
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

        for (int i = 0; i < s[0]; i++)
            sum += d[i][i];

        return Double.parseDouble(Rounding.round(sum, decimals));
    }

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @return the determinant of the square matrix
     */
    public final double getDeterminant() {
        return this.getDeterminant(Rounding.Decimals.FOUR);
    }

    /**
     * The absolute value of an element.
     *
     * @return the absolute value
     */
    @Override
    public final double abs() {
        return this.getDeterminant(Rounding.Decimals.FOUR);
    }

    /**
     * The absolute value of an element.
     *
     * @param decimals rounding to given decimal places
     *
     * @return the absolute value
     */
    @Override
    public final double abs(final Rounding.Decimals decimals) {
        return this.getDeterminant(decimals);
    }

    /**
     * The method calculates the power of a given matrix.
     *
     * @param power the exp value
     *
     * @return the resulting data
     */
    protected final double[][] doPower(final double power) {
        var n = new double[s[0]][s[1]];
        for (int i = 0; i < s[0]; i++) {
            System.arraycopy(d[i], 0, n[i], 0, s[1]);
        }
        for (int i = 0; i < power - 1; i++) {
            n = super.doMultiply(n);
        }
        return n;
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
     * @param decimals the decimal decimals accuracy
     *
     * @return the determinant of the square matrix
     */
    public abstract double getDeterminant(final Rounding.Decimals decimals);
}
