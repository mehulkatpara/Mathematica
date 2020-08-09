package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.linears.InvalidMatrixDimensionProvidedException;
import org.katpara.mathematica.linears.matrices.AbstractMatrix;

/**
 * An abstract class holding all the square matrices together.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public abstract class SquareMatrix extends AbstractMatrix {

    /**
     * Requires to chain down the construction
     */
    protected SquareMatrix() {
    }

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected SquareMatrix(final Number[][] d) {
        super(d);

        if (d.length != d[0].length)
            throw new InvalidMatrixDimensionProvidedException();
    }

    /**
     * The method will return true if the matrix is a
     * row vector, which is 1 x n
     *
     * @return true if it is a row vector
     */
    @Override
    public boolean isRowVector() {
        return false;
    }

    /**
     * The method will return true if the matrix is a
     * column vector, which is n x 1.
     *
     * @return true if it is a column vector
     */
    @Override
    public boolean isColumnVector() {
        return false;
    }

    /**
     * The method will return true if the matrix is a
     * square matrix, which is n x n.
     *
     * @return true if it is a square matrix
     */
    @Override
    public boolean isSquareMatrix() {
        return true;
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    public abstract boolean isSymmetric();

    /**
     * The method returns truw if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    public abstract boolean isDiagonal();

    /**
     * The method returns true if the matrix is an identity matrix.
     *
     * @return true if it's an identity matrix
     */
    public abstract boolean isIdentity();
}
