package org.katpara.mathematica.linears.matrices.squares.triangulars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linear.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linear.NotLowerTriangularMatrixException;
import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.SquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.LowerTriangularMatrix;
import org.katpara.mathematica.util.Rounding;

import static org.junit.jupiter.api.Assertions.*;

class LowerTriangularMatrixTest {

    private SquareMatrix m;

    @BeforeEach
    void setUp() {
        m = new LowerTriangularMatrix(new double[][]{
                {1, 0, 0, 0, 0},
                {1, 2, 0, 0, 0},
                {1, 2, 3, 0, 0},
                {1, 2, 3, 4, 0},
                {1, 2, 3, 4, 5}
        });
    }

    @Test
    void testConstructor() {
        assertAll(
                () -> assertThrows(NullArgumentProvidedException.class, () -> new LowerTriangularMatrix(null)),
                () -> assertThrows(NotSquareMatrixException.class, () -> new LowerTriangularMatrix(new double[][]{
                        {1, 0, 0, 0, 0},
                        {1, 2, 0, 0, 0},
                        {1, 2, 3, 0, 0},
                        {1, 2, 3, 4, 0}
                })),
                () -> assertThrows(NotLowerTriangularMatrixException.class, () -> new LowerTriangularMatrix(new double[][]{
                        {1, 0, 2, 0, 0},
                        {1, 2, 0, 1, 0},
                        {1, 2, 3, 0, 0},
                        {1, 2, 3, 4, 0},
                        {1, 2, 3, 4, 5}
                }))
        );
    }

    @Test
    void testTranspose() {
        SquareMatrix m1 = new AnySquareMatrix(new double[][]{
                {1, 1, 1, 1, 1},
                {0, 2, 2, 2, 2},
                {0, 0, 3, 3, 3},
                {0, 0, 0, 4, 4},
                {0, 0, 0, 0, 5}
        });
        assertEquals(m1, m.transpose());
    }

    @Test
    void testIsLowerTriangular() {
        assertTrue(m.isLowerTriangular());
    }

    @Test
    void testAddScalar() {
        System.out.println(m.add(10).getClass().getCanonicalName());
    }

    @Test
    void testAddMatrix() {
        Matrix m1 = new LowerTriangularMatrix(new double[][]{
                {10, 0, 0, 0},
                {10, 20, 0, 0},
                {10, 20, 30, 0},
                {10, 20, 30, 40},
        });
        Matrix m2 = new LowerTriangularMatrix(new double[][]{
                {10, 0, 0, 0, 0},
                {10, 20, 0, 0, 0},
                {10, 20, 30, 0, 0},
                {10, 20, 30, 40, 0},
                {10, 20, 30, 40, 50}
        });
        Matrix m3 = new AnySquareMatrix(new double[][]{
                {10, 40, 40, 40, 40},
                {10, 20, 30, 30, 30},
                {10, 20, 30, 20, 20},
                {10, 20, 30, 40, 10},
                {10, 20, 30, 40, 50}
        });
        Matrix m4 = DiagonalSquareMatrix.getInstance(new double[]{10, 20, 30, 40, 50});
        Matrix m5 = IdentityMatrix.getInstance(5);
        Matrix r2 = new LowerTriangularMatrix(new double[][]{
                {11, 0, 0, 0, 0},
                {11, 22, 0, 0, 0},
                {11, 22, 33, 0, 0},
                {11, 22, 33, 44, 0},
                {11, 22, 33, 44, 55}
        });
        Matrix r3 = new AnySquareMatrix(new double[][]{
                {11, 40, 40, 40, 40},
                {11, 22, 30, 30, 30},
                {11, 22, 33, 20, 20},
                {11, 22, 33, 44, 10},
                {11, 22, 33, 44, 55},
        });
        Matrix r4 = new LowerTriangularMatrix(new double[][]{
                {11, 0, 0, 0, 0},
                {1, 22, 0, 0, 0},
                {1, 2, 33, 0, 0},
                {1, 2, 3, 44, 0},
                {1, 2, 3, 4, 55}
        });
        Matrix r5 = new LowerTriangularMatrix(new double[][]{
                {2, 0, 0, 0, 0},
                {1, 3, 0, 0, 0},
                {1, 2, 4, 0, 0},
                {1, 2, 3, 5, 0},
                {1, 2, 3, 4, 6}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m.add(m1)),
                () -> assertEquals(r2, m.add(m2)),
                () -> assertEquals("LowerTriangularMatrix", r2.getClass().getSimpleName()),
                () -> assertEquals(r3, m.add(m3)),
                () -> assertEquals("AnySquareMatrix", r3.getClass().getSimpleName()),
                () -> assertEquals(r4, m.add(m4)),
                () -> assertEquals("LowerTriangularMatrix", r4.getClass().getSimpleName()),
                () -> assertEquals(r5, m.add(m5)),
                () -> assertEquals("LowerTriangularMatrix", r5.getClass().getSimpleName()),
                () -> assertEquals(m.add(m5), m5.add(m))
        );
    }

