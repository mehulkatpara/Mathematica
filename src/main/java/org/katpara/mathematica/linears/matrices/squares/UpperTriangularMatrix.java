package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.NotLowerTriangularMatrixException;
import org.katpara.mathematica.exceptions.linears.dep.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;

/**
 * The class represents an upper triangular matrix, where all the elements
 * below the main-diagonal is zero.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class UpperTriangularMatrix extends DiagonalSquareMatrix {

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    public UpperTriangularMatrix(final Number[][] d) {
        if (d == null)
            throw new NullArgumentProvidedException();

        for (int i = 0; i < d.length; i++) {
            if (d[i].length != d.length)
                throw new NotSquareMatrixException();

            for (int j = 0; j < d.length; j++) {
                if (d[i][j] == null)
                    throw new NullArgumentProvidedException();

                if (j < i && d[i][j].doubleValue() != 0)
                    throw new NotLowerTriangularMatrixException();
            }
        }

        this.d = d;
        this.s = new int[]{d.length, d.length};
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    @Override
    public boolean isSymmetric() {
        return false;
    }

    /**
     * The method returns true if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    @Override
    public boolean isDiagonal() {
        return false;
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
        return new LowerTriangularMatrix(super.doTranspose());
    }

    /**
     * The addition of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public Matrix add(final Matrix m) {
        if (m instanceof DiagonalSquareMatrix) {
            return new UpperTriangularMatrix(super.doAdd(m));
        }

        return super.add(m);
    }

    /**
     * The subtraction of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public Matrix subtract(final Matrix m) {
        if (m instanceof DiagonalSquareMatrix) {
            return new UpperTriangularMatrix(super.doSubtract(m));
        }

        return super.subtract(m);
    }
}
