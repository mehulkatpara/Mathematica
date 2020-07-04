package org.katpara.mathematica.linears.matrices;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void testArrayZeroVector() {
        assertAll(
                () -> assertThrows(InvalidParameterProvidedException.class, () -> Matrix.getZeroMatrix(0, 0)),
                () -> assertThrows(InvalidParameterProvidedException.class, () -> Matrix.getZeroMatrix(0, 1)),
                () -> assertThrows(InvalidParameterProvidedException.class, () -> Matrix.getZeroMatrix(1, 0)),
                () -> System.out.println(Matrix.getZeroMatrix(1, 1)),
                () -> assertThrows(InvalidParameterProvidedException.class, () -> Matrix.getZeroMatrix(new int[0])),
                () -> assertThrows(InvalidParameterProvidedException.class, () -> Matrix.getZeroMatrix(new int[1])),
                () -> assertThrows(InvalidParameterProvidedException.class, () -> Matrix.getZeroMatrix(new int[2])),
                () -> System.out.println(Matrix.getZeroMatrix(new int[]{2, 2}))
        );
    }
}