package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.util.Rounding;

/**
 * The class represents any N x N square matrix. This is the most general class.
 * If your matrix is a specific type of Matrix, then please use that matrix type,
 * that way the computation to calculate various properties and operations would
 * be most optimized.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class AnySquareMatrix extends SquareMatrix {
    private static final long serialVersionUID = 978698801528776001L;

    /**
     * Requires to chain down the construction
     */
    protected AnySquareMatrix() {
        super();
    }

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    public AnySquareMatrix(final Number[][] d) {
        super(d);
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    @Override
    public boolean isSymmetric() {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (d[i][j].doubleValue() != d[j][i].doubleValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * The method returns true if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    @Override
    public boolean isDiagonal() {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (i != j && d[i][j].doubleValue() != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * The method returns true if the matrix is an identity matrix.
     *
     * @return true if it's an identity matrix
     */
    @Override
    public boolean isIdentity() {
        for (int i = 0; i < d.length; i++) {
            if (d[i][i].doubleValue() != 1)
                return false;

            for (int j = 0; j < d[0].length; j++) {
                if (i != j && d[i][j].doubleValue() != 0)
                    return false;
            }
        }

        return true;
    }

    /**
     * the method returns true if the matrix is a lower triangular matrix
     *
     * @return true if it's a lower triangular
     */
    @Override
    public boolean isLowerTriangular() {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (j > i && d[i][j].doubleValue() != 0)
                    return false;
            }
        }

        return true;
    }

    /**
     * the method returns true if the matrix is an upper triangular matrix
     *
     * @return true if it's a upper triangular
     */
    @Override
    public boolean isUpperTriangular() {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (j < i && d[i][j].doubleValue() != 0)
                    return false;
            }
        }

        return true;
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
        return 0;
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
        return 0;
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
        return new AnySquareMatrix(super.doTranspose());
    }

    /**
     * The scalar addition of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    @Override
    public final Matrix add(final double scalar) {
        return new AnySquareMatrix(super.doAdd(scalar));
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
        return new AnySquareMatrix(super.doAdd(m));
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
        return new AnySquareMatrix(super.doSubtract(m));
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
        return new AnySquareMatrix(super.doMultiply(scalar));
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
        return new AnySquareMatrix(super.doMultiply(m));
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
        return new AnySquareMatrix(super.doMultiply(m.multiplicativeInverse()));
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        return null;
    }

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix additiveInverse() {
        return new AnySquareMatrix(super.doAdditiveInverse());
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
        return null;
    }
}
