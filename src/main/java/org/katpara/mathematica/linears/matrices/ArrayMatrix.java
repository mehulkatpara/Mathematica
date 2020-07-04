package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.exceptions.InvalidMatrixDimensionException;
import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;
import org.katpara.mathematica.exceptions.NotSquareMatrixException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.linears.vectors.Vector;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The class is an implementations of the Matrix interface.
 * The class uses two dimensional array to create a matrix.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class ArrayMatrix implements Matrix,
        RandomAccess, Cloneable, java.io.Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 3493256845029049971L;

    private final Number[][] e;
    private final AtomicInteger i = new AtomicInteger(0);

    /**
     * The constructor creates a Matrix.
     *
     * @param e the matrix array.
     *
     * @throws InvalidMatrixDimensionException when array has less than 1 row
     *                                when array has less then 1 column
     */
    public ArrayMatrix(final Number[][] e) {
        if (e.length == 0 || e.length + e[0].length < 2 )
            throw new InvalidMatrixDimensionException();

        Arrays.stream(e).forEach(i ->
                Arrays.stream(i).forEach(ie -> {
                    if (ie == null)
                        throw new NullArgumentProvidedException();
                }));

        this.e = e;
    }

    /**
     * The constructor creats a matrix out of the list containing a list of numbers,
     * or a set containing a list of numbers.
     *
     * @param c the collection of list of numbers
     */
    public ArrayMatrix(final Collection<List<Number>> c) {
        if (!(c instanceof List) && !(c instanceof Set))
            throw new InvalidParameterProvidedException("The matrix can only be a type of List or Set");

        if (c.size() == 0)
            throw new InvalidMatrixDimensionException();

        var _ref = new Object() {
            int _i = 0;
            final int[] d = new int[]{c.size(), 0};
            Number[][] n;
        };

        c.forEach(r -> {
            if (r.size() == 0)
                throw new InvalidMatrixDimensionException();

            if (_ref.d[1] == 0) {
                _ref.d[1] = r.size();
                _ref.n = new Number[_ref.d[0]][_ref.d[1]];
            } else if (_ref.d[1] != r.size()) {
                throw new InvalidMatrixDimensionException("The rows have multiple sizes");
            }

            var _i = i.getAndIncrement();
            r.forEach(_e -> _ref.n[_i][_ref._i++] = _e);
            _ref._i = 0;
        });

        e = _ref.n;
    }

    public ArrayMatrix(final List<? extends Vector> vl) {
        if (vl.size() < 1)
            throw new InvalidMatrixDimensionException();

        var _ref = new Object() {
            final int[] d = new int[]{vl.size(), 0};
            Number[][] n;
        };
        vl.forEach(v -> {
            if (_ref.d[1] == 0) {
                _ref.d[1] = v.getDimension();
                _ref.n = new Number[_ref.d[0]][_ref.d[1]];
            } else if (_ref.d[1] != v.getDimension()) {
                throw new InvalidMatrixDimensionException("The vectors have different dimensions.");
            }

            _ref.n[i.getAndIncrement()] = v.getElements();
        });

        e = _ref.n;
    }

    public ArrayMatrix(final Set<Vector> vs) {
        this(new ArrayList<>(vs));
    }

    public ArrayMatrix(final Map<?, ? extends Vector> vm) {
        this(new ArrayList<>(vm.values()));
    }

    /**
     * In linear algebra, a zero matrix or null matrix is a matrix whose
     * all the elements are zero.
     *
     * @return true if the matrix is zero or null matrix.
     */
    @Override
    public boolean isZeroMatrix() {
        //TODO: Implement the zero matrix check logic.
        return false;
    }

    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    @Override
    public int[] getDimension() {
        return new int[]{e.length, e[0].length};
    }

    /**
     * The method returns all the elements of a matrix.
     *
     * @return the matrix elements
     */
    @Override
    public Number[][] getElements() {
        return e;
    }

    /**
     * The method will return true if the matrix is a
     * row vector, which is 1 x n
     *
     * @return true if it is a row vector
     */
    @Override
    public boolean isRowVector() {
        return e.length == 1;
    }

    /**
     * The method will return true if the matrix is a
     * column vector, which is n x 1.
     *
     * @return true if it is a column vector
     */
    @Override
    public boolean isColumnVector() {
        return e[0].length == 1;
    }

    /**
     * The method will return true if the matrix is a
     * square matrix, which is n x n.
     *
     * @return true if it is a square matrix
     */
    @Override
    public boolean isSquareMatrix() {
        return e.length == e[0].length;
    }

    /**
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @return the trace of matrix
     */
    @Override
    public double trace() {
        if(!isSquareMatrix())
            throw new NotSquareMatrixException();

        return 0;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        Arrays.stream(e).forEach(r -> {
            var ls = new LinkedList<String>();
            Arrays.stream(r).forEach(e -> ls.add(e.toString()));
            s.append("|");
            s.append(String.join(", ", ls));
            s.append("|\n");
        });
        return s.toString();
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

        final ArrayMatrix that = (ArrayMatrix) obj;
        var o = that.getElements();

        if (!Arrays.equals(this.getDimension(), that.getDimension())) return false;

        for (int j = 0; j < this.e.length; j++) {
            for (int k = 0; k < e[j].length; k++) {
                if (!e[j][k].equals(o[j][k]))
                    return false;
            }
        }
        return true;
    }
}
