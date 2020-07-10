package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.exceptions.linears.InvalidMatrixDimensionException;
import org.katpara.mathematica.exceptions.linears.InvalidMatrixOperationException;
import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.linears.vectors.Vector;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.katpara.mathematica.linears.matrices.Matrix.MatrixType.*;

/**
 * The class is an implementations of the Matrix interface.
 * Internally the class has two properties;
 * <ul>
 *     <li>e: a two dimensional array</li>
 *     <li>t: a type of Matrix</li>
 * </ul>
 * <p>
 * e: It is a two-dimensional array that holds all the elements of a matrix.
 * t: It is a type defined as {@link org.katpara.mathematica.linears.matrices.Matrix.MatrixType}.
 * <p>
 * When you create a matrix using one of these constructors, the type is absolutely "NOT_SPECIFIED".
 * Which means that I have no way to predetermine the type of matrix you are creating.
 * However, the things get interesting when you are creating a matrix using {@link Matrix} interface.
 * I have already implemented a logic to create almost all kind of constant matrices. So if you are
 * creating a matrix of a certain kind, that I have already coded, please use the interface.
 * <p>
 * The reason is that, I am doing many calculations based on the type of matrix. If I already know
 * the type of the matrix, I can optimize the process to work really really fast. Thank you :)
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public final class ArrayMatrix implements Matrix,
        RandomAccess, Cloneable, java.io.Serializable {

    private static final long serialVersionUID = 3493256845029049971L;

    private final Number[][] e;
    private final MatrixType t;
    private final AtomicInteger i = new AtomicInteger(0);

    /**
     * The constructor creates a matrix out of two-dimensional array and
     * {@link org.katpara.mathematica.linears.matrices.Matrix.MatrixType} parameter.
     * <p>
     * The {@link org.katpara.mathematica.linears.matrices.Matrix.MatrixType} is useful to
     * calculate various properties much faster than going through all the necessary calculations.
     * <p>
     * When a user creates the type of a matrix is usually "NOT_SPECIFIED", since I have no way to
     * figure out what kind of constant matrix you are defining. The only way I can figure out the
     * type is by when you use {@link Matrix} interface to create a predefined matrix for you.
     * So if you are creating a matrix that is already pre-defined, please use {@link Matrix} interface.
     *
     * @param e a two-dimensional elements array
     * @param t a type of matrix
     *
     * @throws InvalidMatrixDimensionException when matrix doesn't have at least one element.
     * @throws NullArgumentProvidedException   when the array has a null value
     */
    ArrayMatrix(final Number[][] e, final MatrixType t) {
        if (e.length == 0 || e.length + e[0].length < 2)
            throw new InvalidMatrixDimensionException();

        Arrays.stream(e).forEach(i ->
                Arrays.stream(i).forEach(ie -> {
                    if (ie == null)
                        throw new NullArgumentProvidedException();
                }));

        this.e = e;
        this.t = t;
    }

    /**
     * The constructor creates a Matrix using two-dimensional array.
     * The type of created matrix is "NOT_SPECIFIED"; since it's user generated.
     * <p>
     * If you are creating a predefined matrix, please use {@link Matrix} interface
     * to create a matrix for you, it's easy and helps me to optimize various calculations
     * much faster. I have many predefined matrices for you to use out of the box, to help
     * you create various matrices with ease, please check
     * {@link org.katpara.mathematica.linears.matrices.Matrix.MatrixType} for more information.
     *
     * @param e the matrix array.
     *
     * @throws InvalidMatrixDimensionException when matrix doesn't have at least one element.
     * @throws NullArgumentProvidedException   when the array has a null value
     */
    public ArrayMatrix(final Number[][] e) {
        if (e.length == 0 || e.length + e[0].length < 2)
            throw new InvalidMatrixDimensionException();

        Arrays.stream(e).forEach(i ->
                Arrays.stream(i).forEach(ie -> {
                    if (ie == null)
                        throw new NullArgumentProvidedException();
                }));

        this.e = e;
        this.t = NOT_SPECIFIED;
    }

    /**
     * The constructor creates a matrix out of the list containing a list of numbers,
     * or a set containing a list of numbers. The type of this matrix is "NOT_SPECIFIED".
     * <p>
     * If you are creating a predefined matrix, please use {@link Matrix} interface
     * to create a matrix for you, it's easy and helps me to optimize various calculations
     * much faster. I have many predefined matrices for you to use out of the box, to help
     * you create various matrices with ease, please check
     * {@link org.katpara.mathematica.linears.matrices.Matrix.MatrixType} for more information.
     *
     * @param c the collection of list of numbers
     *
     * @throws InvalidParameterProvidedException when a collection is not a type of List or Set
     * @throws InvalidMatrixDimensionException   when the collection is empty
     * @throws InvalidMatrixDimensionException   when rows are of variable length
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
        this.t = NOT_SPECIFIED;
    }

    /**
     * The constructor is used to create a matrix out of a list of {@link Vector}.
     * Each row of a matrix is a {@link Vector}.
     * The type of the matrix is "NOT_SPECIFIED".
     *
     * @param vl the {@link Vector} List
     *
     * @throws InvalidMatrixDimensionException if the list is empty
     * @throws InvalidMatrixDimensionException when given vectors are on various dimensions
     */
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

            _ref.n[i.getAndIncrement()] = v.toArray();
        });

        e = _ref.n;
        this.t = NOT_SPECIFIED;
    }

    /**
     * The constructor is used to create a matrix out of a set of matrices.
     * The constructor internally relies on {@link #ArrayMatrix(List)} to
     * create a matrix, please note.
     *
     * @param vs a set of {@link Vector}
     *
     * @throws InvalidMatrixDimensionException if the set is empty
     * @throws InvalidMatrixDimensionException when given vectors are on various dimensions
     */
    public ArrayMatrix(final Set<Vector> vs) {
        this(new ArrayList<>(vs));
    }

    /**
     * The constructor creates a matrix out of a map of vectors. The keys of the map
     * is disregarded when creating a matrix out of a Map. In that case, the keys
     * can be anything as long as it makes a valid map, however the values must be
     * a type of {@link Vector}.
     * <p>
     * Please note, that this constructor internally relies on {@link #ArrayMatrix(List)}
     * to create a matrix.
     *
     * @param vm a map of {@link Vector}
     *
     * @throws InvalidMatrixDimensionException if the map is empty
     * @throws InvalidMatrixDimensionException when given vectors are on various dimensions
     */
    public ArrayMatrix(final Map<?, ? extends Vector> vm) {
        this(new ArrayList<>(vm.values()));
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
     * The method returns the type of a matrix.
     * For more details see {@link MatrixType}
     *
     * @return the type of matrix
     */
    @Override
    public MatrixType getType() {
        return t;
    }

    /**
     * The method will return true if the matrix is a
     * row vector, which is 1 x n
     *
     * @return true if it is a row vector
     */
    @Override
    public boolean isRowVector() {
        return e.length == 1 && e[0].length > 1;
    }

    /**
     * The method will return true if the matrix is a
     * column vector, which is n x 1.
     *
     * @return true if it is a column vector
     */
    @Override
    public boolean isColumnVector() {
        return e.length > 1 && e[0].length == 1;
    }

    /**
     * The method will return true if the matrix is a
     * square matrix, which is n x n.
     *
     * @return true if it is a square matrix
     */
    @Override
    public boolean isSquareMatrix() {
        switch (t) {
            case SHIFT:
            case EXCHANGE:
            case HILBERT:
            case REDHEFFER:
            case IDENTITY:
            case LEHMER:
            case PASCAL:
                return true;
            default:
                return e.length == e[0].length;
        }
    }

    /**
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @return the trace of the square matrix
     *
     * @throws InvalidMatrixOperationException when the matrix is not a square matrix
     */
    @Override
    public double getTrace() {
        if (!isSquareMatrix())
            throw new InvalidMatrixOperationException("The matrix is not a square matrix.");

        switch (t) {
            case IDENTITY:
            case LEHMER:
            case ONE:
                return e.length;
            case SHIFT:
                return 0;
            case EXCHANGE:
                return (e.length % 2 == 0) ? 0 : 1;
            case PASCAL:
                return ((e[0].length > 1 && e[0][1].intValue() == 0)
                        || (e.length > 1 && e[1][0].intValue() == 0)) ? e.length : calculateTrace();
            default:
                return calculateTrace();
        }
    }

    /**
     * The method will calculate a trace of the square matrix.
     *
     * @return the trace of the matrix
     */
    private double calculateTrace() {
        return IntStream.range(0, e.length)
                .mapToDouble(i -> e[i][i].doubleValue())
                .sum();
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public int getRank() {
        //TODO: Rank calculation for other matrices.
        switch (t) {
            case ONE:
                return 1;
            case IDENTITY:
                return e.length;
            case SHIFT:
                return e.length - 1;
            default:
                return 0;
        }
    }

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @return the determinant of the square matrix
     */
    @Override
    public double getDeterminant() {
        if (!isSquareMatrix())
            throw new InvalidMatrixOperationException();

        if (e.length == 1) {
            return e[0][0].doubleValue();
        } else {
            return getDeterminant(e);
        }
    }

    /**
     * The method is responsible to sort, refine, replace rows.
     * This is very crusial for determinant optimization.
     *
     * @param e the two dimensional array
     *
     * @return the determinant of the matrix.
     */
    private double getDeterminant(final Number[][] e) {
        if (e.length == 2) {
            return (e[0][0].doubleValue() * e[1][1].doubleValue())
                    - (e[0][1].doubleValue() * e[1][0].doubleValue());
        } else {
            return calculateDeterminant(e);
        }
    }

    /**
     * The method calculates the determinant of a given matrix.
     *
     * @param e the two-dimensional array
     *
     * @return the determinant of the matrix
     */
    private double calculateDeterminant(final Number[][] e) {
        var _ref = new Object() {
            final int l = e.length;
            double d = 0;
            int[] m, n;
        };

        _ref.m = IntStream.range(0, _ref.l).filter(m -> m != 0).toArray();

        IntStream.range(0, _ref.l).forEach(i -> {
            _ref.n = IntStream.range(0, _ref.l).filter(n -> n != i).toArray();

            _ref.d += (e[0][i].doubleValue() == 0) ? 0 :
                    ((i % 2 == 0) ? e[0][i].doubleValue() : -e[0][i].doubleValue())
                            * getDeterminant(subset(e, _ref.m, _ref.n));
        });

        return _ref.d;
    }

    /**
     * The method returns the subset matrix by given rows and columns.
     *
     * @param e The two-dimensional matrix elements
     * @param m an array containing the rows to pick
     * @param n an array containing the columns to pick
     *
     * @return the subset of given two-dimensional array
     */
    private Number[][] subset(final Number[][] e, final int[] m, final int[] n) {
        var _e = new Number[m.length][n.length];
        IntStream.range(0, m.length).forEach(i -> IntStream.range(0, n.length).forEach(j -> _e[i][j] = e[m[i]][n[j]]));
        return _e;
    }

    /**
     * A permanent of a square matrix is similar to determinant.
     *
     * @return the permanent of a matrix
     */
    @Override
    public double getPermanent() {
        return 0;
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
        StringBuffer s = new StringBuffer();
        Arrays.stream(e).forEach(r -> {
            var ls = new LinkedList<String>();
            Arrays.stream(r).forEach(e -> ls.add(e.toString()));
            s.append("|");
            s.append(String.join(",\t\t", ls));
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
     *         argument; {@code false} otherwise.
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