    @Test
    void testSubtractMatrix() {
        Matrix m1 = new LowerTriangularMatrix(new double[][]{
                {10, 0, 0, 0},
                {10, 20, 0, 0},
                {10, 20, 30, 0},
                {10, 20, 30, 40},
        });
        Matrix m2 = new LowerTriangularMatrix(new double[][]{
                {10, 0, 0, 0, 0},
                {10, 20, 0, 0, 0},
                {10, 20, 30, 0, 0},
                {10, 20, 30, 40, 0},
                {10, 20, 30, 40, 50}
        });
        Matrix m3 = new AnySquareMatrix(new double[][]{
                {10, 40, 40, 40, 40},
                {10, 20, 30, 30, 30},
                {10, 20, 30, 20, 20},
                {10, 20, 30, 40, 10},
                {10, 20, 30, 40, 50}
        });
        Matrix m4 = DiagonalSquareMatrix.getInstance(new double[]{10, 20, 30, 40, 50});
        Matrix m5 = IdentityMatrix.getInstance(5);
        Matrix r2 = new LowerTriangularMatrix(new double[][]{
                {-9, 0, 0, 0, 0},
                {-9, -18, 0, 0, 0},
                {-9, -18, -27, 0, 0},
                {-9, -18, -27, -36, 0},
                {-9, -18, -27, -36, -45}
        });
        Matrix r3 = new AnySquareMatrix(new double[][]{
                {-9, -40, -40, -40, -40},
                {-9, -18, -30, -30, -30},
                {-9, -18, -27, -20, -20},
                {-9, -18, -27, -36, -10},
                {-9, -18, -27, -36, -45},
        });
        Matrix r4 = new LowerTriangularMatrix(new double[][]{
                {-9, 0, 0, 0, 0},
                {1, -18, 0, 0, 0},
                {1, 2, -27, 0, 0},
                {1, 2, 3, -36, 0},
                {1, 2, 3, 4, -45}
        });
        Matrix r5 = new LowerTriangularMatrix(new double[][]{
                {0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {1, 2, 2, 0, 0},
                {1, 2, 3, 3, 0},
                {1, 2, 3, 4, 4}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m.subtract(m1)),
                () -> assertEquals(r2, m.subtract(m2)),
                () -> assertEquals("LowerTriangularMatrix", r2.getClass().getSimpleName()),
                () -> assertEquals(r3, m.subtract(m3)),
                () -> assertEquals("AnySquareMatrix", r3.getClass().getSimpleName()),
                () -> assertEquals(r4, m.subtract(m4)),
                () -> assertEquals("LowerTriangularMatrix", r4.getClass().getSimpleName()),
                () -> assertEquals(r5, m.subtract(m5)),
                () -> assertEquals("LowerTriangularMatrix", r5.getClass().getSimpleName())
        );
    }

    @Test
    void testScalarMultiplication() {
        Matrix r1 = new AnySquareMatrix(new double[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        });

        Matrix r2 = new LowerTriangularMatrix(new double[][]{
                {-1, 0, 0, 0, 0},
                {-1, -2, 0, 0, 0},
                {-1, -2, -3, 0, 0},
                {-1, -2, -3, -4, 0},
                {-1, -2, -3, -4, -5}
        });

        Matrix r3 = new LowerTriangularMatrix(new double[][]{
                {10, 0, 0, 0, 0},
                {10, 20, 0, 0, 0},
                {10, 20, 30, 0, 0},
                {10, 20, 30, 40, 0},
                {10, 20, 30, 40, 50}
        });

        assertAll(
                () -> assertEquals(m, m.multiply(1)),
                () -> assertEquals(r1, m.multiply(0)),
                () -> assertEquals(r2, m.multiply(-1)),
                () -> assertEquals(r3, m.multiply(10))
        );
    }

    @Test
    void testMultiplicativeInverse() {
        Matrix m2 = new LowerTriangularMatrix(new double[][]{
                {2, 0, 0},
                {4, 0, 0},
                {9, 5, 7}
        });

        Matrix m3 = new LowerTriangularMatrix(new double[][]{
                {2, 0, 0},
                {4, 6, 0},
                {9, 5, 7}
        });

        Matrix r3 = new LowerTriangularMatrix(new double[][]{
                {0.5000, 0.0000, 0.0000},
                {-0.3333, 0.1667, 0.0000},
                {-0.4048, -0.1190, 0.1429}
        });

        assertAll(
                () -> assertThrows(NotInvertibleException.class, m2::multiplicativeInverse),
                () -> assertEquals(r3.toString(), m3.multiplicativeInverse().toString(Rounding.Decimals.FOUR))
        );
    }

    @Test
    void testPower() {
        Matrix m2 = new LowerTriangularMatrix(new double[][]{
                {2, 0, 0},
                {4, 6, 0},
                {9, 5, 7}
        });
        Matrix r2 = new LowerTriangularMatrix(new double[][]{
                {8, 0, 0},
                {208, 216, 0},
                {903, 635, 343}
        });
        Matrix r3 = new LowerTriangularMatrix(new double[][]{
                {0.5000, 0.0000, 0.0000},
                {-0.3333, 0.1667, 0.0000},
                {-0.4048, -0.1190, 0.1429}
        });

        assertAll(
                () -> assertEquals(IdentityMatrix.getInstance(3), m2.power(0)),
                () -> assertEquals(m2, m2.power(1)),
                () -> assertEquals(r3.toString(), m2.power(-1).toString()),
                () -> assertEquals(r2, m2.power(3))
        );
    }

    @Test
    void testDivide() {
        Matrix m2 = new LowerTriangularMatrix(new double[][]{
                {2, 0, 0},
                {4, 6, 0},
                {9, 5, 7}
        });

        Matrix m3 = new LowerTriangularMatrix(new double[][]{
                {7, 0, 0},
                {0, 3, 0},
                {0, 0, 5}
        });

        Matrix r3 = new LowerTriangularMatrix(new double[][]{
                {0.2857, 0.0000, 0.0000},
                {0.5714, 2.0000, 0.0000},
                {1.2857, 1.6667, 1.4000}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m2.divide(IdentityMatrix.getInstance(4))),
                () -> assertThrows(NotInvertibleException.class, () -> m2.divide(NullMatrix.getInstance(3))),
                () -> assertEquals(IdentityMatrix.getInstance(3), m2.divide(m2)),
                () -> assertEquals(m2, m2.divide(IdentityMatrix.getInstance(3))),
                () -> assertEquals(r3.toString(), m2.divide(m3).toString())
        );
    }
}