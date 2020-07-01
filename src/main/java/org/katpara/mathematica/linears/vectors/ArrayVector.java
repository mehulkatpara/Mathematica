package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.exceptions.NullArgumentProvided;
import org.katpara.mathematica.exceptions.VectorInvalidDimension;

import java.util.*;

/**
 * The ArrayVector class is an implementations of the Vector interface.
 * The class provides vector operations by manipulating the arrays.
 *
 * @author Mehul Katpara <mkatpara19@gmail.com>
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
     * vector; otherwise the {@link VectorInvalidDimension} exception is thrown.
     * <p>
     * All the elements in the array must be not-null; fail to do so will
     * result in {@link NullArgumentProvided} exception.
     *
     * @param e the Number array
     * @throws VectorInvalidDimension if the size of Number array is less than 2
     * @throws NullArgumentProvided   when the contains null values
     */
    public ArrayVector(final Number[] e) {
        if (e.length < 2)
            throw new VectorInvalidDimension();

        for (Number n : e)
            if (n == null)
                throw new NullArgumentProvided();

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
     * will result in the {@link VectorInvalidDimension} exception.
     *
     * @param l the list of elements
     * @throws VectorInvalidDimension when the list have less than 2 elements
     */
    public ArrayVector(final List<? extends Number> l) {
        if ((e = new Number[l.size()]).length < 2)
            throw new VectorInvalidDimension();

        for (int i = 0; i < l.size(); i++)
            e[i] = l.get(i);
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
     * will result in the {@link VectorInvalidDimension} exception.
     *
     * @param s the list of elements
     * @throws VectorInvalidDimension when the set have less than 2 elements
     */
    public ArrayVector(final Set<? extends Number> s) {
        if ((e = new Number[s.size()]).length < 2)
            throw new VectorInvalidDimension();

        Iterator<? extends Number> it = s.iterator();
        int i = 0;
        while (it.hasNext()) {
            e[i] = it.next();
            i++;
        }
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
     * to comply will result in the {@link VectorInvalidDimension} exception.
     *
     * @param m the map of <Any-key, Number>
     * @throws VectorInvalidDimension when the map has less than 2 pairs
     */
    public ArrayVector(final Map<?, ? extends Number> m) {
        if ((e = new Number[m.size()]).length < 2)
            throw new VectorInvalidDimension();

        int i = 0;
        for (Number n : m.values()) {
            e[i] = n;
            i++;
        }
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
        double sum = 0;
        for (Number n : e)
            sum += n.doubleValue() * n.doubleValue();

        return Math.sqrt(sum);
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
     * The method will scale the vector by the given value.
     * Please not, this operation is mutable.
     *
     * @param scalar the scalar you want to scale the vector with.
     *               if:
     *               scalar > 1      -> The scaled vector will be scaled up in the same direction.
     *               0 < scalar > 1  -> The scaled vector is shrunk in the same direction.
     *               scalar = 0      -> The scaled vector becomes a zero vector.
     *               -1 < scalar > 0 -> The scaled vector is shrunk but in the opposite direction.
     *               scalar < -1     -> The scaled vector is scaled up but in the opposite direction.
     * @return the self vector but scaled by the given number.
     */
    @Override
    public Vector scale(final Number scalar) {
        for (int i = 0; i < e.length; i++)
            e[i] = e[i].doubleValue() * scalar.doubleValue();

        return this;
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
}
