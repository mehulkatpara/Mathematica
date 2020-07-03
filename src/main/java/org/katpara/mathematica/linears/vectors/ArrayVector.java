package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.exceptions.NullArgumentProvided;
import org.katpara.mathematica.exceptions.InvalidVectorDimension;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
public final class ArrayVector implements Vector,
        RandomAccess, Cloneable, java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 6608801568539797402L;

    /**
     * The vector elements are stored as an array of numbers
     */
    private final Number[] e;

    /**
     * Just an accumulator for collection constructors.
     */
    private final AtomicInteger i = new AtomicInteger();

    /**
     * The constructor will create a vector with the dimension of 2.
     *
     * @param i the i-hat of a vector (x axis value)
     * @param j the j-hat of a vector (y axis value)
     */
    public ArrayVector(final Number i, final Number j) {
        e = new Number[2];
        e[0] = i;
        e[1] = j;
    }

    /**
     * The constructor will create a vector with the dimension of 3.
     *
     * @param i the i-hat of a vector (x axis value)
     * @param j the j-hat of a vector (y axis value)
     * @param k the k-hat of a vector (z axis value)
     */
    public ArrayVector(final Number i, final Number j, final Number k) {
        e = new Number[3];
        e[0] = i;
        e[1] = j;
        e[2] = k;
    }

    /**
     * The constructor creates a vector from the given {@link Number} array.
     * <p>
     * The Number array has to have at least 2 or more elements to create a
     * vector; otherwise the {@link InvalidVectorDimension} exception is thrown.
     * <p>
     * All the elements in the array must be not-null; fail to do so will
     * result in {@link NullArgumentProvided} exception.
     *
     * @param e the Number array
     *
     * @throws InvalidVectorDimension if the size of Number array is less than 2
     * @throws NullArgumentProvided   when the contains null values
     */
    public ArrayVector(final Number[] e) {
        if (e.length < 2)
            throw new InvalidVectorDimension();

        Arrays.stream(e).forEach(n -> {
            if (n == null) throw new NullArgumentProvided();
        });
        this.e = e;
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
     * will result in the {@link InvalidVectorDimension} exception.
     *
     * @param l the list of elements
     *
     * @throws InvalidVectorDimension when the list have less than 2 elements
     */
    public ArrayVector(final List<? extends Number> l) {
        if ((e = new Number[l.size()]).length < 2)
            throw new InvalidVectorDimension();

        l.forEach(n -> e[i.getAndIncrement()] = n);
    }

    /**
     * The constructor will create a vector from a {@link Set}.
     * The elements of the set must be subclass of {@link Number}.
     * That means the allowed classes are:
     * {@link Short}
     * {@link Byte}
     * {@link Integer}
     * {@link Long}
     * {@link Float}
     * {@link Double}
     * <p>
     * The set must contain at least 2 Numbers or more; failed to comply
     * will result in the {@link InvalidVectorDimension} exception.
     *
     * @param s the list of elements
     *
     * @throws InvalidVectorDimension when the set have less than 2 elements
     */
    public ArrayVector(final Set<? extends Number> s) {
        if ((e = new Number[s.size()]).length < 2)
            throw new InvalidVectorDimension();

        s.forEach(n -> e[i.getAndIncrement()] = n);
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
     * to comply will result in the {@link InvalidVectorDimension} exception.
     *
     * @param m the map of &lt;Any-key, Number&gt;
     *
     * @throws InvalidVectorDimension when the map has less than 2 pairs
     */
    public ArrayVector(final Map<?, ? extends Number> m) {
        if ((e = new Number[m.size()]).length < 2)
            throw new InvalidVectorDimension();

        m.values().forEach(n -> e[i.getAndIncrement()] = n);
    }

    /**
     * The method will tell on which dimension the vector resides.
     *
     * @return the dimension of the vector
     */
    @Override
    public int getDimension() {
        return e.length;
    }

    /**
     * The method will calculate the magnitude of the vector.
     *
     * @return the double value representing the magnitude
     */
    @Override
    public double getMagnitude() {
        return Math.sqrt(Arrays.stream(e)
                .map(n -> n.doubleValue() * n.doubleValue())
                .reduce(0.0, Double::sum));
    }

    /**
     * The method will return all the elements of the vectors as an array.
     *
     * @return the array of all the elements.
     */
    @Override
    public Number[] getElements() {
        return e;
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
    @Override
    public String toString() {
        List<String> l = new LinkedList<>();
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
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        final ArrayVector that = (ArrayVector) obj;
        if (e.length != that.getDimension()) return false;
        return Arrays.equals(e, that.getElements());
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
    @Override
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
    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();

        Number[] n = new Number[e.length];
        System.arraycopy(e, 0, n, 0, e.length);

        return new ArrayVector(n);
    }
}
