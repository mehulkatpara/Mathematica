package org.katpara.mathematica.linears.matrices.squares.triangulars;

import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.linear.NotLowerTriangularMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.rectangulars.AnyRectangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;

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
     * The public constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    public LowerTriangularMatrix(final double[][] d) {
        this(d, false);
    }

    /**
     * The constructor performs the snity check based on the boolean parameter.
     *
     * @param d     the array data
     * @param check whether to check for sanity or no
     *
     * @throws NotLowerTriangularMatrixException when the sanity check fails
     */
    public LowerTriangularMatrix(final double[][] d, final boolean check) {
        super(d);

        if (check) {
            for (var i = 0; i < s[0]; i++) {
                for (var j = i + 1; j < s[1]; j++) {
                    if (d[i][j] != 0)
                        throw new NotLowerTriangularMatrixException();
                }
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
     * The method adds two matrices.
     *
     * @param m the matrix to add
     *
     * @return the resulting matrix
     */
    @Override
    protected Matrix addMatrix(final Matrix m) {
        var _d = m.toArray();
        if (m instanceof LowerTriangularMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                for (int j = 0; j <= i; j++) {
                    n[i][j] = d[i][j] + _d[i][j];
                }
            }

            return new LowerTriangularMatrix(n);
        } else if (m instanceof DiagonalSquareMatrix) {
            var n = new double[s[0]][s[0]];
            for (int i = 0; i < s[0]; i++) {
                n[i][i] = d[i][i] + _d[i][i];
                System.arraycopy(d[i], 0, n[i], 0, i);
            }

            return new LowerTriangularMatrix(n);
        }

        return getSquareMatrix(super.doAdd(_d));
    }

    /**
     * The method multiplies a scalar.
     *
     * @param scalar the scalar to multiply
     *
     * @return the resulting matrix
     */
    @Override
    protected Matrix multiplyScalar(final double scalar) {
        var n = new double[s[0]][s[0]];
        for (var i = 0; i < s[0]; i++) {
            for (var j = 0; j <= i; j++) {
                n[i][j] = d[i][j] * scalar;
            }
        }

        return new LowerTriangularMatrix(n);
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
        if (m instanceof LowerTriangularMatrix)
            return new LowerTriangularMatrix(multiplyLowerTriangle(m.toArray()));

        if (m instanceof DiagonalSquareMatrix)
            return new LowerTriangularMatrix(multiplyDiagonalTriangle(m.toArray()));

        var n = super.doMultiply(m.toArray());
        return (n.length == n[0].length) ? getSquareMatrix(n) :
                       new AnyRectangularMatrix(n);
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        return new LowerTriangularMatrix(doMultiplicativeInverse(d));
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
     * The method calculates the power of a matrix.
     *
     * @param power the exponent
     *
     * @return the resulting square matrix
     */
    @Override
    protected Matrix calculatePower(final int power) {
        var n = new double[s[0]][s[0]];
        System.arraycopy(d, 0, n, 0, s[0]);
        for (int i = 1; i < Math.abs(power); i++) {
            n = multiplyLowerTriangle(n);
        }

        if (power < 0) {
            n = doMultiplicativeInverse(n);
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
     * The method calculates the multiplicative inverse of the matrix.
     *
     * @param m the matrix data
     *
     * @return the inverse data
     *
     * @throws NotInvertibleException if the inversion is not possible
     */
    private double[][] doMultiplicativeInverse(final double[][] m) {
        for (var i = 0; i < s[0]; i++) {
            if (m[i][i] == 0)
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
