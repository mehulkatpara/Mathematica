package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.rectangulars.AnyRectangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.LowerTriangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.UpperTriangularMatrix;
import org.katpara.mathematica.util.Rounding;

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
        for (var i = 0; i < n.length; i++) {
            for (var j = 0; j < n.length; j++) {
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
        for (var i = 0; i < s[0]; i++) {
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
     * @return the determinant of the square matrix
     */
    @Override
    public double getDeterminant() {
        var det = d[0][0];
        for (var i = 1; i < s[0]; i++)
            det *= d[i][i];

        return det;
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public int getRank() {
        var rank = s[0];
        for (var i = 0; i < s[0]; i++)
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
     * The method adds two matrices.
     *
     * @param m the matrix to add
     *
     * @return the resulting matrix
     */
    @Override
    protected final Matrix addMatrix(final Matrix m) {
        var _d = m.toArray();
        if (m instanceof DiagonalSquareMatrix) {
            var n = new double[s[0]];
            for (var i = 0; i < s[0]; i++)
                n[i] = d[i][i] + _d[i][i];

            return getInstance(n);
        }

        var n = new double[s[0]][s[1]];
        System.arraycopy(_d, 0, n, 0, s[0]);

        for (int i = 0; i < s[0]; i++) {
            n[i][i] = d[i][i] + n[i][i];
        }

        if (m instanceof LowerTriangularMatrix)
            return new LowerTriangularMatrix(n);

        if (m instanceof UpperTriangularMatrix)
            return new UpperTriangularMatrix(n);

        return getSquareMatrix(n);
    }

    /**
     * The method multiplies a scalar.
     *
     * @param scalar the scalar to multiply
     *
     * @return the resulting matrix
     */
    @Override
    protected final Matrix multiplyScalar(final double scalar) {
        var n = new double[s[0]];
        for (var i = 0; i < s[0]; i++) {
            n[i] = d[i][i] * scalar;
        }

        return getInstance(n);
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
        var _d = m.toArray();
        if(m instanceof DiagonalSquareMatrix) {
            var n = new double[s[0]];
            for (int i = 0; i < s[0]; i++) {
                n[i] = d[i][i] * _d[i][i];
            }

            return getInstance(n);
        }

        if(m instanceof UpperTriangularMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                for (int j = i; j < s[0]; j++) {
                    n[i][j] = d[i][i] * _d[i][j];
                }
            }

            return new UpperTriangularMatrix(n);
        }

        if(m instanceof LowerTriangularMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                for (int j = 0; j <= i; j++) {
                    n[i][j] = d[i][i] * _d[i][j];
                }
            }

            return new LowerTriangularMatrix(n);
        }

        var n = super.doMultiply(_d);
        return (n.length == n[0].length) ? getSquareMatrix(n) :
                       new AnyRectangularMatrix(n);
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
        var n = new double[s[0]];
        for (int i = 1; i < Math.abs(power); i++) {
            for (int j = 0; j < s[0]; j++) {
                n[j] = Math.pow(d[j][j], power);
            }
        }

        if (power < 0) {
            for (var i = 0; i < s[0]; i++) {
                n[i] = 1.0 / n[i];
            }
        }

        return getInstance(n);
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        var n = new double[s[0]];
        for (var i = 0; i < s[0]; i++) {
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
        for (var i = 0; i < s[0]; i++) {
            n[i] = -d[i][i];
        }

        return getInstance(n);
    }
}