package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.rectangulars.AnyRectangularMatrix;
import org.katpara.mathematica.linears.matrices.util.Decomposable;
import org.katpara.mathematica.linears.matrices.util.LUDecomposition;

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
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    public AnySquareMatrix(final double[][] d) {
        super(d);
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    @Override
    public boolean isSymmetric() {
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                if (d[i][j] != d[j][i]) {
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
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                if (i != j && d[i][j] != 0) {
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
        for (int i = 0; i < s[0]; i++) {
            if (d[i][i] != 1)
                return false;

            for (int j = 0; j < s[1]; j++) {
                if (i != j && d[i][j] != 0)
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
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                if (j > i && d[i][j] != 0)
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
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                if (j < i && d[i][j] != 0)
                    return false;
            }
        }

        return true;
    }

    /**
     * The method calculates the power of a matrix.
     *
     * @param power the exponent
     *
     * @return the resulting square matrix
     */
    @Override
    protected Matrix calculatePower(final int power) {
        var n = new double[s[0]][s[1]];
        for (var i = 0; i < s[0]; i++) {
            System.arraycopy(d[i], 0, n[i], 0, s[1]);
        }
        for (var i = 0; i < power - 1; i++) {
            n = super.doMultiply(n);
        }
        return getSquareMatrix(n);
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
        var det = 0.0;
        if (d[0][0] != 0) {
            double[][] u = new LUDecomposition().decompose(this)[1];
            det = u[0][0];
            for (int i = 1; i < s[0]; i++) {
                det *= u[i][i];
            }
        }

        //TODO: Implement This
        return det;
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public int getRank() {
        var rank = s[0];

        if (d[0][0] != 0) {
            double[][] u = new LUDecomposition().decompose(this)[1];
            for (int i = 1; i < s[0]; i++) {
                if(u[i][i] == 0)
                    rank -= 1;
            }
        }

        return rank;
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
     * The method adds a scalar.
     *
     * @param scalar the scalar to add.
     *
     * @return The square matrix
     */
    @Override
    protected final Matrix addScalar(final double scalar) {
        return getSquareMatrix(super.doAdd(scalar));
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
        return getSquareMatrix(super.doAdd(m.toArray()));
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
        return getSquareMatrix(super.doMultiply(scalar));
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
        return (n.length == n[0].length) ? getSquareMatrix(n) :
                       new AnyRectangularMatrix(n);
    }

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix multiplicativeInverse() {
        //TODO: Implement this
        return null;
    }

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    @Override
    public Matrix additiveInverse() {
        return getSquareMatrix(super.doAdditiveInverse());
    }

    /**
     * The method returns a customized matrix based on the array size.
     *
     * @param n the matrix data
     *
     * @return the square matrix
     */
    protected final Matrix getSquareMatrix(final double[][] n) {
        // TODO: More detailed such as 2x2 and 3x3
        return new AnySquareMatrix(n);
    }
}
