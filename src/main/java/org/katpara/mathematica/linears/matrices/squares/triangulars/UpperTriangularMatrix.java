package org.katpara.mathematica.linears.matrices.squares.triangulars;

import org.katpara.mathematica.exceptions.linears.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linears.NotLowerTriangularMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;

import java.util.Arrays;

/**
 * The class represents an upper triangular matrix, where all the elements
 * below the main-diagonal is zero.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class UpperTriangularMatrix extends TriangularMatrix {
    private static final long serialVersionUID = 825461631784142004L;

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    public UpperTriangularMatrix(final double[][] d) {
        super(d);

        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d.length; j++) {
                if (j < i && d[i][j] != 0)
                    throw new NotLowerTriangularMatrixException();
            }
        }
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
     * The multiplication of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public Matrix multiply(final Matrix m) {
        if(m instanceof UpperTriangularMatrix ||
                   m instanceof DiagonalSquareMatrix) {
            if(s[0] != m.getSize()[1])
                throw new MatrixDimensionMismatchException();

            return new UpperTriangularMatrix(super.doMultiply(m.toArray()));
        }

        return super.multiply(m);
    }

    /**
     * The division of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public Matrix divide(final Matrix m) {
        if(m instanceof UpperTriangularMatrix ||
                   m instanceof DiagonalSquareMatrix) {
            if(s[0] != m.getSize()[1])
                throw new MatrixDimensionMismatchException();

            return new UpperTriangularMatrix(super.doMultiply(m.multiplicativeInverse().toArray()));
        }

        return super.divide(m);
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
        if(m instanceof UpperTriangularMatrix ||
                   m instanceof DiagonalSquareMatrix) {
            if(!Arrays.equals(getSize(), m.getSize()))
                throw new MatrixDimensionMismatchException();

            return new UpperTriangularMatrix(super.doAdd(m.toArray()));
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
        if(m instanceof UpperTriangularMatrix ||
                   m instanceof DiagonalSquareMatrix) {
            if(!Arrays.equals(getSize(), m.getSize()))
                throw new MatrixDimensionMismatchException();

            return new UpperTriangularMatrix(super.doSubtract(m.toArray()));
        }

        return super.subtract(m);
    }

    /**
     * The scalar multiplication of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    @Override
    public Matrix multiply(final double scalar) {
        if (scalar == 0)
            return NullMatrix.getInstance(s[0]);
        if (scalar == 1)
            return this;
        if (scalar == -1)
            return this.additiveInverse();

        return new UpperTriangularMatrix(super.doMultiply(scalar));
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        //TODO: To be implemented
        return null;
    }

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix additiveInverse() {
        return new UpperTriangularMatrix(super.doAdditiveInverse());
    }

    /**
     * The power of an element.
     *
     * @param power the exponent
     *
     * @return the value after applying power
     */
    @Override
    public Matrix power(final double power) {
        if (power == 0)
            return IdentityMatrix.getInstance(s[0]);

        if (power == 1)
            return this;

        return new LowerTriangularMatrix(super.doPower(power));
    }
}
