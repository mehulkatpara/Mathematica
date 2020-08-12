package org.katpara.mathematica.linears.matrices.squares.triangulars;

import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.SquareMatrix;
import org.katpara.mathematica.util.Rounding;

public abstract class TriangularMatrix extends AnySquareMatrix {
    private static final long serialVersionUID = 952182322882023415L;
    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected TriangularMatrix(final double[][] d) {
        super(d);
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    @Override
    public final boolean isSymmetric() {
        return false;
    }

    /**
     * The method returns true if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    @Override
    public final boolean isDiagonal() {
        return false;
    }

    /**
     * The method returns true if the matrix is an identity matrix.
     *
     * @return true if it's an identity matrix
     */
    @Override
    public final boolean isIdentity() {
        return false;
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
    public final double getDeterminant(final Rounding.Decimals decimals) {
        var det = d[0][0];

        for (int i = 1; i < d.length; i++)
            det *= d[i][i];

        return Double.parseDouble(Rounding.round(det, decimals));
    }


    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public final int getRank() {
        var rank = d.length;

        for (int i = 0; i < d.length; i++)
            if (d[i][i] == 0)
                rank -= 1;

        return rank;
    }
}
