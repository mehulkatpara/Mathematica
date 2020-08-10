package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.Element;
import org.katpara.mathematica.util.Rounding;

/**
 * The interface defines a Matrix in the system, and it's operations.
 * Mathematically A matrix is an rectangular object of numbers, which
 * has rows and columns.
 * <p>
 * The mathematical application of a matrix is to perform a learning
 * transformation of a space. It can scale up or down, turn and twist
 * the space. That can lead to deformation of vectors as well.
 * </p>
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public interface Matrix extends Element<Matrix> {
    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    int[] getSize();

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
     * The method returns all the elements of a matrix.
     *
     * @return the matrix elements
     */
    Number[][] toArray();

    /**
     * The method returns the row elements of a Matrix.
     *
     * @param row the row index
     *
     * @return the matrix elements as a list of vectors
     */
    Number[] getRow(final int row);

    /**
     * The method returns the column elements of a Matrix.
     *
     * @param column the column index
     *
     * @return the matrix elements as a list of vectors
     */
    Number[] getColumn(final int column);

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    int getRank();

    /**
     * The method transposes the matrix.
     *
     * @return the transposed matrix
     */
    Matrix transpose();
}
