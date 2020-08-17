package org.katpara.mathematica.linears.matrices.rectangulars;

import org.katpara.mathematica.exceptions.linear.NotRectangularMatrixException;
import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.AbstractMatrix;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.util.Rounding;

public abstract class RectangularMatrix extends AbstractMatrix {
    private static final long serialVersionUID = 864206708158951432L;

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected RectangularMatrix(final double[][] d) {
        super(d);

        if(s[0] == s[1])
            throw new NotRectangularMatrixException();
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
        throw new NotSquareMatrixException();
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
    public final double abs() {
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
    public final double abs(final Rounding.Decimals decimals) {
        throw new NotSquareMatrixException();
    }
}
