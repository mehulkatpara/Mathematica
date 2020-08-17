package org.katpara.mathematica.linears.matrices;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.linears.matrices.dep.ArrayMatrixOld;
import org.katpara.mathematica.linears.matrices.dep.Matrix_Old;
import org.katpara.mathematica.util.Rounding;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linear.dep.InvalidMatrixDimensionException;
import org.katpara.mathematica.exceptions.linear.dep.InvalidMatrixOperationException;
import org.katpara.mathematica.linears.vectors.ArrayVector;
import org.katpara.mathematica.linears.vectors.Vector;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMatrixOldTest {

    @Test
    void testArrayConstructor() {

        Number[][] t1 = new Number[0][0];
        Number[][] t2 = new Number[1][0];
        Number[][] t3 = new Number[][]{{1}};
        Number[][] t4 = new Number[][]{{1, null}, {2, 0}};
        Number[][] t5 = new Number[][]{{1, 2}, {null, 0}};
        Number[][] t6 = new Number[][]{{1, 2}, {1, 0}};

        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(t1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(t2)),
                () -> new ArrayMatrixOld(t3),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new ArrayMatrixOld(t4)),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new ArrayMatrixOld(t5)),
                () -> new ArrayMatrixOld(t6)
        );
    }

    @Test
    void testVectorConstructor() {
        List<Vector> vl1 = Collections.emptyList();
        List<Vector> vl2 = List.of(ArrayVector.of(1, 2), ArrayVector.of(1, 2, 3));
        List<Vector> vl3 = List.of(ArrayVector.of(1, 2));
        List<Vector> vl4 = List.of(ArrayVector.of(1, 2, 3), ArrayVector.of(4, 5, 6));

        Set<Vector> vs1 = Collections.emptySet();
        Set<Vector> vs2 = Set.of(ArrayVector.of(1, 2), ArrayVector.of(1, 2, 3));
        Set<Vector> vs3 = Set.of(ArrayVector.of(1, 2));
        Set<Vector> vs4 = Set.of(ArrayVector.of(1, 2, 3), ArrayVector.of(4, 5, 6));

        Map<Integer, Vector> vm1 = Collections.emptyMap();
        Map<Integer, Vector> vm2 = Map.of(1, ArrayVector.of(1, 2), 2, ArrayVector.of(1, 2, 3));
        Map<Integer, Vector> vm3 = Map.of(1, ArrayVector.of(1, 2));
        Map<Integer, Vector> vm4 = Map.of(1, ArrayVector.of(1, 2), 2, ArrayVector.of(1, 2));

        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(vl1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(vl2)),
                () -> new ArrayMatrixOld(vl3),
                () -> new ArrayMatrixOld(vl4),

                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(vs1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(vs2)),
                () -> new ArrayMatrixOld(vs3),
                () -> new ArrayMatrixOld(vs4),

                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(vm1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(vm2)),
                () -> new ArrayMatrixOld(vm3),
                () -> new ArrayMatrixOld(vm4)
        );

    }

    @Test
    void testCollectionConstructor() {

        List<List<Number>> l1 = List.of(Collections.emptyList());
        List<List<Number>> l2 = List.of(List.of(1), Collections.emptyList());
        List<List<Number>> l3 = List.of(List.of(1), List.of(1, 2));
        List<List<Number>> l4 = List.of(List.of(1), List.of(1));

        Set<List<Number>> s1 = Set.of(Collections.emptyList());
        Set<List<Number>> s2 = Set.of(List.of(1), Collections.emptyList());
        Set<List<Number>> s3 = Set.of(List.of(1), List.of(1, 2));
        Set<List<Number>> s4 = Set.of(List.of(1), List.of(2));

        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(l1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(l2)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(l3)),
                () -> new ArrayMatrixOld(l4),

                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(s1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(s2)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrixOld(s3)),
                () -> new ArrayMatrixOld(s4)
        );
    }

    @Test
    void testDimension() {
        var _d1 = new ArrayMatrixOld(new Number[][]{{1, 2}}).getDimension();
        var _d2 = new ArrayMatrixOld(new Number[][]{{1}, {1}}).getDimension();
        var _d3 = new ArrayMatrixOld(List.of(
                ArrayVector.of(1, 2, 3),
                ArrayVector.of(4, 5, 6))
        ).getDimension();

        assertAll(
                () -> assertEquals(1, _d1[0]),
                () -> assertEquals(2, _d1[1]),
                () -> assertEquals(2, _d2[0]),
                () -> assertEquals(1, _d2[1]),
                () -> assertEquals(2, _d3[0]),
                () -> assertEquals(3, _d3[1])
        );
    }

    @Test
    void testToArray() {
        var _d1 = new ArrayMatrixOld(new Number[][]{{1, 2}, {3, 4}});
        var _d2 = new ArrayMatrixOld(List.of(
                ArrayVector.of(1, 2, 3),
                ArrayVector.of(4, 5, 6),
                ArrayVector.of(7, 8, 9)));

        var e_d1 = _d1.toArray();
        var e_d2 = _d2.toArray();
        assertAll(
                () -> assertEquals(1, e_d1[0][0]),
                () -> assertEquals(2, e_d1[0][1]),
                () -> assertEquals(3, e_d1[1][0]),
                () -> assertEquals(4, e_d1[1][1]),
                () -> assertEquals(1, e_d2[0][0]),
                () -> assertEquals(2, e_d2[0][1]),
                () -> assertEquals(3, e_d2[0][2]),
                () -> assertEquals(4, e_d2[1][0]),
                () -> assertEquals(5, e_d2[1][1]),
                () -> assertEquals(6, e_d2[1][2]),
                () -> assertEquals(7, e_d2[2][0]),
                () -> assertEquals(8, e_d2[2][1]),
                () -> assertEquals(9, e_d2[2][2])
        );
    }

    @Test
    void testToList() {
        var _d1 = new ArrayMatrixOld(new Number[][]{{1, 2}, {3, 4}});
        var lists = _d1.toList();

        assertAll(
                () -> assertEquals(2, lists.size()),
                () -> assertEquals(2, lists.get(0).size()),
                () -> assertArrayEquals(new Number[]{1, 2}, lists.get(0).toArray(Number[]::new)),
                () -> assertArrayEquals(new Number[]{3, 4}, lists.get(1).toArray(Number[]::new))
        );

    }

    @Test
    void testToArrayVectors() {
        var _d1 = new ArrayMatrixOld(new Number[][]{{1, 2, 3}, {4, 5, 6}});
        var list = _d1.toArrayVectors();

        assertAll(
                () -> assertEquals(2, list.size()),
                () -> assertEquals(3, list.get(0).getDimension()),
                () -> assertEquals(ArrayVector.of(1, 2, 3), list.get(0)),
                () -> assertEquals(ArrayVector.of(4, 5, 6), list.get(1))
        );
    }

    @Test
    void testMatrixEquals() {
        var _d1 = new ArrayMatrixOld(new Number[][]{{1, 2}, {3, 4}});
        var _d2 = new ArrayMatrixOld(new Number[][]{{1, 2}, {3, 4}});
        var _d3 = new ArrayMatrixOld(new Number[][]{{11, 20}, {23, 34}});
        var _d4 = new ArrayMatrixOld(new Number[][]{{1, 2, 3}, {4, 5, 6}});
        var _d5 = new ArrayMatrixOld(List.of(
                ArrayVector.of(1, 2),
                ArrayVector.of(3, 4)));

        assertAll(
                () -> assertEquals(_d1, _d2),
                () -> assertNotEquals(_d1, _d3),
                () -> assertNotEquals(_d1, _d4),
                () -> assertEquals(_d1, _d5)
        );
    }

    @Test
    void testIsRowVector() {
        var _d1 = new ArrayMatrixOld(new Number[][]{{1, 2}});
        var _d2 = new ArrayMatrixOld(List.of(ArrayVector.of(1, 2, 3)));
        var _d3 = new ArrayMatrixOld(List.of(
                ArrayVector.of(1, 2, 3),
                ArrayVector.of(4, 5, 6)));

        assertAll(
                () -> assertTrue(_d1.isRowVector()),
                () -> assertTrue(_d2.isRowVector()),
                () -> assertFalse(_d3.isRowVector())
        );
    }

    @Test
    void testIsColumnVector() {
        var _d1 = new ArrayMatrixOld(new Number[][]{{1}, {2}});
        var _d2 = new ArrayMatrixOld(List.of(List.of(1), List.of(2), List.of(3)));
        var _d3 = new ArrayMatrixOld(List.of(
                ArrayVector.of(1, 2, 3),
                ArrayVector.of(4, 5, 6)));

        assertAll(
                () -> assertTrue(_d1.isColumnVector()),
                () -> assertTrue(_d2.isColumnVector()),
                () -> assertFalse(_d3.isColumnVector())
        );
    }

    @Test
    void testIsSquareMatrix() {
        var _d1 = new ArrayMatrixOld(new Number[][]{{1, 2}, {3, 4}});
        var _d2 = new ArrayMatrixOld(List.of(
                ArrayVector.of(1, 2, 3),
                ArrayVector.of(4, 5, 6),
                ArrayVector.of(7, 8, 9)));
        var _d3 = new ArrayMatrixOld(List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6)));

        assertAll(
                () -> assertTrue(_d1.isSquareMatrix()),
                () -> assertTrue(_d2.isSquareMatrix()),
                () -> assertTrue(ArrayMatrixOld.exchangeMatrix(4).isSquareMatrix()),
                () -> assertTrue(ArrayMatrixOld.hilbertMatrix(4).isSquareMatrix()),
                () -> assertTrue(ArrayMatrixOld.lehmerMatrix(4).isSquareMatrix()),
                () -> assertTrue(ArrayMatrixOld.oneMatrix(4, 4).isSquareMatrix()),
                () -> assertFalse(ArrayMatrixOld.oneMatrix(4, 5).isSquareMatrix()),
                () -> assertFalse(_d3.isSquareMatrix())
        );
    }

    @Test
    void testTrace() {
        assertAll(
                () -> assertThrows(InvalidMatrixOperationException.class,
                        () -> ArrayMatrixOld.oneMatrix(5, 10).getTrace()),
                () -> assertThrows(InvalidMatrixOperationException.class,
                        () -> new ArrayMatrixOld(new Number[][]{{1, 2, 3}, {4, 5, 6}}).getTrace()),
                () -> assertEquals(5, ArrayMatrixOld.identityMatrix(5).getTrace()),
                () -> assertEquals(5, ArrayMatrixOld.oneMatrix(5, 5).getTrace()),
                () -> assertEquals(0, ArrayMatrixOld.shiftMatrix(3, ArrayMatrixOld.ShiftMatrixType.LOWER).getTrace()),
                () -> assertEquals(0, ArrayMatrixOld.exchangeMatrix(4).getTrace()),
                () -> assertEquals(1, ArrayMatrixOld.exchangeMatrix(7).getTrace()),
                () -> assertEquals(12, ArrayMatrixOld.pascalMatrix(12, ArrayMatrixOld.PascalMatrixType.UPPER).getTrace()),
                () -> assertEquals(8, ArrayMatrixOld.pascalMatrix(8, ArrayMatrixOld.PascalMatrixType.LOWER).getTrace()),
                () -> assertEquals(5D, new ArrayMatrixOld(new Number[][]{
                        {3, 2, 0, 4},
                        {4, 1, -2, 3},
                        {-3, -2, -4, 7},
                        {3, 1, 1, 5}
                }).getTrace()),
                () -> assertEquals(30D, new ArrayMatrixOld(new Number[][]{
                        {1, 2, 3, 4, 2},
                        {-4, 11, 5, 2, 0},
                        {-1, 0, 3, 2, 3},
                        {22, 5, 3, 1, 1},
                        {3, 5, -22, 1, 14},
                }).getTrace())
        );
    }

    @Test
    void testDeterminant() {
        assertAll(
                () -> assertThrows(InvalidMatrixOperationException.class, () ->
                                                                                  new ArrayMatrixOld(new Number[][]{{1, 2, 3}, {1, 2, 3}}).getDeterminant()),
                () -> assertEquals(1D, new ArrayMatrixOld(new Number[][]{{1}}).getDeterminant()),
                () -> assertEquals(-4D, new ArrayMatrixOld(new Number[][]{{3, 2}, {5, 2}}).getDeterminant()),
                () -> assertEquals(16D, new ArrayMatrixOld(new Number[][]{
                        {1, 2, 1, 0},
                        {0, 3, 1, 1},
                        {-1, 0, 3, 1},
                        {3, 1, 2, 0}
                }).getDeterminant(Rounding.Decimals.FOUR)),
                () -> assertEquals(256, new ArrayMatrixOld(new Number[][]{
                        {3, 7, 0},
                        {8, 0, -2},
                        {0, -4, -5}
                }).getDeterminant()),
                () -> assertEquals(-49964D, new ArrayMatrixOld(new Number[][]{
                        {6, 3, -6, -4, 9},
                        {-2, -5, 9, 2, 10},
                        {2, 3, 4, -5, 6},
                        {8, 6, 1, 4, 7},
                        {12, 8, -20, 17, 4}
                }).getDeterminant(Rounding.Decimals.SIX)),
                () -> assertEquals(0.0, new ArrayMatrixOld(new Number[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                }).getDeterminant())
        );
    }

    @Test
    void testZeroMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> ArrayMatrixOld.zeroMatrix(0, 0)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> ArrayMatrixOld.zeroMatrix(0, 1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> ArrayMatrixOld.zeroMatrix(1, 0)),
                () -> System.out.println(ArrayMatrixOld.zeroMatrix(1, 1))
        );
    }

    @Test
    void testOneMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> ArrayMatrixOld.oneMatrix(0, 0)),
                () -> System.out.println(ArrayMatrixOld.oneMatrix(5, 5))
        );
    }

    @Test
    void testPascalMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class,
                        () -> ArrayMatrixOld.pascalMatrix(0, ArrayMatrixOld.PascalMatrixType.SYMMETRIC)),
                () -> System.out.println(ArrayMatrixOld.pascalMatrix(5, ArrayMatrixOld.PascalMatrixType.UPPER)),
                () -> System.out.println(ArrayMatrixOld.pascalMatrix(5, ArrayMatrixOld.PascalMatrixType.LOWER)),
                () -> System.out.println(ArrayMatrixOld.pascalMatrix(5, ArrayMatrixOld.PascalMatrixType.SYMMETRIC))
        );
    }

    @Test
    void testLehmerMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> ArrayMatrixOld.lehmerMatrix(0)),
                () -> System.out.println(ArrayMatrixOld.lehmerMatrix(1)),
                () -> System.out.println(ArrayMatrixOld.lehmerMatrix(4))
        );
    }

    @Test
    void testHilbertMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> ArrayMatrixOld.hilbertMatrix(0)),
                () -> System.out.println(ArrayMatrixOld.hilbertMatrix(1)),
                () -> System.out.println(ArrayMatrixOld.hilbertMatrix(4))
        );
    }


    @Test
    void testIdentityMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> ArrayMatrixOld.identityMatrix(0)),
                () -> System.out.println(ArrayMatrixOld.identityMatrix(1)),
                () -> System.out.println(ArrayMatrixOld.identityMatrix(4))
        );
    }

    @Test
    void testExchangeMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> ArrayMatrixOld.exchangeMatrix(0)),
                () -> System.out.println(ArrayMatrixOld.exchangeMatrix(1)),
                () -> System.out.println(ArrayMatrixOld.exchangeMatrix(4))
        );
    }

    @Test
    void testRedhefferMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> ArrayMatrixOld.redhefferMatrix(0)),
                () -> System.out.println(ArrayMatrixOld.redhefferMatrix(1)),
                () -> System.out.println(ArrayMatrixOld.redhefferMatrix(4)),
                () -> System.out.println(ArrayMatrixOld.redhefferMatrix(12))
        );
    }

    @Test
    void testShiftMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class,
                        () -> ArrayMatrixOld.shiftMatrix(0, ArrayMatrixOld.ShiftMatrixType.UPPER)),
                () -> System.out.println(ArrayMatrixOld.shiftMatrix(1, ArrayMatrixOld.ShiftMatrixType.UPPER)),
                () -> System.out.println(ArrayMatrixOld.shiftMatrix(5, ArrayMatrixOld.ShiftMatrixType.UPPER)),
                () -> assertThrows(InvalidMatrixDimensionException.class,
                        () -> ArrayMatrixOld.shiftMatrix(0, ArrayMatrixOld.ShiftMatrixType.LOWER)),
                () -> System.out.println(ArrayMatrixOld.shiftMatrix(1, ArrayMatrixOld.ShiftMatrixType.LOWER)),
                () -> System.out.println(ArrayMatrixOld.shiftMatrix(5, ArrayMatrixOld.ShiftMatrixType.LOWER))


        );
    }

    @Test
    void testRank() {
        assertAll(
                () -> assertEquals(5, ArrayMatrixOld.identityMatrix(5).getRank()),
                () -> assertEquals(1, ArrayMatrixOld.oneMatrix(5, 10).getRank()),
                () -> assertEquals(4, ArrayMatrixOld.shiftMatrix(5, ArrayMatrixOld.ShiftMatrixType.LOWER).getRank()),
                () -> assertEquals(2, new ArrayMatrixOld(new Number[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}).getRank()),
                () -> assertEquals(1, new ArrayMatrixOld(new Number[][]{{1, 2, 3}}).getRank()),
                () -> assertEquals(1, new ArrayMatrixOld(new Number[][]{{1}, {2}, {3}}).getRank()),
                () -> assertEquals(1, new ArrayMatrixOld(new Number[][]{{0, 0}, {0, 0}}).getRank()),
                () -> assertEquals(3, new ArrayMatrixOld(new Number[][]{{12, 29, 72}, {2, 7, 8}, {6, 5, 0}}).getRank()),
                () -> assertEquals(3, new ArrayMatrixOld(new Number[][]{{2, 7, 8}, {0, 6, 5}, {5, 7, 0}, {5, 2, 1}}).getRank())
        );
    }

    @Test
    void testAddition() {

        Matrix_Old m = new ArrayMatrixOld(new Number[][]{{1, 2}, {3, 4}});

        assertAll(
                () -> assertEquals(m.add(10), new ArrayMatrixOld(new Number[][]{{11, 2}, {3, 14}})),
                () -> assertEquals(m.add(new ArrayMatrixOld(new Number[][]{{5, 6}, {7, 8}})),
                        new ArrayMatrixOld(new Number[][]{{6, 8}, {10, 12}})),
                () -> assertEquals(m.subtract(new ArrayMatrixOld(new Number[][]{{1, 2}, {3, 4}})),
                        new ArrayMatrixOld(new Number[][]{{0, 0}, {0, 0}}))
        );
    }


    @Test
    void testMultiplication() {
        Matrix_Old a = new ArrayMatrixOld(new Number[][]{{1, 2}, {3, 4}});
        Matrix_Old b = new ArrayMatrixOld(new Number[][]{{1, 2}, {5, 9}});
        Matrix_Old c = new ArrayMatrixOld(new Number[][]{{1, 8}, {3, 6}});
        Matrix_Old d = new ArrayMatrixOld(new Number[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}});
        Matrix_Old e = new ArrayMatrixOld(new Number[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}});

        assertAll(
                () -> assertThrows(InvalidMatrixOperationException.class, () -> c.multiply(d)),
                () -> assertEquals(new ArrayMatrixOld(new Number[][]{{2, 4}, {6, 8}}), a.multiply(2)),
                () -> assertEquals(new ArrayMatrixOld(new Number[][]{{7, 20}, {32, 94}}), b.multiply(c)),
                () -> assertEquals(new ArrayMatrixOld(new Number[][]{{50, 60}, {114, 140}, {178, 220}}), d.multiply(e))
        );
    }

    @Test
    void testTranspose() {
        assertEquals(new ArrayMatrixOld(new Number[][]{{0, 7, 3}, {4, 0, 1}}),
                new ArrayMatrixOld(new Number[][]{{0, 4}, {7, 0}, {3, 1}}).transpose());
    }

    @Test
    void testInverse() {
        ArrayMatrixOld m = new ArrayMatrixOld(new Number[][]{
                {25, 5, 1},
                {64, 8, 1},
                {144, 12, 1}
        });
        ArrayMatrixOld a = new ArrayMatrixOld(new Number[][]{
                {0.0476, -0.0833, 0.0357},
                {-0.9524, 1.4167, -0.4643},
                {4.5714, -5.0, 1.4286}
        });
        ArrayMatrixOld m1 = new ArrayMatrixOld(new Number[][]{
                {1, 2, 1},
                {2, 2, 3},
                {-1, -3, 0}
        });
        ArrayMatrixOld a1 = new ArrayMatrixOld(new Number[][]{
                {-9.0, 3.0, -4.0},
                {3.0, -1.0, 1.0},
                {4.0, -1.0, 2.0}
        });

        assertAll(
                () -> assertThrows(InvalidMatrixOperationException.class,
                        () -> new ArrayMatrixOld(new Number[][]{{1, 2}}).inverse()),
                () -> assertThrows(InvalidMatrixOperationException.class,
                        () -> new ArrayMatrixOld(new Number[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}).inverse()),
                () -> assertEquals(a, m.inverse(Rounding.Decimals.FOUR)),
                () -> assertEquals(a1, m1.inverse())
        );
    }

    @Test
    void testStaticGenerators() {
        Matrix_Old m1 = ArrayMatrixOld.of(5);
        Matrix_Old m2 = ArrayMatrixOld.of(5, Rounding.Decimals.FOUR);
        Matrix_Old m3 = ArrayMatrixOld.of(5, 10, 100, Rounding.Decimals.ZERO);
        Matrix_Old m4 = ArrayMatrixOld.of(5, 5, 10, Rounding.Decimals.FOUR);

        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m3);
        System.out.println(m4);


        Matrix_Old m5 = ArrayMatrixOld.of(3, 2);
        Matrix_Old m6 = ArrayMatrixOld.of(2, 4, Rounding.Decimals.SIX);
        Matrix_Old m7 = ArrayMatrixOld.of(3, 1, -9, 9, Rounding.Decimals.ZERO);
        Matrix_Old m8 = ArrayMatrixOld.of(5, 7, 50, 100, Rounding.Decimals.FOUR);

        System.out.println(m5);
        System.out.println(m6);
        System.out.println(m7);
        System.out.println(m8);
    }
}