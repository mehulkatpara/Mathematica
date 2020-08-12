package org.katpara.mathematica.linears.matrices.squares;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.ColumnOutOfBoundException;
import org.katpara.mathematica.exceptions.linears.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linears.RowOutOfBoundException;
import org.katpara.mathematica.exceptions.linears.dep.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;

import static org.junit.jupiter.api.Assertions.*;

class AnySquareMatrixTest {

    SquareMatrix m1;

    @BeforeEach
    void setUp() {
        m1 = new AnySquareMatrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 10},
        });
    }

    @Test
    void constructor() {
        assertAll(
                () -> assertThrows(NullArgumentProvidedException.class, () -> new AnySquareMatrix(null)),
                () -> assertThrows(NullArgumentProvidedException.class,
                        () -> new AnySquareMatrix(new double[][]{null})),
                () -> assertThrows(NotSquareMatrixException.class,
                        () -> new AnySquareMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}}))
        );
    }

    @Test
    void isRowVector() {
        assertFalse(m1.isRowVector());
    }

    @Test
    void isColumnVector() {
        assertFalse(m1.isColumnVector());
    }

    @Test
    void isSquareMatrix() {
        assertTrue(m1.isSquareMatrix());
    }

    @Test
    void getTrace() {
        //TODO: To be implemented
    }

    @Test
    void abs() {
        //TODO: To be implemented
    }

    @Test
    void getSize() {
        assertArrayEquals(new int[]{3, 3}, m1.getSize());
    }

    @Test
    void getRow() {
        assertAll(
                () -> assertThrows(RowOutOfBoundException.class, () -> m1.getRow(-1)),
                () -> assertThrows(RowOutOfBoundException.class, () -> m1.getRow(10)),
                () -> assertArrayEquals(new double[]{1, 2, 3}, m1.getRow(0))
        );

    }

    @Test
    void getColumn() {
        assertAll(
                () -> assertThrows(ColumnOutOfBoundException.class, () -> m1.getColumn(-1)),
                () -> assertThrows(ColumnOutOfBoundException.class, () -> m1.getColumn(10)),
                () -> assertArrayEquals(new double[]{1, 4, 7}, m1.getColumn(0))
        );
    }

    @Test
    void isSymmetric() {
        SquareMatrix m2 = new AnySquareMatrix(new double[][]{
                {1, 2, 9},
                {2, 4, 3},
                {9, 3, 7},
        });

        assertAll(
                () -> assertFalse(m1.isSymmetric()),
                () -> assertTrue(m2.isSymmetric())
        );
    }

    @Test
    void isDiagonal() {
        SquareMatrix m2 = new AnySquareMatrix(new double[][]{
                {1, 0, 0},
                {0, 4, 0},
                {0, 0, 7},
        });

        assertAll(
                () -> assertFalse(m1.isDiagonal()),
                () -> assertTrue(m2.isDiagonal())
        );
    }

    @Test
    void isIdentity() {
        SquareMatrix m2 = new AnySquareMatrix(new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1},
        });

        assertAll(
                () -> assertFalse(m1.isIdentity()),
                () -> assertTrue(m2.isIdentity())
        );
    }

    @Test
    void isLowerTriangular() {
        SquareMatrix m2 = new AnySquareMatrix(new double[][]{
                {1, 0, 0},
                {2, 1, 0},
                {3, 2, 1},
        });

        assertAll(
                () -> assertFalse(m1.isLowerTriangular()),
                () -> assertTrue(m2.isLowerTriangular())
        );
    }

    @Test
    void isUpperTriangular() {
        SquareMatrix m2 = new AnySquareMatrix(new double[][]{
                {1, 2, 3},
                {0, 1, 2},
                {0, 0, 1},
        });

        assertAll(
                () -> assertFalse(m1.isUpperTriangular()),
                () -> assertTrue(m2.isUpperTriangular())
        );
    }

    @Test
    void getDeterminant() {
        //TODO: To be implemented
    }

    @Test
    void getRank() {
        // TODO: TO BE Implemented
    }

    @Test
    void transpose() {
        Matrix r1 = new AnySquareMatrix(new double[][]{
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 10}
        });
        assertEquals(r1, m1.transpose());
    }

    @Test
    void addScalar() {

        Matrix r1 = new AnySquareMatrix(new double[][]{
                {11, 12, 13},
                {14, 15, 16},
                {17, 18, 20}
        });
        assertAll(
                () -> assertEquals(m1, m1.add(0)),
                () -> assertEquals(r1, m1.add(10))
        );
    }

    @Test
    void addMatrix() {
        Matrix r1 = new AnySquareMatrix(new double[][]{
                {2, 4, 6},
                {8, 10, 12},
                {14, 16, 20}
        });
        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(NullMatrix.getInstance(5))),
                () -> assertEquals(m1, m1.add(NullMatrix.getInstance(3))),
                () -> assertEquals(r1, m1.add(m1))
        );
    }

    @Test
    void subtract() {
        Matrix m2 = new AnySquareMatrix(new double[][]{
                {2, 4, 6},
                {8, 10, 12},
                {14, 16, 20}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(NullMatrix.getInstance(5))),
                () -> assertEquals(m1, m1.subtract(NullMatrix.getInstance(3))),
                () -> assertEquals(NullMatrix.getInstance(3), m1.subtract(m1)),
                () -> assertEquals(m1, m2.subtract(m1))
        );
    }

    @Test
    void multiplyScalar() {
        Matrix r1 = new AnySquareMatrix(new double[][]{
                {2, 4, 6},
                {8, 10, 12},
                {14, 16, 20}
        });
        assertAll(
                () -> assertEquals(NullMatrix.getInstance(3), m1.multiply(0)),
                () -> assertEquals(m1, m1.multiply(1)),
                () -> assertEquals(m1.additiveInverse(), m1.multiply(-1)),
                () -> assertEquals(r1, m1.multiply(2))
        );
    }

    @Test
    void multiplyMatrix() {

        Matrix m2 = new AnySquareMatrix(new double[][]{
                {1, 1, 2},
                {3, 8, 9},
                {2, 6, 1}
        });

        Matrix r1 = new AnySquareMatrix(new double[][]{
                {13, 35, 23},
                {31, 80, 59},
                {51, 131, 96}
        });
        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class,
                        () -> m1.multiply(NullMatrix.getInstance(5))),
                () -> assertEquals(NullMatrix.getInstance(3), m1.multiply(NullMatrix.getInstance(3))),
                () -> assertEquals(m1, m1.multiply(IdentityMatrix.getInstance(3))),
                () -> assertEquals(r1, m1.multiply(m2))
        );
    }

    @Test
    void divide() {
        //TODO: To be implemented
    }

    @Test
    void multiplicativeInverse() {
        //TODO: To be implemented
    }

    @Test
    void additiveInverse() {
        Matrix r1 = new AnySquareMatrix(new double[][]{
                {-1, -2, -3},
                {-4, -5, -6},
                {-7, -8, -10},
        });

        assertEquals(r1, m1.additiveInverse());
    }

    @Test
    void power() {
        SquareMatrix m2 = new AnySquareMatrix(new double[][]{
                {1, 2},
                {3, 4}
        });

        SquareMatrix r2 = new AnySquareMatrix(new double[][]{
                {7, 10},
                {15, 22}
        });

        SquareMatrix r3 = new AnySquareMatrix(new double[][]{
                {37, 54},
                {81, 118}
        });

        Matrix r4 = new AnySquareMatrix(new double[][]{
                {30, 36, 45},
                {66, 81, 102},
                {109, 134, 169}
        });

        assertAll(
                () -> assertEquals(r2, m2.power(2)),
                () -> assertEquals(r3, m2.power(3)),
                () -> assertEquals(r4, m1.power(2))
        );
    }
}