package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.exceptions.linears.InvalidVectorDimensionException;

import java.io.Serializable;
import java.util.List;

/**
 * The Vector interface. The known implementation is ArrayVector.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public interface Vector extends Cloneable, Serializable {

    /**
     * When you work with angles there are two options to get an angle
     * either in degrees or in radian.
     */
    enum Angle {DEGREE, RADIAN}

    /**
     * The property is used to calculate the projection of vectors.
     */
    enum Projection {SCALAR, VECTOR}

    /**
     * A dimension of a vector is determined based on the number of elements
     * it holds. Such as, a two-dimensional vector could be represented as,
     * (v1, v12); the same as an n-dimensional vector can be represented
     * as (v1, v2,...., vn), where n belongs to the set of natural numbers "N".
     * <p>
     * Many vector operations are dimension dependant, such as addition,
     * subtraction, and multiplication. Which requires two vectors must be
     * in the same dimension.
     *
     * @return the dimension of the vector
     */
    int getDimension();

    /**
     * The magnitude of a vector, also known as "norm", is square root of
     * the sum all the vector elements powered by 2. A magnitude of a vector
     * is represented by the length of a vector and written as |V| for vector V.
     * <p>
     * For n-dimensional vector, the magnitude is defined as;
     * |v| = sqrt(v1^2 + v2^2 + ... + vn^2).
     *
     * @return the magnitude of the vector
     */
    double getMagnitude();

    /**
     * The method calculates the cosines with respect to their dimensional axioms.
     * The number of elements in the returned array will be equal to the number of
     * dimensions.
     *
     * @param angle The angle, see {@link Angle}
     *
     * @return an array of cosines with respect to axiom.
     */
    double[] getCosines(Angle angle);

    /**
     * The method returns the elements of a vector as an array.
     *
     * @return the array of {@link java.lang.Number}
     */
    Number[] toArray();

    /**
     * The method returns the elements of a vector as a list of {@link java.lang.Number}.
     *
     * @return the list of {@link java.lang.Number}
     */
    List<Number> toList();

    /**
     * If the given vector is orthogonal to the current vector, then it returns true;
     * otherwise it would be false.
     * <p>
     * Orthogonality is known as a vector that is perpendicular to the given matrix,
     * i.e. if the vector makes the 90 degrees angle with the current matrix.
     *
     * @param vector the vector to check orthogonality
     *
     * @return true if it's orthogonal
     */
    boolean isOrthogonal(Vector vector);

    /**
     * If the given vector is parallel to the current vector then it returns the factor;
     * otherwise it will be -1.
     * <p>
     * The parallel vectors have the same direction but different magnitudes, which
     * means the given vector is scaled up or down in the same or opposite direction
     * of the current vector.
     * <p>
     * If a vector V1 = (v1, v2, ... , vn), and a vector W = (w1, w2, ..., wn);
     * where n belongs to the set of Integers. Then the parallel vectors can be
     * written as;
     * W = a(V); where a is a constant (here "the factor").
     * i.e (w1, w2, ..., wn) = (av1, av2, ..., avn)
     *
     * @param vector the vector to check parallelism
     *
     * @return if it's parallel then returns the factor, otherwise -1
     */
    double isParallel(Vector vector);

    /**
     * The method returns the angle between two vectors.
     * The second parameter can be true/false, depending on if you want the angle
     * in degrees (true) or in radian (false).
     *
     * @param vector the another vector to calculate
     * @param angle  the angle either in degree or radian, See, {@link Angle}
     *
     * @return the angle in degrees or radian.
     */
    double angle(final Vector vector, final Angle angle);

    /**
     * The method will return the inverse vector.
     * The inverse vector satisfy the following equation:
     * V + inverse(V) = 0 (Zero Vector).
     *
     * @return the inverse vector
     */
    Vector inverse();

    /**
     * The method will scale the vector by the given value.
     * Please not, this operation is mutable.
     *
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
    Vector scale(final double scalar);

    /**
     * The method will transpose vector to another dimension. If the given dimension
     * is less then 2 or the same as the vector's dimension then the method will
     * throw {@link InvalidVectorDimensionException} exception.
     * <p>
     * If I have a two-dimensional vector V = (x, y) and if I transpose the dimension
     * to the 5th dimension then the resulting vector will be, (x, y, 0, 0, 0); the
     * same as if I have a 4-dimensional vector v = (x, y, z, t) and if I try to
     * transpose the vector to the 2nd dimension then the resulting vector will be
     * (x, y).
     *
     * @param dimension the dimension to be transposed to
     *
     * @return the transposed vector
     *
     * @throws InvalidVectorDimensionException when the given dimension is less
     *                                         than 2 or the same as the given
     *                                         vector dimension
     */
    Vector transpose(final int dimension);

    /**
     * The method will add a vector to the current vector.
     * In order to add another vector, both vectors must in the same dimension.
     *
     * @param vector the vector to be added
     *
     * @return the resulting vector
     *
     * @throws InvalidVectorDimensionException if the vectors have different dimensions
     */
    Vector add(final Vector vector);

    /**
     * The method will add a list of vectors to the current vector.
     * In order to vectors from the list, all vectors must in the same dimension.
     *
     * @param vectors the list of vector
     *
     * @return the resulting vector
     *
     * @throws InvalidVectorDimensionException if the vectors have different dimensions
     */
    Vector add(final List<Vector> vectors);

    /**
     * The method will subtract a vector from the current vector.
     * In order to subtract another vector, both vectors must in the same dimension.
     *
     * @param vector the vector to be subtracted
     *
     * @return the resulting vector
     */
    Vector subtract(final Vector vector);

    /**
     * The method will return a dot product of two vectors.
     * If both vectors are on different dimensions then
     * {@link InvalidVectorDimensionException} exception is thrown.
     *
     * @param vector the second vector
     *
     * @return the resulting dot product
     *
     * @throws InvalidVectorDimensionException when both products are on different
     *                                         dimensions.
     */
    double dot(final Vector vector);

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
     * @param vector the second 3 dimensional vector
     *
     * @return the cross product vector
     *
     * @throws InvalidVectorDimensionException when both vectors are in
     *                                         the third dimension.
     */
    Vector cross(final Vector vector);

    /**
     * The method calculates the scalar project of a given vector onto
     * the current vector.
     * <p>
     * Let this vector be V and the given vector be W.
     * The scalar projection is defined as,
     * dot(V, W) / magnitude(V)
     *
     * @param vector the projecting vector
     *
     * @return the projected scalar
     */
    double scalarProjection(final Vector vector);

    /**
     * The method calculates the vector project of a given vector onto
     * the current vector. This will produce another vector.
     * <p>
     * Let this vector be V and the given vector be W.
     * The vector projection is defined as,
     * [dot(V, W) / magnitude(V)] x V
     *
     * @param vector the projecting vector
     *
     * @return the projected scalar
     */
    Vector vectorProjection(final Vector vector);

    /**
     * The method will return a rejection vector from the the given vector.
     * The projecting vector can be calculated as;
     * R = V - ScalarProjection(W)
     *
     * @param vector the projecting vector
     *
     * @return the rejection vector
     */
    Vector vectorRejection(final Vector vector);
}