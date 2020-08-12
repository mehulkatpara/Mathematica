package org.katpara.mathematica.linears.matrices.rectangulars;

import org.katpara.mathematica.exceptions.linears.MatrixDimensionMismatchException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;

import java.util.Arrays;

public class AnyRectangularMatrix extends RectangularMatrix {
    private static final long serialVersionUID = 693420854170444322L;

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    public AnyRectangularMatrix(final double[][] d) {
        super(d);
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
        return new AnyRectangularMatrix(super.doTranspose());
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

        return new AnyRectangularMatrix(super.doAdd(scalar));
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
        if (!Arrays.equals(getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        if(m instanceof NullMatrix)
            return this;

        return new AnyRectangularMatrix(super.doAdd(m.toArray()));
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
        if(this == m)
            return NullMatrix.getInstance(s[0], s[1]);

        if (!Arrays.equals(getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        if(m instanceof NullMatrix)
            return this;

        return new AnyRectangularMatrix(super.doSubtract(m.toArray()));
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
        if (scalar == 1)
            return this;
        else if (scalar == 0)
            return NullMatrix.getInstance(s[0], s[1]);
        else if (scalar == -1)
            return this.additiveInverse();
        else
            return new AnyRectangularMatrix(super.doMultiply(scalar));
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
        else if (m instanceof IdentityMatrix)
            return this;
        else if (m instanceof NullMatrix)
            return NullMatrix.getInstance(s[0], m.getSize()[1]);

        var n = super.doMultiply(m.toArray());
        if(n.length == n[0].length) {
            return new AnySquareMatrix(n);
            //TODO: MORE SQUARE MATRIX IMPL, Like N == 2, N == 3
        }

        return new AnyRectangularMatrix(n);
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
        return new AnyRectangularMatrix(super.doAdditiveInverse());
    }
}
