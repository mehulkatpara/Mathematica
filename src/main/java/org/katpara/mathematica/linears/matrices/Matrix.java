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
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @return the trace of the square matrix
     */
    double getTrace();

    /**
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @param decimals the decimal points of accuracy
     *
     * @return the trace of the square matrix
     */
    double getTrace(final Rounding.Decimals decimals);

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @return the determinant of the square matrix
     */
    double getDeterminant();

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @param decimals the decimal decimals accuracy
     *
     * @return the determinant of the square matrix
     */
    double getDeterminant(final Rounding.Decimals decimals);

    /**
     * The method transposes the matrix.
     *
     * @return the transposed matrix
     */
    Matrix transpose();

    /**
     * The method perform deep-cloning.
     *
     * @return the cloned matrix
     */
    Matrix copy();
}
