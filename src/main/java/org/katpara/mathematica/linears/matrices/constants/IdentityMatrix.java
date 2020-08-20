package org.katpara.mathematica.linears.matrices.constants;

import org.katpara.mathematica.exceptions.linear.InvalidMatrixDimensionProvidedException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;
import org.katpara.mathematica.util.Rounding;

/**
 * The class represent an identity matrix.
 * Identity matrix has main diagonal 1, and the rest 0.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class IdentityMatrix extends DiagonalSquareMatrix {
    private static final long serialVersionUID = 7196575322083508L;

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    private IdentityMatrix(final double[][] d) {
        super(d);
    }

    /**
     * creates a diagonal square matrix from an array of data.
     *
     * @param row the number of rows
     */
    public static IdentityMatrix getInstance(final int row) {
        if(row <= 0)
            throw new InvalidMatrixDimensionProvidedException();

        var n = new double[row][row];
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n.length; j++) {
                n[i][j] = (i == j) ? 1 : 0;
            }
        }

        return new IdentityMatrix(n);
    }

    /**
     * The method returns true if the matrix is an identity matrix.
     *
     * @return true if it's an identity matrix
     */
    @Override
    public boolean isIdentity() {
        return true;
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public int getRank() {
        return d.length;
    }

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @param decimals the decimal decimals accuracy
     *
     * @return the determinant of the square matrix
     */
    @Override
    public double getDeterminant(final Rounding.Decimals decimals) {
        return 1;
    }

    /**
     * The method multiplies two matrices.
     *
     * @param m the matrix to multiply with
     *
     * @return the resulting matrix
     */
    @Override
    protected Matrix multiplyMatrix(final Matrix m) {
        return (m instanceof IdentityMatrix) ? this : m;
    }

    /**
     * The method calculates the power of a matrix.
     *
     * @param power the exponent
     *
     * @return the resulting square matrix
     */
    @Override
    protected Matrix calculatePower(final int power) {
        return this;
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        return this;
    }
}
