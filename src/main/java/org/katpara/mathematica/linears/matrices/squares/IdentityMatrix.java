package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.linears.InvalidMatrixDimensionProvidedException;
import org.katpara.mathematica.exceptions.linears.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linears.NotIdentityMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.util.Rounding;

import java.util.Arrays;

/**
 * The class represent an identity matrix.
 * Identity matrix has main diagonal 1, and the rest 0.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class IdentityMatrix extends DiagonalSquareMatrix {

    /**
     * creates a diagonal square matrix from an array of data.
     *
     * @param row the number of rows
     */
    public IdentityMatrix(final int row) {
        if(row <= 0)
            throw new InvalidMatrixDimensionProvidedException();

        d = new Number[row][row];
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d.length; j++) {
                d[i][j] = (i == j) ? 1 : 0;
            }
        }
        s = new int[]{d.length, d.length};
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
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @return the trace of the square matrix
     */
    @Override
    public double getTrace() {
        return d.length;
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
    @Override
    public double getTrace(final Rounding.Decimals decimals) {
        return d.length;
    }

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @return the determinant of the square matrix
     */
    @Override
    public double getDeterminant() {
        return 1;
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
     * The addition of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public Matrix add(final Matrix m) {
        if (m instanceof IdentityMatrix) {
            if(!Arrays.equals(this.getSize(), m.getSize()))
                throw new MatrixDimensionMismatchException();

            var n = new Number[d.length];
            Arrays.fill(n, 2);
            return new DiagonalSquareMatrix(n);
        }

        return super.add(m);
    }

    /**
     * The subtraction of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public Matrix subtract(final Matrix m) {
        return super.subtract(m);
        //TODO: When you have zero matrix implemented, redo this.
    }

    /**
     * The scalar multiplication of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    @Override
    public Matrix multiply(final double scalar) {
        var n = new Number[d.length];
        Arrays.fill(n, scalar);

        return new DiagonalSquareMatrix(n);
    }

    /**
     * The multiplication of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public Matrix multiply(final Matrix m) {
        return (m instanceof IdentityMatrix) ? this : m;
    }

    /**
     * The division of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public Matrix divide(final Matrix m) {
        return (m instanceof IdentityMatrix) ? this : super.divide(m);
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

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix additiveInverse() {
        var n = new Number[d.length];
        Arrays.fill(n, -1);

        return new DiagonalSquareMatrix(n);
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
        return this;
    }
}
