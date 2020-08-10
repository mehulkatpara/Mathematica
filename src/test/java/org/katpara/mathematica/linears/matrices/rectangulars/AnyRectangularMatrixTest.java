package org.katpara.mathematica.linears.matrices.rectangulars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linears.dep.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.IdentityMatrix;

import static org.junit.jupiter.api.Assertions.*;

class AnyRectangularMatrixTest {

    private Matrix m;

    @BeforeEach
    void setUp() {
        m = new AnyRectangularMatrix(new Number[][]{
                {1, 2, 3, 4},
                {5, 9, 9, 7},
                {2, 13, 8, 17}
        });
    }

    @Test
    void constructor() {
        assertAll(
                () -> assertThrows(NullArgumentProvidedException.class, () -> new AnyRectangularMatrix(null)),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new AnyRectangularMatrix(new Number[][]{null})),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new AnyRectangularMatrix(new Number[][]{{null}})),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new AnyRectangularMatrix(new Number[][]{{1, 2}, {null, 1}}))
        );
    }

    @Test
    void isRowVector() {
        assertFalse(m.isRowVector());
        assertTrue(new AnyRectangularMatrix(new Number[][]{{1, 2, 3}}).isRowVector());
    }

    @Test
    void isColumnVector() {
        assertFalse(m.isColumnVector());
        assertTrue(new AnyRectangularMatrix(new Number[][]{{1}, {2}, {3}}).isColumnVector());
    }

    @Test
    void isSquareMatrix() {
        assertFalse(m.isSquareMatrix());
    }

    @Test
    void multiplicativeInverse() {
        assertThrows(NotInvertibleException.class, () -> m.multiplicativeInverse());
    }

    @Test
    void power() {
        assertThrows(NotSquareMatrixException.class, () -> m.power(2));
    }

    @Test
    void abs() {
        assertThrows(NotSquareMatrixException.class, () -> m.abs());
    }

    @Test
    void getSize() {
        assertArrayEquals(new int[]{3, 4}, m.getSize());
    }

    @Test
    void getRow() {
        assertArrayEquals(new Number[]{5, 9, 9, 7}, m.getRow(1));
    }

    @Test
    void getColumn() {
        assertArrayEquals(new Number[]{1, 5, 2}, m.getColumn(0));
    }

    @Test
    void getRank() {
        //TODO: To be implemented
    }

    @Test
    void transpose() {
        Matrix i = new AnyRectangularMatrix(new Number[][]{
                {1, 5, 2},
                {2, 9, 13},
                {3, 9, 8},
                {4, 7, 17}
        });

        assertEquals(i, m.transpose());
    }

    @Test
    void addScalar() {
        Matrix s = new AnyRectangularMatrix(new Number[][]{
                {11, 12, 13, 14},
                {15, 19, 19, 17},
                {12, 23, 18, 27}
        });

        assertEquals(m, m.add(0));
        assertEquals(s, m.add(10));
    }

    @Test
    void testAdd() {
        Matrix m2 = new AnyRectangularMatrix(new Number[][]{
                {11, 12, 13, 14},
                {15, 19, 19, 17}
        });
        Matrix m3 = new AnyRectangularMatrix(new Number[][]{
                {10, 10, 10, 10},
                {10, 10, 10, 10},
                {10, 10, 10, 10}
        });
        Matrix r3 = new AnyRectangularMatrix(new Number[][]{
                {11, 12, 13, 14},
                {15, 19, 19, 17},
                {12, 23, 18, 27}
        });
        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m.add(m2)),
                () -> assertEquals(m, m.add(NullMatrix.getInstance(3, 4))),
                () -> assertEquals(r3, m.add(m3))
        );
    }

    @Test
    void subtract() {
        Matrix m2 = new AnyRectangularMatrix(new Number[][]{
                {11, 12, 13, 14},
                {15, 19, 19, 17}
        });
        Matrix m3 = new AnyRectangularMatrix(new Number[][]{
                {10, 10, 10, 10},
                {10, 10, 10, 10},
                {10, 10, 10, 10}
        });
        Matrix r3 = new AnyRectangularMatrix(new Number[][]{
                {-9, -8, -7, -6},
                {-5, -1, -1, -3},
                {-8, 3, -2, 7}
        });
        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m.subtract(m2)),
                () -> assertEquals(m, m.subtract(NullMatrix.getInstance(3, 4))),
                () -> assertEquals(NullMatrix.getInstance(3, 4), m.subtract(m)),
                () -> assertEquals(r3, m.subtract(m3))
        );
    }

    @Test
    void multiplyScalar() {
        Matrix r = new AnyRectangularMatrix(new Number[][]{
                {10, 20, 30, 40},
                {50, 90, 90, 70},
                {20, 130, 80, 170}
        });

        assertAll(
                () -> assertEquals(NullMatrix.getInstance(3, 4), m.multiply(0)),
                () -> assertEquals(m, m.multiply(1)),
                () -> assertEquals(m.additiveInverse(), m.multiply(-1)),
                () -> assertEquals(r, m.multiply(10))
        );
    }

    @Test
    void multiplyMatrix() {
        Matrix m2 = new AnyRectangularMatrix(new Number[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        });

        Matrix r = new AnySquareMatrix(new Number[][]{
                {1, 2, 3},
                {10, 18, 18},
                {6, 39, 24}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m.multiply(NullMatrix.getInstance(5, 2))),
                () -> assertEquals(NullMatrix.getInstance(3, 2), m.multiply(NullMatrix.getInstance(4, 2))),
                () -> assertEquals(m, m.multiply(new IdentityMatrix(4))),
                () -> assertEquals(r, m.multiply(m2))
        );
    }

    @Test
    void divide() {
        assertThrows(NotInvertibleException.class, () -> m.divide(NullMatrix.getInstance(4, 3)));
    }

    @Test
    void additiveInverse() {
        Matrix r = new AnyRectangularMatrix(new Number[][]{
                {-1, -2, -3, -4},
                {-5, -9, -9, -7},
                {-2, -13, -8, -17}
        });

        assertEquals(r, m.additiveInverse());
    }
}