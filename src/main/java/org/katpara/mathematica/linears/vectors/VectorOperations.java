package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.exceptions.VectorInvalidDimension;

public final class VectorOperations {
    @java.io.Serial
    private static final long serialVersionUID = 4042529171225355742L;

    public static Vector getInverseVector(final Vector v) {
        Number[] e = v.getElements(), n = new Number[e.length];
        for (int i = 0; i < e.length; i++)
            n[i] = -e[i].doubleValue();

        return new ArrayVector(n);
    }

    public static Vector addVector(final Vector v1, final Vector v2) {
        return addSubVector(v1, v2, true);
    }

    public static Vector subtractVector(final Vector v1, final Vector v2) {
        return addSubVector(v1, v2, false);
    }

    private static Vector addSubVector(final Vector v1, final Vector v2, boolean a) {
        Number[] e1 = v1.getElements(), e2 = v2.getElements(), n;
        if (e1.length != e2.length)
            throw new VectorInvalidDimension("Both vectors have different dimensions");

        n = new Number[e1.length];
        for (int i = 0; i < e1.length; i++)
            n[i] = a ? e1[i].doubleValue() + e2[i].doubleValue() :
                    e1[i].doubleValue() - e2[i].doubleValue();

        return new ArrayVector(n);
    }
}
