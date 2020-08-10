package org.katpara.mathematica.linears.matrices.rectangulars;

import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.linears.MatrixDimensionMismatchException;
import org.katpara.mathematica.linears.matrices.Matrix;

import java.util.Arrays;

/**
 * The matrix represent a null or zero matrix.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class NullMatrix extends AnyRectangularMatrix {

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected NullMatrix(final Number[][] d) {
        super(d);
    }

    public static NullMatrix getInstance(final int row, final int column) {
        Number[][] n = new Number[row][column];
        for (int i = 0; i < row; i++) {
            Arrays.fill(n[i], 0);
        }

        return new NullMatrix(n);
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public int getRank() {
        return 0;
    }

    /**
     * The method transposes the matrix.
     *
     * @return the transposed matrix
     */
    @Override
    public Matrix transpose() {
        return this;
    }

    /**
     * The scalar addition of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    @Override
    public Matrix add(final double scalar) {
        if(scalar == 0)
            return this;

        return super.add(scalar);
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
        if(!Arrays.equals(getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        return m;
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
        if(!Arrays.equals(getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        return m.additiveInverse();
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
        return this;
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
        var _s = m.getSize();

        if (s[1] != _s[0])
            throw new MatrixDimensionMismatchException();

        return getInstance(s[0], _s[1]);
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
        return this.multiply(m.multiplicativeInverse());
    }

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix additiveInverse() {
        return this;
    }
}
