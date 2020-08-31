package org.katpara.mathematica.linears.matrices.util;

import org.katpara.mathematica.exceptions.linear.CholeskyDecompositionNotPossible;
import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.exceptions.linear.NotSymmetricMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.SquareMatrix;

public class CholeskyDecomposition implements Decomposable {

    /**
     * The method decomposes matrices.
     * The first element is a lower triangular matrix array,
     * the second argument is a transpose of that array.
     *
     * @param m the matrix to be decomposed
     *
     * @return the decomposed matrices
     */
    @Override
    public double[][][] decompose(final Matrix m) {
        if (!m.isSquareMatrix())
            throw new NotSquareMatrixException();

        SquareMatrix sm = (SquareMatrix) m;
        if (!sm.isSymmetric())
            throw new NotSymmetricMatrixException();

        var s = sm.getSize();
        var n = sm.toArray();
        double[][] l = new double[s[0]][s[0]];

        if(m instanceof IdentityMatrix) {
            for (int i = 0; i < s[0]; i++) {
                l[i][i] = 1;
            }
        }if (m instanceof DiagonalSquareMatrix) {
            for (int i = 0; i < s[0]; i++) {
                l[i][i] = Math.sqrt(n[i][i]);
            }
        } else {
            for (int i = 0; i < s[0]; i++) {
                if(n[i][i] == 0)
                    throw new CholeskyDecompositionNotPossible();
            }

            for (int i = 0; i < s[0]; i++) {
                for (int j = 0; j <= i; j++) {
                    var sum = 0.0;
                    if (i == j) {
                        for (int k = 0; k < j; k++) {
                            sum += l[i][k] * l[i][k];
                        }

                        if(sum > n[j][j])
                            throw new CholeskyDecompositionNotPossible();

                        l[i][j] = Math.sqrt(n[j][j] - sum);
                    }
                    else {
                        for (int k = 0; k < j; k++) {
                            sum += l[j][k] * l[i][k];
                        }
                        l[i][j] = (n[i][j] - sum) / l[j][j];
                    }
                }
            }
        }

        return new double[][][]{l};
    }
}
