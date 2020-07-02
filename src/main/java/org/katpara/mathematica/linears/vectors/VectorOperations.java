package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.exceptions.InvalidParameterProvided;
import org.katpara.mathematica.exceptions.InvalidVectorDimension;

import java.util.List;

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

    public static Vector addVectors(final List<? extends Vector> vl) {
        return addSubVectors(vl, true);
    }

    public static Vector subtractVectors(final List<? extends Vector> vl) {
        return addSubVectors(vl, false);
    }


    public static Vector transposeDimensions(final Vector v, final int d) {
        if (d < 2 || v.getDimension() == d)
            throw new InvalidVectorDimension();

        Number[] n = new Number[d], e = v.getElements();
        if(d < v.getDimension())
            System.arraycopy(e, 0, n, 0, d);
        else {
            System.arraycopy(e, 0, n, 0, e.length);
            for (int i = e.length; i < d; i++)
                n[i] = 0;
        }

        return new ArrayVector(n);
    }

    private static Vector addSubVectors(final List<? extends Vector> vl, final boolean a) {
        if (vl.size() < 2)
            throw new InvalidParameterProvided("The list must have at least 2 vectors");

        Vector fv = vl.get(0);
        for (int i = 1; i < vl.size(); i++)
            fv = addSubVector(fv, vl.get(i), a);

        return fv;
    }

    private static Vector addSubVector(final Vector v1, final Vector v2, final boolean a) {
        Number[] e1 = v1.getElements(), e2 = v2.getElements(), n;
        if (e1.length != e2.length)
            throw new InvalidVectorDimension("Both vectors have different dimensions");

        n = new Number[e1.length];
        for (int i = 0; i < e1.length; i++)
            n[i] = a ? e1[i].doubleValue() + e2[i].doubleValue() :
                    e1[i].doubleValue() - e2[i].doubleValue();

        return new ArrayVector(n);
    }
}
