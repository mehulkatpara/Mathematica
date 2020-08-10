package org.katpara.mathematica.linears.matrices.squares;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.util.Rounding;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalSquareMatrixTest {

    private SquareMatrix m, m1, m2;

    @BeforeEach
    void setUp() {
        m = new DiagonalSquareMatrix(new Number[]{1, 2, 3, 4, 5});
        m1 = new DiagonalSquareMatrix(new Number[]{2, 3, 4, 5, 6});
        m2 = new DiagonalSquareMatrix(new Number[]{1, 2.34, 3.3, 4.92837, 5.888822});
    }

    @Test
    void testConstructor() {
        assertAll(
                () -> assertThrows(NullArgumentProvidedException.class, () -> new DiagonalSquareMatrix(null)),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new DiagonalSquareMatrix(new Number[]{null, null})),
                () -> assertArrayEquals(new int[]{5, 5}, m.getSize())
        );
    }

    @Test
    void isSymmetric() {
        assertTrue(m.isSymmetric());
    }

    @Test
    void isSquareMatrix() {
        assertTrue(m.isSquareMatrix());
    }

    @Test
    void getRank() {
        assertEquals(5, m.getRank());
    }

    @Test
    void getTrace() {
        assertEquals(15.0, m.getTrace());
    }

    @Test
    void getDecimalTrace() {
        assertEquals(17.46, m2.getTrace(Rounding.Decimals.TWO));
    }

    @Test
    void getDeterminant() {
        assertEquals(120.0, m.getDeterminant());
    }

    @Test
    void testGetDeterminant() {
        assertEquals(224.11, m2.getDeterminant(Rounding.Decimals.TWO));
    }

    @Test
    void transpose() {
        assertEquals(m, m.transpose());
    }

    @Test
    void addMatrix() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{3, 5, 7, 9, 11}), m.add(m1));
    }

    @Test
    void addScalar() {
        assertEquals(new AnySquareMatrix(new Number[][]{
                {11, 10, 10, 10, 10},
                {10, 12, 10, 10, 10},
                {10, 10, 13, 10, 10},
                {10, 10, 10, 14, 10},
                {10, 10, 10, 10, 15}}), m.add(10));
    }

    @Test
    void subtract() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{1, 1, 1, 1, 1}), m1.subtract(m));
    }

    @Test
    void multiplyMatrix() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{2, 6, 12, 20, 30}), m.multiply(m1));
    }

    @Test
    void multiplyScalar() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{10, 20, 30, 40, 50}), m.multiply(10));
    }

    @Test
    void divide() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{1, 1, 1, 1, 1}), m.divide(m));
    }

    @Test
    void multiplicativeInverse() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{1, 0.5, 1/3d, 0.25, 0.2}), m.multiplicativeInverse());
    }

    @Test
    void additiveInverse() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{-1, -2, -3, -4, -5}), m.additiveInverse());
    }

    @Test
    void power() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{1, 4, 9, 16, 25}), m.power(2));
        assertEquals(new DiagonalSquareMatrix(new Number[]{1, 0.5, 1/3d, 0.25, 0.2}), m.power(-1));
    }
}