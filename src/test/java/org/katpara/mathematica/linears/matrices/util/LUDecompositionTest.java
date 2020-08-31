package org.katpara.mathematica.linears.matrices.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.rectangulars.AnyRectangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.SquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.LowerTriangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.UpperTriangularMatrix;
import org.katpara.mathematica.util.Rounding;

import static org.junit.jupiter.api.Assertions.*;

class LUDecompositionTest {

    private Decomposable dc;

    @BeforeEach
    void setUp() {
        dc = new LUDecomposition();
    }

    @Test
    void decomposeFail() {
        Matrix m = new AnyRectangularMatrix(new double[][]{
                {1, 2, 3},
                {1, 2, 3},
        });
        assertThrows(NotSquareMatrixException.class, () -> dc.decompose(m));
    }

    @Test
    void decomposeUpperTriangular() {
        Matrix m = new UpperTriangularMatrix(new double[][]{
                {1, 2, 3},
                {0, 2, 3},
                {0, 0, 3}
        });
        assertAll(
                () -> assertEquals(IdentityMatrix.getInstance(3), new LowerTriangularMatrix(dc.decompose(m)[0])),
                () -> assertEquals(m, new UpperTriangularMatrix(dc.decompose(m)[1]))
        );
    }

    @Test
    void decomposeDiagonal() {
        Matrix m = DiagonalSquareMatrix.getInstance(new double[]{2, 4, 6, 8});
        assertAll(
                () -> assertEquals(IdentityMatrix.getInstance(4), new LowerTriangularMatrix(dc.decompose(m)[0])),
                () -> assertEquals(m, new UpperTriangularMatrix(dc.decompose(m)[1]))
        );
    }

    @Test
    void decomposeIdentity() {
        Matrix m = IdentityMatrix.getInstance(5);
        assertAll(
                () -> assertEquals(IdentityMatrix.getInstance(5), new LowerTriangularMatrix(dc.decompose(m)[0])),
                () -> assertEquals(IdentityMatrix.getInstance(5), new UpperTriangularMatrix(dc.decompose(m)[1]))
        );
    }

    @Test
    void decomposeZero() {
        Matrix m = NullMatrix.getInstance(5);
        assertAll(
                () -> assertEquals(IdentityMatrix.getInstance(5), new LowerTriangularMatrix(dc.decompose(m)[0])),
                () -> assertEquals(NullMatrix.getInstance(5), new UpperTriangularMatrix(dc.decompose(m)[1]))
        );
    }

    @Test
    void decomposeAny() {
        Matrix m = new AnySquareMatrix(new double[][]{
                {9, 7, 8, 6, 4, 5},
                {6, 3, 6, 7, 9, 3},
                {4, 5, 6, 1, 0, 9},
                {5, 1, 5, 7, 3, 1},
                {5, 7, 8, 9, 1, 7},
                {0, 3, 7, 4, 8, 9}
        });
        System.out.println(new LowerTriangularMatrix(dc.decompose(m)[0]));
        System.out.println(new UpperTriangularMatrix(dc.decompose(m)[1]));
    }

    @Test
    void testSomething() {
        SquareMatrix m = new AnySquareMatrix(new double[][]{
                {1, 7, 8, 6, 4, 5},
                {6, 0, 6, 7, 9, 3},
                {4, 5, 0, 1, 0, 9},
                {5, 1, 5, 0, 3, 1},
                {5, 7, 8, 9, 0, 7},
                {0, 3, 7, 4, 8, 0}
        });

        System.out.println(m.getDeterminant());
    }
}