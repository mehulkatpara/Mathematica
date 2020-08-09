package org.katpara.mathematica.linears.vectors;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.linears.matrices.dep.ArrayMatrixOld;
import org.katpara.mathematica.linears.matrices.dep.Matrix_Old;
import org.katpara.mathematica.util.Rounding;
import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.dep.InvalidVectorDimensionException;
import org.katpara.mathematica.exceptions.linears.dep.InvalidVectorOperationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ArrayVectorTest {

    @Test
    void testNumberConstructor() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> new ArrayVector(new Number[]{1})),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new ArrayVector(new Number[2])),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new ArrayVector(new Number[]{null, null})),
                () -> assertArrayEquals(ArrayVector.of(1, 2).toArray(), new Number[]{1, 2}),
                () -> assertNotNull(ArrayVector.of(1, 2).toString())
        );
    }

    @Test
    void testListConstructor() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> new ArrayVector(List.of(1))),
                () -> assertEquals(ArrayVector.of(1, 2), new ArrayVector(List.of(1, 2))),
                () -> assertEquals(ArrayVector.of(1, 2, 3), new ArrayVector(List.of(1, 2, 3))),
                () -> assertEquals(new ArrayVector(new Number[]{1, 2, 3, 4}), new ArrayVector(List.of(1, 2, 3, 4)))
        );
    }

    @Test
    void testSetConstructor() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> new ArrayVector(new LinkedHashSet<>(List.of(1)))),
                () -> assertEquals(ArrayVector.of(1, 2), new ArrayVector(new LinkedHashSet<>(List.of(1, 2)))),
                () -> assertEquals(ArrayVector.of(1, 2, 3), new ArrayVector(new LinkedHashSet<>(List.of(1, 2, 3)))),
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
                () -> assertEquals(ArrayVector.of(1, 2), new ArrayVector(m1)),
                () -> assertEquals(ArrayVector.of(1, 2, 3), new ArrayVector(m2))
        );
    }

    @Test
    void testVectorDimensions() {
        assertAll(
                () -> assertEquals(2, ArrayVector.of(1, 2).getDimension()),
                () -> assertEquals(3, ArrayVector.of(1, 2, 3).getDimension())
        );
    }

    @Test
    void testVectorMagnitude() {
        assertEquals(5, ArrayVector.of(3, 4).getMagnitude());
    }


    @Test
    void testVectorElements() {
        assertArrayEquals(new Number[]{3, 4}, ArrayVector.of(3, 4).toArray());
    }

    @Test
    void testHashCode() {
        Vector v1 = ArrayVector.of(3, 4);
        Vector v2 = ArrayVector.of(4, 5);
        Vector v3 = ArrayVector.of(3, 4);
        assertAll(
                () -> assertNotEquals(v1.hashCode(), v2.hashCode()),
                () -> assertEquals(v1.hashCode(), v3.hashCode())
        );
    }

    @Test
    void testEqualsCode() {
        Vector v1 = ArrayVector.of(3, 4);
        Vector v2 = ArrayVector.of(4, 5);
        assertNotEquals(v1, v2);

        Vector v3 = ArrayVector.of(3, 4);
        assertEquals(v1, v3);
    }

    @Test
    void testInverseVector() {
        assertEquals(ArrayVector.of(-3, -4), ArrayVector.of(3, 4).inverse());
    }

    @Test
    void testVectorScalar() {
        assertEquals(ArrayVector.of(2, 4), ArrayVector.of(1, 2).scale(2));
    }

    @Test
    void testAddVector() {
        assertAll(
                () -> assertThrows(InvalidVectorOperationException.class,
                        () -> ArrayVector.of(1, 2).add(ArrayVector.of(1, 2, 3))),
                () -> assertEquals(ArrayVector.of(2, 4, 6),
                        ArrayVector.of(1, 2, 3).add(ArrayVector.of(1, 2, 3)))
        );
    }

    @Test
    void testSubtractVector() {
        assertAll(
                () -> assertThrows(InvalidVectorOperationException.class,
                        () -> ArrayVector.of(1, 2).subtract(ArrayVector.of(1, 2, 3))
                ),
                () -> assertEquals(ArrayVector.of(0, 0, 0),
                        ArrayVector.of(1, 2, 3).subtract(ArrayVector.of(1, 2, 3)))
        );
    }


    @Test
    void testAddListOfVectors() {
        Vector v = ArrayVector.of(1, 2, 3);
        assertAll(
                () -> assertThrows(InvalidParameterProvidedException.class,
                        () -> v.add(new ArrayList<>(List.of(ArrayVector.of(2, 3))))),
                () -> assertEquals(ArrayVector.of(3, 6, 9),
                        v.add(new ArrayList<>(List.of(ArrayVector.of(1, 2, 3),
                                ArrayVector.of(1, 2, 3))))),
                () -> assertEquals(ArrayVector.of(3.25, 6.5, 9.75),
                        ArrayVector.of(1, 2, 3).add(List.of(
                                ArrayVector.of(1, 2, 3),
                                ArrayVector.of(1.25, 2.5, 3.75))))
        );
    }

    @Test
    void testTransposeDimension() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> ArrayVector.of(2, 3).transpose(1)),
                () -> assertThrows(InvalidVectorDimensionException.class,
                        () -> ArrayVector.of(1, 2, 3).transpose(3)),
                () -> assertEquals(ArrayVector.of(1, 2),
                        ArrayVector.of(1, 2, 3).transpose(2)),
                () -> assertEquals(
                        new ArrayVector(new Number[]{1, 2, 3, 0, 0}),
                        ArrayVector.of(1, 2, 3).transpose(5))
        );
    }

    @Test
    void testDotProducts() {
        assertAll(
                () -> assertThrows(InvalidVectorOperationException.class,
                        () -> ArrayVector.of(1, 2).dot(ArrayVector.of(1, 2, 3))),
                () -> assertEquals(24, ArrayVector.of(3, 4)
                                               .dot(ArrayVector.of(4, 3))),
                () -> assertEquals(55, ArrayVector.of(7, 1, 3)
                                               .dot(ArrayVector.of(5, 5, 5)))
        );
    }

    @Test
    void testAngle() {
        assertAll(
                () -> assertThrows(InvalidVectorOperationException.class,
                        () -> ArrayVector.of(1, 2)
                                      .angle(ArrayVector.of(1, 2, 3), Vector.Angle.DEGREE)),
                () -> assertEquals(45.00000000000001, ArrayVector.of(2, 2)
                                                              .angle(ArrayVector.of(0, 3), Vector.Angle.DEGREE)),
                () -> assertEquals(45.0,
                        ArrayVector.of(2, 2)
                                .angle(ArrayVector.of(0, 3), Vector.Angle.DEGREE, Rounding.Decimals.TWO)),
                () -> assertEquals(0.7853981633974484, ArrayVector.of(2, 2)
                                                               .angle(ArrayVector.of(0, 3), Vector.Angle.RADIAN)),
                () -> assertEquals(131.647015792716, ArrayVector.of(3, -4, 5)
                                                             .angle(ArrayVector.of(2, 7, -3), Vector.Angle.DEGREE))
        );
    }

    @Test
    void testCrossProduct() {
        assertAll(
                () -> assertThrows(InvalidVectorOperationException.class,
                        () -> ArrayVector.of(1, 2).cross(ArrayVector.of(1, 2))),
                () -> assertThrows(InvalidVectorOperationException.class,
                        () -> ArrayVector.of(1, 2, 3).cross(ArrayVector.of(1, 2))),
                () -> assertThrows(InvalidVectorOperationException.class,
                        () -> ArrayVector.of(1, 2).cross(ArrayVector.of(1, 2, 3))),
                () -> assertEquals(ArrayVector.of(5, 1, 11),
                        ArrayVector.of(2, 1, -1).cross(ArrayVector.of(-3, 4, 1))),
                () -> assertEquals(
                        ArrayVector.of(-5, -1, -11),
                        ArrayVector.of(-3, 4, 1).cross(ArrayVector.of(2, 1, -1)))
        );
    }

    @Test
    void testOrthogonal() {
        assertAll(
                () -> assertThrows(InvalidVectorOperationException.class,
                        () -> ArrayVector.of(1, 2).isOrthogonal(ArrayVector.of(1, 2, 3))),
                () -> assertFalse(ArrayVector.of(1, 2, 3)
                                          .isOrthogonal(ArrayVector.of(1, 2, 3))),
                () -> assertTrue(ArrayVector.of(1, 0)
                                         .isOrthogonal(ArrayVector.of(0, 1)))
        );
    }

    @Test
    void testParallel() {
        assertAll(
                () -> assertThrows(InvalidVectorOperationException.class,
                        () -> ArrayVector.of(1, 2).isParallel(ArrayVector.of(1, 2, 3))),
                () -> assertTrue(ArrayVector.of(1, 2, 3)
                                         .isParallel(ArrayVector.of(1, 2, 3))),
                () -> assertTrue(ArrayVector.of(1, 2, 3)
                                         .isParallel(ArrayVector.of(3, 6, 9))),
                () -> assertFalse(ArrayVector.of(1, 0)
                                          .isParallel(ArrayVector.of(0, 1)))
        );
    }

    @Test
    void testUnitVector() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> ArrayVector.of(1)),
                () -> System.out.println(ArrayVector.of(5))
        );
    }

    @Test
    void testCosines() {
        assertArrayEquals(new double[]{0.2672612419124244, 0.5345224838248488, 0.8017837257372732},
                ArrayVector.of(1, 2, 3).getCosines(Vector.Angle.RADIAN));
    }

    @Test
    void testScalarProjection() {
        assertEquals(-33D / 13D, ArrayVector.of(5, -12).scalarProjection(ArrayVector.of(3, 4)));
    }

    @Test
    void testVectorProjection() {
        assertEquals(ArrayVector.of(-0.9763313609467456, 2.3431952662721893),
                ArrayVector.of(5, -12).vectorProjection(ArrayVector.of(3, 4)));
    }

    @Test
    void testVectorRejection() {
        System.out.println(ArrayVector.of(5, -12).vectorRejection(ArrayVector.of(3, 4)));
    }

    @Test
    void testScalarAddition() {
        assertEquals(ArrayVector.of(11D, 12D), ArrayVector.of(1, 2).add(10));
    }

    @Test
    void testRandomVector() {
        assertAll(
                () -> assertEquals(3, ArrayVector.of(0.876, 0.409, 0.822).getDimension()),
                () -> assertEquals(3, ArrayVector.of(3, 98, 100, Rounding.Decimals.TWO).getDimension()),
                () -> assertEquals(5, ArrayVector.of(5, Rounding.Decimals.FOUR).getDimension())
        );
    }

    @Test
    void testVectorFunction() {
        assertAll(
                () -> assertThrows(InvalidVectorDimensionException.class, () -> ArrayVector.of(1, Math::sqrt, Rounding.Decimals.TWO)),
                () -> System.out.println(ArrayVector.of(4, 0, 1, Math::acos, Rounding.Decimals.THREE)),
                () -> System.out.println(ArrayVector.of(3, Math::sqrt, Rounding.Decimals.SEVEN)),
                () -> ArrayVector.of(4, 0, 1, Math::log, Rounding.Decimals.THREE),
                () -> ArrayVector.of(3, -1, 0, (e) -> e * 10),
                () -> ArrayVector.of(2, (e) -> e * 1.2, Rounding.Decimals.TWO),
                () -> ArrayVector.of(2, (e) -> e * 1)
        );
    }

    @Test
    void testMatrixMultiplication() {
        Matrix_Old f = new ArrayMatrixOld(new Number[][]{{1, 2, 3},{4, 5, 6},{7, 8, 9}});
        Vector v = ArrayVector.of(2, 1, 3);

        assertAll(
                () -> assertThrows(InvalidVectorOperationException.class, () -> ArrayVector.of(1, 2).multiply(f)),
                () -> assertEquals(ArrayVector.of(13, 31, 49), v.multiply(f))
        );
    }
}