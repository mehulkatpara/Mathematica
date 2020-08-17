package org.katpara.mathematica.linears.matrices.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.linears.matrices.Matrix;
import org.katpara.mathematica.linears.matrices.rectangulars.AnyRectangularMatrix;
import org.katpara.mathematica.linears.matrices.squares.AnySquareMatrix;

import static org.junit.jupiter.api.Assertions.assertThrows;


class LUPFactorisationTest {

    Matrix m1, m2, m3;

    @BeforeEach
    void setUp() {
        m1 = new AnyRectangularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {4, 5, 6}
                }
        );

        m2 = new AnySquareMatrix(
                new double[][]{
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 10}
                }
        );
    }

    @Test
    void factorize() {
        assertThrows(NotSquareMatrixException.class, () -> LUPFactorisation.getInstance().factorize(m1));

        LUPFactorisation.getInstance().factorize(m2);
    }
}