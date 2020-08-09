package org.katpara.mathematica.linears.matrices.squares;

import org.katpara.mathematica.exceptions.linears.NotLowerTriangularMatrixException;

/**
 * The class represents a lower triangular matrix, where all the elements
 * above the main-diagonal is zero.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class LowerTriangularMatrix extends AnySquareMatrix {

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    private LowerTriangularMatrix(final Number[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d.length; j++) {
                if (j > i && d[i][j].doubleValue() != 0)
                    throw new NotLowerTriangularMatrixException();
            }
        }

        this.d = d;
        this.s = new int[]{d.length, d.length};
    }
}
