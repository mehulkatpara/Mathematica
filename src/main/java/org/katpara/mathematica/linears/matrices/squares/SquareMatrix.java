package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.linears.InvalidMatrixDimensionProvidedException;
import org.katpara.mathematica.linears.matrices.AbstractMatrix;
import org.katpara.mathematica.util.Rounding;

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
}
