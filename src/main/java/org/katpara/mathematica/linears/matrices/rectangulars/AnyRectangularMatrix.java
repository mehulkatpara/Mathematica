package org.katpara.mathematica.linears.matrices.rectangulars;

import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;

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
     * The method adds a scalar.
     *
     * @param scalar the scalar to add.
     *
     * @return The square matrix
     */
    @Override
    protected Matrix addScalar(final double scalar) {
        return new AnyRectangularMatrix(super.doAdd(scalar));
    }

    /**
     * The method adds two matrices.
     *
     * @param m the matrix to add
     *
     * @return the resulting matrix
     */
    @Override
    protected Matrix addMatrix(final Matrix m) {
        return new AnyRectangularMatrix(super.doAdd(m.toArray()));
    }

    /**
     * The method multiplies a scalar.
     *
     * @param scalar the scalar to multiply
     *
     * @return the resulting matrix
     */
    @Override
    protected Matrix multiplyScalar(final double scalar) {
        return new AnyRectangularMatrix(super.doMultiply(scalar));
    }

    /**
     * The method multiplies two matrices.
     *
     * @param m the matrix to multiply with
     *
     * @return the resulting matrix
     */
    @Override
    protected Matrix multiplyMatrix(final Matrix m) {
        var n = super.doMultiply(m.toArray());
        if (n.length == n[0].length) {
            return new AnySquareMatrix(n);
            //TODO: MORE SQUARE MATRIX IMPL, Like N == 2, N == 3
        }

        return new AnyRectangularMatrix(n);
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
