package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;
import org.katpara.mathematica.exceptions.InvalidVectorDimensionException;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * The class provides a way to manipulate 2 or more vectors.
 * All the methods in the class are immutable, at no point
 * any of the method will modify the given vector, it will
 * simply return a new vector at the end of the calculation.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class VectorOperations {
    @java.io.Serial
    private static final long serialVersionUID = 4042529171225355742L;

    /**
     * The method will return an inverse vector if a given vector.
     *
     * @param v the vector
     *
     * @return the inverse vector of a given vector.
     */
    public static Vector getInverseVector(final Vector v) {
        Number[] n = Arrays.stream(v.getElements())
                .map(el -> -el.doubleValue())
                .toArray(Number[]::new);

        return new ArrayVector(n);
    }

    /**
     * The method will scale the vector by the given value.
     * Please not, this operation is mutable.
     *
     * @param v the vector to be scaled
     * @param scalar the scalar you want to scale the vector with.
     *               if:
     *                <ul>
     *                <li>scalar &gt; 1          -&gt; The scaled vector will be scaled up in the same direction.
     *                <li>0 &lt; scalar &lt; 1   -&gt; The scaled vector is shrunk in the same direction.
     *                <li>scalar = 0             -&gt; The scaled vector becomes a zero vector.
     *                <li>-1 &lt; scalar &lt; 0  -&gt; The scaled vector is shrunk but in the opposite direction.
     *                <li>scalar &lt; -1         -&gt; The scaled vector is scaled up but in the opposite direction.
     *               </ul>
     *
     * @return the self vector but scaled by the given number.
     */
    public static Vector scale(final Vector v, final Number scalar) {
        Number[] n = Arrays.stream(v.getElements())
                .map(e -> e.doubleValue() * scalar.doubleValue())
                .toArray(Number[]::new);

        return new ArrayVector(n);
    }

    /**
     * The method will add two vectors.
     *
     * @param v1 the base vector
     * @param v2 the vector to be added
     *
     * @return the resulting vector
     */
    public static Vector addVectors(final Vector v1, final Vector v2) {
        return addSubTwoVectors(v1, v2, true);
    }

    /**
     * The method will subtract a vector from a vector.
     *
     * @param v1 the base vector
     * @param v2 the vector to be subtracted
     *
     * @return the resulting vector
     */
    public static Vector subtractVectors(final Vector v1, final Vector v2) {
        return addSubTwoVectors(v1, v2, false);
    }

    /**
     * The method will add a list of vectors together and will
     * return a resulting vector.
     *
     * @param vl the list of vectors
     *
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
     *
     * @return the resulting vector after subtracting all from the base vector
     */
    public static Vector subtractVectors(final List<? extends Vector> vl) {
        return addSubVectors(vl, false);
    }


    /**
     * The method will transpose vector to another dimension.
     * If the given dimension is less then 2 or the same as
     * the vector's dimension then the method will throw
     * {@link InvalidVectorDimensionException} exception.
     *
     * @param v the vector
     * @param d dimension to be transposed
     *
     * @return the transposed vector
     *
     * @throws InvalidVectorDimensionException when the given dimension is less
     *                                than 2 or the same as the given
     *                                vector dimension
     */
    public static Vector transpose(final Vector v, final int d) {
        if (d < 2 || v.getDimension() == d)
            throw new InvalidVectorDimensionException();

        Number[] n = new Number[d], e = v.getElements();
        if (d < e.length)
            System.arraycopy(e, 0, n, 0, d);
        else {
            System.arraycopy(e, 0, n, 0, e.length);
            IntStream.range(e.length, d).forEach(i -> n[i] = 0);
        }

        return new ArrayVector(n);
    }

    /**
     * The method will return a dot product of two vectors.
     * If both vectors are on different dimensions then
     * {@link InvalidVectorDimensionException} exception is thrown.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     *
     * @return the resulting dot product
     *
     * @throws InvalidVectorDimensionException when both products are on different
     *                                dimensions.
     */
    public static double dotProduct(final Vector v1, final Vector v2) {
        Number[] e1, e2;
        if ((e1 = v1.getElements()).length != (e2 = v2.getElements()).length)
            throw new InvalidVectorDimensionException();

        return IntStream.range(0, e1.length)
                .mapToDouble(i -> e1[i].doubleValue() * e2[i].doubleValue())
                .reduce(0, Double::sum);
    }

    /**
     * The method returns the cross product of two vectors.
     * A cross product of two vectors if a new vector, this
     * new vector is perpendicular to both vectors.
     * <p>
     * Please note: if two vectors M and N is given then;
     * M x N != N x M
     * When you switch an order, the resulting vector
     * would be in the opposite direction.
     * <p>
     * If given vectors are not in 3 dimensions then,
     * {@link InvalidVectorDimensionException} is thrown.
     *
     * @param v1 the first 3 dimensional vector
     * @param v2 the second 3 dimensional vector
     *
     * @return the cross product vector
     *
     * @throws InvalidVectorDimensionException when both vectors are in
     *                                the third dimension.
     */
    public static Vector crossProduct(final Vector v1, final Vector v2) {
        Number[] e1, e2, n = new Number[3];
        if ((e1 = v1.getElements()).length != 3 || (e2 = v2.getElements()).length != 3)
            throw new InvalidVectorDimensionException("The cross product is only supported for vectors in 3rd dimension");

        n[0] = e1[1].doubleValue() * e2[2].doubleValue() - e1[2].doubleValue() * e2[1].doubleValue();
        n[1] = e1[2].doubleValue() * e2[0].doubleValue() - e1[0].doubleValue() * e2[2].doubleValue();
        n[2] = e1[0].doubleValue() * e2[1].doubleValue() - e1[1].doubleValue() * e2[0].doubleValue();

        return new ArrayVector(n);
    }

    /**
     * The method returns an angle between two vectors.
     * The method is capable of returning a result either in
     * degrees of angle or in radian.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @param d  boolean for either in degrees or radian
     *
     * @return the angle
     */
    public static double angle(final Vector v1, final Vector v2, final boolean d) {
        double radian = Math.acos(dotProduct(v1, v2) / (v1.getMagnitude() * v2.getMagnitude()));
        return d ? Math.toDegrees(radian) : radian;
    }

    private static Vector addSubVectors(final List<? extends Vector> vl, final boolean a) {
        if (vl.size() < 2)
            throw new InvalidParameterProvidedException("The list must have at least 2 vectors");

        final Vector[] fv = {vl.get(0)};
        vl.stream().skip(1).forEach(v -> fv[0] = addSubTwoVectors(fv[0], v, a));
        return fv[0];
    }

    private static Vector addSubTwoVectors(final Vector v1, final Vector v2, final boolean a) {
        Number[] e1 = v1.getElements(), e2 = v2.getElements(), n;
        if (e1.length != e2.length)
            throw new InvalidVectorDimensionException("Both vectors have different dimensions");

        n = new Number[e1.length];
        final AtomicInteger ai = new AtomicInteger(0);
        IntStream.range(0, e1.length)
                .mapToDouble(i -> a ?
                        e1[i].doubleValue() + e2[i].doubleValue() :
                        e1[i].doubleValue() - e2[i].doubleValue())
                .forEach(d -> n[ai.getAndIncrement()] = d);

        return new ArrayVector(n);
    }
}
