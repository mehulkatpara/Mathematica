package org.katpara.mathematica.linears.matrices;

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
     * @return the trace of matrix
     */
    double trace();

    /**
     * The method will return a zero or null matrix, whose all the elements are zero.
     *
     * @param m the number of rows
     * @param n the number of columns
     *
     * @return a {@link Matrix} with all 0 entries
     *
     * @throws InvalidParameterProvidedException when # of row + # of column < 1;
     *                                           this ensures that at least one element
     *                                           exist all the time.
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
     * @throws InvalidParameterProvidedException if d.length != 2.
     */
    static ArrayMatrix getZeroMatrix(final int[] d) {
        if (d.length != 2)
            throw new InvalidParameterProvidedException("The dimension must contain 2 elements.");

        return getZeroMatrix(d[0], d[1]);
    }
}
