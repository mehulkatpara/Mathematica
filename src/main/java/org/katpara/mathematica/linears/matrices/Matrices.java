package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public final class Matrices {
    @java.io.Serial
    private static final long serialVersionUID = 7021020217611144484L;

    public static ArrayMatrix getSubMatrix(final Matrix e, final int[] m, final int[] n) {
        var _e = e.getElements();

        if (m.length > _e.length || n.length > _e[0].length)
            throw new InvalidParameterProvidedException("the number of rows and columns are greater than matrix");

        for (final int value : m)
            if (value > _e.length)
                throw new InvalidParameterProvidedException("invalid rows provided in the parameter");

        for (final int value : n)
            if (value > _e[0].length)
                throw new InvalidParameterProvidedException("invalid columns provided in the parameter");

        Arrays.sort(m);
        Arrays.sort(n);

        var _n = new Number[m.length][n.length];
        IntStream.range(0, m.length).forEach(i -> IntStream.range(0, n.length).forEach(j -> _n[i][j] = _e[m[i]][n[j]]));

        return new ArrayMatrix(_n);
    }

    public static Matrix getInverse(final Matrix m) {
        return null;
    }

    public static Matrix scale(final double scalar) {
        return null;
    }
}
