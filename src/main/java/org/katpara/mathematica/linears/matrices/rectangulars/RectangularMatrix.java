package org.katpara.mathematica.linears.matrices.rectangulars;

import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.dep.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.AbstractMatrix;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;
import org.katpara.mathematica.util.Rounding;

public abstract class RectangularMatrix extends AbstractMatrix {

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected RectangularMatrix(final Number[][] d) {
        super(d);
    }

    /**
     * The method will return true if the matrix is a
     * row vector, which is 1 x n
     *
     * @return true if it is a row vector
     */
    @Override
    public final boolean isRowVector() {
        return this.s[0] == 1 && this.s[1] > 1;
    }

    /**
     * The method will return true if the matrix is a
     * column vector, which is n x 1.
     *
     * @return true if it is a column vector
     */
    @Override
    public final boolean isColumnVector() {
        return this.s[0] > 1 && this.s[1] == 1;
    }

    /**
     * The method will return true if the matrix is a
     * square matrix, which is n x n.
     *
     * @return true if it is a square matrix
     */
    @Override
    public final boolean isSquareMatrix() {
        return false;
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public final Matrix multiplicativeInverse() {
        throw new NotInvertibleException();
    }

    /**
     * The power of an element.
     *
     * @param power the exponent
     *
     * @return the value after applying power
     */
    @Override
    public final Matrix power(final double power) {
        throw new NotSquareMatrixException();
    }

    /**
     * The absolute value of an element.
     *
     * @return the absolute value
     */
    @Override
    public final Number abs() {
        throw new NotSquareMatrixException();
    }

    /**
     * The absolute value of an element.
     *
     * @param decimals rounding to given decimal places
     *
     * @return the absolute value
     */
    @Override
    public final Number abs(final Rounding.Decimals decimals) {
        throw new NotSquareMatrixException();
    }
}
