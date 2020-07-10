package org.katpara.mathematica.linears.vectors;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.InvalidVectorDimensionException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArrayVectorTest {

    @Test
    void testNumberConstructor() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> new ArrayVector(new Number[]{1})),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new ArrayVector(new Number[2])),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new ArrayVector(new Number[]{null, null})),
                () -> assertEquals(new ArrayVector(1, 2), new ArrayVector(new Number[]{1, 2})),
                () -> assertNotNull(new ArrayVector(1, 2).toString()),
                () -> assertNotNull(new ArrayVector(1, 2, 3).toString())
        );
    }

    @RepeatedTest(100)
    void testListConstructor() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> new ArrayVector(List.of(1))),
                () -> assertEquals(new ArrayVector(1, 2), new ArrayVector(List.of(1, 2))),
                () -> assertEquals(new ArrayVector(1, 2, 3), new ArrayVector(List.of(1, 2, 3))),
                () -> assertEquals(new ArrayVector(new Number[]{1, 2, 3, 4}), new ArrayVector(List.of(1, 2, 3, 4)))
        );
    }

    @RepeatedTest(100)
    void testSetConstructor() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> new ArrayVector(new LinkedHashSet<>(List.of(1)))),
                () -> assertEquals(new ArrayVector(1, 2), new ArrayVector(new LinkedHashSet<>(List.of(1, 2)))),
                () -> assertEquals(new ArrayVector(1, 2, 3), new ArrayVector(new LinkedHashSet<>(List.of(1, 2, 3)))),
                () -> assertEquals(new ArrayVector(new Number[]{1, 2, 3, 4})
                        , new ArrayVector(new LinkedHashSet<>(List.of(1, 2, 3, 4))))
        );
    }

    @RepeatedTest(100)
    void testMapConstructor() {
        Map<Integer, Integer> m1 = new LinkedHashMap<>();
        m1.put(1, 1);
        m1.put(2, 2);

        Map<Integer, Integer> m2 = new LinkedHashMap<>();
        m2.put(1, 1);
        m2.put(2, 2);
        m2.put(3, 3);

        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> new ArrayVector(Map.of(1, 1))),
                () -> assertEquals(new ArrayVector(1, 2), new ArrayVector(m1)),
                () -> assertEquals(new ArrayVector(1, 2, 3), new ArrayVector(m2))
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
        assertArrayEquals(new Number[]{3, 4}, new ArrayVector(3, 4).getElements());
    }

    @Test
    void testHashCode() {
        Vector v1 = new ArrayVector(3, 4);
        Vector v2 = new ArrayVector(4, 5);
        assertNotEquals(v1.hashCode(), v2.hashCode());
    }

    @Test
    void testEqualsCode() {
        Vector v1 = new ArrayVector(3, 4);
        Vector v2 = new ArrayVector(4, 5);
        assertNotEquals(v1, v2);

        Vector v3 = new ArrayVector(3, 4);
        assertEquals(v1, v3);
    }
}