package org.katpara.mathematica.linears.vectors;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.NullArgumentProvided;
import org.katpara.mathematica.exceptions.VectorInvalidDimension;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArrayVectorTest {

    @Test
    void testNumberConstructor() {
        assertAll(
                () -> assertThrows(VectorInvalidDimension.class, () -> new ArrayVector(new Number[]{1})),
                () -> assertThrows(NullArgumentProvided.class, () -> new ArrayVector(new Number[2])),
                () -> assertThrows(NullArgumentProvided.class, () -> new ArrayVector(new Number[]{null, null})),
                () -> new ArrayVector(new Number[]{1, 2}),
                () -> new ArrayVector(1, 2),
                () -> new ArrayVector(1, 2, 3),
                () -> assertNotNull(new ArrayVector(1, 2).toString())
        );
    }

    @Test
    void testListConstructor() {
        assertAll(
                () -> assertThrows(VectorInvalidDimension.class, () -> new ArrayVector(List.of(1))),
                () -> new ArrayVector(List.of(1, 2))
        );
    }

    @Test
    void testSetConstructor() {
        assertAll(
                () -> assertThrows(VectorInvalidDimension.class, () -> new ArrayVector(Set.of(1))),
                () -> new ArrayVector(Set.of(1, 2))
        );
    }

    @Test
    void testMapConstructor() {
        assertAll(
                () -> assertThrows(VectorInvalidDimension.class, () -> new ArrayVector(Map.of(1, 1))),
                () -> new ArrayVector(Map.of(1, 1, 2, 2))
        );
    }

    @Test
    void testVectorDimensions() {
        assertAll(
                () -> assertEquals(2, new ArrayVector(1, 2).getDimension()),
                () -> assertEquals(3, new ArrayVector(1, 2, 3).getDimension())
        );
    }

    @Test
    void testVectorMagnitude() {
        assertEquals(5, new ArrayVector(3, 4).getMagnitude());
    }


    @Test
    void testVectorElements() {
        assertArrayEquals(new Number[]{3, 4},  new ArrayVector(3, 4).getElements());
    }

    @Test
    void testVectorScalar() {
        Number[] n = new ArrayVector(3, 4).scale(3).getElements();

        assertAll(
                () -> assertEquals(9.0, n[0]),
                () -> assertEquals(12.0, n[1])
        );
    }
}