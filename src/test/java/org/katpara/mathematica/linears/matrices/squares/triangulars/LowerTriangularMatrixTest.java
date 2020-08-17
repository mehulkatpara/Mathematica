package org.katpara.mathematica.linears.matrices.squares.triangulars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.linear.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linear.NotLowerTriangularMatrixException;
import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.SquareMatrix;

import static org.junit.jupiter.api.Assertions.*;

class LowerTriangularMatrixTest {

    SquareMatrix m1;

    @BeforeEach
    void setUp() {
        m1 = new LowerTriangularMatrix(new double[][]{
                {2, 0, 0},
                {4, 6, 0},
                {9, 5, 7}
        });
    }

    @Test
    void constructor() {
        assertAll(
                () -> assertThrows(NotSquareMatrixException.class, () -> new LowerTriangularMatrix(new double[][]{
                        {1, 2, 3},
                        {1, 2, 3}
                })),
                () -> assertThrows(NotLowerTriangularMatrixException.class, () -> new LowerTriangularMatrix(new double[][]{
                        {1, 2, 3},
                        {1, 2, 3},
                        {1, 2, 3}
                }))
        );
    }

    @Test
    void checkTriangular() {

        SquareMatrix m2 = new LowerTriangularMatrix(new double[][]{
                {2, 0, 0},
                {0, 6, 0},
                {0, 0, 7}
        });

        SquareMatrix m3 = IdentityMatrix.getInstance(5);

        assertAll(
                () -> assertTrue(m1.isSquareMatrix()),
                () -> assertTrue(m1.isLowerTriangular()),
                () -> assertFalse(m1.isUpperTriangular()),
                () -> assertFalse(m1.isIdentity()),
                () -> assertFalse(m1.isDiagonal()),
                () -> assertFalse(m1.isSymmetric()),
                () -> assertTrue(m2.isLowerTriangular()),
                () -> assertTrue(m2.isDiagonal()),
                () -> assertTrue(m3.isLowerTriangular()),
                () -> assertTrue(m3.isDiagonal()),
                () -> assertTrue(m3.isDiagonal())
        );
    }


    @Test
    void getDeterminant() {
        assertEquals(84, m1.getDeterminant());
    }

    @Test
    void testRank() {
        assertEquals(3, m1.getRank());
    }

    @Test
    void testTranspose() {

        Matrix m2 = new LowerTriangularMatrix(new double[][]{
                {2, 4, 9},
                {0, 6, 5},
                {0, 0, 7}
        });
        assertEquals(m2, m1.transpose());
    }

    @Test
    void testAddScalar() {
        Matrix m2 = m1.add(10);
        Matrix m3 = m1.add(DiagonalSquareMatrix.getInstance(new double[]{1, 3, 5}));
        Matrix m5 = m1.add(new UpperTriangularMatrix(new double[][]{
                {1, 2, 3},
                {0, 4, 5},
                {0, 0, 6}
        }));

        SquareMatrix r2 = new AnySquareMatrix(new double[][]{
                {12, 10, 10},
                {14, 16, 10},
                {19, 15, 17}
        });
        SquareMatrix r3 = new LowerTriangularMatrix(new double[][]{
                {3, 0, 0},
                {4, 9, 0},
                {9, 5, 12}
        });

        assertAll(
                () -> assertEquals(r2, m2),
                () -> assertEquals("AnySquareMatrix", m2.getClass().getSimpleName()),
                () -> assertEquals(r3, m3),
                () -> assertEquals("LowerTriangularMatrix", m3.getClass().getSimpleName()),
                () -> assertEquals("LowerTriangularMatrix", m1.add(r3).getClass().getSimpleName()),
                () -> assertEquals("AnySquareMatrix", m5.getClass().getSimpleName())
        );
    }

    @Test
    void additiveInverse() {
        SquareMatrix r1 = new AnySquareMatrix(new double[][]{
                {-2, 0, 0},
                {-4, -6, 0},
                {-9, -5, -7}
        });
        assertEquals(r1, m1.additiveInverse());
        assertTrue(r1.isLowerTriangular());
    }


    @Test
    void multiplyScalar() {
        Matrix m2 = m1.multiply(0);
        Matrix m3 = m1.multiply(1);
        Matrix m4 = m1.multiply(-1);
        Matrix m5 = m1.multiply(10);

        Matrix r4 = new LowerTriangularMatrix(new double[][]{
                {-2, 0, 0},
                {-4, -6, 0},
                {-9, -5, -7}
        });

        Matrix r5 = new LowerTriangularMatrix(new double[][]{
                {20, 0, 0},
                {40, 60, 0},
                {90, 50, 70}
        });

        assertAll(
                () -> assertEquals("NullMatrix", m2.getClass().getSimpleName()),
                () -> assertEquals(m3, m1),
                () -> assertEquals(r4, m4),
                () -> assertEquals(r5, m5)
        );
    }

    @Test
    void multiplyMatrix() {
        Matrix m2 = new LowerTriangularMatrix(new double[][]{
                {7, 0, 0},
                {7, 9, 0},
                {2, 4, 1}
        });

        Matrix m3 = new LowerTriangularMatrix(new double[][]{
                {7, 0, 0},
                {0, 9, 0},
                {0, 0, 1}
        });

        Matrix m4 = new UpperTriangularMatrix(new double[][]{
                {7, 4, 8},
                {0, 9, 5},
                {0, 0, 1}
        });


        Matrix r2 = new LowerTriangularMatrix(new double[][]{
                {14, 0, 0},
                {70, 54, 0},
                {112, 73, 7}
        });

        Matrix r3 = new LowerTriangularMatrix(new double[][]{
                {14, 0, 0},
                {28, 54, 0},
                {63, 45, 7}
        });

        Matrix r4 = new AnySquareMatrix(new double[][]{
                {14, 8, 16},
                {28, 70, 62},
                {63, 81, 104}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(IdentityMatrix.getInstance(4))),
                () -> assertEquals(r2, m1.multiply(m2)),
                () -> assertEquals("LowerTriangularMatrix", r2.getClass().getSimpleName()),
                () -> assertEquals(r3, m1.multiply(m3)),
                () -> assertEquals("LowerTriangularMatrix", r3.getClass().getSimpleName()),
                () -> assertEquals(r4, m1.multiply(m4)),
                () -> assertEquals(m1, m1.multiply(IdentityMatrix.getInstance(3))),
                () -> assertEquals(NullMatrix.getInstance(3), m1.multiply(NullMatrix.getInstance(3)))
        );
    }
}