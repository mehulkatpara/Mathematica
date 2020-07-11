package org.katpara.mathematica.linears.vectors;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.InvalidVectorDimensionException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ArrayVectorTest {

    @Test
    void testNumberConstructor() {

        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> new ArrayVector(new Number[]{1})),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new ArrayVector(new Number[2])),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new ArrayVector(new Number[]{null, null})),
                () -> assertArrayEquals(Vector.arrayVectorOf(1, 2).toArray(), new Number[]{1, 2}),
                () -> assertNotNull(Vector.arrayVectorOf(1, 2).toString())
        );
    }

    @Test
    void testListConstructor() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> new ArrayVector(List.of(1))),
                () -> assertEquals(Vector.arrayVectorOf(1, 2), new ArrayVector(List.of(1, 2))),
                () -> assertEquals(Vector.arrayVectorOf(1, 2, 3), new ArrayVector(List.of(1, 2, 3))),
                () -> assertEquals(new ArrayVector(new Number[]{1, 2, 3, 4}), new ArrayVector(List.of(1, 2, 3, 4)))
        );
    }

    @Test
    void testSetConstructor() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> new ArrayVector(new LinkedHashSet<>(List.of(1)))),
                () -> assertEquals(Vector.arrayVectorOf(1, 2), new ArrayVector(new LinkedHashSet<>(List.of(1, 2)))),
                () -> assertEquals(Vector.arrayVectorOf(1, 2, 3), new ArrayVector(new LinkedHashSet<>(List.of(1, 2, 3)))),
                () -> assertEquals(new ArrayVector(new Number[]{1, 2, 3, 4})
                        , new ArrayVector(new LinkedHashSet<>(List.of(1, 2, 3, 4))))
        );
    }

    @Test
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
                () -> assertEquals(Vector.arrayVectorOf(1, 2), new ArrayVector(m1)),
                () -> assertEquals(Vector.arrayVectorOf(1, 2, 3), new ArrayVector(m2))
        );
    }

    @Test
    void testVectorDimensions() {
        assertAll(
                () -> assertEquals(2, Vector.arrayVectorOf(1, 2).getDimension()),
                () -> assertEquals(3, Vector.arrayVectorOf(1, 2, 3).getDimension())
        );
    }

    @Test
    void testVectorMagnitude() {
        assertEquals(5, Vector.arrayVectorOf(3, 4).getMagnitude());
    }


    @Test
    void testVectorElements() {
        assertArrayEquals(new Number[]{3, 4}, Vector.arrayVectorOf(3, 4).toArray());
    }

    @Test
    void testHashCode() {
        Vector v1 = Vector.arrayVectorOf(3, 4);
        Vector v2 = Vector.arrayVectorOf(4, 5);
        assertNotEquals(v1.hashCode(), v2.hashCode());
    }

    @Test
    void testEqualsCode() {
        Vector v1 = Vector.arrayVectorOf(3, 4);
        Vector v2 = Vector.arrayVectorOf(4, 5);
        assertNotEquals(v1, v2);

        Vector v3 = Vector.arrayVectorOf(3, 4);
        assertEquals(v1, v3);
    }

    @Test
    void testInverseVector() {
        assertArrayEquals(new Number[]{-3D, -4D}, Vector.arrayVectorOf(3, 4).inverse().toArray());
    }

    @Test
    void testVectorScalar() {
        assertEquals(Vector.arrayVectorOf(2D, 4D), Vector.arrayVectorOf(1, 2).scale(2));
    }

    @Test
    void testAddVector() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(1, 2).add(Vector.arrayVectorOf(1, 2, 3))),
                () -> assertArrayEquals(new Number[]{2D, 4D, 6D},
                        Vector.arrayVectorOf(1, 2, 3).add(Vector.arrayVectorOf(1, 2, 3)).toArray())
        );
    }

    @Test
    void testSubtractVector() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(1, 2).subtract(Vector.arrayVectorOf(1, 2, 3))
                ),
                () -> assertArrayEquals(new Number[]{0D, 0D, 0D},
                        Vector.arrayVectorOf(1, 2, 3).subtract(Vector.arrayVectorOf(1, 2, 3)).toArray())
        );
    }


    @Test
    void testAddListOfVectors() {
        Vector v = Vector.arrayVectorOf(1, 2, 3);
        assertAll(
                () -> assertThrows(InvalidParameterProvidedException.class,
                        () -> v.add(new ArrayList<>(List.of(Vector.arrayVectorOf(2, 3))))),
                () -> assertEquals(Vector.arrayVectorOf(3D, 6D, 9D),
                        v.add(new ArrayList<>(List.of(Vector.arrayVectorOf(1, 2, 3),
                                Vector.arrayVectorOf(1, 2, 3))))),
                () -> assertEquals(Vector.arrayVectorOf(3.25, 6.5, 9.75),
                        Vector.arrayVectorOf(1, 2, 3).add(List.of(
                                Vector.arrayVectorOf(1, 2, 3),
                                Vector.arrayVectorOf(1.25, 2.5, 3.75))))
        );
    }

    @Test
    void testTransposeDimension() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(2, 3).transpose(1)),
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(1, 2, 3).transpose(3)),
                () -> assertEquals(Vector.arrayVectorOf(1, 2),
                        Vector.arrayVectorOf(1, 2, 3).transpose(2)),
                () -> assertEquals(
                        new ArrayVector(new Number[]{1, 2, 3, 0, 0}),
                        Vector.arrayVectorOf(1, 2, 3).transpose(5))
        );
    }

    @Test
    void testDotProducts() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(1, 2).dot(Vector.arrayVectorOf(1, 2, 3))),
                () -> assertEquals(24, Vector.arrayVectorOf(3, 4)
                                               .dot(Vector.arrayVectorOf(4, 3))),
                () -> assertEquals(55, Vector.arrayVectorOf(7, 1, 3)
                                               .dot(Vector.arrayVectorOf(5, 5, 5)))
        );
    }

    @Test
    void testAngle() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(1, 2)
                                      .angle(Vector.arrayVectorOf(1, 2, 3), true)),
                () -> assertEquals(45.00000000000001, Vector.arrayVectorOf(2, 2)
                                                              .angle(Vector.arrayVectorOf(0, 3), true)),
                () -> assertEquals(0.7853981633974484, Vector
                                                               .arrayVectorOf(2, 2)
                                                               .angle(Vector.arrayVectorOf(0, 3), false)),
                () -> assertEquals(131.647015792716, Vector.arrayVectorOf(3, -4, 5)
                                                             .angle(Vector.arrayVectorOf(2, 7, -3), true))
        );
    }

    @Test
    void testCrossProduct() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(1, 2).cross(Vector.arrayVectorOf(1, 2))),
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(1, 2, 3).cross(Vector.arrayVectorOf(1, 2))),
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(1, 2).cross(Vector.arrayVectorOf(1, 2, 3))),
                () -> assertEquals(Vector.arrayVectorOf(5D, 1D, 11D),
                        Vector.arrayVectorOf(2, 1, -1).cross(Vector.arrayVectorOf(-3, 4, 1))),
                () -> assertEquals(
                        Vector.arrayVectorOf(-5D, -1D, -11D),
                        Vector.arrayVectorOf(-3, 4, 1).cross(Vector.arrayVectorOf(2, 1, -1)))
        );
    }

    @Test
    void testOrthogonal() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(1, 2).isOrthogonal(Vector.arrayVectorOf(1, 2, 3))),
                () -> assertFalse(Vector.arrayVectorOf(1, 2, 3)
                                          .isOrthogonal(Vector.arrayVectorOf(1, 2, 3))),
                () -> assertTrue(Vector.arrayVectorOf(1, 0)
                                         .isOrthogonal(Vector.arrayVectorOf(0, 1)))
        );
    }

    @Test
    void testParallel() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> Vector.arrayVectorOf(1, 2).isParallel(Vector.arrayVectorOf(1, 2, 3))),
                () -> assertEquals(1, Vector.arrayVectorOf(1, 2, 3)
                                          .isParallel(Vector.arrayVectorOf(1, 2, 3))),
                () -> assertEquals(3, Vector.arrayVectorOf(1, 2, 3)
                                              .isParallel(Vector.arrayVectorOf(3, 6, 9))),
                () -> assertEquals(-1, Vector.arrayVectorOf(1, 0)
                                         .isParallel(Vector.arrayVectorOf(0, 1)))
        );
    }
}