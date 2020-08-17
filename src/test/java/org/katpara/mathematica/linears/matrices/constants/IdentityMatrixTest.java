package org.katpara.mathematica.linears.matrices.constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.linear.InvalidMatrixDimensionProvidedException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.DiagonalSquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.SquareMatrix;
import org.katpara.mathematica.linears.matrices.squares.triangulars.LowerTriangularMatrix;

import static org.junit.jupiter.api.Assertions.*;

class IdentityMatrixTest {

    private SquareMatrix m;
    @BeforeEach
    void setUp() {
        m = IdentityMatrix.getInstance(5);
    }

    @Test
    void testConstructor() {
        assertThrows(InvalidMatrixDimensionProvidedException.class, () -> IdentityMatrix.getInstance(0));
        assertThrows(InvalidMatrixDimensionProvidedException.class, () -> IdentityMatrix.getInstance(-1));
        System.out.println(IdentityMatrix.getInstance(1));
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
        Matrix m2 = m.add(m);
        Matrix m3 = new LowerTriangularMatrix(new double[][]{
                {1, 0, 0, 0, 0},
                {2, 2, 0, 0, 0},
                {3, 3, 3, 0, 0},
                {4, 4, 4, 4, 0},
                {5, 5, 5, 5, 5}
        });
        Matrix r3 = new LowerTriangularMatrix(new double[][]{
                {2, 0, 0, 0, 0},
                {2, 3, 0, 0, 0},
                {3, 3, 4, 0, 0},
                {4, 4, 4, 5, 0},
                {5, 5, 5, 5, 6}
        });

        assertAll(
                () -> assertEquals(new AnySquareMatrix(new double[][]{
                        {11, 10, 10, 10, 10},
                        {10, 11, 10, 10, 10},
                        {10, 10, 11, 10, 10},
                        {10, 10, 10, 11, 10},
                        {10, 10, 10, 10, 11}
                }), m.add(10)),
                () -> assertEquals(DiagonalSquareMatrix.getInstance(new double[]{2, 2, 2, 2, 2}), m2),
                () -> assertEquals("DiagonalSquareMatrix", m2.getClass().getSimpleName()),
                () -> assertEquals(r3, m.add(m3)),
                () -> assertEquals("LowerTriangularMatrix", m.add(m3).getClass().getSimpleName())
        );
    }

    @Test
    void subtract() {
        assertEquals(DiagonalSquareMatrix.getInstance(new double[]{0, 0, 0, 0, 0}), m.subtract(m));
    }

    @Test
    void multiplyMatrix() {
        SquareMatrix m1 = new AnySquareMatrix(new double[][]{
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
        assertEquals(DiagonalSquareMatrix.getInstance(new double[]{2, 2, 2, 2, 2}), m.multiply(2));
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
        assertEquals(DiagonalSquareMatrix.getInstance(new double[]{-1, -1, -1, -1, -1}), m.additiveInverse());
    }

    @Test
    void power() {
        assertEquals(m, m.power(10));
    }
}