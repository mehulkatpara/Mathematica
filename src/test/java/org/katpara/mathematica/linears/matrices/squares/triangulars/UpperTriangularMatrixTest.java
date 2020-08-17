package org.katpara.mathematica.linears.matrices.squares.triangulars;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.linears.matrices.Matrix;

class UpperTriangularMatrixTest {

    @Test
    void testInverse() {
        Matrix m = new UpperTriangularMatrix(new double[][]{
                {2, 5, 7},
                {0, 1, 9},
                {0, 0, 3}
        });

        Matrix m2 = new UpperTriangularMatrix(new double[][]{
                {2, 1, 8, 6, 4},
                {0, 8, 5, 6, 1},
                {0, 0, 1, 0, 8},
                {0, 0, 0, 9, 1},
                {0, 0, 0, 0, 2}
        });

        System.out.println(m2.multiplicativeInverse());
    }
}