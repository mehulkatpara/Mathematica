package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linears.NonInvertibleMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.util.Rounding;

/**
 * The class represents a diagonal square matrix in the system.
 * A diagonal square matrix has all the elements zero, outside
 * of it's main diagonal.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class DiagonalSquareMatrix extends AnySquareMatrix {

    /**
     * Required for extending classes
     */
    public DiagonalSquareMatrix() { }

    /**
     * creates a diagonal square matrix from an array of data.
     *
     * @param e the matrix elements
     */
    public DiagonalSquareMatrix(final Number[] e) {
        if(e == null)
            throw new NullArgumentProvidedException();

        d = new Number[e.length][e.length];
        for (int i = 0; i < e.length; i++) {
            if(e[i] == null)
                throw new NullArgumentProvidedException();

            for (int j = 0; j < e.length; j++) {
                d[i][j] = (i == j) ? e[i] : 0;
            }
        }
        s = new int[]{e.length, e.length};
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    @Override
    public boolean isSymmetric() {
        return true;
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public int getRank() {
        var rank = d.length;

        for (int i = 0; i < d.length; i++)
            if (d[i][i].doubleValue() == 0)
                rank -= 1;

        return rank;
    }

    /**
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @return the trace of the square matrix
     */
    @Override
    public double getTrace() {
        return getTrace(Rounding.Decimals.FOUR);
    }

    /**
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @param decimals the decimal points of accuracy
     *
     * @return the trace of the square matrix
     */
    @Override
    public double getTrace(final Rounding.Decimals decimals) {
        var sum = 0.0;

        for (int i = 0; i < d.length; i++)
            sum += d[i][i].doubleValue();

        return Double.parseDouble(Rounding.round(sum, decimals));
    }

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @return the determinant of the square matrix
     */
    @Override
    public double getDeterminant() {
        return getDeterminant(Rounding.Decimals.FOUR);
    }

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @param decimals the decimal decimals accuracy
     *
     * @return the determinant of the square matrix
     */
    @Override
    public double getDeterminant(final Rounding.Decimals decimals) {
        var det = d[0][0].doubleValue();

        for (int i = 1; i < d.length; i++)
            det *= d[i][i].doubleValue();

        return Double.parseDouble(Rounding.round(det, decimals));
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
     * The method perform deep-cloning.
     *
     * @return the cloned matrix
     */
    @Override
    public Matrix copy() {
        var n = new Number[d.length];
        for (int i = 0; i < d.length; i++)
            n[i] = d[i][i];

        return new DiagonalSquareMatrix(n);
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
            var _d = m.toArray();
            if (d.length != _d.length)
                throw new MatrixDimensionMismatchException();

            var n = new Number[d.length];
            for (int i = 0; i < d.length; i++)
                n[i] = d[i][i].doubleValue() + _d[i][i].doubleValue();

            return new DiagonalSquareMatrix(n);
        } else {
            return super.add(m);
        }
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
            var _d = m.toArray();
            if (d.length != _d.length)
                throw new MatrixDimensionMismatchException();

            var n = new Number[d.length];
            for (int i = 0; i < d.length; i++)
                n[i] = d[i][i].doubleValue() - _d[i][i].doubleValue();

            return new DiagonalSquareMatrix(n);
        } else {
            return super.subtract(m);
        }
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
        var n = new Number[d.length];
        for (int i = 0; i < d.length; i++) {
            n[i] = d[i][i].doubleValue() * scalar;
        }

        return new DiagonalSquareMatrix(n);
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
        if (m instanceof DiagonalSquareMatrix) {
            var _d = m.toArray();
            if (d.length != _d.length)
                throw new MatrixDimensionMismatchException();

            var n = new Number[d.length];
            for (int i = 0; i < d.length; i++)
                n[i] = d[i][i].doubleValue() * _d[i][i].doubleValue();

            return new DiagonalSquareMatrix(n);
        } else {
            return super.multiply(m);
        }
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
        if (m instanceof DiagonalSquareMatrix) {
            var _d = m.toArray();
            if (d.length != _d.length)
                throw new MatrixDimensionMismatchException();

            var n = new Number[d.length];
            for (int i = 0; i < d.length; i++)
                n[i] = d[i][i].doubleValue() * (1 / _d[i][i].doubleValue());

            return new DiagonalSquareMatrix(n);
        } else {
            return super.multiply(m);
        }
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        var n = new Number[d.length];
        for (int i = 0; i < d.length; i++) {
            double v;
            if ((v = d[i][i].doubleValue()) != 0) {
                n[i] = 1 / v;
            } else {
                throw new NonInvertibleMatrixException();
            }
        }

        return new DiagonalSquareMatrix(n);
    }

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix additiveInverse() {
        var n = new Number[d.length];
        for (int i = 0; i < d.length; i++)
            n[i] = -d[i][i].doubleValue();

        return new DiagonalSquareMatrix(n);
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
        var n = new Number[d.length];
        for (int i = 0; i < d.length; i++)
            n[i] = Math.pow(d[i][i].doubleValue(), power);

        return new DiagonalSquareMatrix(n);
    }
}
