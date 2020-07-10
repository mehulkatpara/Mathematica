package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;
import org.katpara.mathematica.exceptions.linears.InvalidVectorDimensionException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.linears.annotations.Angle;

import java.util.*;
import java.util.stream.Collectors;

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
    private final int dimension;

    /**
     * The array stores new processed values of e
     */
    private Number[] n;

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
            if (n == null) throw new NullArgumentProvidedException();

        this.e = e;
        this.dimension = e.length;
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

        this.dimension = e.length;
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
        return dimension;
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
        var sum = 0;

        for (var n : e)
            sum += n.doubleValue() * n.doubleValue();

        return Math.sqrt(sum);
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
     * The method returns the angle between two vectors.
     * The second parameter can be true/false, depending on if you want the angle
     * in degrees (true) or in radian (false).
     *
     * @param vector the another vector to calculate
     * @param degree the angle either in degree or radian
     *
     * @return the angle in degrees or radian.
     */
    @Override
    public double angle(final Vector vector, final boolean degree) {
        double radian = Math.acos(dot(vector) / (getMagnitude() * vector.getMagnitude()));
        return degree ? Math.toDegrees(radian) : radian;
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
        n = new Number[dimension];
        for (var i = 0; i < dimension; i++)
            n[i] = -e[i].doubleValue();

        return new ArrayVector(n);
    }

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
    @Override
    public Vector scale(final double scalar) {
        n = new Number[dimension];
        for (var i = 0; i < dimension; i++)
            n[i] = e[i].doubleValue() * scalar;

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
        if (dimension < 2 || this.dimension == dimension)
            throw new InvalidVectorDimensionException();

        n = new Number[dimension];
        if (dimension < this.dimension)
            System.arraycopy(e, 0, n, 0, dimension);
        else {
            System.arraycopy(e, 0, n, 0, this.dimension);
            for (var i = this.dimension; i < dimension; i++) {
                n[i] = 0;
            }
        }

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
     * @throws InvalidVectorDimensionException if the vectors have different dimensions
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

        n = e;
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
            throw new InvalidVectorDimensionException("Both vectors have different dimensions");

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
     * {@link InvalidVectorDimensionException} exception is thrown.
     *
     * @param vector the second vector
     *
     * @return the resulting dot product
     *
     * @throws InvalidVectorDimensionException when both products are on different
     *                                         dimensions.
     */
    @Override
    public double dot(final Vector vector) {
        Number[] _e;
        if (dimension != (_e = vector.toArray()).length)
            throw new InvalidVectorDimensionException();

        var sum = 0;
        for (var i = 0; i < dimension; i++) {
            sum += e[i].doubleValue() * _e[i].doubleValue();
        }

        return sum;
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
     * @param vector the second 3 dimensional vector
     *
     * @return the cross product vector
     *
     * @throws InvalidVectorDimensionException when both vectors are in
     *                                         the third dimension.
     */
    @Override
    public Vector cross(final Vector vector) {
        Number[] _e, n = new Number[3];
        if (dimension != 3 || (_e = vector.toArray()).length != 3)
            throw new InvalidVectorDimensionException("The cross product is only supported for vectors in 3rd dimension");

        n[0] = e[1].doubleValue() * _e[2].doubleValue() - e[2].doubleValue() * _e[1].doubleValue();
        n[1] = e[2].doubleValue() * _e[0].doubleValue() - e[0].doubleValue() * _e[2].doubleValue();
        n[2] = e[0].doubleValue() * _e[1].doubleValue() - e[1].doubleValue() * _e[0].doubleValue();

        return new ArrayVector(n);
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
        var l = new LinkedList<String>();
        Arrays.stream(e).forEach(v -> l.add(v.toString()));
        return "<" + String.join(", ", l) + ">";
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
     * argument; {@code false} otherwise.
     *
     * @see #hashCode()
     * @see HashMap
     */
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        var that = (ArrayVector) obj;
        if (dimension != that.getDimension()) return false;
        return Arrays.equals(e, that.toArray());
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

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     * <p>
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     * <p>
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     * <p>
     * The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     * <p>
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     *
     * @return a clone of this instance.
     *
     * @throws CloneNotSupportedException if the object's class does not
     *                                    support the {@code Cloneable} interface. Subclasses
     *                                    that override the {@code clone} method can also
     *                                    throw this exception to indicate that an instance cannot
     *                                    be cloned.
     * @see Cloneable
     */
    protected Object clone() throws CloneNotSupportedException {
        super.clone();

        n = new Number[dimension];
        System.arraycopy(e, 0, n, 0, dimension);

        return new ArrayVector(n);
    }
}
