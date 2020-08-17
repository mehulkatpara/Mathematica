package org.katpara.mathematica.linears.matrices.constants;

import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.linear.InvalidMatrixDimensionProvidedException;
import org.katpara.mathematica.exceptions.linear.MatrixDimensionMismatchException;
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
     * The scalar addition of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    @Override
    public Matrix add(final double scalar) {
        if (scalar == 0)
            return this;

        if (this.isSquareMatrix())
            return new AnySquareMatrix(super.doAdd(scalar));
        else
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
        if (!m.isSquareMatrix())
            throw new NotSquareMatrixException();

        return this.multiply(m.multiplicativeInverse());
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
    public Matrix power(final double power) {
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
