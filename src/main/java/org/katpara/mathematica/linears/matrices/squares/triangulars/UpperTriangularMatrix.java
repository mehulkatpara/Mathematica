package org.katpara.mathematica.linears.matrices.squares.triangulars;

import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.linear.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linear.NotUpperTriangularMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;

import java.util.Arrays;

/**
 * The class represents an upper triangular matrix, where all the elements
 * below the main-diagonal is zero.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class UpperTriangularMatrix extends TriangularMatrix {
    private static final long serialVersionUID = 825461631784142004L;

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    public UpperTriangularMatrix(final double[][] d) {
        super(d);

        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < i; j++) {
                if (d[i][j] != 0)
                    throw new NotUpperTriangularMatrixException();
            }
        }
    }

    /**
     * the method returns true if the matrix is a lower triangular matrix
     *
     * @return true if it's a lower triangular
     */
    @Override
    public boolean isLowerTriangular() {
        return false;
    }

    /**
     * the method returns true if the matrix is an upper triangular matrix
     *
     * @return true if it's a upper triangular
     */
    @Override
    public boolean isUpperTriangular() {
        return true;
    }

    /**
     * The method transposes the matrix.
     *
     * @return the transposed matrix
     */
    @Override
    public Matrix transpose() {
        var n = new double[s[0]][s[0]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = i; j < s[0]; j++) {
                n[j][i] = d[i][j];
            }
        }

        return new LowerTriangularMatrix(n);
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

        var _d = m.toArray();
        if (m instanceof UpperTriangularMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                for (int j = i; j < s[1]; j++) {
                    n[i][j] = d[i][j] + _d[i][j];
                }
            }

            return new UpperTriangularMatrix(n);
        } else if (m instanceof DiagonalSquareMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                n[i][i] = d[i][i] + _d[i][i];
                System.arraycopy(d[i], i + 1, n[i], i + 1, s[0]);
            }

            return new UpperTriangularMatrix(n);
        }

        return getSquareMatrix(super.doAdd(_d));
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
        if (this == m)
            return NullMatrix.getInstance(s[0]);

        if (!Arrays.equals(getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        if (m instanceof NullMatrix)
            return this;

        var _d = m.toArray();
        if (m instanceof UpperTriangularMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                for (int j = i; j < s[1]; j++) {
                    n[i][j] = d[i][j] - _d[i][j];
                }
            }

            return new UpperTriangularMatrix(n);
        } else if (m instanceof DiagonalSquareMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                n[i][i] = d[i][i] - _d[i][i];
                System.arraycopy(d[i], i + 1, n[i], i + 1, s[0]);
            }

            return new UpperTriangularMatrix(n);
        }

        return getSquareMatrix(super.doSubtract(_d));
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

        var n = new double[s[0]][s[0]];
        for (var i = 0; i < s[0]; i++) {
            for (var j = i; j < s[1]; j++) {
                n[i][j] = d[i][j] * scalar;
            }
        }

        return new UpperTriangularMatrix(n);
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

        if (s[0] != _s[1])
            throw new MatrixDimensionMismatchException();

        if (m instanceof IdentityMatrix)
            return this;

        if (m instanceof NullMatrix)
            return NullMatrix.getInstance(s[0], _s[1]);

        if (m instanceof UpperTriangularMatrix)
            return new UpperTriangularMatrix(multiplyUpperTriangle(m.toArray()));

        if (m instanceof DiagonalSquareMatrix)
            return new LowerTriangularMatrix(multiplyDiagonalTriangle(m.toArray()));

        return getSquareMatrix(super.doMultiply(m.toArray()));
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
        if (m == this)
            return IdentityMatrix.getInstance(s[0]);

        var _s = m.getSize();
        if (s[0] != _s[1])
            throw new MatrixDimensionMismatchException();

        if (m instanceof IdentityMatrix)
            return this;

        if (m instanceof UpperTriangularMatrix)
            return new UpperTriangularMatrix(multiplyUpperTriangle(m.multiplicativeInverse().toArray()));

        if (m instanceof DiagonalSquareMatrix)
            return new UpperTriangularMatrix(multiplyDiagonalTriangle(m.multiplicativeInverse().toArray()));

        return getSquareMatrix(super.doMultiply(m.multiplicativeInverse().toArray()));
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        for (var i = 0; i < s[0]; i++) {
            if (d[i][i] == 0)
                throw new NotInvertibleException();
        }

        var n = new double[s[0]][s[0]];
        for (int i = s[0]; i >= 0; i--) {
            for (int j = i; j < s[1]; j++) {
                if (i == j) {
                    n[i][j] = 1 / d[i][j];
                } else {
                    var sum = 0.0;
                    for (int k = i + 1; k <= j; k++) {
                        sum += d[i][k] * n[k][j];
                    }
                    n[i][j] = -sum / d[i][i];
                }
            }
        }

        return new UpperTriangularMatrix(n);
    }

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix additiveInverse() {
        var n = new double[s[0]][s[0]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = i; j < s[0]; j++) {
                n[i][j] = -d[i][j];
            }
        }

        return new LowerTriangularMatrix(n);
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
        if (power == 1)
            return this;
        if (power == 0)
            return IdentityMatrix.getInstance(s[0]);
        if (power == -1)
            return multiplicativeInverse();

        var n = new double[s[0]][s[0]];
        System.arraycopy(d, 0, n, 0, s[0]);

        for (int i = 1; i < power; i++) {
            n = multiplyUpperTriangle(n);
        }

        return new UpperTriangularMatrix(n);
    }

    /**
     * The method multiplies two upper matrix arrays.
     *
     * @param m the lower matrix array
     *
     * @return the resulting lower matrix array
     */
    private double[][] multiplyUpperTriangle(final double[][] m) {
        var n = new double[s[0]][s[0]];
        for (var i = 0; i < s[0]; i++) {
            for (var j = i; j < s[1]; j++) {
                if (i == j) {
                    n[i][j] = d[i][j] * m[i][j];
                } else {
                    for (int k = i; k < s[0]; k++) {
                        n[i][j] += d[i][k] * m[k][j];
                    }
                }
            }
        }

        return n;
    }

    /**
     * The method multiplies the upper matrix array with diagonal matrix array
     *
     * @param m the diagonal matrix array
     *
     * @return the resulting lower matrix array
     */
    private double[][] multiplyDiagonalTriangle(final double[][] m) {
        var n = new double[s[0]][s[0]];
        for (var i = 0; i < s[0]; i++) {
            for (var j = i; j < s[0]; j++) {
                n[i][j] = d[i][j] * m[j][j];
            }
        }
        return n;
    }
}
