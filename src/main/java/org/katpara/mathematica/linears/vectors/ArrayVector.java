package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.exceptions.NullArgumentProvided;
import org.katpara.mathematica.exceptions.VectorInvalidDimension;

import java.util.*;

public final class ArrayVector implements Vector,
        RandomAccess, Cloneable, java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 6608801568539797402L;

    private final Number[] e;

    public ArrayVector(final Number i, final Number j) {
        e = new Number[2];
        e[0] = i;
        e[1] = j;
    }

    public ArrayVector(final Number i, final Number j, final Number k) {
        e = new Number[3];
        e[0] = i;
        e[1] = j;
        e[2] = k;
    }

    public ArrayVector(final Number[] e) {
        if (e.length < 2)
            throw new VectorInvalidDimension();

        for (Number n : e)
            if (n == null)
                throw new NullArgumentProvided();

        this.e = e;
    }

    public ArrayVector(final List<? extends Number> l) {
        if(l.size() < 2)
            throw new VectorInvalidDimension();

        e = new Number[l.size()];
        for (int i = 0; i < l.size(); i++)
            e[i] = l.get(i);
    }

    public ArrayVector(final Set<? extends Number> s) {
        if(s.size() < 2)
            throw new VectorInvalidDimension();

        e = new Number[s.size()];
        Iterator<? extends Number> it = s.iterator();
        int i = 0;
        while (it.hasNext()){
            e[i] = it.next();
            i++;
        }
    }

    public ArrayVector(final Map<?, ? extends Number> m) {
        if(m.size() < 2)
            throw new VectorInvalidDimension();

        e = new Number[m.size()];
        int i =0;
        for (Number n: m.values()) {
            e[i] = n;
            i++;
        }
    }

    @Override
    public int getDimension() {
        return e.length;
    }

    @Override
    public double getMagnitude() {
        double sum = 0;
        for (Number n: e)
            sum += n.doubleValue() * n.doubleValue();

        return Math.sqrt(sum);
    }

    @Override
    public Number[] getElements() {
        return e;
    }

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
