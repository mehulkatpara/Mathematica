package org.katpara.mathematica.linears.matrices;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.InvalidMatrixDimension;
import org.katpara.mathematica.exceptions.NullArgumentProvided;
import org.katpara.mathematica.linears.matrices.ArrayMatrix;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMatrixTest {

    @Test
    void testArrayConstructor() {

        // Row = 0, Column = 0
        Number[][] t1 = new Number[0][0];
        // Row = 1, Column = 0
        Number[][] t2 = new Number[1][0];
        // Row = 1, Column = 1
        Number[][] t3 = new Number[1][1];
        // Row = 2, Column = 0
        Number[][] t4 = new Number[2][0];
        // Null values
        Number[][] t5 = new Number[][]{{1, null}, {2, 0}};
        Number[][] t6 = new Number[][]{{1, 2}, {null, 0}};

        Number[][] t7 = new Number[][]{{1, 2}, {1, 0}};

        assertAll(
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(t1)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(t2)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(t3)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(t4)),
                () -> assertThrows(NullArgumentProvided.class, () -> new ArrayMatrix(t5)),
                () -> assertThrows(NullArgumentProvided.class, () -> new ArrayMatrix(t6)),
                () -> new ArrayMatrix(t7)
        );
    }

}