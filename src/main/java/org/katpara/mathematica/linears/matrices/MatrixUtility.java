package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;

import java.util.stream.IntStream;

public class MatrixUtility {
    /**
     * The method will return a zero or null matrix, whose all the elements are zero.
     *
     * @param m the number of rows
     * @param n the number of columns
     *
     * @return an {@link ArrayMatrix} with all 0 entries
     *
     * @throws InvalidParameterProvidedException when # of row + # of column < 1;
     *                                           this ensures that at least one element
     *                                           exist all the time.
     */
    static ArrayMatrix getZeroMatrix(final int m, final int n) {
        if (m + n < 2)
            throw new InvalidParameterProvidedException("Zero matrix should have at least one element");

        var e = new Number[m][n];
        IntStream.range(0, m).forEach(i -> IntStream.range(0, n).forEach(j -> e[i][j] = 0));
        return new ArrayMatrix(e);
    }
}
