package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.commons.Rounding;
import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.InvalidMatrixOperationException;
import org.katpara.mathematica.exceptions.linears.InvalidVectorDimensionException;
import org.katpara.mathematica.exceptions.linears.InvalidVectorOperationException;
import org.katpara.mathematica.linears.matrices.Matrix;

import java.util.*;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;

import static org.katpara.mathematica.linears.vectors.Vector.Angle.DEGREE;
import static org.katpara.mathematica.linears.vectors.Vector.Angle.RADIAN;

/**
 * The ArrayVector class is an implementations of the Vector interface,
 * that represent a vector in the system.
 * <p>
 * The vector must be at least 2 dimensional or more. There are constructors
 * to create 2 and 3 dimensional vectors with ease which we use most frequently
 * in mathematics, however the dimensions are not limited. You can create a
 * vector with multi dimensional by passing an array, list, set or even map instances.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class ArrayVector implements Vector {
    private static final long serialVersionUID = 6608801568539797402L;

    /**
     * The vector elements are stored as an array of numbers
     */
    private final Number[] e;

    /**
     * The dimension of the vector
     */
    private final int d;

    /**
     * Useful to cache some of the calculated properties
     */
    private final Cache c = new Cache();

    /**
     * The constructor creates a vector from the given {@link Number} array.
     * <p>
     * The Number array has to have at least 2 or more elements to create a
     * vector; otherwise the {@link InvalidVectorDimensionException} exception is thrown.
     * <p>
     * All the elements in the array must be not-null; fail to do so will
     * result in {@link NullArgumentProvidedException} exception.
     *
     * @param e the Number array
     *
     * @throws InvalidVectorDimensionException if the size of Number array is less than 2
     * @throws NullArgumentProvidedException   when the contains null values
     */
    public ArrayVector(final Number[] e) {
        if (e.length < 2)
            throw new InvalidVectorDimensionException();

        for (var n : e)
            if (n == null)
                throw new NullArgumentProvidedException();

        this.e = e;
        this.d = e.length;
    }

    /**
     * The constructor will create a vector from a {@link List}.
     * The elements of the list must be subclass of {@link Number}.
     * That means the allowed classes are:
     * {@link Short}
     * {@link Byte}
     * {@link Integer}
     * {@link Long}
     * {@link Float}
     * {@link Double}
     * <p>
     * The list must contain at least 2 Numbers or more; failed to comply
     * will result in the {@link InvalidVectorDimensionException} exception.
     *
     * @param numbers the list of elements
     *
     * @throws InvalidVectorDimensionException when the list have less than 2 elements
     */
    public ArrayVector(final Collection<? extends Number> numbers) {
        if ((e = new Number[numbers.size()]).length < 2)
            throw new InvalidVectorDimensionException();

        var i = 0;
        for (var n : numbers)
            e[i++] = n;

        this.d = e.length;
    }

    /**
     * The constructor will create a vector from a {@link Map}.
     * The keys of the map can be anything, it will be ignored here,
     * however the values of the map must be the subclass of {@link Number}.
     * That means the allowed classes are:
     * {@link Short}
     * {@link Byte}
     * {@link Integer}
     * {@link Long}
     * {@link Float}
     * {@link Double}
     * <p>
     * The set must contain at least 2 kye-value pairs in the map or more; failed
     * to comply will result in the {@link InvalidVectorDimensionException} exception.
     *
     * @param m the map of &lt;Any-key, Number&gt;
     *
     * @throws InvalidVectorDimensionException when the map has less than 2 pairs
     */
    public ArrayVector(final Map<?, ? extends Number> m) {
        this(new ArrayList<>(m.values()));
    }

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
    @Override
    public int getDimension() {
        return d;
    }

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
    @Override
    public double getMagnitude() {
        if (c.getM() == 0) {
            var s = 0;
            for (var n : e)
                s += n.doubleValue() * n.doubleValue();

            c.setM(Math.sqrt(s));
        }

        return c.getM();
    }

    /**
     * The magnitude of a vector, also known as "norm", is square root of
     * the sum all the vector elements powered by 2. A magnitude of a vector
     * is represented by the length of a vector and written as |V| for vector V.
     * <p>
     * For n-dimensional vector, the magnitude is defined as;
     * |v| = sqrt(v1^2 + v2^2 + ... + vn^2).
     *
     * @param point the value round up to the given decimal point
     *              see, {@link Rounding.POINT}
     *
     * @return the magnitude of the vector
     */
    @Override
    public double getMagnitude(final Rounding.POINT point) {
        return Rounding.round(getMagnitude(), point).doubleValue();
    }

    /**
     * The method calculates the cosines with respect to their dimensional axioms.
     * The number of elements in the returned array will be equal to the number of
     * dimensions.
     *
     * @param a The angle, see {@link Angle}
     *
     * @return an array of cosines with respect to axiom.
     */
    @Override
    public double[] getCosines(final Angle a) {
        if (a == DEGREE && c.getCd() == null) {
            c.setCd(calculateCosines(a));
        } else if (a == RADIAN && c.getCr() == null) {
            c.setCr(calculateCosines(a));
        }

        return (a == DEGREE) ? c.getCd() : c.getCr();
    }

    /**
     * The method calculates the cosines with respect to their dimensional axioms.
     * The number of elements in the returned array will be equal to the number of
     * dimensions.
     *
     * @param a the angle, see {@link Angle}
     * @param p the rounding point
     *
     * @return an array of cosines with respect to axiom.
     */
    @Override
    public double[] getCosines(final Angle a, final Rounding.POINT p) {
        var n = getCosines(a);
        for (var i = 0; i < d; i++)
            n[i++] = Rounding.round(n[i], p).doubleValue();

        return n;
    }

    /**
     * The method calculates the cosigns.
     *
     * @param a the angle specification
     *
     * @return the array with cosines
     */
    private double[] calculateCosines(final Angle a) {
        var n = new double[d];
        var m = getMagnitude();
        var i = 0;

        if (a == DEGREE)
            for (var _e : e)
                n[i++] = Math.toDegrees(_e.doubleValue() / m);
        else
            for (var _e : e)
                n[i++] = _e.doubleValue() / m;

        return n;
    }

    /**
     * The method returns the elements of a vector as an array.
     *
     * @return the array of {@link Number}
     */
    @Override
    public Number[] toArray() {
        return e;
    }

    /**
     * The method returns the elements of a vector as a list of {@link Number}.
     *
     * @return the list of {@link Number}
     */
    @Override
    public List<Number> toList() {
        return Arrays.stream(e).collect(Collectors.toList());
    }

    /**
     * If the given vector is orthogonal to the current vector, then it returns true;
     * otherwise it would be false.
     * <p>
     * Orthogonality is known as a vector that is perpendicular to the given matrix,
     * i.e. if the vector makes the 90 degrees angle with the current matrix.
     *
     * @param v the vector to check orthogonality
     *
     * @return true if it's orthogonal
     */
    @Override
    public boolean isOrthogonal(final Vector v) {
        return angle(v, DEGREE) != 0;
    }

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
     * @param v the vector to check parallelism
     *
     * @return if it's parallel then returns true
     */
    @Override
    public boolean isParallel(final Vector v) {
        if (d != v.getDimension())
            throw new InvalidVectorOperationException("Vectors have different dimensions");

        var _e = v.toArray();
        var a = _e[0].doubleValue() / e[0].doubleValue();

        for (int i = 1; i < d; i++)
            if (a != _e[i].doubleValue() / e[i].doubleValue())
                return false;

        return true;
    }

    /**
     * The method returns the angle between two vectors.
     * The second parameter can be true/false, depending on if you want the angle
     * in degrees (true) or in radian (false).
     *
     * @param v the another vector to calculate
     * @param a the angle either in degree or radian, see {@link Angle}
     *
     * @return the angle in degrees or radian.
     */
    @Override
    public double angle(final Vector v, final Angle a) {
        var r = Math.acos(dot(v) / (getMagnitude() * v.getMagnitude()));
        return (a == DEGREE) ? Math.toDegrees(r) : r;
    }

    /**
     * The method returns the angle between two vectors.
     * The second parameter can be true/false, depending on if you want the angle
     * in degrees (true) or in radian (false).
     *
     * @param v the another vector to calculate
     * @param a the angle either in degree or radian, See, {@link Angle}
     * @param p the decimal point you want to round up to
     *
     * @return the angle in degrees or radian.
     */
    @Override
    public double angle(final Vector v, final Angle a, final Rounding.POINT p) {
        return Rounding.round(angle(v, a), p).doubleValue();
    }

    /**
     * The method will return the inverse vector.
     * The inverse vector satisfy the following equation:
     * V + inverse(V) = 0 (Zero Vector).
     *
     * @return the inverse vector
     */
    @Override
    public Vector inverse() {
        var n = new Number[d];
        var i = 0;

        for (var e : e)
            n[i++] = -e.doubleValue();

        return new ArrayVector(n);
    }

    /**
     * The method will scale the vector by the given value.
     * Please not, this operation is mutable.
     *
     * @param s the scalar you want to scale the vector with.
     *          if:
     *           <ul>
     *           <li>scalar &gt; 1          -&gt; The scaled vector will be scaled up in the same direction.
     *           <li>0 &lt; scalar &lt; 1   -&gt; The scaled vector is shrunk in the same direction.
     *           <li>scalar = 0             -&gt; The scaled vector becomes a zero vector.
     *           <li>-1 &lt; scalar &lt; 0  -&gt; The scaled vector is shrunk but in the opposite direction.
     *           <li>scalar &lt; -1         -&gt; The scaled vector is scaled up but in the opposite direction.
     *          </ul>
     *
     * @return the self vector but scaled by the given number.
     */
    @Override
    public Vector scale(final double s) {
        var n = new Number[d];
        var i = 0;

        for (var e : e)
            n[i++] = e.doubleValue() * s;

        return new ArrayVector(n);
    }

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
    @Override
    public Vector transpose(final int dimension) {
        if (dimension < 2 || this.d == dimension)
            throw new InvalidVectorDimensionException();

        var n = new Number[dimension];
        if (dimension < this.d)
            System.arraycopy(e, 0, n, 0, dimension);
        else {
            System.arraycopy(e, 0, n, 0, this.d);
            for (var i = this.d; i < dimension; i++) {
                n[i] = 0;
            }
        }

        return new ArrayVector(n);
    }

    /**
     * The method performs the scalar addition on the vector.
     *
     * @param s the scalar to add
     *
     * @return a resulting vector
     */
    @Override
    public Vector add(final Number s) {
        var n = new Number[d];
        int i = 0;

        for (var e : e)
            n[i++] = e.doubleValue() + s.doubleValue();

        return new ArrayVector(n);
    }

    /**
     * The method will add a vector to the current vector.
     * In order to add another vector, both vectors must in the same dimension.
     *
     * @param vector the vector to be added
     *
     * @return the resulting vector
     *
     * @throws InvalidVectorOperationException if the vectors have different dimensions
     */
    @Override
    public Vector add(final Vector vector) {
        return new ArrayVector(addElements(e, vector.toArray()));
    }

    /**
     * The method will add a list of vectors to the current vector.
     * In order to vectors from the list, all vectors must in the same dimension.
     *
     * @param vectors the list of vector
     *
     * @return the resulting vector
     */
    @Override
    public Vector add(final List<Vector> vectors) {
        if (vectors.size() < 2)
            throw new InvalidParameterProvidedException("The list must have at least 2 vectors");

        var n = e;
        for (Vector vector : vectors)
            addElements(n, vector.toArray());

        return new ArrayVector(n);
    }

    /**
     * The method adds two array together.
     *
     * @param e  the base array
     * @param _e the array to be added
     *
     * @return the array addition
     */
    private Number[] addElements(final Number[] e, final Number[] _e) {
        if (e.length != _e.length)
            throw new InvalidVectorOperationException("Both vectors have different dimensions");

        for (var i = 0; i < e.length; i++)
            e[i] = e[i].doubleValue() + _e[i].doubleValue();

        return e;
    }

    /**
     * The method will subtract a vector from the current vector.
     * In order to subtract another vector, both vectors must in the same dimension.
     *
     * @param vector the vector to be subtracted
     *
     * @return the resulting vector
     */
    @Override
    public Vector subtract(final Vector vector) {
        return add(vector.inverse());
    }

    /**
     * The method will return a dot product of two vectors.
     * If both vectors are on different dimensions then
     * {@link InvalidVectorOperationException} exception is thrown.
     *
     * @param vector the second vector
     *
     * @return the resulting dot product
     *
     * @throws InvalidVectorOperationException when both products are on different
     *                                         dimensions.
     */
    @Override
    public double dot(final Vector vector) {
        Number[] _e;
        if (d != (_e = vector.toArray()).length)
            throw new InvalidVectorOperationException("Both Vectors have different dimensions");

        var sum = 0;
        for (var i = 0; i < d; i++) {
            sum += e[i].doubleValue() * _e[i].doubleValue();
        }

        return sum;
    }

    /**
     * The method will return a dot product of two vectors.
     * If both vectors are on different dimensions then
     * {@link InvalidVectorOperationException} exception is thrown.
     *
     * @param v the second vector
     * @param p  the rounding point, {@link Rounding.POINT}
     *
     * @return the resulting dot product
     *
     * @throws InvalidVectorOperationException when both products are on different
     *                                         dimensions.
     */
    @Override
    public double dot(final Vector v, final Rounding.POINT p) {
        return Rounding.round(dot(v), p).doubleValue();
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
     * {@link InvalidVectorOperationException} is thrown.
     *
     * @param v the second 3 dimensional vector
     *
     * @return the cross product vector
     *
     * @throws InvalidVectorOperationException when both vectors are in
     *                                         the third dimension.
     */
    @Override
    public Vector cross(final Vector v) {
        Number[] _e, n = new Number[3];
        if (d != 3 || (_e = v.toArray()).length != 3)
            throw new InvalidVectorOperationException("The cross product is only supported for vectors in 3rd dimension");

        n[0] = e[1].doubleValue() * _e[2].doubleValue() - e[2].doubleValue() * _e[1].doubleValue();
        n[1] = e[2].doubleValue() * _e[0].doubleValue() - e[0].doubleValue() * _e[2].doubleValue();
        n[2] = e[0].doubleValue() * _e[1].doubleValue() - e[1].doubleValue() * _e[0].doubleValue();

        return new ArrayVector(n);
    }

    /**
     * The method calculates the scalar project of a given vector onto
     * the current vector.
     * <p>
     * Let this vector be V and the given vector be W.
     * The scalar projection is defined as,
     * dot(V, W) / magnitude(V)
     *
     * @param v the projecting vector
     *
     * @return the projected scalar
     */
    @Override
    public double scalarProjection(final Vector v) {
        if (d != v.getDimension())
            throw new InvalidVectorOperationException("Vectors have different dimensions");

        return dot(v) / getMagnitude();
    }

    /**
     * The method calculates the scalar project of a given vector onto
     * the current vector.
     * <p>
     * Let this vector be V and the given vector be W.
     * The scalar projection is defined as,
     * dot(V, W) / magnitude(V)
     *
     * @param v the projecting vector
     * @param p  the rounding point, {@link Rounding.POINT}
     *
     * @return the projected scalar
     */
    @Override
    public double scalarProjection(final Vector v, final Rounding.POINT p) {
        return Rounding.round(scalarProjection(v), p).doubleValue();
    }

    /**
     * The method calculates the vector project of a given vector onto
     * the current vector. This will produce another vector.
     * <p>
     * Let this vector be V and the given vector be W.
     * The vector projection is defined as,
     * [dot(V, W) / magnitude(V)] x V
     *
     * @param v the projecting vector
     *
     * @return the projected scalar
     */
    @Override
    public Vector vectorProjection(final Vector v) {
        return new ArrayVector(calculateProjection(v));
    }

    /**
     * The method will return a rejection vector from the the given vector.
     * The projecting vector can be calculated as;
     * R = V - ScalarProjection(W)
     *
     * @param v the projecting vector
     *
     * @return the rejection vector
     */
    @Override
    public Vector vectorRejection(final Vector v) {
        Number[] n = calculateProjection(v), m = new Number[d];

        for (int i = 0; i < d; i++)
            m[i] = e[i].doubleValue() - n[i].doubleValue();

        return new ArrayVector(m);
    }

    /**
     * The method will perform multiplication of a matrix with a vector.
     * The vector must have the dimension equal to the number of columns of the matrix.
     *
     * @param m the matrix to multiply
     *
     * @return the resulting vector
     *
     * @throws InvalidVectorOperationException if the number of columns is not equal to
     *                                         the dimension of a given vector
     */
    @Override
    public Vector multiply(final Matrix m) {
        Number[][] _e = m.toArray();

        if(d != _e[0].length)
            throw new InvalidVectorOperationException("The vector dimension doesn't match with the matrix columns");

        var n = new Number[d];
        for (int i = 0; i < _e[0].length; i++) {
            n[i] = 0;
            for (int j = 0; j < _e[1].length; j++) {
                n[i] = n[i].doubleValue() + (e[j].doubleValue() * _e[i][j].doubleValue());
            }
        }

        return new ArrayVector(n);
    }

    /**
     * The method calculates the elements of projection
     *
     * @param v the projecting vector
     *
     * @return the array with projection
     */
    private Number[] calculateProjection(final Vector v) {
        double sp = scalarProjection(v), m = getMagnitude();
        var n = new Number[d];
        var i = 0;

        for (var e : e)
            n[i++] = sp * e.doubleValue() / m;

        return n;
    }

    /**
     * The method will return an unit vector of given dimensions.
     *
     * @param d the dimension of a vector
     *
     * @return a vector
     */
    public static Vector of(final int d) {
        var n = new Number[d];
        Arrays.fill(n, 1);
        return new ArrayVector(n);
    }

    /**
     * The method creates a two-dimensional {@link ArrayVector}.
     *
     * @param x x value
     * @param y y Value
     *
     * @return an {@link ArrayVector}
     */
    public static Vector of(final Number x, final Number y) {
        return new ArrayVector(new Number[]{x, y});
    }

    /**
     * The method creates a three-dimensional {@link ArrayVector}.
     *
     * @param x x value
     * @param y y Value
     * @param z z Value
     *
     * @return an {@link ArrayVector}
     */
    public static Vector of(final Number x, final Number y, final Number z) {
        return new ArrayVector(new Number[]{x, y, z});
    }

    /**
     * The method creates a three-dimensional {@link ArrayVector}.
     *
     * @param x x value
     * @param y y Value
     * @param z z Value
     * @param t t Value
     *
     * @return an {@link ArrayVector}
     */
    public static Vector of(final Number x, final Number y, final Number z, final Number t) {
        return new ArrayVector(new Number[]{x, y, z, t});
    }

    /**
     * The method will generate vector elements based on the provided lambda functions.
     *
     * @param d the number of dimensions
     * @param o the lambda function to be apply at each increment
     *
     * @return the {@link ArrayVector}
     */
    public static Vector of(final int d, final DoubleUnaryOperator o) {
        var n = new Number[d];

        for (var i = 0; i < d; i++)
            n[i] = o.applyAsDouble(i);

        return new ArrayVector(n);
    }

    /**
     * The method will generate vector elements based on the provided lambda functions.
     * It will also rounds up the values to the given decimal points.
     *
     * @param d the number of dimensions
     * @param o the lambda function to be apply at each increment
     * @param p the decimal point precision
     *
     * @return the {@link ArrayVector}
     */
    public static Vector of(final int d, final DoubleUnaryOperator o, final Rounding.POINT p) {
        var n = new Number[d];

        for (var i = 0; i < d; i++)
            n[i] = Rounding.round(o.applyAsDouble(i), p);

        return new ArrayVector(n);
    }

    /**
     * The method will generate vector elements based on the provided lambda functions.
     * The min and max are the lower and upper bound values for generating values.
     *
     * @param d   the number of dimensions
     * @param min the lower bound value (inclusive)
     * @param max the upper bound value (Exclusive)
     * @param o   the lambda function to be apply at each increment
     *
     * @return the {@link ArrayVector}
     */
    public static Vector of(final int d, final double min, final double max, final DoubleUnaryOperator o) {
        var n = random(d, min, max);

        for (var i = 0; i < d; i++)
            n[i] = o.applyAsDouble(n[i].doubleValue());

        return new ArrayVector(n);
    }

    /**
     * The method will generate vector elements based on the provided lambda functions.
     * The min and max are the lower and upper bound values for generating values.
     * The method also rounds up values to given decimal points.
     *
     * @param d   the number of dimensions
     * @param min the lower bound value (inclusive)
     * @param max the upper bound value (Exclusive)
     * @param o   the lambda function to be apply at each increment
     * @param p   the decimal point precision
     *
     * @return the {@link ArrayVector}
     */
    public static Vector of(final int d, final double min, final double max, final DoubleUnaryOperator o, final Rounding.POINT p) {
        var n = random(d, min, max);

        for (var i = 0; i < d; i++)
            n[i] = Rounding.round(o.applyAsDouble(n[i].doubleValue()), p);

        return new ArrayVector(n);
    }

    /**
     * The method will generate a vector of
     *
     * @param d the number of dimensions
     * @param p the rounding decimals
     *
     * @return the random vector
     */
    public static Vector of(final int d, final Rounding.POINT p) {
        return new ArrayVector(random(d, 0, 1, p));
    }

    /**
     * The method will create a random vector, with given upper and lower bound limits,
     * as well as the rounding mode.
     *
     * @param d   the dimension of the vector
     * @param min the lower bound limit
     * @param max the upper bound limit
     * @param p   the rounding decimal points
     *
     * @return the random vector
     */
    public static Vector of(final int d, final double min, final double max, final Rounding.POINT p) {
        final Number[] n = random(d, min, max, p);
        return new ArrayVector(n);
    }

    /**
     * The method converts the generated random values to given decimal points.
     *
     * @param d   the number of values
     * @param min the minimum value, it is included.
     * @param max the maximum value, it is excluded
     * @param p   the decimal point precision
     *
     * @return the array of values with decimal point precision
     */
    private static Number[] random(final int d, final double min, final double max, final Rounding.POINT p) {
        var n = random(d, min, max);

        for (var i = 0; i < d; i++)
            n[i] = Rounding.round(n[i], p);

        return n;
    }

    /**
     * The method generates random values between the given range.
     *
     * @param d   the number of values
     * @param min the minimum value, it is included.
     * @param max the maximum value, it is excluded
     *
     * @return an array of random values
     */
    private static Number[] random(final int d, final double min, final double max) {
        var n = new Number[d];

        for (int i = 0; i < d; i++)
            n[i] = min + (Math.random() * (max - min));

        return n;
    }

    /**
     * The class is used for caching some of the constant properties.
     */
    private static class Cache {
        private double m;
        private double[] cd;
        private double[] cr;
        private String s;

        private double getM() {
            return m;
        }

        private void setM(final double m) {
            this.m = m;
        }

        private double[] getCd() {
            return cd;
        }

        private void setCd(final double[] cd) {
            this.cd = cd;
        }

        private double[] getCr() {
            return cr;
        }

        private void setCr(final double[] cr) {
            this.cr = cr;
        }

        private String getS() {
            return s;
        }

        private void setS(final String s) {
            this.s = s;
        }
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    public String toString() {
        if (c.getS() == null) {
            var l = new LinkedList<String>();
            Arrays.stream(e).forEach(v -> l.add(v.toString()));
            c.setS("<" + String.join(", ", l) + ">");
        }

        return c.getS();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     *
     * @return {@code true} if this object is the same as the obj
     *         argument; {@code false} otherwise.
     *
     * @see #hashCode()
     * @see HashMap
     */
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (this.hashCode() == obj.hashCode()) return true;

        var that = (ArrayVector) obj;
        if (d != that.getDimension()) return false;

        Number[] n = that.toArray();
        for (int i = 0; i < d; i++)
            if (e[i].doubleValue() != n[i].doubleValue())
                return false;

        return true;
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     *     method, then calling the {@code hashCode} method on each of
     *     the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link Object#equals(Object)}
     *     method, then calling the {@code hashCode} method on each of the
     *     two objects must produce distinct integer results.  However, the
     *     programmer should be aware that producing distinct integer results
     *     for unequal objects may improve the performance of hash tables.
     * </ul>
     *
     * @return a hash code value for this object.
     *
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    public int hashCode() {
        return Arrays.hashCode(e);
    }
}
