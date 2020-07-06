package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.exceptions.InvalidMatrixDimensionException;
import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;

/**
 * The interface defines a Matrix in the system, and it's operations.
 * Mathematically A matrix is an rectangular object of numbers, which
 * has rows and columns.
 * <p>
 * The mathematical application of a matrix is
 * to perform a learning transformation of a space. It can scale up or
 * down, turn and twist the space. That can lead to deformation of
 * vectors as well.
 * </p>
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public interface Matrix {

    /**
     * In linear algebra, a zero matrix or null matrix is a matrix whose
     * all the elements are zero.
     *
     * @return true if the matrix is zero or null matrix.
     */
    boolean isZeroMatrix();

    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    int[] getDimension();

    /**
     * The method returns all the elements of a matrix.
     *
     * @return the matrix elements
     */
    Number[][] getElements();

    /**
     * The method will return true if the matrix is a
     * row vector, which is 1 x n
     *
     * @return true if it is a row vector
     */
    boolean isRowVector();

    /**
     * The method will return true if the matrix is a
     * column vector, which is n x 1.
     *
     * @return true if it is a column vector
     */
    boolean isColumnVector();

    /**
     * The method will return true if the matrix is a
     * square matrix, which is n x n.
     *
     * @return true if it is a square matrix
     */
    boolean isSquareMatrix();

    /**
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @return the trace of the square matrix
     */
    double trace();

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @return the determinant of the square matrix
     */
    double determinant();

    /**
     * The method will return a zero or null matrix, whose all the elements are zero.
     *
     * @param m the number of rows
     * @param n the number of columns
     *
     * @return a {@link Matrix} with all 0 entries
     *
     * @throws InvalidMatrixDimensionException when # of row + # of column < 2;
     *                                         this ensures that at least one element
     *                                         exist all the time.
     */
    static ArrayMatrix getZeroMatrix(final int m, final int n) {
        return MatrixUtility.getZeroMatrix(m, n);
    }

    /**
     * A dummy method that internally calls {@link #getZeroMatrix(int, int)} to get a
     * zero matrix.
     *
     * @param d an array of 2 elements, # of rows and # of columns.
     *
     * @return an {@link ArrayMatrix} with all 0 entries
     *
     * @throws InvalidMatrixDimensionException if d.length != 2.
     */
    static ArrayMatrix getZeroMatrix(final int[] d) {
        if (d.length != 2)
            throw new InvalidMatrixDimensionException("The dimension must contain 2 elements.");

        return getZeroMatrix(d[0], d[1]);
    }

    /**
     * The Enum is used to create a type of pascal matrix.
     *
     * <ul>
     *  <li> UPPER creates an upper triangular matrix.
     *  <li> LOWER creates a lower triangular matrix.
     *  <li> SYMMETRIC creates a symmetric matrix.
     * </ul>
     */
    enum PascalMatrixType {UPPER, LOWER, SYMMETRIC}

    /**
     * The type of matrix created by the sytem.
     */
    enum MatrixType {
        ONE,
        ZERO,
        PASCAL,
        LEHMER,
        HILBERT,
        EXCHANGE,
        IDENTITY,
        NOT_SPECIFIED
    }

    /**
     * The method will return a zero or null matrix, whose all the elements are zero.
     *
     * @param n the number of rows and columns
     * @param t the type of matrix;
     *          LOWER       - Lover triangular
     *          UPPER       - Upper triangular
     *          SYMMETRIC   - a symmetric matrix
     *
     * @return a Pascal's {@link Matrix}
     *
     * @throws InvalidMatrixDimensionException when n < 1, at least one element should exist.
     */
    static ArrayMatrix getPascalMatrix(final int n, final PascalMatrixType t) {
        return MatrixUtility.getPascalMatrix(n, t);
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
        return MatrixUtility.getOneMatrix(m, n);
    }

    /**
     * A dummy method that internally calls {@link #getOneMatrix(int, int)} to get an
     * all-one matrix.
     *
     * @param d an array of 2 elements, # of rows and # of columns.
     *
     * @return an {@link ArrayMatrix} with all 1 entries
     *
     * @throws InvalidMatrixDimensionException if d.length != 2.
     */
    static ArrayMatrix getOneMatrix(final int[] d) {
        if (d.length != 2)
            throw new InvalidMatrixDimensionException("The dimension must contain 2 elements.");

        return getOneMatrix(d[0], d[1]);
    }

    /**
     * A lehmer matrix is a constant systematic square matrix.
     *
     * @param n the number of rows and columns
     *
     * @return the lehmer {@link ArrayMatrix}
     */
    static ArrayMatrix getLehmerMatrix(final int n) {
        return MatrixUtility.getLehmerMatrix(n);
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
    static ArrayMatrix getIdentityMatrix(final int n) {
        return MatrixUtility.getIdentityMatrix(n);
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
    static ArrayMatrix getHilbertMatrix(final int n) {
        return MatrixUtility.getHilbertMatrix(n);
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
    static ArrayMatrix getExchangeMatrix(final int n) {
        return MatrixUtility.getExchangeMatrix(n);
    }
}
