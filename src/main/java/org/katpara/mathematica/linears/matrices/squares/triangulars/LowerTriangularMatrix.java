package org.katpara.mathematica.linears.matrices.squares.triangulars;

import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.linear.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linear.NotLowerTriangularMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;

import java.util.Arrays;

/**
 * The class represents a lower triangular matrix, where all the elements
 * above the main-diagonal is zero.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class LowerTriangularMatrix extends TriangularMatrix {
    private static final long serialVersionUID = 7196575322083508L;

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    public LowerTriangularMatrix(final double[][] d) {
        super(d);

        for (var i = 0; i < s[0]; i++) {
            for (var j = i + 1; j < s[1]; j++) {
                if (d[i][j] != 0)
                    throw new NotLowerTriangularMatrixException();
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
        return true;
    }

    /**
     * the method returns true if the matrix is an upper triangular matrix
     *
     * @return true if it's a upper triangular
     */
    @Override
    public boolean isUpperTriangular() {
        return false;
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
            for (int j = 0; j <= i; j++) {
                n[j][i] = d[i][j];
            }
        }
        return new UpperTriangularMatrix(n);
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
        if (m instanceof LowerTriangularMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                for (int j = 0; j <= i; j++) {
                    n[j][i] = d[i][j] + _d[i][j];
                }
            }

            return new LowerTriangularMatrix(n);
        } else if (m instanceof DiagonalSquareMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                n[i][i] = d[i][i] + _d[i][i];

                for (int j = 0; j < i; j++) {
                    n[j][i] = d[i][j];
                }
            }

            return new LowerTriangularMatrix(n);
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
        if (m instanceof LowerTriangularMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                for (int j = 0; j <= i; j++) {
                    n[j][i] = d[i][j] - _d[i][j];
                }
            }

            return new LowerTriangularMatrix(n);
        } else if (m instanceof DiagonalSquareMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                n[i][i] = d[i][i] - _d[i][i];

                for (int j = 0; j < i; j++) {
                    n[j][i] = d[i][j];
                }
            }

            return new LowerTriangularMatrix(n);
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
            for (var j = 0; j <= i; j++) {
                n[i][j] = d[i][j] * scalar;
            }
        }

        return new LowerTriangularMatrix(n);
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

        if (m instanceof LowerTriangularMatrix)
            return new LowerTriangularMatrix(multiplyLowerTriangle(m.toArray()));

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
        if(m == this)
            return IdentityMatrix.getInstance(s[0]);

        var _s = m.getSize();
        if (s[0] != _s[1])
            throw new MatrixDimensionMismatchException();

        if (m instanceof IdentityMatrix)
            return this;

        if (m instanceof LowerTriangularMatrix)
            return new LowerTriangularMatrix(multiplyLowerTriangle(m.multiplicativeInverse().toArray()));

        if (m instanceof DiagonalSquareMatrix)
            return new LowerTriangularMatrix(multiplyDiagonalTriangle(m.multiplicativeInverse().toArray()));

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
        for (var i = 0; i < s[0]; i++) {
            for (var j = 0; j <= i; j++) {
                if (i == j) {
                    n[i][i] = 1 / d[i][i];
                } else {
                    var sum = 0.0;
                    for (var k = 0; k <= i; k++) {
                        sum += d[i][k] * n[k][j];
                    }

                    n[i][j] = -sum / d[i][i];
                }
            }
        }

        return new LowerTriangularMatrix(n);
    }

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix additiveInverse() {
        var n = new double[s[0]][s[0]];
        for (var i = 0; i < s[0]; i++) {
            for (var j = 0; j <= i; j++) {
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
            n = multiplyLowerTriangle(n);
        }

        return new LowerTriangularMatrix(n);
    }

    /**
     * The method multiplies two lower matrix arrays.
     *
     * @param m the lower matrix array
     *
     * @return the resulting lower matrix array
     */
    private double[][] multiplyLowerTriangle(final double[][] m) {
        var n = new double[s[0]][s[0]];
        for (var i = 0; i < s[0]; i++) {
            for (var j = 0; j <= i; j++) {
                if (i == j) {
                    n[i][j] = d[i][j] * m[i][j];
                } else {
                    for (var k = j; k < s[0]; k++) {
                        n[i][j] += d[i][k] * m[k][j];
                    }
                }
            }
        }

        return n;
    }

    /**
     * The method multiplies the lower matrix array with diagonal matrix array
     *
     * @param m the diagonal matrix array
     *
     * @return the resulting lower matrix array
     */
    private double[][] multiplyDiagonalTriangle(final double[][] m) {
        var n = new double[s[0]][s[0]];
        for (var i = 0; i < s[0]; i++) {
            for (var j = 0; j <= i; j++) {
                n[i][j] = d[i][j] * m[j][j];
            }
        }

        return n;
    }

    /**
     * The method will check if the given matrix is a lower triangular matrix.
     * If so it will perform the forward substitution.
     * <p>
     * The method provides Ax = b solution.
     * The x will be returned and b is provided.
     *
     * @param b the array to evaluate with
     *
     * @return the resulting array
     *
     * @throws NotLowerTriangularMatrixException if the matrix is not lower triangular
     */
    @Override
    public double[] forwardSubstitution(final double[] b) {
        return super.doForwardSubstitution(b);
    }
}
