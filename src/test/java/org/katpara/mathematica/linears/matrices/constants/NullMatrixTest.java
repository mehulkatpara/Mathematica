package org.katpara.mathematica.linears.matrices.constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.linear.InvalidMatrixDimensionProvidedException;
import org.katpara.mathematica.exceptions.linear.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.rectangulars.AnyRectangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;

import static org.junit.jupiter.api.Assertions.*;

class NullMatrixTest {

    private Matrix m1, m2;

    @BeforeEach
    void setUp() {
        m1 = NullMatrix.getInstance(3, 4);
        m2 = NullMatrix.getInstance(3);
    }

    @Test
    void testConstructor() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> NullMatrix.getInstance(0)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> NullMatrix.getInstance(0, 0)),
                () -> assertArrayEquals(new int[]{3, 3}, m2.getSize()),
                () -> assertArrayEquals(new int[]{3, 4}, m1.getSize())
        );
    }

    @Test
    void rank() {
        assertEquals(0, NullMatrix.getInstance(3).getRank());
    }

    @Test
    void dimensionChecks() {
        assertAll(
                () -> assertTrue(NullMatrix.getInstance(1, 3).isRowVector()),
                () -> assertTrue(NullMatrix.getInstance(3, 1).isColumnVector()),
                () -> assertTrue(NullMatrix.getInstance(3, 3).isSquareMatrix())
        );
    }

    @Test
    void transpose() {
        assertEquals(m1, m1.transpose());
    }

    @Test
    void addScalar() {

        Matrix r1 = new AnyRectangularMatrix(new double[][]{
                {10, 10, 10, 10},
                {10, 10, 10, 10},
                {10, 10, 10, 10}
        });

        Matrix r2 = new AnySquareMatrix(new double[][]{
                {10, 10, 10},
                {10, 10, 10},
                {10, 10, 10}
        });

        assertAll(
                () -> assertEquals(m1, m1.add(0)),
                () -> assertEquals("AnyRectangularMatrix", m1.add(10).getClass().getSimpleName()),
                () -> assertEquals(r1, m1.add(10)),
                () -> assertEquals("AnySquareMatrix", m2.add(10).getClass().getSimpleName()),
                () -> assertEquals(r2, m2.add(10))
        );
    }

    @Test
    void addMatrix() {
        Matrix m3 = new AnyRectangularMatrix(new double[][]{
                {10, 10, 10, 10},
                {10, 10, 10, 10},
                {10, 10, 10, 10}
        });

        Matrix r3 = new AnyRectangularMatrix(new double[][]{
                {10, 10, 10, 10},
                {10, 10, 10, 10},
                {10, 10, 10, 10}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(m2)),
                () -> assertEquals(m1, m1.add(m1)),
                () -> assertEquals(r3, m1.add(m3))
        );
    }

    @Test
    void subtract() {
        Matrix m3 = new AnyRectangularMatrix(new double[][]{
                {10, 10, 10, 10},
                {10, 10, 10, 10},
                {10, 10, 10, 10}
        });

        Matrix r3 = new AnyRectangularMatrix(new double[][]{
                {-10, -10, -10, -10},
                {-10, -10, -10, -10},
                {-10, -10, -10, -10}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(m2)),
                () -> assertEquals(m1, m1.subtract(m1)),
                () -> assertEquals(r3, m1.subtract(m3))
        );
    }

    @Test
    void multiplyScalar() {
        assertEquals(m1, m1.multiply(20));
    }

    @Test
    void multiplyMatrix() {
        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(m2)),
                () -> assertEquals(m2, m1.multiply(NullMatrix.getInstance(4, 3)))
        );
    }

    @Test
    void divide() {
        assertAll(
                () -> assertThrows(NotSquareMatrixException.class, () -> m2.divide(m1)),
                () -> assertThrows(NotInvertibleException.class, () -> m2.divide(NullMatrix.getInstance(3)))
        );
    }


    @Test
    void additiveInverse() {
        assertEquals(m1, m1.additiveInverse());
    }

    @Test
    void power() {
        assertAll(
                () -> assertThrows(NotSquareMatrixException.class, () -> m1.power(1)),
                () -> assertEquals(m2, m2.power(10))
        );
    }
}