package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.linear.MatrixDimensionMismatchException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.rectangulars.AnyRectangularMatrix;
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
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    public AnySquareMatrix(final double[][] d) {
        super(d);
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    @Override
    public boolean isSymmetric() {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (d[i][j] != d[j][i]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * The method returns true if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    @Override
    public boolean isDiagonal() {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (i != j && d[i][j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * The method returns true if the matrix is an identity matrix.
     *
     * @return true if it's an identity matrix
     */
    @Override
    public boolean isIdentity() {
        for (int i = 0; i < d.length; i++) {
            if (d[i][i] != 1)
                return false;

            for (int j = 0; j < d[0].length; j++) {
                if (i != j && d[i][j] != 0)
                    return false;
            }
        }

        return true;
    }

    /**
     * the method returns true if the matrix is a lower triangular matrix
     *
     * @return true if it's a lower triangular
     */
    @Override
    public boolean isLowerTriangular() {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (j > i && d[i][j] != 0)
                    return false;
            }
        }

        return true;
    }

    /**
     * the method returns true if the matrix is an upper triangular matrix
     *
     * @return true if it's a upper triangular
     */
    @Override
    public boolean isUpperTriangular() {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (j < i && d[i][j] != 0)
                    return false;
            }
        }

        return true;
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
     * The method transposes the matrix.
     *
     * @return the transposed matrix
     */
    @Override
    public Matrix transpose() {
        return new AnySquareMatrix(super.doTranspose());
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
        var _s = m.getSize();

        if (s[1] != _s[0])
            throw new MatrixDimensionMismatchException();

        return mul(m, _s);
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
        var _s = m.getSize();

        if (s[1] != _s[0])
            throw new MatrixDimensionMismatchException();

        return mul(m.multiplicativeInverse(), _s);
    }

    /**
     * Method multiplies two matrices.
     *
     * @param m  the matrix to multiply
     * @param _s the size of the matrix
     *
     * @return the matrix
     */
    private Matrix mul(final Matrix m, final int[] _s) {
        if (m instanceof IdentityMatrix)
            return this;

        if (m instanceof NullMatrix)
            return NullMatrix.getInstance(s[0], _s[1]);

        var n = super.doMultiply(m.toArray());
        return (n.length == n[0].length) ? getSquareMatrix(n) :
                       new AnyRectangularMatrix(n);
    }

    /**
     * The scalar addition of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    @Override
    public final Matrix add(final double scalar) {
        if (scalar == 0)
            return this;

        return getSquareMatrix(super.doAdd(scalar));
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
        if (!Arrays.equals(getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        if (m instanceof NullMatrix)
            return this;

        return getSquareMatrix(super.doAdd(m.toArray()));
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
        if (!Arrays.equals(getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        if (this == m)
            return NullMatrix.getInstance(s[0]);

        if (m instanceof NullMatrix)
            return this;

        return getSquareMatrix(super.doSubtract(m.toArray()));
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
        if (scalar == 0)
            return NullMatrix.getInstance(s[0]);
        if (scalar == 1)
            return this;
        if (scalar == -1)
            return this.additiveInverse();

        return getSquareMatrix(super.doMultiply(scalar));
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
        return getSquareMatrix(super.doAdditiveInverse());
    }

    /**
     * The power of an element.
     *
     * @param power the exponent
     *
     * @return the value after applying power
     */
    @Override
    public Matrix power(final int power) {
        if (power == 0)
            return IdentityMatrix.getInstance(s[0]);

        if (power == 1)
            return this;

        return getSquareMatrix(doPower(power));
    }

    /**
     * The method returns a customized matrix based on the array size.
     *
     * @param n the matrix data
     *
     * @return the square matrix
     */
    protected Matrix getSquareMatrix(final double[][] n) {
        // TODO: More detailed such as 2x2 and 3x3
        return new AnySquareMatrix(n);
    }
}
