package org.katpara.mathematica.linears.matrices.dep;

import org.katpara.mathematica.util.Rounding;
import org.katpara.mathematica.exceptions.linears.dep.InvalidMatrixOperationException;
import org.katpara.mathematica.linears.vectors.Vector;

import java.util.List;

/**
 * The interface defines a Matrix_Old in the system, and it's operations.
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
public interface Matrix_Old {

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
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    int getRank();

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
    Matrix_Old transpose();

    /**
     * The method will return an inverse matrix of a given matrix.
     * It returns a null matrix if the inverse is not possible.
     *
     * @return The inverse matrix
     *
     * @throws InvalidMatrixOperationException if the matrix is not a square matrix
     */
    Matrix_Old inverse();

    /**
     * The method will return an inverse matrix of a given matrix.
     * It returns a null matrix if the inverse is not possible.
     *
     * @param decimals The efficiency to the given decimal places
     *
     * @return The inverse matrix
     *
     * @throws InvalidMatrixOperationException if the matrix is not a square matrix
     */
    Matrix_Old inverse(final Rounding.Decimals decimals);

    /**
     * The method performs a scalar addition on a square matrix.
     * The operation is somewhat be described as;
     * Let's consider a matrix "M", and scalar "a"
     * then M + a = M + a(I), where I is an identity matrix.
     *
     * @param scalar the scalar to add
     *
     * @return the resulting matrix
     *
     * @throws InvalidMatrixOperationException if the matrix is not a square matrix
     */
    Matrix_Old add(final Number scalar);

    /**
     * The method adds two matrices together.
     * Let's consider matrices "A" and "B";
     * The method performs, A + B operation, and returns the resulting matrixOld.
     *
     * @param matrixOld the matrixOld to add
     *
     * @return the resulting matrixOld
     *
     * @throws InvalidMatrixOperationException if two matrices don't have the same dimension
     */
    Matrix_Old add(final Matrix_Old matrixOld);

    /**
     * The method subtracts two matrices together.
     * Let's consider matrices "A" and "B";
     * The method performs, A - B operation, and returns the resulting matrixOld.
     *
     * @param matrixOld the matrixOld to subtract
     *
     * @return the resulting matrixOld
     *
     * @throws InvalidMatrixOperationException if two matrices don't have the same dimension
     */
    Matrix_Old subtract(final Matrix_Old matrixOld);

    /**
     * The method will perform a scalar multiplication on a matrix and returns a new matrix.
     * For example, Let us consider a matrix A, and any scalar c. The scalar multiplication
     * can be defined as;
     * c x A = cA.
     *
     * @param scalar a scalar to scale the matrix with
     *
     * @return a new scalded matrix
     */
    Matrix_Old multiply(final Number scalar);

    /**
     * The method will perform a scalar multiplication on a matrix and returns a new matrix.
     * For example, Let us consider a matrix A, and any scalar c. The scalar multiplication
     * can be defined as;
     * c x A = cA.
     *
     * @param scalar a scalar to scale the matrix with
     * @param decimals the rounding to the given decimal points
     *
     * @return a new scalded matrix
     */
    Matrix_Old multiply(final Number scalar, final Rounding.Decimals decimals);

    /**
     * The method will perform a matrixOld multiplication of a matrixOld and returns a new Matrix_Old.
     * <p>
     * If the given matrices are A, and B, of respective dimensions m x n and n x p. then
     * number of column of a matrixOld A has to be equal to the number of rows B. The resulting
     * matrixOld would be the dimensions of m x p.
     * (A)mxn X (B)nxp = (C)mxp, where # or columns of A and and # of rows of B are equal.
     *
     * @param matrixOld the matrixOld to multiply
     *
     * @return the resulting matrixOld
     *
     * @throws InvalidMatrixOperationException if the number of columns of the matrixOld is not
     *                                         equal to the number of rows of another matrixOld
     */
    Matrix_Old multiply(final Matrix_Old matrixOld);

    /**
     * The method will perform a matrixOld multiplication of a matrixOld and returns a new Matrix_Old.
     * <p>
     * If the given matrices are A, and B, of respective dimensions m x n and n x p. then
     * number of column of a matrixOld A has to be equal to the number of rows B. The resulting
     * matrixOld would be the dimensions of m x p.
     * (A)mxn X (B)nxp = (C)mxp, where # or columns of A and and # of rows of B are equal.
     *
     * @param matrixOld the matrixOld to multiply
     * @param decimals the rounding level to decimal places
     *
     * @return the resulting matrixOld
     *
     * @throws InvalidMatrixOperationException if the number of columns of the matrixOld is not
     *                                         equal to the number of rows of another matrixOld
     */
    Matrix_Old multiply(final Matrix_Old matrixOld, final Rounding.Decimals decimals);
}
