package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.linears.MatrixDimensionMismatchException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.util.Rounding;

import java.util.Arrays;

/**
 * The class represents any N x N square matrix. This is the most general class.
 * If your matrix is a specific type of Matrix, then please use that matrix type,
 * that way the computation to calculate various properties and operations would
 * be most optimized.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class AnySquareMatrix extends SquareMatrix {
    private static final long serialVersionUID = 978698801528776001L;

    /**
     * Requires to chain down the construction
     */
    protected AnySquareMatrix() {
    }

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected AnySquareMatrix(final Number[][] d) {
        super(d);
    }

    /**
     * The method checks if the matrix is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    @Override
    public boolean isSymmetric() {
        for (int i = 0; i < d.length; i++)
            for (int j = 0; j < d[0].length; j++)
                if (d[i][j].doubleValue() != d[j][i].doubleValue())
                    return false;

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
        return 0;
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
        return 0;
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
        return 0;
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
        return 0;
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
        return 0;
    }

    /**
     * The method transposes the matrix.
     *
     * @return the transposed matrix
     */
    @Override
    public Matrix transpose() {
        return null;
    }

    /**
     * The method perform deep-cloning.
     *
     * @return the cloned matrix
     */
    @Override
    public Matrix copy() {
        return null;
    }

    /**
     * The scalar addition of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    @Override
    public Matrix add(final double scalar) {
        var n = new Number[d.length][d.length];
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d.length; j++) {
                n[i][j] = d[i][j].doubleValue() + scalar;
            }
        }

        return new AnySquareMatrix(n);
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
        if (!Arrays.equals(this.getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        Number[][] _d = m.toArray(), n = new Number[d.length][d.length];
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d.length; j++) {
                n[i][j] = d[i][j].doubleValue() + _d[i][j].doubleValue();
            }
        }

        return new AnySquareMatrix(n);
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
        if (!Arrays.equals(this.getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        Number[][] _d = m.toArray(), n = new Number[d.length][d.length];
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d.length; j++) {
                n[i][j] = d[i][j].doubleValue() - _d[i][j].doubleValue();
            }
        }

        return new AnySquareMatrix(n);
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
        return null;
    }

    /**
     * The multiplication of two elements.
     *
     * @param e the element
     *
     * @return the element
     */
    @Override
    public Matrix multiply(final Matrix e) {
        return null;
    }

    /**
     * The division of two elements.
     *
     * @param e the element
     *
     * @return the element
     */
    @Override
    public Matrix divide(final Matrix e) {
        return null;
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        return null;
    }

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix additiveInverse() {
        return null;
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
        return null;
    }
}
