package org.katpara.mathematica.linears.matrices.squares;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.util.Rounding;

import static org.junit.jupiter.api.Assertions.*;

class IdentityMatrixTest {

    private SquareMatrix m;
    @BeforeEach
    void setUp() {
        m = new IdentityMatrix(5);
    }

    @Test
    void getRank() {
        assertEquals(5, m.getRank());
    }

    @Test
    void getTrace() {
        assertEquals(5, m.getTrace());
    }

    @Test
    void getDeterminant() {
        assertEquals(1, m.getDeterminant());
    }

    @Test
    void add() {
        assertAll(
                () -> assertEquals(new AnySquareMatrix(new Number[][]{
                        {11, 10, 10, 10, 10},
                        {10, 11, 10, 10, 10},
                        {10, 10, 11, 10, 10},
                        {10, 10, 10, 11, 10},
                        {10, 10, 10, 10, 11}
                }), m.add(10)),
                () -> assertEquals(new DiagonalSquareMatrix(new Number[]{2, 2, 2, 2, 2}), m.add(m))
        );
    }

    @Test
    void subtract() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{0, 0, 0, 0, 0}), m.subtract(m));
    }

    @Test
    void multiplyMatrix() {
        SquareMatrix m1 = new AnySquareMatrix(new Number[][]{
                {11, 10, 10, 10, 10},
                {10, 11, 10, 10, 10},
                {10, 10, 11, 10, 10},
                {10, 10, 10, 11, 10},
                {10, 10, 10, 10, 11}
        });
        assertEquals(m1, m.multiply(m1));
    }

    @Test
    void multiplyScalar() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{2, 2, 2, 2, 2}), m.multiply(2));
    }

    @Test
    void divide() {
        assertEquals(m, m.divide(m));
    }

    @Test
    void multiplicativeInverse() {
        assertEquals(m, m.multiplicativeInverse());
    }

    @Test
    void additiveInverse() {
        assertEquals(new DiagonalSquareMatrix(new Number[]{-1, -1, -1, -1, -1}), m.additiveInverse());
    }

    @Test
    void power() {
        assertEquals(m, m.power(10));
    }
}