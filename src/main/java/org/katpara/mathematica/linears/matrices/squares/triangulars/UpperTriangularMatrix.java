package org.katpara.mathematica.linears.matrices.squares.triangulars;

import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.linear.NotUpperTriangularMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.rectangulars.AnyRectangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;

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
        this(d, true);
    }

    /**
     * The constructor performs the sanity check based on the parameter value.
     *
     * @param d     the array data
     * @param check whether to check the sanity
     *
     * @throws NotUpperTriangularMatrixException when the sanity check fails
     */
    public UpperTriangularMatrix(final double[][] d, final boolean check) {
        super(d);

        if (check) {
            for (var i = 0; i < s[0]; i++) {
                for (var j = 0; j < i; j++) {
                    if (d[i][j] != 0)
                        throw new NotUpperTriangularMatrixException();
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
        for (var i = 0; i < s[0]; i++) {
            for (var j = i; j < s[0]; j++) {
                n[j][i] = d[i][j];
            }
        }

        return new LowerTriangularMatrix(n);
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
        if (m instanceof UpperTriangularMatrix) {
            var n = new double[s[0]][s[0]];
            for (var i = 0; i < s[0]; i++) {
                for (var j = i; j < s[1]; j++) {
                    n[i][j] = d[i][j] + _d[i][j];
                }
            }

            return new UpperTriangularMatrix(n);
        } else if (m instanceof DiagonalSquareMatrix) {
            var n = new double[s[0]][s[0]];
            for (var i = 0; i < s[0]; i++) {
                n[i][i] = d[i][i] + _d[i][i];

                var l = i + 1;
                System.arraycopy(d[i], l, n[i], l, s[0] - l);
            }

            return new UpperTriangularMatrix(n);
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
            for (var j = i; j < s[1]; j++) {
                n[i][j] = d[i][j] * scalar;
            }
        }

        return new UpperTriangularMatrix(n);
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
        if (m instanceof UpperTriangularMatrix)
            return new UpperTriangularMatrix(multiplyUpperTriangle(_d));

        if (m instanceof DiagonalSquareMatrix) {
            var n = new double[s[0]][s[0]];
            for (var i = 0; i < s[0]; i++) {
                for (var j = i; j < s[0]; j++) {
                    n[i][j] = d[i][j] * _d[j][j];
                }
            }
            return new UpperTriangularMatrix(n);
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
        var n = new double[s[0]][s[0]];
        System.arraycopy(d, 0, n, 0, s[0]);
        for (var i = 1; i < Math.abs(power); i++) {
            n = multiplyUpperTriangle(n);
        }

        if (power < 0) {
            n = doMultiplicativeInverse(n);
        }

        return new UpperTriangularMatrix(n);
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        return new UpperTriangularMatrix(doMultiplicativeInverse(d));
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
            for (var j = i; j < s[0]; j++) {
                n[i][j] = -d[i][j];
            }
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
                    for (var k = i; k < s[0]; k++) {
                        n[i][j] += d[i][k] * m[k][j];
                    }
                }
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
        for (var i = s[0]; i >= 0; i--) {
            for (var j = i; j < s[1]; j++) {
                if (i == j) {
                    n[i][j] = 1 / m[i][j];
                } else {
                    var sum = 0.0;
                    for (var k = i + 1; k <= j; k++) {
                        sum += m[i][k] * n[k][j];
                    }
                    n[i][j] = -sum / m[i][i];
                }
            }
        }
        return n;
    }
}
