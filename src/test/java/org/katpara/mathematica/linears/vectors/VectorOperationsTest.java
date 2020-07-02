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
    void testAddVector() {
        assertAll(
                () -> assertThrows(InvalidVectorDimension.class,
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
                () -> assertThrows(InvalidVectorDimension.class,
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
                        () -> VectorOperations.transposeDimensions(new ArrayVector(2, 3), 1)),
                () -> assertThrows(InvalidVectorDimension.class,
                        () -> VectorOperations.transposeDimensions(new ArrayVector(1, 2, 3), 3)),
                () ->
                        assertEquals(
                                new ArrayVector(1, 2),
                                VectorOperations.transposeDimensions(new ArrayVector(1, 2, 3), 2)),
                () ->
                        assertEquals(
                                new ArrayVector(new Number[]{1, 2, 3, 0, 0}),
                                VectorOperations.transposeDimensions(new ArrayVector(1, 2, 3), 5))
        );
    }

}