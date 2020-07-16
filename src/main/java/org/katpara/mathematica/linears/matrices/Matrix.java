package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.exceptions.linears.InvalidMatrixDimensionException;
import org.katpara.mathematica.linears.vectors.Vector;

import java.util.List;

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
     * The Enum is used to create a type of pascal matrix.
     *
     * <ul>
     *      <li> UPPER creates an upper triangular matrix.
     *      <li> LOWER creates a lower triangular matrix.
     *      <li> SYMMETRIC creates a symmetric matrix.
     * </ul>
     */
    enum PascalMatrixType {UPPER, LOWER, SYMMETRIC}

    /**
     * The enum is used to create a type of shift matrix.
     *
     * <ul>
     *     <li>Upper shift matrix is a matrix only on the superdiagonal is 1, else 0</li>
     *     <li>Upper shift matrix is a matrix only on the subdiagonal is 1, else 0</li>
     * </ul>
     */
    enum ShiftMatrixType {UPPER, LOWER}

    /**
     * The type of matrix created by the sytem.
     */
    enum MatrixType {ONE, ZERO, SHIFT, PASCAL, LEHMER, HILBERT, EXCHANGE, IDENTITY, REDHEFFER, NOT_SPECIFIED}

    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    int[] getDimension();

    /**
     * The method returns the type of a matrix.
     * For more details see {@link MatrixType}
     *
     * @return the type of matrix
     */
    MatrixType getType();

    /**
     * The method returns all the elements of a matrix.
     *
     * @return the matrix elements
     */
    Number[][] toArray();

    /**
     * The method returns all the elements as a list of lists.
     *
     * @return the matrix elements as a list of lists
     */
    List<List<Number>> toList();

    /**
     * The method returns the list of Vectors.
     *
     * @return the matrix elements as a list of vectors
     */
    List<Vector> toArrayVectors();

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
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    int rank();

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
     * @throws InvalidMatrixDimensionException when # of row + # of column &lt; 2;
     *                                         this ensures that at least one element
     *                                         exist all the time.
     */
    static ArrayMatrix zeroArrayMatrix(final int m, final int n) {
        return ArrayMatrix.zeroMatrix(m, n);
    }

    /**
     * In mathematics, a matrix of one, or all-ones matrix is a matrix whose all elements are 1.
     *
     * @param m the number of rows
     * @param n the number of columns
     *
     * @return a one matrix
     *
     * @throws InvalidMatrixDimensionException when m + n &lt; 2
     */
    static ArrayMatrix oneArrayMatrix(final int m, final int n) {
        return ArrayMatrix.oneMatrix(m, n);
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
     * @throws InvalidMatrixDimensionException when n &lt; 1, at least one element should exist.
     */
    static ArrayMatrix pascalArrayMatrix(final int n, final PascalMatrixType t) {
        return ArrayMatrix.pascalMatrix(n, t);
    }

    /**
     * A lehmer matrix is a constant systematic square matrix.
     *
     * @param n the number of rows and columns
     *
     * @return the lehmer {@link ArrayMatrix}
     */
    static ArrayMatrix lehmerArrayMatrix(final int n) {
        return ArrayMatrix.lehmerMatrix(n);
    }

    /**
     * The identity matrix is a square matrix whose diagonal is always 1 and all the
     * other elements are 0.
     *
     * @param n the number of rows and columns
     *
     * @return an identity {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    static ArrayMatrix identityArrayMatrix(final int n) {
        return ArrayMatrix.identityMatrix(n);
    }

    /**
     * A Hilbert matrix is a square matrix with entries being the unit fractions.
     *
     * @param n the number of rows and columns
     *
     * @return a hilbert {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    static ArrayMatrix hilbertArrayMatrix(final int n) {
        return ArrayMatrix.hilbertMatrix(n);
    }

    /**
     * An exchange matrix is a square matrix whose counterdiagonal is always 1 and the
     * rest of the elements are 0.
     *
     * @param n the square matrix
     *
     * @return an exchange {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    static ArrayMatrix exchangeArrayMatrix(final int n) {
        return ArrayMatrix.exchangeMatrix(n);
    }

    /**
     * A redheffer matrix is a (0-1) square matrix, whose entries are either 1 or 0.
     * The matrix is calculated as if n is divisible by m, then it's 1 otherwise it's 0.
     *
     * @param n the number of rows and columns
     *
     * @return a redheffer {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    static ArrayMatrix redhefferArrayMatrix(final int n) {
        return ArrayMatrix.redhefferMatrix(n);
    }

    /**
     * The shift matrix is a matrix whose diagonal has shifted one level up or down, known as
     * super diagonal matrix, or lower diagonal matrix.
     *
     * @param n the number of rows and columns
     * @param t the type of matrix, i.e UPPER or LOWER
     *
     * @return a shift {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    static ArrayMatrix shiftArrayMatrix(final int n, final ShiftMatrixType t) {
        return ArrayMatrix.shiftMatrix(n, t);
    }
}
