package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.MatrixDimensionMismatchException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.LowerTriangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.TriangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.UpperTriangularMatrix;
import org.katpara.mathematica.util.Rounding;

import java.util.Arrays;

/**
 * The class represents a diagonal square matrix in the system.
 * A diagonal square matrix has all the elements zero, outside
 * of it's main diagonal.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class DiagonalSquareMatrix extends AnySquareMatrix {
    private static final long serialVersionUID = 952182322882023415L;

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected DiagonalSquareMatrix(final double[][] d) {
        super(d);
    }

    /**
     * creates a diagonal square matrix from an array of data.
     *
     * @param n the matrix elements
     */
    public static DiagonalSquareMatrix getInstance(final double[] n) {
        if (n == null || n.length <= 0)
            throw new NullArgumentProvidedException();

        var _d = new double[n.length][n.length];
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n.length; j++) {
                _d[i][j] = (i == j) ? n[i] : 0;
            }
        }

        return new DiagonalSquareMatrix(_d);
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    @Override
    public final boolean isSymmetric() {
        return true;
    }

    /**
     * The method returns true if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    @Override
    public final boolean isDiagonal() {
        return true;
    }

    /**
     * The method returns true if the matrix is an identity matrix.
     *
     * @return true if it's an identity matrix
     */
    @Override
    public boolean isIdentity() {
        for (int i = 0; i < s[0]; i++) {
            if (d[i][i] != 1)
                return false;
        }

        return true;
    }

    /**
     * the method returns true if the matrix is a lower triangular matrix
     *
     * @return true if it's a lower triangular
     */
    @Override
    public final boolean isLowerTriangular() {
        return true;
    }

    /**
     * the method returns true if the matrix is an upper triangular matrix
     *
     * @return true if it's a upper triangular
     */
    @Override
    public final boolean isUpperTriangular() {
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
        var det = d[0][0];

        for (int i = 1; i < d.length; i++)
            det *= d[i][i];

        return Double.parseDouble(Rounding.round(det, decimals));
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public int getRank() {
        var rank = d.length;

        for (int i = 0; i < d.length; i++)
            if (d[i][i] == 0)
                rank -= 1;

        return rank;
    }

    /**
     * The method transposes the matrix.
     *
     * @return the transposed matrix
     */
    @Override
    public final Matrix transpose() {
        return this;
    }

    /**
     * The addition of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public final Matrix add(final Matrix m) {
        if (m instanceof TriangularMatrix || m instanceof DiagonalSquareMatrix) {
            if (!Arrays.equals(getSize(), m.getSize()))
                throw new MatrixDimensionMismatchException();

            if (m instanceof DiagonalSquareMatrix) {
                var _d = m.toArray();
                var n = new double[s[0]];
                for (int i = 0; i < s[0]; i++)
                    n[i] = d[i][i] + _d[i][i];

                return getInstance(n);
            }

            var n = super.doAdd(m.toArray());
            if (m instanceof LowerTriangularMatrix)
                return new LowerTriangularMatrix(n);

            if (m instanceof UpperTriangularMatrix)
                return new UpperTriangularMatrix(n);
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
    public final Matrix subtract(final Matrix m) {
        if (m instanceof TriangularMatrix || m instanceof DiagonalSquareMatrix) {
            if (!Arrays.equals(getSize(), m.getSize()))
                throw new MatrixDimensionMismatchException();

            if (m instanceof DiagonalSquareMatrix) {
                var _d = m.toArray();
                var n = new double[s[0]];
                for (int i = 0; i < s[0]; i++)
                    n[i] = d[i][i] - _d[i][i];

                return getInstance(n);
            }

            var n = super.doSubtract(m.toArray());
            if (m instanceof LowerTriangularMatrix)
                return new LowerTriangularMatrix(n);

            if (m instanceof UpperTriangularMatrix)
                return new UpperTriangularMatrix(n);
        }

        return super.subtract(m);
    }

    /**
     * The scalar multiplication of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    @Override
    public final Matrix multiply(final double scalar) {
        if (scalar == 0)
            return NullMatrix.getInstance(s[0]);
        if (scalar == 1)
            return this;
        if (scalar == -1)
            return this.additiveInverse();

        var n = new double[s[0]];
        for (int i = 0; i < s[0]; i++) {
            n[i] = d[i][i] * scalar;
        }

        return getInstance(n);
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
        if (m instanceof TriangularMatrix ||
                    m instanceof DiagonalSquareMatrix) {
            if (s[1] != m.getSize()[0])
                throw new MatrixDimensionMismatchException();

            var n = super.doMultiply(m.toArray());
            return mul(m, n);
        }

        return super.multiply(m);
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
        if (m instanceof TriangularMatrix ||
                    m instanceof DiagonalSquareMatrix) {
            if (s[1] != m.getSize()[0])
                throw new MatrixDimensionMismatchException();

            var n = super.doMultiply(m.multiplicativeInverse().toArray());
            return mul(m, n);
        }

        return super.divide(m);
    }

    /**
     * The method implements some multiplication logic.
     *
     * @param m the matrix to multiply
     * @param n the matrix data
     *
     * @return the matrix
     */
    protected Matrix mul(final Matrix m, final double[][] n) {
        if (m instanceof LowerTriangularMatrix)
            return new LowerTriangularMatrix(n);

        if (m instanceof UpperTriangularMatrix)
            return new UpperTriangularMatrix(n);


        var _n = new double[s[0]];
        for (int i = 0; i < s[0]; i++) {
            _n[i] = n[i][i];
        }

        return getInstance(_n);
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        var n = new double[s[0]];
        for (int i = 0; i < s[0]; i++) {
            n[i] = 1.0 / d[i][i];
        }

        return DiagonalSquareMatrix.getInstance(n);
    }

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    @Override
    public final Matrix additiveInverse() {
        var n = new double[s[0]];
        for (int i = 0; i < s[0]; i++) {
            n[i] = -d[i][i];
        }

        return getInstance(n);
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
        var n = new double[d.length];
        for (int i = 0; i < d.length; i++)
            n[i] = Math.pow(d[i][i], power);

        return getInstance(n);
    }
}