package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.exceptions.InvalidParameterProvided;
import org.katpara.mathematica.exceptions.InvalidVectorDimension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * The class provides a way to manipulate 2 or more vectors.
 * All the methods in the class are immutable, at no point
 * any of the method will modify the given vector, it will
 * simply return a new vector at the end of the calculation.
 *
 * @author Mehul Katpara <mkatpara19@gmail.com>
 * @since 1.0.0
 */
public final class VectorOperations {
    @java.io.Serial
    private static final long serialVersionUID = 4042529171225355742L;

    /**
     * The method will return an inverse vector if a given vector.
     *
     * @param v the vector
     * @return the inverse vector of a given vector.
     */
    public static Vector getInverseVector(final Vector v) {
        Number[] n = Arrays.stream(v.getElements())
                .map(el -> -el.doubleValue())
                .toArray(Number[]::new);

        return new ArrayVector(n);
    }

    /**
     * The method will add two vectors.
     *
     * @param v1 the base vector
     * @param v2 the vector to be added
     * @return the resulting vector
     */
    public static Vector addVector(final Vector v1, final Vector v2) {
        return addSubVector(v1, v2, true);
    }

    /**
     * The method will subtract a vector from a vector.
     *
     * @param v1 the base vector
     * @param v2 the vector to be subtracted
     * @return the resulting vector
     */
    public static Vector subtractVector(final Vector v1, final Vector v2) {
        return addSubVector(v1, v2, false);
    }

    /**
     * The method will add a list of vectors together and will
     * return a resulting vector.
     *
     * @param vl the list of vectors
     * @return the resulting vector after adding all
     */
    public static Vector addVectors(final List<? extends Vector> vl) {
        return addSubVectors(vl, true);
    }

    /**
     * The method will subtract a list of vectors from the first vector,
     * and return a resulting vector.
     *
     * @param vl the list of vectors
     * @return the resulting vector after subtracting all from the base vector
     */
    public static Vector subtractVectors(final List<? extends Vector> vl) {
        return addSubVectors(vl, false);
    }


    /**
     * The method will transpose vector to another dimension.
     * If the given dimension is less then 2 or the same as
     * the vector's dimension then the method will throw
     * {@link InvalidVectorDimension} exception.
     *
     * @param v the vector
     * @param d dimension to be transposed
     * @return the transposed vector
     * @throws InvalidVectorDimension when the given dimension is less
     *                                than 2 or the same as the given
     *                                vector dimension
     */
    public static Vector transposeDimensions(final Vector v, final int d) {
        if (d < 2 || v.getDimension() == d)
            throw new InvalidVectorDimension();

        Number[] n = new Number[d], e = v.getElements();
        if (d < v.getDimension())
            System.arraycopy(e, 0, n, 0, d);
        else {
            System.arraycopy(e, 0, n, 0, e.length);
            for (int i = e.length; i < d; i++)
                n[i] = 0;
        }

        return new ArrayVector(n);
    }

    /**
     * The method will return a dot product of two vectors.
     * If both vectors are on different dimensions then
     * {@link InvalidVectorDimension} exception is thrown.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the resulting dot product
     * @throws InvalidVectorDimension when both products are on different
     *                                dimensions.
     */
    public static double dotProduct(final Vector v1, final Vector v2) {
        if (v1.getDimension() != v2.getDimension())
            throw new InvalidVectorDimension();

        Number[] e1 = v1.getElements(),
                e2 = v2.getElements(),
                n = new Number[v1.getDimension()];

        for (int i = 0; i < n.length; i++)
            n[i] = e1[i].doubleValue() * e2[i].doubleValue();

        return Arrays.stream(n)
                .reduce(0, (s, e) -> s.doubleValue() + e.doubleValue())
                .doubleValue();
    }

    /**
     * The method returns an angle between two vectors.
     * The method is capable of returning a result either in
     * degrees of angle or in radian.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @param d  boolean for either in degrees or radian
     * @return the angle
     */
    public static double angle(final Vector v1, final Vector v2, final boolean d) {
        double radian = Math.acos(dotProduct(v1, v2) / (v1.getMagnitude() * v2.getMagnitude()));
        return d ? Math.toDegrees(radian) : radian;
    }

    private static Vector addSubVectors(final List<? extends Vector> vl, final boolean a) {
        if (vl.size() < 2)
            throw new InvalidParameterProvided("The list must have at least 2 vectors");

        final Vector[] fv = {vl.get(0)};
        vl.stream().skip(1).forEach(v -> fv[0] = addSubVector(fv[0], v, a));

        return fv[0];
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
