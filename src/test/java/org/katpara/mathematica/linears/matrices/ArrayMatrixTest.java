package org.katpara.mathematica.linears.matrices;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.InvalidMatrixDimensionException;
import org.katpara.mathematica.exceptions.linears.InvalidMatrixOperationException;
import org.katpara.mathematica.linears.vectors.ArrayVector;
import org.katpara.mathematica.linears.vectors.Vector;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMatrixTest {

    @Test
    void testArrayConstructor() {

        Number[][] t1 = new Number[0][0];
        Number[][] t2 = new Number[1][0];
        Number[][] t3 = new Number[][]{{1}};
        Number[][] t4 = new Number[][]{{1, null}, {2, 0}};
        Number[][] t5 = new Number[][]{{1, 2}, {null, 0}};
        Number[][] t6 = new Number[][]{{1, 2}, {1, 0}};

        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(t1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(t2)),
                () -> new ArrayMatrix(t3),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new ArrayMatrix(t4)),
                () -> assertThrows(NullArgumentProvidedException.class, () -> new ArrayMatrix(t5)),
                () -> new ArrayMatrix(t6)
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
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(vl1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(vl2)),
                () -> new ArrayMatrix(vl3),
                () -> new ArrayMatrix(vl4),

                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(vs1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(vs2)),
                () -> new ArrayMatrix(vs3),
                () -> new ArrayMatrix(vs4),

                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(vm1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(vm2)),
                () -> new ArrayMatrix(vm3),
                () -> new ArrayMatrix(vm4)
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
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(l1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(l2)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(l3)),
                () -> new ArrayMatrix(l4),

                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(s1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(s2)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> new ArrayMatrix(s3)),
                () -> new ArrayMatrix(s4)
        );
    }

    @Test
    void testDimension() {
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2}}).getDimension();
        var _d2 = new ArrayMatrix(new Number[][]{{1}, {1}}).getDimension();
        var _d3 = new ArrayMatrix(List.of(
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
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}});
        var _d2 = new ArrayMatrix(List.of(
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
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}});
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
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2, 3}, {4, 5, 6}});
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
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}});
        var _d2 = new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}});
        var _d3 = new ArrayMatrix(new Number[][]{{11, 20}, {23, 34}});
        var _d4 = new ArrayMatrix(new Number[][]{{1, 2, 3}, {4, 5, 6}});
        var _d5 = new ArrayMatrix(List.of(
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
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2}});
        var _d2 = new ArrayMatrix(List.of(ArrayVector.of(1, 2, 3)));
        var _d3 = new ArrayMatrix(List.of(
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
        var _d1 = new ArrayMatrix(new Number[][]{{1}, {2}});
        var _d2 = new ArrayMatrix(List.of(List.of(1), List.of(2), List.of(3)));
        var _d3 = new ArrayMatrix(List.of(
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
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}});
        var _d2 = new ArrayMatrix(List.of(
                ArrayVector.of(1, 2, 3),
                ArrayVector.of(4, 5, 6),
                ArrayVector.of(7, 8, 9)));
        var _d3 = new ArrayMatrix(List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6)));

        assertAll(
                () -> assertTrue(_d1.isSquareMatrix()),
                () -> assertTrue(_d2.isSquareMatrix()),
                () -> assertTrue(Matrix.exchangeArrayMatrix(4).isSquareMatrix()),
                () -> assertTrue(Matrix.hilbertArrayMatrix(4).isSquareMatrix()),
                () -> assertTrue(Matrix.lehmerArrayMatrix(4).isSquareMatrix()),
                () -> assertTrue(Matrix.oneArrayMatrix(4, 4).isSquareMatrix()),
                () -> assertFalse(Matrix.oneArrayMatrix(4, 5).isSquareMatrix()),
                () -> assertFalse(_d3.isSquareMatrix())
        );
    }

    @Test
    void testTrace() {
        assertAll(
                () -> assertThrows(InvalidMatrixOperationException.class,
                        () -> Matrix.oneArrayMatrix(5, 10).getTrace()),
                () -> assertThrows(InvalidMatrixOperationException.class,
                        () -> new ArrayMatrix(new Number[][]{{1, 2, 3}, {4, 5, 6}}).getTrace()),
                () -> assertEquals(5, Matrix.identityArrayMatrix(5).getTrace()),
                () -> assertEquals(5, Matrix.oneArrayMatrix(5, 5).getTrace()),
                () -> assertEquals(0, Matrix.shiftArrayMatrix(3, Matrix.ShiftMatrixType.LOWER).getTrace()),
                () -> assertEquals(0, Matrix.exchangeArrayMatrix(4).getTrace()),
                () -> assertEquals(1, Matrix.exchangeArrayMatrix(7).getTrace()),
                () -> assertEquals(12, Matrix.pascalArrayMatrix(12, Matrix.PascalMatrixType.UPPER).getTrace()),
                () -> assertEquals(8, Matrix.pascalArrayMatrix(8, Matrix.PascalMatrixType.LOWER).getTrace()),
                () -> assertEquals(5D, new ArrayMatrix(new Number[][]{
                        {3, 2, 0, 4},
                        {4, 1, -2, 3},
                        {-3, -2, -4, 7},
                        {3, 1, 1, 5}
                }).getTrace()),
                () -> assertEquals(30D, new ArrayMatrix(new Number[][]{
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
                                                                                  new ArrayMatrix(new Number[][]{{1, 2, 3}, {1, 2, 3}}).getDeterminant()),
                () -> assertEquals(1D, new ArrayMatrix(new Number[][]{{1}}).getDeterminant()),
                () -> assertEquals(-4D, new ArrayMatrix(new Number[][]{{3, 2}, {5, 2}}).getDeterminant()),
                () -> assertEquals(16D, new ArrayMatrix(new Number[][]{
                        {1, 2, 1, 0},
                        {0, 3, 1, 1},
                        {-1, 0, 3, 1},
                        {3, 1, 2, 0}
                }).getDeterminant()),
                () -> assertEquals(256, new ArrayMatrix(new Number[][]{
                        {3, 7, 0},
                        {8, 0, -2},
                        {0, -4, -5}
                }).getDeterminant()),
                () -> assertEquals(-49964D, new ArrayMatrix(new Number[][]{
                        {6, 3, -6, -4, 9},
                        {-2, -5, 9, 2, 10},
                        {2, 3, 4, -5, 6},
                        {8, 6, 1, 4, 7},
                        {12, 8, -20, 17, 4}
                }).getDeterminant())
        );
    }

    @Test
    void testZeroMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.zeroArrayMatrix(0, 0)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.zeroArrayMatrix(0, 1)),
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.zeroArrayMatrix(1, 0)),
                () -> System.out.println(Matrix.zeroArrayMatrix(1, 1))
        );
    }

    @Test
    void testOneMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.oneArrayMatrix(0, 0)),
                () -> System.out.println(Matrix.oneArrayMatrix(5, 5))
        );
    }

    @Test
    void testPascalMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class,
                        () -> Matrix.pascalArrayMatrix(0, Matrix.PascalMatrixType.SYMMETRIC)),
                () -> System.out.println(Matrix.pascalArrayMatrix(5, Matrix.PascalMatrixType.UPPER)),
                () -> System.out.println(Matrix.pascalArrayMatrix(5, Matrix.PascalMatrixType.LOWER)),
                () -> System.out.println(Matrix.pascalArrayMatrix(5, Matrix.PascalMatrixType.SYMMETRIC))
        );
    }

    @Test
    void testLehmerMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.lehmerArrayMatrix(0)),
                () -> System.out.println(Matrix.lehmerArrayMatrix(1)),
                () -> System.out.println(Matrix.lehmerArrayMatrix(4))
        );
    }

    @Test
    void testHilbertMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.hilbertArrayMatrix(0)),
                () -> System.out.println(Matrix.hilbertArrayMatrix(1)),
                () -> System.out.println(Matrix.hilbertArrayMatrix(4))
        );
    }


    @Test
    void testIdentityMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.identityArrayMatrix(0)),
                () -> System.out.println(Matrix.identityArrayMatrix(1)),
                () -> System.out.println(Matrix.identityArrayMatrix(4))
        );
    }

    @Test
    void testExchangeMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.exchangeArrayMatrix(0)),
                () -> System.out.println(Matrix.exchangeArrayMatrix(1)),
                () -> System.out.println(Matrix.exchangeArrayMatrix(4))
        );
    }

    @Test
    void testRedhefferMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class, () -> Matrix.redhefferArrayMatrix(0)),
                () -> System.out.println(Matrix.redhefferArrayMatrix(1)),
                () -> System.out.println(Matrix.redhefferArrayMatrix(4)),
                () -> System.out.println(Matrix.redhefferArrayMatrix(12))
        );
    }

    @Test
    void testShiftMatrix() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionException.class,
                        () -> Matrix.shiftArrayMatrix(0, Matrix.ShiftMatrixType.UPPER)),
                () -> System.out.println(Matrix.shiftArrayMatrix(1, Matrix.ShiftMatrixType.UPPER)),
                () -> System.out.println(Matrix.shiftArrayMatrix(5, Matrix.ShiftMatrixType.UPPER)),
                () -> assertThrows(InvalidMatrixDimensionException.class,
                        () -> Matrix.shiftArrayMatrix(0, Matrix.ShiftMatrixType.LOWER)),
                () -> System.out.println(Matrix.shiftArrayMatrix(1, Matrix.ShiftMatrixType.LOWER)),
                () -> System.out.println(Matrix.shiftArrayMatrix(5, Matrix.ShiftMatrixType.LOWER))


        );
    }

    @Test
    void testRank() {
        assertAll(
                () -> assertEquals(5, Matrix.identityArrayMatrix(5).getRank()),
                () -> assertEquals(1, Matrix.oneArrayMatrix(5, 10).getRank()),
                () -> assertEquals(4, Matrix.shiftArrayMatrix(5, Matrix.ShiftMatrixType.LOWER).getRank()),
                () -> assertEquals(2, new ArrayMatrix(new Number[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}).getRank()),
                () -> assertEquals(1, new ArrayMatrix(new Number[][]{{1, 2, 3}}).getRank()),
                () -> assertEquals(1, new ArrayMatrix(new Number[][]{{1}, {2}, {3}}).getRank()),
                () -> assertEquals(1, new ArrayMatrix(new Number[][]{{0, 0}, {0, 0}}).getRank()),
                () -> assertEquals(3, new ArrayMatrix(new Number[][]{{12, 29, 72}, {2, 7, 8}, {6, 5, 0}}).getRank()),
                () -> assertEquals(3, new ArrayMatrix(new Number[][]{{2, 7, 8}, {0, 6, 5}, {5, 7, 0}, {5, 2, 1}}).getRank())
        );
    }


    @Test
    void testForLoop() {

        double a = new ArrayMatrix(new Number[][]{
                {1, 1, 1},
                {4, 3, -1},
                {3, 5, 3}
        }).getDeterminant();

        double l = new ArrayMatrix(new Number[][]{
                {1, 0, 0},
                {4, 1, 0},
                {3, -2, 1}
        }).getDeterminant();

        double u = new ArrayMatrix(new Number[][]{
                {1, 1, 1},
                {0, -1, -5},
                {0, 0, -10}
        }).getDeterminant();

        System.out.println(a);
        System.out.println(l);
        System.out.println(u);

    }

    @Test
    void testAddition() {

        Matrix m = new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}});

        assertAll(
                () -> assertEquals(m.add(10), new ArrayMatrix(new Number[][]{{11, 2}, {3, 14}})),
                () -> assertEquals(m.add(new ArrayMatrix(new Number[][]{{5, 6}, {7, 8}})),
                        new ArrayMatrix(new Number[][]{{6, 8}, {10, 12}})),
                () -> assertEquals(m.subtract(new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}})),
                        new ArrayMatrix(new Number[][]{{0, 0}, {0, 0}}))
        );
    }


    @Test
    void testMultiplication() {
        Matrix a = new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}});
        Matrix b = new ArrayMatrix(new Number[][]{{1, 2},{5, 9}});
        Matrix c = new ArrayMatrix(new Number[][]{{1, 8},{3, 6}});
        Matrix d = new ArrayMatrix(new Number[][]{{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12}});
        Matrix e = new ArrayMatrix(new Number[][]{{1, 2},{3, 4},{5, 6},{7, 8}});
        Matrix f = new ArrayMatrix(new Number[][]{{1, 2, 3},{4, 5, 6},{7, 8, 9}});
        Vector v = ArrayVector.of(2, 1, 3);

        assertAll(
                () -> assertThrows(InvalidMatrixOperationException.class, () -> c.multiply(d)),
                () -> assertEquals(new ArrayMatrix(new Number[][]{{2, 4},{6, 8}}), a.multiply(2)),
                () -> assertEquals(new ArrayMatrix(new Number[][]{{7, 20},{32, 94}}), b.multiply(c)),
                () -> assertEquals(new ArrayMatrix(new Number[][]{{50, 60}, {114, 140}, {178, 220}}), d.multiply(e)),
                () -> assertEquals(ArrayVector.of(13, 31, 49), f.multiply(v))
        );
    }
}