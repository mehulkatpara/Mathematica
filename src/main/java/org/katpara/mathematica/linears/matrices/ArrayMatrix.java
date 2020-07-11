package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.InvalidMatrixDimensionException;
import org.katpara.mathematica.linears.vectors.ArrayVector;
import org.katpara.mathematica.linears.vectors.Vector;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

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
 * t: It is a type defined as {@link MatrixType}.
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
public class ArrayMatrix implements Matrix {
    private static final long serialVersionUID = 3493256845029049971L;

    /**
     * The field holds matrix data
     */
    private final Number[][] e;

    /**
     * The dimension of the matrix
     */
    private final int[] dimension;

    /**
     * The field holds the type of matrix
     */
    private final Matrix.MatrixType t;

    /**
     * The constructor creates a matrix out of two-dimensional array and
     * {@link MatrixType} parameter.
     * <p>
     * The {@link MatrixType} is useful to
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
            throw new InvalidMatrixDimensionException("The matrix should have at least one element");

        for (var a : e)
            for (var _e : a)
                if (_e == null)
                    throw new NullArgumentProvidedException("The matrix can't contain null values");

        this.e = e;
        this.dimension = new int[]{e.length, e[0].length};
        this.t = t;
    }

    /**
     * The constructor is used to create a matrix out of a list of {@link Vector}.
     * Each row of a matrix is a {@link Vector}.
     * The type of the matrix is "NOT_SPECIFIED".
     *
     * @param vl the {@link Vector} List
     *
     * @throws InvalidMatrixDimensionException if the list is empty,
     *                                         when given vectors are on various dimensions
     */
    public ArrayMatrix(final List<? extends Vector> vl) {
        if (vl.size() < 1)
            throw new InvalidMatrixDimensionException("The list is empty");

        int dimension = 0, i = 0;
        Number[][] n = null;

        for (Vector v : vl) {
            if (dimension == 0) {
                dimension = v.getDimension();
                n = new Number[vl.size()][dimension];
            } else if (dimension != v.getDimension()) {
                throw new InvalidMatrixDimensionException("The vectors have different dimensions.");
            }
            n[i++] = v.toArray();
        }

        e = n;
        this.dimension = new int[]{e.length, e[0].length};
        this.t = NOT_SPECIFIED;
    }

    /**
     * The constructor is used to create a matrix out of a set of matrices.
     * The constructor internally relies on {@link #ArrayMatrix(List)} to
     * create a matrix, please note.
     *
     * @param vs a set of {@link Vector}
     *
     * @throws InvalidMatrixDimensionException if the set is empty,
     *                                         when given vectors are on various dimensions
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
     * @throws InvalidMatrixDimensionException if the map is empty,
     *                                         when given vectors are on various dimensions
     */
    public ArrayMatrix(final Map<?, ? extends Vector> vm) {
        this(new ArrayList<>(vm.values()));
    }

