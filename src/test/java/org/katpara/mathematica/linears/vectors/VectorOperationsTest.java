package org.katpara.mathematica.linears.vectors;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.InvalidParameterProvided;
import org.katpara.mathematica.exceptions.InvalidVectorDimension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VectorOperationsTest {

    @Test
    void testInverseVector() {
        assertArrayEquals(new Number[]{-3D, -4D},
                VectorOperations.getInverseVector(new ArrayVector(3, 4)).getElements());
    }

    @Test
    void testVectorScalar() {
        assertEquals(new ArrayVector(2D, 4D), VectorOperations.scale(new ArrayVector(1, 2), 2));
    }

    @Test
    void testAddVector() {
        assertAll(
                () -> assertThrows(InvalidVectorDimension.class,
                        () -> VectorOperations.addVectors(
                                new ArrayVector(1, 2),
                                new ArrayVector(1, 2, 3)
                        )
                ),
                () -> assertArrayEquals(new Number[]{2D, 4D, 6D},
                        VectorOperations
                                .addVectors(
                                        new ArrayVector(1, 2, 3),
                                        new ArrayVector(1, 2, 3))
                                .getElements()
                )
        );
    }

    @Test
    void testSubtractVector() {
        assertAll(
                () -> assertThrows(InvalidVectorDimension.class,
                        () -> VectorOperations.subtractVectors(
                                new ArrayVector(1, 2),
                                new ArrayVector(1, 2, 3)
                        )
                ),
                () -> assertArrayEquals(new Number[]{0D, 0D, 0D},
                        VectorOperations
                                .subtractVectors(
                                        new ArrayVector(1, 2, 3),
                                        new ArrayVector(1, 2, 3))
                                .getElements()
                )
        );
    }


    @Test
    void testAddListOfVectors() {
        assertAll(
                () -> assertThrows(InvalidParameterProvided.class,
                        () -> VectorOperations.addVectors(
                                new ArrayList<>(List.of(new ArrayVector(2, 3))))),
                () -> {
                    assertArrayEquals(
                            new Number[]{2D, 4D, 6D},
                            VectorOperations
                                    .addVectors(new ArrayList<>(
                                            List.of(
                                                    new ArrayVector(1, 2, 3),
                                                    new ArrayVector(1, 2, 3))))
                                    .getElements());
                },
                () -> {
                    assertArrayEquals(
                            new Number[]{3.25, 6.5, 9.75},
                            VectorOperations
                                    .addVectors(new ArrayList<>(
                                            List.of(
                                                    new ArrayVector(1, 2, 3),
                                                    new ArrayVector(1, 2, 3),
                                                    new ArrayVector(1.25, 2.5, 3.75))))
                                    .getElements());
                }
        );
    }

    @Test
    void testSubtractListOfVectors() {
        assertAll(
                () -> assertThrows(InvalidParameterProvided.class,
                        () -> VectorOperations.addVectors(
                                new ArrayList<>(List.of(new ArrayVector(2, 3))))),
                () -> {
                    assertArrayEquals(
                            new Number[]{0.0, 0.0, 0.0},
                            VectorOperations
                                    .subtractVectors(new ArrayList<>(
                                            List.of(
                                                    new ArrayVector(1, 2, 3),
                                                    new ArrayVector(1, 2, 3))))
                                    .getElements());
                },
                () -> {
                    assertArrayEquals(
                            new Number[]{1D, 1D, 1D},
                            VectorOperations
                                    .subtractVectors(new ArrayList<>(
                                            List.of(
                                                    new ArrayVector(3.25, 5.5, 7.75),
                                                    new ArrayVector(1, 2, 3),
                                                    new ArrayVector(1.25, 2.5, 3.75))))
                                    .getElements());
                }
        );
    }

    @Test
    void testTransposeDimension() {
        assertAll(
                () -> assertThrows(InvalidVectorDimension.class,
                        () -> VectorOperations.transpose(new ArrayVector(2, 3), 1)),
                () -> assertThrows(InvalidVectorDimension.class,
                        () -> VectorOperations.transpose(new ArrayVector(1, 2, 3), 3)),
                () ->
                        assertEquals(
                                new ArrayVector(1, 2),
                                VectorOperations.transpose(new ArrayVector(1, 2, 3), 2)),
                () ->
                        assertEquals(
                                new ArrayVector(new Number[]{1, 2, 3, 0, 0}),
                                VectorOperations.transpose(new ArrayVector(1, 2, 3), 5))
        );
    }

    @Test
    void testDotProducts() {
        assertAll(
                () -> assertThrows(InvalidVectorDimension.class,
                        () -> VectorOperations.dotProduct(
                                new ArrayVector(1, 2),
                                new ArrayVector(1, 2, 3)
                        )),
                () -> assertEquals(24, VectorOperations.dotProduct(
                        new ArrayVector(3, 4),
                        new ArrayVector(4, 3)
                )),
                () -> assertEquals(55, VectorOperations.dotProduct(
                        new ArrayVector(7, 1, 3),
                        new ArrayVector(5, 5, 5)
                ))
        );
    }

    @Test
    void testAngle() {
        assertAll(
                () -> assertThrows(InvalidVectorDimension.class,
                        () -> VectorOperations.angle(
                                new ArrayVector(1, 2),
                                new ArrayVector(1, 2, 3),
                                true
                        )),
                () -> assertEquals(45.00000000000001, VectorOperations.angle(
                        new ArrayVector(2, 2),
                        new ArrayVector(0, 3),
                        true
                )),
                () -> assertEquals(0.7853981633974484, VectorOperations.angle(
                        new ArrayVector(2, 2),
                        new ArrayVector(0, 3),
                        false
                )),
                () -> assertEquals(131.647015792716, VectorOperations.angle(
                        new ArrayVector(3, -4, 5),
                        new ArrayVector(2, 7, -3),
                        true
                ))
        );
    }

    @Test
    void testCrossProduct() {
        assertAll(
                () -> assertThrows(InvalidVectorDimension.class,
                        () -> VectorOperations.crossProduct(
                                new ArrayVector(1, 2),
                                new ArrayVector(1, 2)
                        )),
                () -> assertThrows(InvalidVectorDimension.class,
                        () -> VectorOperations.crossProduct(
                                new ArrayVector(1, 2, 3),
                                new ArrayVector(1, 2)
                        )),
                () -> assertThrows(InvalidVectorDimension.class,
                        () -> VectorOperations.crossProduct(
                                new ArrayVector(1, 2 ),
                                new ArrayVector(1, 2, 3)
                        )),
                () -> assertEquals(
                        new ArrayVector(5D, 1D, 11D),
                        VectorOperations.crossProduct(
                                new ArrayVector(2, 1, -1),
                                new ArrayVector(-3, 4, 1)
                        )),
                () -> assertEquals(
                        new ArrayVector(-5D, -1D, -11D),
                        VectorOperations.crossProduct(
                                new ArrayVector(-3, 4, 1),
                                new ArrayVector(2, 1, -1)
                        ))
        );
    }

}