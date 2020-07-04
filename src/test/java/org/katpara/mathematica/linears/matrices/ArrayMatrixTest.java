package org.katpara.mathematica.linears.matrices;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.katpara.mathematica.exceptions.InvalidMatrixDimension;
import org.katpara.mathematica.exceptions.NullArgumentProvided;
import org.katpara.mathematica.linears.vectors.ArrayVector;
import org.katpara.mathematica.linears.vectors.Vector;

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
        Number[][] t3 = new Number[1][1];
        Number[][] t4 = new Number[2][0];
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

    @RepeatedTest(100)
    void testCollectionConstructor() {

        List<List<Number>> l1 = List.of(Collections.emptyList());
        List<List<Number>> l2 = List.of(Collections.emptyList(), Collections.emptyList());
        List<List<Number>> l3 = List.of(List.of(1), Collections.emptyList());
        List<List<Number>> l4 = List.of(List.of(1), List.of(1, 2));
        List<List<Number>> l5 = List.of(List.of(1), List.of(1));

        Set<List<Number>> s1 = Set.of(Collections.emptyList());
        Set<List<Number>> s2 = Set.of(List.of(1), Collections.emptyList());
        Set<List<Number>> s3 = Set.of(List.of(1), List.of(1, 2));
        Set<List<Number>> s4 = Set.of(List.of(1), List.of(2));

        assertAll(
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(l1)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(l2)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(l3)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(l4)),
                () -> new ArrayMatrix(l5),

                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(s1)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(s2)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(s3)),
                () -> new ArrayMatrix(s4)
        );
    }

    @RepeatedTest(100)
    void testVectorConstructor() {
        List<Vector> vl1 = Collections.emptyList();
        List<Vector> vl2 = List.of(new ArrayVector(1, 2), new ArrayVector(1, 2, 3));
        List<Vector> vl3 = List.of(new ArrayVector(1, 2));
        List<Vector> vl4 = List.of(new ArrayVector(1, 2, 3), new ArrayVector(4, 5, 6));

        Set<Vector> vs1 = Collections.emptySet();
        Set<Vector> vs2 = Set.of(new ArrayVector(1, 2), new ArrayVector(1, 2, 3));
        Set<Vector> vs3 = Set.of(new ArrayVector(1, 2));
        Set<Vector> vs4 = Set.of(new ArrayVector(1, 2, 3), new ArrayVector(4, 5, 6));

        Map<Integer, Vector> vm1 = Collections.emptyMap();
        Map<Integer, Vector> vm2 = Map.of(1, new ArrayVector(1, 2), 2, new ArrayVector(1, 2, 3));
        Map<Integer, Vector> vm3 = Map.of(1, new ArrayVector(1, 2));
        Map<Integer, Vector> vm4 = Map.of(1, new ArrayVector(1, 2), 2, new ArrayVector(1, 2));

        assertAll(
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(vl1)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(vl2)),
                () -> new ArrayMatrix(vl3),
                () -> new ArrayMatrix(vl4),

                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(vs1)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(vs2)),
                () -> new ArrayMatrix(vs3),
                () -> new ArrayMatrix(vs4),

                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(vm1)),
                () -> assertThrows(InvalidMatrixDimension.class, () -> new ArrayMatrix(vm2)),
                () -> new ArrayMatrix(vm3),
                () -> new ArrayMatrix(vm4)
        );

    }

    @Test
    void testDimension() {
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2}}).getDimension();
        var _d2 = new ArrayMatrix(new Number[][]{{1}, {1}}).getDimension();
        var _d3 = new ArrayMatrix(List.of(
                new ArrayVector(1, 2, 3),
                new ArrayVector(4, 5, 6))
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
    void testIsRowVector() {
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2}});
        var _d2 = new ArrayMatrix(List.of(new ArrayVector(1, 2, 3)));
        var _d3 = new ArrayMatrix(List.of(
                new ArrayVector(1, 2, 3),
                new ArrayVector(4, 5, 6)));

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
                new ArrayVector(1, 2, 3),
                new ArrayVector(4, 5, 6)));

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
                new ArrayVector(1, 2, 3),
                new ArrayVector(4, 5, 6),
                new ArrayVector(7, 8, 9)));
        var _d3 = new ArrayMatrix(List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6)));

        assertAll(
                () -> assertTrue(_d1.isSquareMatrix()),
                () -> assertTrue(_d2.isSquareMatrix()),
                () -> assertFalse(_d3.isSquareMatrix())
        );
    }


    @RepeatedTest(100)
    void testElements() {
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}});
        var _d2 = new ArrayMatrix(List.of(
                new ArrayVector(1, 2, 3),
                new ArrayVector(4, 5, 6),
                new ArrayVector(7, 8, 9)));

        var e_d1 = _d1.getElements();
        var e_d2 = _d2.getElements();
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
    void testMatrixEquals() {
        var _d1 = new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}});
        var _d2 = new ArrayMatrix(new Number[][]{{1, 2}, {3, 4}});
        var _d3 = new ArrayMatrix(new Number[][]{{11, 20}, {23, 34}});
        var _d4 = new ArrayMatrix(new Number[][]{{1, 2, 3}, {4, 5, 6}});
        var _d5 = new ArrayMatrix(List.of(
                new ArrayVector(1, 2),
                new ArrayVector(3, 4)));

        assertAll(
                () -> assertEquals(_d1, _d2),
                () -> assertNotEquals(_d1, _d3),
                () -> assertNotEquals(_d1, _d4),
                () -> assertEquals(_d1, _d5)
        );
    }
}