    /**
     * The constructor creates a Matrix using two-dimensional array.
     * The type of created matrix is "NOT_SPECIFIED"; since it's user generated.
     * <p>
     * If you are creating a predefined matrix, please use {@link Matrix} interface
     * to create a matrix for you, it's easy and helps me to optimize various calculations
     * much faster. I have many predefined matrices for you to use out of the box, to help
     * you create various matrices with ease, please check
     * {@link MatrixType} for more information.
     *
     * @param e the matrix array.
     *
     * @throws InvalidMatrixDimensionException when matrix doesn't have at least one element.
     * @throws NullArgumentProvidedException   when the array has a null value
     */
    public ArrayMatrix(final Number[][] e) {
        this(e, NOT_SPECIFIED);
    }

    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    @Override
    public int[] getDimension() {
        return dimension;
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
     * The method returns all the elements of a matrix.
     *
     * @return the matrix elements
     */
    @Override
    public Number[][] toArray() {
        return e;
    }

    /**
     * The method returns all the elements as a list of lists.
     *
     * @return the matrix elements as a list of lists
     */
    @Override
    public List<List<Number>> toList() {
        var list = new ArrayList<List<Number>>();
        for (var a : e)
            list.add(new ArrayList<>(Arrays.asList(a)));

        return list;
    }

    /**
     * The method returns the list of Vectors.
     *
     * @return the matrix elements as a list of vectors
     */
    @Override
    public List<Vector> toArrayVectors() {
        var list = new ArrayList<Vector>();

        for (var a : e)
            list.add(new ArrayVector(a));

        return list;
    }

    /**
     * The method will return true if the matrix is a
     * row vector, which is 1 x n
     *
     * @return true if it is a row vector
     */
    @Override
    public boolean isRowVector() {
        return false;
    }

    /**
     * The method will return true if the matrix is a
     * column vector, which is n x 1.
     *
     * @return true if it is a column vector
     */
    @Override
    public boolean isColumnVector() {
        return false;
    }

    /**
     * The method will return true if the matrix is a
     * square matrix, which is n x n.
     *
     * @return true if it is a square matrix
     */
    @Override
    public boolean isSquareMatrix() {
        return false;
    }

    /**
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @return the trace of the square matrix
     */
    @Override
    public double getTrace() {
        return 0;
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public int getRank() {
        return 0;
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
        return 0;
    }

    /**
     * The method will return a zero or null matrix, whose all the elements are zero.
     *
     * @param m the number of rows
     * @param n the number of columns
     *
     * @return an ArrayMatrix with all 0 entries
     *
     * @throws InvalidMatrixDimensionException when # of row + # of column < 2;
     *                                         this ensures that at least one element
     *                                         exist all the time.
     */
    static ArrayMatrix zeroMatrix(final int m, final int n) {
        return calculateZeroOneMatrix(m, n, ZERO);
    }

    /**
     * In mathematics, a matrix of one, or all-ones matrix is a matrix whose all elements are 1.
     *
     * @param m the number of rows
     * @param n the number of columns
     *
     * @return am ArrayMatrix with all 1 entries
     *
     * @throws InvalidMatrixDimensionException when m + n < 2
     */
    static ArrayMatrix oneMatrix(final int m, final int n) {
        return calculateZeroOneMatrix(m, n, ONE);
    }

    /**
     * The method produces Zero or One matrix, based on the type argument.
     *
     * @param m the number of rows
     * @param n the number of columns
     * @param t the type of matrix, i. e. Zero or One
     *
     * @return a one matrix
     *
     * @throws InvalidMatrixDimensionException when m + n < 2
     */
    private static ArrayMatrix calculateZeroOneMatrix(final int m, final int n, final MatrixType t) {
        if (m + n < 2)
            throw new InvalidMatrixDimensionException("One matrix should have at least one element");

        var e = new Number[m][n];

        if (t == ZERO)
            Arrays.fill(e[0], 0);

        if (t == ONE)
            Arrays.fill(e[0], 1);

        for (int i = 1; i < e.length; i++)
            System.arraycopy(e[0], 0, e[i], 0, e[0].length);

        return new ArrayMatrix(e, t);
    }

    /**
     * The method will create a pascal's square matrix.
     *
     * @param n the number of rows and columns of a square matrix
     * @param t the type of matrix
     *          i.e. UPPER, LOWER or SYMMETRIC
     *
     * @return a Pascal's {@link Matrix}
     *
     * @throws InvalidMatrixDimensionException when n < 1, at least one element should exist.
     */
    static ArrayMatrix pascalMatrix(final int n, final PascalMatrixType t) {
        if (n < 1)
            throw new InvalidMatrixDimensionException("Pascal's matrix should have at least one element");

        var r = new Number[n];
        var e = new Number[n][n];

        if (t == PascalMatrixType.UPPER) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    e[i][j] = (i == j || i == 0) ? 1 : (j < i) ? 0 : e[i][j - 1].longValue() + r[j - 1].longValue();

                r = e[i];
            }
        }

        if (t == PascalMatrixType.LOWER) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    e[i][j] = (i == j || j == 0) ? 1 : (i < j) ? 0 : r[j].longValue() + r[j - 1].longValue();

                r = e[i];
            }
        }

        if (t == PascalMatrixType.SYMMETRIC) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    e[i][j] = (i == 0 || j == 0) ? 1 : e[i][j - 1].longValue() + r[j].longValue();

                r = e[i];
            }
        }

        return new ArrayMatrix(e, PASCAL);
    }

    /**
     * A lehmer matrix is a constant systematic square matrix.
     *
     * @param n the number of rows and columns
     *
     * @return InvalidMatrixDimensionException if n < 1
     */
    static ArrayMatrix lehmerMatrix(final int n) {
        return new ArrayMatrix(
                calculateDoubleMatrix
                        (n, (i, j) -> (j >= i) ? (i + 1) / (j + 1) : (j + 1) / (i + 1)), LEHMER);
    }

    /**
     * A Hilbert matrix is a square matrix with entries being the unit fractions.
     *
     * @param n the number of rows and columns
     *
     * @return a hilbert {@link ArrayMatrix_old}
     *
     * @throws InvalidMatrixDimensionException if n < 1
     */
    static ArrayMatrix hilbertMatrix(final int n) {
        return new ArrayMatrix(
                calculateDoubleMatrix
                        (n, (i, j) -> 1 / (i + 1 + (j + 1) - 1)), HILBERT);
    }

    /**
     * The identity matrix is a square matrix whose diagonal is always 1 and all the
     * other elements are 0.
     *
     * @param n the number of rows and columns
     *
     * @return an identity {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n < 1
     */
    static ArrayMatrix identityMatrix(final int n) {
        return new ArrayMatrix(
                calculateIntMatrix
                        (n, (i, j) -> (i.equals(j)) ? 1 : 0), IDENTITY);

    }

    /**
     * An exchange matrix is a square matrix whose counterdiagonal is always 1 and the
     * rest of the elements are 0.
     *
     * @param n the square matrix
     *
     * @return an exchange {@link ArrayMatrix_old}
     *
     * @throws InvalidMatrixDimensionException if n < 1
     */
    static ArrayMatrix exchangeMatrix(final int n) {
        return new ArrayMatrix(
                calculateIntMatrix
                        (n, (i, j) -> (j == n - i - 1) ? 1 : 0), EXCHANGE);
    }

    /**
     * A redheffer matrix is a (0-1) square matrix, whose entries are either 1 or 0.
     * The matrix is calculated as if n is divisible by m, then it's 1 otherwise it's 0.
     *
     * @param n the number of rows and columns
     *
     * @return a redheffer {@link ArrayMatrix_old}
     *
     * @throws InvalidMatrixDimensionException if n < 1
     */
    static ArrayMatrix redhefferMatrix(final int n) {
        return new ArrayMatrix(
                calculateIntMatrix
                        (n, (i, j) -> (j == 0) ? 1 : (((j + 1) % (i + 1) == 0) ? 1 : 0)), REDHEFFER);
    }

    /**
     * The shift matrix is a matrix whose diagonal has shifted one level up or down, known as
     * super diagonal matrix, or lower diagonal matrix.
     *
     * @param n the number of rows and columns
     * @param t the type of matrix, i.e UPPER or LOWER
     *
     * @return a shift {@link ArrayMatrix_old}
     *
     * @throws InvalidMatrixDimensionException if n < 1
     */
    static ArrayMatrix shiftMatrix(final int n, final ShiftMatrixType t) {
        if (t == ShiftMatrixType.UPPER)
            return new ArrayMatrix(calculateIntMatrix(n, (i, j) -> (j == i + 1) ? 1 : 0), SHIFT);

        return new ArrayMatrix(calculateIntMatrix(n, (i, j) -> (j == i - 1) ? 1 : 0), SHIFT);
    }

    /**
     * The method is helpful to create some of the constant matrices.
     *
     * @param n        the dimension of the matrix
     * @param operator the binary operation
     *
     * @return a two dimensional array
     */
    private static Number[][] calculateIntMatrix(final int n, BinaryOperator<Integer> operator) {
        if (n < 1)
            throw new InvalidMatrixDimensionException("Shift matrix should have at least one element");

        var e = new Number[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                e[i][j] = operator.apply(i, j);

        return e;
    }

    /**
     * The method is helpful to create some of the constant matrices.
     *
     * @param n        the dimension of the matrix
     * @param operator the binary operation
     *
     * @return a two dimensional array
     */
    private static Number[][] calculateDoubleMatrix(final int n, DoubleBinaryOperator operator) {
        if (n < 1)
            throw new InvalidMatrixDimensionException("Shift matrix should have at least one element");

        var e = new Number[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                e[i][j] = operator.applyAsDouble(i, j);

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
    public boolean equals(final Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        final ArrayMatrix that = (ArrayMatrix) obj;
        var o = that.toArray();

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
