package org.katpara.mathematica.linears.matrices.constants;

import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.linear.InvalidMatrixDimensionProvidedException;
import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.AbstractMatrix;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.rectangulars.AnyRectangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;
import org.katpara.mathematica.util.Rounding;

import java.util.Arrays;

/**
 * The matrix represent a null or zero matrix.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class NullMatrix extends AbstractMatrix {
    private static final long serialVersionUID = 632803417168479305L;

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    private NullMatrix(final double[][] d) {
        super(d);
    }

    /**
     * The class method creates a square null matrix of provided rows.
     *
     * @param row the dimensions of rows and columns.
     *
     * @return the null matrix
     *
     * @throws InvalidMatrixDimensionProvidedException when row <= 0
     */
    public static NullMatrix getInstance(final int row) {
        return getInstance(row, row);
    }

    /**
     * The class method creates a rectangular null matrix of given row and columns.
     *
     * @param row    the row dimensions
     * @param column the column dimensions
     *
     * @return the null matrix
     *
     * @throws InvalidMatrixDimensionProvidedException when row && column is 0 or less.
     */
    public static NullMatrix getInstance(final int row, final int column) {
        if (row <= 0 || column <= 0)
            throw new InvalidMatrixDimensionProvidedException();

        double[][] n = new double[row][column];
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
     * The method adds a scalar.
     *
     * @param scalar the scalar to add.
     *
     * @return The square matrix
     */
    @Override
    protected Matrix addScalar(final double scalar) {
        var n = super.doAdd(scalar);
        if (n.length == n[0].length) {
            return new AnySquareMatrix(n);
            //TODO: MORE SQUARE MATRIX IMPL, Like N == 2, N == 3
        }

        return new AnyRectangularMatrix(n);
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
        return m;
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
        return this;
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
        return getInstance(s[0], m.getSize()[1]);
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        throw new NotInvertibleException();
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

    /**
     * The power of an element.
     *
     * @param power the exponent
     *
     * @return the value after applying power
     */
    @Override
    public Matrix power(final int power) {
        if (!this.isSquareMatrix())
            throw new NotSquareMatrixException();

        return this;
    }

    /**
     * The absolute value of an element.
     *
     * @return the absolute value
     */
    @Override
    public double abs() {
        return 0;
    }

    /**
     * The absolute value of an element.
     *
     * @param decimals rounding to given decimal places
     *
     * @return the absolute value
     */
    @Override
    public double abs(final Rounding.Decimals decimals) {
        return 0;
    }
}
