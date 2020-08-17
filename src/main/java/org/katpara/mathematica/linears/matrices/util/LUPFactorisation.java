package org.katpara.mathematica.linears.matrices.util;

import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;

public final class LUPFactorisation implements Factorisable<LUPFactorisation> {
    private static final long serialVersionUID = 827331618620713866L;

    /**
     * The permutation matrix
     */
    private double[][] p;

    /**
     * The lower triangular matrix
     */
    private double[][] l;

    /**
     * The upper triangular matrix
     */
    private double[][] u;

    /**
     * The inner matrix array
     */
    private double[][] n;

    /**
     * the object to hold the reference
     */
    private static LUPFactorisation obj;

    /**
     * The method returns the singlton LUPFactorisation object.
     *
     * @return the LUPFactorisation object
     */

    public static LUPFactorisation getInstance() {
        if (obj == null)
            obj = new LUPFactorisation();

        return obj;
    }

    /**
     * The method resolves factorization.
     *
     * @param m the matrix to resolve
     *
     * @return the resolved object
     */
    @Override
    public LUPFactorisation factorize(final Matrix m) {
        if (!m.isSquareMatrix())
            throw new NotSquareMatrixException();

        var _s = m.getSize();

        n = new double[_s[0]][_s[0]];
        p = new double[_s[0]][_s[0]];
        l = new double[_s[0]][_s[0]];
        u = new double[_s[0]][_s[0]];
        System.arraycopy(m.toArray(), 0, n, 0, _s[0]);

        // Create P and L matrix
        for (int i = 0; i < _s[0]; i++) {
            p[i][i] = 1;
        }

        for (int i = 0; i < _s[0]; i++) {

            // Finding the max pivot
            var pivot = i;
            for (int k = i + 1; k < _s[0]; k++) {
                if (n[k][i] > n[i][i])
                    pivot = k;
            }

            // Swapping rows
            if (pivot != i) {
                var t1 = n[i];
                n[i] = n[pivot];
                n[pivot] = t1;

                var t2 = p[i];
                p[i] = p[pivot];
                p[pivot] = t2;
            }




        }

        return null;
    }

    /**
     * The method returns the permutation matrix.
     *
     * @return the permutation matrix
     */
    private double[][] getP() {
        return p;
    }

    /**
     * The method returns the lower triangular matrix.
     *
     * @return the lower triangular matrix
     */
    private double[][] getL() {
        return l;
    }

    /**
     * The method returns the upper triangular matrix.
     *
     * @return the upper triangular matrix
     */
    private double[][] getU() {
        return u;
    }
}
