package org.katpara.mathematica.linears.matrices.squares.triangulars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.linear.MatrixDimensionMismatchException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;

import static org.junit.jupiter.api.Assertions.*;

class UpperTriangularMatrixTest {

    private Matrix m1;

    @BeforeEach
    void setUp() {
        m1 = new UpperTriangularMatrix(new double[][]{
                {2, 5, 7},
                {0, 1, 9},
                {0, 0, 3}
        });
    }

    @Test
    void testTranspose() {
        Matrix m2 = m1.transpose();

        Matrix r2 = new LowerTriangularMatrix(new double[][]{
                {2, 0, 0},
                {5, 1, 0},
                {7, 9, 3}
        });

        assertAll(
                () -> assertEquals(r2, m2),
                () -> assertEquals("LowerTriangularMatrix", m2.getClass().getSimpleName())
        );
    }

    @Test
    void testAddMatrix() {

        Matrix m2 = m1.add(IdentityMatrix.getInstance(3));
        Matrix m3 = new AnySquareMatrix(new double[][]{
                {3, 5, 7},
                {1, 2, 9},
                {2, 0, 4}
        });
        Matrix r2 = new UpperTriangularMatrix(new double[][]{
                {3, 5, 7},
                {0, 2, 9},
                {0, 0, 4}
        });
        Matrix r3 = new AnySquareMatrix(new double[][]{
                {5, 10, 14},
                {1, 3, 18},
                {2, 0, 7}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(IdentityMatrix.getInstance(5))),
                () -> assertEquals(m1, m1.add(NullMatrix.getInstance(3))),
                () -> assertEquals(r2, m2),
                () -> assertEquals("UpperTriangularMatrix", m2.getClass().getSimpleName()),
                () -> assertEquals("UpperTriangularMatrix", m2.add(m1).getClass().getSimpleName()),
                () -> assertEquals(r3, m3.add(m1)),
                () -> assertEquals("AnySquareMatrix", m3.add(m1).getClass().getSimpleName())
        );
    }

    @Test
    void testSubtract() {
        Matrix m2 = new UpperTriangularMatrix(new double[][]{
                {3, 5, 7},
                {0, 2, 9},
                {0, 0, 4}
        });

        Matrix r2 = new UpperTriangularMatrix(new double[][]{
                {-1, 0, 0},
                {0, -1, 0},
                {0, 0, -1}
        });


        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(IdentityMatrix.getInstance(5))),
                () -> assertEquals(NullMatrix.getInstance(3), m1.subtract(m1)),
                () -> assertEquals(m1, m1.subtract(NullMatrix.getInstance(3))),
                () -> assertEquals(m1, m1.subtract(NullMatrix.getInstance(3))),
                () -> assertEquals(r2, m1.subtract(m2))
        );
    }

    @Test
    void testScalar() {
        Matrix r1 = new UpperTriangularMatrix(new double[][]{
                {20, 50, 70},
                {0, 10, 90},
                {0, 0, 30}
        });

        assertEquals(r1, m1.multiply(10));
    }

    @Test
    void testMultiply() {
        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(NullMatrix.getInstance(4, 5))),
                () -> assertEquals(NullMatrix.getInstance(3, 5), m1.multiply(NullMatrix.getInstance(3, 5))),
                () -> assertEquals(m1, m1.multiply(IdentityMatrix.getInstance(3)))
        );
    }

    @Test
    void testAdditiveInverse() {
        Matrix r1 = m1 = new UpperTriangularMatrix(new double[][]{
                {-2, -5, -7},
                {-0, -1, -9},
                {-0, -0, -3}
        });

        assertEquals(r1, m1.additiveInverse());
    }

    @Test
    void testMultiplicativeInverse() {
        Matrix m2 = new UpperTriangularMatrix(new double[][]{
                {2, 8, 6},
                {0, 0, 2},
                {0, 0, 1}
        });

        Matrix r1 = new UpperTriangularMatrix(new double[][]{
                {0.5000, -2.5000, 6.3333},
                {0.0000, 1.0000, -3.0000},
                {0.0000, 0.0000, 0.3333}
        });

        assertAll(
                () -> assertThrows(NotInvertibleException.class, m2::multiplicativeInverse),
                () -> assertEquals(r1.toString(), m1.multiplicativeInverse().toString())
        );
    }

    @Test
    void testPower() {
        Matrix m2 = new UpperTriangularMatrix(new double[][]{
                {2, 8, 6},
                {0, 7, 2},
                {0, 0, 1}
        });

        Matrix r2 = new UpperTriangularMatrix(new double[][]{
                {8, 536, 202},
                {0, 343, 114},
                {0, 0, 1}
        });

        Matrix r3 = new UpperTriangularMatrix(new double[][]{
                {0.2500, -3.7500, 12.7778},
                {0.0000, 1.0000, -4.0000},
                {0.0000, 0.0000, 0.1111},
        });

        assertAll(
                () -> assertEquals(IdentityMatrix.getInstance(3), m1.power(0)),
                () -> assertEquals(m1, m1.power(1)),
                () -> assertEquals(r2, m2.power(3)),
                () -> assertEquals(r3.toString(), m1.power(-2).toString())
        );
    }
}