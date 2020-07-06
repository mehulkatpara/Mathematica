package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.exceptions.InvalidMatrixDimensionException;

import java.util.stream.IntStream;

import static org.katpara.mathematica.linears.matrices.Matrix.MatrixType.*;

public class MatrixUtility {
    /**
     * The method will return a zero or null matrix, whose all the elements are zero.
     *
     * @param m the number of rows
     * @param n the number of columns
     *
     * @return an {@link ArrayMatrix} with all 0 entries
     *
     * @throws InvalidMatrixDimensionException when # of row + # of column < 2;
     *                                         this ensures that at least one element
     *                                         exist all the time.
     */
    static ArrayMatrix getZeroMatrix(final int m, final int n) {
        if (m + n < 2)
            throw new InvalidMatrixDimensionException("Zero matrix should have at least one element");

        var e = new Number[m][n];
        IntStream.range(0, m).forEach(i -> IntStream.range(0, n).forEach(j -> e[i][j] = 0));
        return new ArrayMatrix(e, ZERO);
    }

    /**
     * The method will create a pascal's square matrix.
     *
     * @param n the number of rows and columns of a square matrix
     * @param t the type of matrix
     *          i.e. UPPER, LOWER or SYMMETRIC
     *
     * @return a Pascal's {@link Matrix}
     *
     * @throws InvalidMatrixDimensionException when n < 1, at least one element should exist.
     */
    static ArrayMatrix getPascalMatrix(final int n, final Matrix.PascalMatrixType t) {
        if (n < 1)
            throw new InvalidMatrixDimensionException("Pascal's matrix should have at least one element");

        var _ref = new Object() {
            final Number[][] e = new Number[n][n];
            Number[] r;
        };

        if (t == Matrix.PascalMatrixType.UPPER) {
            IntStream.range(0, n).forEach(i -> {
                IntStream.range(0, n).forEach(j -> _ref.e[i][j] = (i == j) ? 1
                        : (i == 0) ? 1 : ((j < i) ? 0
                        : _ref.e[i][j - 1].longValue() + _ref.r[j - 1].longValue()));

                _ref.r = _ref.e[i];
            });
        } else if (t == Matrix.PascalMatrixType.LOWER) {
            IntStream.range(0, n).forEach(i -> {
                IntStream.range(0, n).forEach(j -> _ref.e[i][j] = (i == j) ? 1
                        : (i < j) ? 0 : ((j == 0) ? 1
                        : _ref.r[j].intValue() + _ref.r[j - 1].longValue()));

                _ref.r = _ref.e[i];
            });
        } else {
            IntStream.range(0, n).forEach(i -> {
                IntStream.range(0, n).forEach(j -> _ref.e[i][j] = (i == 0 || j == 0) ? 1
                        : _ref.e[i][j - 1].longValue() + _ref.r[j].longValue());

                _ref.r = _ref.e[i];
            });
        }

        return new ArrayMatrix(_ref.e, PASCAL);
    }

    /**
     * In mathematics, a matrix of one, or all-ones matrix is a matrix whose all elements are 1.
     *
     * @param m the number of rows
     * @param n the number of columns
     *
     * @return a one matrix
     *
     * @throws InvalidMatrixDimensionException when m + n < 2
     */
    static ArrayMatrix getOneMatrix(final int m, final int n) {
        if (m + n < 2)
            throw new InvalidMatrixDimensionException("One matrix should have at least one element");

        var e = new Number[m][n];
        IntStream.range(0, m).forEach(i -> IntStream.range(0, n).forEach(j -> e[i][j] = 1));
        return new ArrayMatrix(e, ONE);
    }

    /**
     * A lehmer matrix is a constant systematic square matrix.
     *
     * @param n the number of rows and columns
     *
     * @return InvalidMatrixDimensionException if n < 1
     */
    public static ArrayMatrix getLehmerMatrix(final int n) {
        if (n < 1)
            throw new InvalidMatrixDimensionException("Lehmer matrix should have at least one element");

        var e = new Number[n][n];
        IntStream.range(0, n).forEach(i ->
                IntStream.range(0, n).forEach(j -> e[i][j] = (j >= i) ?
                        ((double) i + 1) / ((double) j + 1) :
                        ((double) j + 1) / ((double) i + 1)));

        return new ArrayMatrix(e, LEHMER);
    }

    /**
     * The identity matrix is a square matrix whose diagonal is always 1 and all the
     * other elements are 0.
     *
     * @param n the number of rows and columns
     *
     * @return an identity {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n < 1
     */
    public static ArrayMatrix getIdentityMatrix(final int n) {
        if (n < 1)
            throw new InvalidMatrixDimensionException("Identity matrix should have at least one element");

        var e = new Number[n][n];
        IntStream.range(0, n).forEach(i -> IntStream.range(0, n).forEach(j -> e[i][j] = (i == j) ? 1 : 0));
        return new ArrayMatrix(e, IDENTITY);
    }

    /**
     * A Hilbert matrix is a square matrix with entries being the unit fractions.
     *
     * @param n the number of rows and columns
     *
     * @return a hilbert {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n < 1
     */
    public static ArrayMatrix getHilbertMatrix(final int n) {
        if (n < 1)
            throw new InvalidMatrixDimensionException("Hilbert matrix should have at least one element");

        var e = new Number[n][n];
        IntStream.range(0, n).forEach(i -> IntStream.range(0, n).forEach(j ->
                e[i][j] = 1 / ((double) (i + 1) + (double) (j + 1) - 1)));
        return new ArrayMatrix(e, HILBERT);
    }

    /**
     * An exchange matrix is a square matrix whose counterdiagonal is always 1 and the
     * rest of the elements are 0.
     *
     * @param n the square matrix
     *
     * @return an exchange {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n < 1
     */
    public static ArrayMatrix getExchangeMatrix(final int n) {
        if (n < 1)
            throw new InvalidMatrixDimensionException("Exchange matrix should have at least one element");

        var e = new Number[n][n];
        IntStream.range(0, n).forEach(i -> IntStream.range(0, n).forEach(j -> e[i][j] = (j == n - i - 1) ? 1 : 0));
        return new ArrayMatrix(e, EXCHANGE);
    }
}
