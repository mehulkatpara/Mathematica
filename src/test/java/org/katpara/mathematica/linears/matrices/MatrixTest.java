package org.katpara.mathematica.linears.matrices;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.InvalidMatrixDimensionException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatrixTest {

    @Test
    void testZeroMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getZeroMatrix(0, 0)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getZeroMatrix(0, 1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getZeroMatrix(1, 0)),
                () -> System.out.println(Matrix.getZeroMatrix(1, 1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getZeroMatrix(new int[0])),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getZeroMatrix(new int[1])),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getZeroMatrix(new int[2])),
                () -> System.out.println(Matrix.getZeroMatrix(new int[]{2, 2}))
        );
    }

    @RepeatedTest(100)
    void testPascalMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class,
                        () -> Matrix.getPascalMatrix(0, Matrix.PascalMatrixType.SYMMETRIC)),
                () -> Matrix.getPascalMatrix(5, Matrix.PascalMatrixType.UPPER),
                () -> Matrix.getPascalMatrix(5, Matrix.PascalMatrixType.LOWER),
                () -> Matrix.getPascalMatrix(5, Matrix.PascalMatrixType.SYMMETRIC)
        );
    }

    @Test
    void testOneMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getOneMatrix(0, 0)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getOneMatrix(new int[]{1})),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getOneMatrix(new int[]{0, 0})),
                () -> Matrix.getOneMatrix(5, 5)
        );
    }

    @Test
    void testLehmerMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getLehmerMatrix(0)),
                () -> Matrix.getLehmerMatrix(1),
                () -> Matrix.getLehmerMatrix(4)
        );
    }

    @Test
    void testIdentityMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getIdentityMatrix(0)),
                () -> Matrix.getIdentityMatrix(1),
                () -> Matrix.getIdentityMatrix(4)
        );
    }

    @Test
    void testHilbertMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getHilbertMatrix(0)),
                () -> Matrix.getHilbertMatrix(1),
                () -> Matrix.getHilbertMatrix(4)
        );
    }

    @Test
    void testExchangeMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getExchangeMatrix(0)),
                () -> Matrix.getExchangeMatrix(1),
                () -> Matrix.getExchangeMatrix(4)
        );
    }

    @Test
    void testRedhefferMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.getRedhefferMatrix(0)),
                () -> Matrix.getRedhefferMatrix(1),
                () -> Matrix.getRedhefferMatrix(4),
                () -> Matrix.getRedhefferMatrix(12)
        );
    }

    @Test
    void testShiftMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class,
                        () -> Matrix.getShiftMatrix(0, Matrix.ShiftMatrixType.UPPER)),
                () -> System.out.println(Matrix.getShiftMatrix(1, Matrix.ShiftMatrixType.UPPER)),
                () -> System.out.println(Matrix.getShiftMatrix(5, Matrix.ShiftMatrixType.UPPER)),
                () -> assertThrows(InvalidMatrixDimensionException.class,
                        () -> Matrix.getShiftMatrix(0, Matrix.ShiftMatrixType.LOWER)),
                () -> System.out.println(Matrix.getShiftMatrix(1, Matrix.ShiftMatrixType.LOWER)),
                () -> System.out.println(Matrix.getShiftMatrix(5, Matrix.ShiftMatrixType.LOWER))
        );
    }
}