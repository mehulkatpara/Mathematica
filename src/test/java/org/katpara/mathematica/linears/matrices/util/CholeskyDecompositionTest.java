package org.katpara.mathematica.linears.matrices.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.linear.CholeskyDecompositionNotPossible;
import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.exceptions.linear.NotSymmetricMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.rectangulars.AnyRectangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.LowerTriangularMatrix;

import static org.junit.jupiter.api.Assertions.*;

class CholeskyDecompositionTest {

    private Decomposable dc;

    @BeforeEach
    void setUp() {
        dc = new CholeskyDecomposition();
    }

    @Test
    void testRectangular() {
        Matrix m = new AnyRectangularMatrix(new double[][]{
                {2, 0, 0, 1},
                {0, 3, 0, 0},
                {0, 0, 5, 5}
        });

        assertThrows(NotSquareMatrixException.class, () -> dc.decompose(m));
    }

    @Test
    void testNotSymmetric() {
        Matrix m = new AnySquareMatrix(new double[][]{
                {2, 0, 3},
                {7, 3, 0},
                {0, 0, 5}
        });
        assertThrows(NotSymmetricMatrixException.class, () -> dc.decompose(m));
    }

    @Test
    void testDiagonal() {
        Matrix m = DiagonalSquareMatrix.getInstance(new double[]{4, 9, 25});
        Matrix r = new AnySquareMatrix(new double[][]{
                {2, 0, 0},
                {0, 3, 0},
                {0, 0, 5}
        });

        assertEquals(r, new AnySquareMatrix(dc.decompose(m)[0]));
    }

    @Test
    void testZeroOnDiagonal() {
        Matrix m1 = new AnySquareMatrix(new double[][]{
                {0, 1, 2},
                {1, 3, 4},
                {2, 4, 9}
        });

        Matrix m2 = new AnySquareMatrix(new double[][]{
                {7, 1, 2},
                {1, 3, 4},
                {2, 4, 0}
        });

        Matrix m3 = new AnySquareMatrix(new double[][]{
                {7, 1, 2},
                {1, 0.012, 4},
                {2, 4, 0}
        });

        assertAll(
                () -> assertThrows(CholeskyDecompositionNotPossible.class, () -> dc.decompose(m1)),
                () -> assertThrows(CholeskyDecompositionNotPossible.class, () -> dc.decompose(m2)),
                () -> assertThrows(CholeskyDecompositionNotPossible.class, () -> dc.decompose(m3))
        );
    }

    @Test
    void testSomething() {
        Matrix t = new AnySquareMatrix(new double[][]{
                {7, 1, 2},
                {1, 3, 4},
                {2, 4, 9}
        });

        Matrix m1 = new LowerTriangularMatrix(dc.decompose(t)[0]);
        Matrix r1 = new LowerTriangularMatrix(new double[][]{
                {2.6458, 0.0000, 0.0000},
                {0.3780, 1.6903, 0.0000},
                {0.7559, 2.1974, 1.8974}
        });
        assertEquals(r1.toString(), m1.toString());
    }
}