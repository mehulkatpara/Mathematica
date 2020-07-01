package org.katpara.mathematica.linears.vectors;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.VectorInvalidDimension;

import static org.junit.jupiter.api.Assertions.*;

class VectorOperationsTest {

    @Test
    void testInverseVector() {
        assertArrayEquals(new Number[]{-3D, -4D},
                VectorOperations.getInverseVector(new ArrayVector(3, 4)).getElements());
    }

    @Test
    void testAddVector() {
        assertAll(
                () -> assertThrows(VectorInvalidDimension.class,
                        () -> VectorOperations.addVector(
                                new ArrayVector(1, 2),
                                new ArrayVector(1, 2, 3)
                        )
                ),
                () -> assertArrayEquals(new Number[]{2D, 4D, 6D},
                        VectorOperations
                                .addVector(
                                        new ArrayVector(1, 2, 3),
                                        new ArrayVector(1, 2, 3))
                                .getElements()
                )
        );
    }

    @Test
    void testSubtractVector() {
        assertAll(
                () -> assertThrows(VectorInvalidDimension.class,
                        () -> VectorOperations.subtractVector(
                                new ArrayVector(1, 2),
                                new ArrayVector(1, 2, 3)
                        )
                ),
                () -> assertArrayEquals(new Number[]{0D, 0D, 0D},
                        VectorOperations
                                .subtractVector(
                                        new ArrayVector(1, 2, 3),
                                        new ArrayVector(1, 2, 3))
                                .getElements()
                )
        );
    }

}