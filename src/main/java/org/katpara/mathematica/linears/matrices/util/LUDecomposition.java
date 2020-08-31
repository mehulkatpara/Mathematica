package org.katpara.mathematica.linears.matrices.util;

import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.UpperTriangularMatrix;

public final class LUDecomposition implements Decomposable {
    private static final long serialVersionUID = 98035217535729742L;

    /**
     * The method decomposes matrices.
     * The first element of the returned value is a lower triangular matrix,
     * the second element of the returned value is a upper triangular matrix.
     *
     * @param m the matrix to be decomposed
     *
     * @return the decomposed matrices
     */
    @Override
    public double[][][] decompose(final Matrix m) {
        if (!m.isSquareMatrix())
            throw new NotSquareMatrixException();

        var s = m.getSize();
        double[][] l = new double[s[0]][s[1]],
                u = new double[s[0]][s[1]],
                n = m.toArray();

        // Create U and L matrix
        System.arraycopy(n, 0, u, 0, s[0]);
        for (int i = 0; i < s[0]; i++) {
            l[i][i] = 1;
        }

        if (!(m instanceof DiagonalSquareMatrix ||
                      m instanceof UpperTriangularMatrix ||
                      m instanceof NullMatrix)) {
            for (int i = 0; i < s[0]; i++) {
                for (int k = i + 1; k < s[0]; k++) {
                    l[k][i] = u[k][i] / u[i][i];
                    for (int j = i; j < s[1]; j++) {
                        u[k][j] = u[k][j] - (l[k][i] * u[i][j]);
                    }
                }
            }
        }

        return new double[][][]{l, u};
    }
}
