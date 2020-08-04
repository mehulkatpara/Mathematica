package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.commons.Rounding;
import org.katpara.mathematica.exceptions.InvalidParameterProvidedException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.InvalidMatrixDimensionException;
import org.katpara.mathematica.exceptions.linears.InvalidMatrixOperationException;
import org.katpara.mathematica.linears.vectors.ArrayVector;
import org.katpara.mathematica.linears.vectors.Vector;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;

import static org.katpara.mathematica.linears.matrices.Matrix.MatrixType.*;

/**
 * The class is an implementations of the Matrix interface.
 * Internally the class has three properties;
 * <ul>
 *     <li>e: a two-dimensional array</li>
 *     <li>d: the dimension of the matrix</li>
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
    private final int[] d;

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
        this.d = new int[]{e.length, e[0].length};
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

        int d = 0, i = 0;
        Number[][] n = null;

        for (Vector v : vl) {
            if (d == 0) {
                d = v.getDimension();
                n = new Number[vl.size()][d];
            } else if (d != v.getDimension()) {
                throw new InvalidMatrixDimensionException("The vectors have different dimensions.");
            }
            n[i++] = v.toArray();
        }

        e = n;
        this.d = new int[]{e.length, e[0].length};
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
     * The constructor creates a matrix out of the list containing a list of numbers,
     * or a set containing a list of numbers. The type of this matrix is "NOT_SPECIFIED".
     * <p>
     * If you are creating a predefined matrix, please use {@link Matrix} interface
     * to create a matrix for you, it's easy and helps me to optimize various calculations
     * much faster. I have many predefined matrices for you to use out of the box, to help
     * you create various matrices with ease, please check
     * {@link MatrixType} for more information.
     *
     * @param lists the collection of list of numbers
     *
     * @throws InvalidParameterProvidedException when a collection is not a type of List or Set
     * @throws InvalidMatrixDimensionException   when the collection is empty
     * @throws InvalidMatrixDimensionException   when rows are of variable length
     */
    public ArrayMatrix(final Collection<List<Number>> lists) {
        if (!(lists instanceof List) && !(lists instanceof Set))
            throw new InvalidParameterProvidedException("The matrix can only be a type of List or Set");

        if (lists.size() == 0)
            throw new InvalidMatrixDimensionException("The list must have at least one list");

        int d = 0, i = 0;
        Number[][] n = null;

        for (var list : lists) {
            if (list.size() == 0)
                throw new InvalidMatrixDimensionException();

            if (d == 0) {
                d = list.size();
                n = new Number[lists.size()][d];
            } else if (list.size() != d) {
                throw new InvalidMatrixDimensionException("The rows have multiple sizes");
            }

            n[i] = list.toArray(Number[]::new);
        }

        e = n;
        this.d = new int[]{e.length, e[0].length};
        this.t = NOT_SPECIFIED;
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
        return d;
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
        return d[0] == 1 && d[1] > 1;
    }

    /**
     * The method will return true if the matrix is a
     * column vector, which is n x 1.
     *
     * @return true if it is a column vector
     */
    @Override
    public boolean isColumnVector() {
        return d[0] > 1 && d[1] == 1;
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
                return d[0] == d[1];
        }
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
        if (!isSquareMatrix())
            throw new InvalidMatrixOperationException("The matrix is not a square matrix.");

        switch (t) {
            case IDENTITY:
            case LEHMER:
            case ONE:
                return d[0];
            case SHIFT:
                return 0;
            case EXCHANGE:
                return (d[0] % 2 == 0) ? 0 : 1;
            case PASCAL:
                return ((d[1] > 1 && e[0][1].intValue() == 0)
                                || (d[0] > 1 && e[1][0].intValue() == 0)) ? d[0] : calculateTrace();
            default:
                return calculateTrace();
        }
    }

    /**
     * The trace of the matrix is defined as the sum of all the elements,
     * on the main diagonal.
     * <p>
     * The trace only exist for a square matrix.
     *
     * @param p the decimal points of accuracy
     *
     * @return the trace of the square matrix
     */
    @Override
    public double getTrace(final Rounding.POINT p) {
        return Rounding.round(getTrace(), p);
    }

    /**
     * The method will calculate a trace of the square matrix.
     *
     * @return the trace of the matrix
     */
    private double calculateTrace() {
        var s = 0;

        for (var i = 0; i < d[0]; i++)
            s += e[i][i].doubleValue();

        return s;
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public int getRank() {
        switch (t) {
            case ONE:
            case ZERO:
                return 1;
            case IDENTITY:
            case EXCHANGE:
            case HILBERT:
            case LEHMER:
            case PASCAL:
            case REDHEFFER:
                return d[0];
            case SHIFT:
                return d[0] - 1;
            default:
                if (isColumnVector() || isRowVector()) return 1;
                return calculateRank();
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

        var det = 0.0;
        if (d[0] == 1) {
            det = e[0][0].doubleValue();
        } else if (d[0] == 2) {
            det = (e[0][0].doubleValue() * e[1][1].doubleValue()) -
                           (e[0][1].doubleValue() * e[1][0].doubleValue());
        } else if (d[1] == 3) {
            double _e1 = e[0][0].doubleValue(),
                    _e2 = e[0][1].doubleValue(),
                    _e3 = e[0][2].doubleValue(),
                    _e4 = e[1][0].doubleValue(),
                    _e5 = e[1][1].doubleValue(),
                    _e6 = e[1][2].doubleValue(),
                    _e7 = e[2][0].doubleValue(),
                    _e8 = e[2][1].doubleValue(),
                    _e9 = e[2][2].doubleValue();

            det = (
                    (_e1 * _e5 * _e9) + (_e2 * _e6 * _e7) + (_e3 * _e4 * _e8) -
                            (_e3 * _e5 * _e7) - (_e2 * _e4 * _e9) - (_e1 * _e6 * _e8)
            );
        } else {
            Number[][][] n = lu();
            det = n[1][0][0].doubleValue();
            for (var i = 1; i < n[1].length; i++)
                if(n[1][i][i].doubleValue() == 0 || Double.isNaN(n[1][i][i].doubleValue())) {
                    det = 0;
                    break;
                } else {
                    det *= n[1][i][i].doubleValue();
                }
        }

        return det;
    }

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @param point the decimal point accuracy
     *
     * @return the determinant of the square matrix
     */
    @Override
    public double getDeterminant(final Rounding.POINT point) {
        return Rounding.round(getDeterminant(), point);
    }

    /**
     * The method transposes the matrix.
     *
     * @return the transposed matrix
     */
    @Override
    public Matrix transpose() {
        var n = new Number[d[1]][d[0]];

        for (var i = 0; i < d[0]; i++)
            for (var j = 0; j < d[1]; j++)
                n[j][i] = e[i][j];

        return new ArrayMatrix(n);
    }

    /**
     * The method will return an inverse matrix of a given matrix.
     * It returns a null matrix if the inverse is not possible.
     *
     * @return The inverse matrix
     *
     * @throws InvalidMatrixOperationException if the matrix is not a square matrix
     */
    @Override
    public Matrix inverse() {
        return calculateInverse(Rounding.POINT.TEN);
    }

    /**
     * The method will return an inverse matrix of a given matrix.
     * It returns a null matrix if the inverse is not possible.
     *
     * @param p The efficiency to the given decimal places
     *
     * @return The inverse matrix
     *
     * @throws InvalidMatrixOperationException if the matrix is not a square matrix
     */
    @Override
    public Matrix inverse(final Rounding.POINT p) {
        return calculateInverse(p);
    }

    /**
     * The method will return an inverse matrix of a given matrix.
     * It returns a null matrix if the inverse is not possible.
     *
     * @return The inverse matrix
     *
     * @throws InvalidMatrixOperationException if the matrix is not a square matrix
     */
    public Matrix calculateInverse(final Rounding.POINT p) {
        if (!isSquareMatrix())
            throw new InvalidMatrixOperationException("The matrix is not a square matrix");

        if (d[0] == 2) {
            var n = new Number[2][2];
            double a = e[0][0].doubleValue(), b = e[0][1].doubleValue(),
                    c = e[1][0].doubleValue(), d = e[1][1].doubleValue(),
                    v = (a * d) - (b * c);

            if (v == 0)
                return null;

            n[0][0] = d / v;
            n[0][1] = -b / v;
            n[1][0] = -c / v;
            n[1][1] = a / v;

            return new ArrayMatrix(n);
        } else {
            Number[][][] lu = lu();

            for (var i = 0; i < lu[1].length; i++) {
                if (lu[1][i][i].doubleValue() == 0) {
                    throw new InvalidMatrixOperationException("The matrix is singular");
                }
            }

            return new ArrayMatrix(backwardSubstitution(lu[1], forwardSubstitution(lu[0]), p));
        }
    }

    /**
     * The method performs the backward substitution on a given 2 matrix.
     *
     * @param n the matrix
     * @param z the matrix
     * @param p the accuracy to given decimal points
     *
     * @return the resulting array
     */
    private Number[][] backwardSubstitution(final Number[][] n, final Number[][] z, final Rounding.POINT p) {
        var r = new Number[n.length][n.length];
        for (var i = n.length - 1; i >= 0; i--) {
            for (var j = n.length - 1; j >= 0; j--) {
                if (i == n.length - 1) {
                    r[i][j] = Rounding.round(z[i][j].doubleValue() / n[i][i].doubleValue(), p);
                } else {
                    var sum = 0.0;
                    for (int k = n.length - 1; k > i; k--)
                        sum -= n[i][k].doubleValue() * r[k][j].doubleValue();

                    r[i][j] = Rounding.round((z[i][j].doubleValue() + sum) / n[i][i].doubleValue(), p);
                }
            }
        }

        return r;
    }

    /**
     * The method performs a forward substitution, on a given matrix.
     *
     * @param n the lover triangular matrix
     *
     * @return the resulting substitution
     */
    private Number[][] forwardSubstitution(final Number[][] n) {
        var z = new Number[n.length][n.length];
        for (var i = 0; i < n.length; i++) {
            for (var j = 0; j < n.length; j++) {
                if (j <= i) {
                    if (j == i) {
                        z[i][j] = 1;
                    } else {
                        var sum = 0.0;
                        for (var k = 0; k < i; k++) {
                            sum -= n[i][k].doubleValue() * z[k][j].doubleValue();
                        }
                        z[i][j] = sum;
                    }
                } else {
                    z[i][j] = 0;
                }
            }
        }
        return z;
    }

    /**
     * The method performs a scalar addition on a square matrix.
     * The operation is somewhat be described as;
     * Let's consider a matrix "M", and scalar "a"
     * then M + a = M + a(I), where I is an identity matrix.
     *
     * @param s the scalar to add
     *
     * @return the resulting matrix
     *
     * @throws InvalidMatrixOperationException if the matrix is not a square matrix
     */
    @Override
    public Matrix add(final Number s) {
        if (!isSquareMatrix())
            throw new InvalidMatrixOperationException("Scalar addition is only for square matrices.");

        return new ArrayMatrix(
                addSubArrays(e, calculateDoubleMatrix(d[0], (i, j) -> (i == j) ? s.doubleValue() : 0), true));
    }

    /**
     * The method adds two matrices together.
     * Let's consider matrices "A" and "B";
     * The method performs, A + B operation, and returns the resulting matrix.
     *
     * @param m the matrix to add
     *
     * @return the resulting matrix
     *
     * @throws InvalidMatrixOperationException if two matrices don't have the same dimension
     */
    @Override
    public Matrix add(final Matrix m) {
        if (!Arrays.equals(d, m.getDimension()))
            throw new InvalidMatrixOperationException("Matrices dimensions must be the same.");

        return new ArrayMatrix(addSubArrays(e, m.toArray(), true));
    }

    /**
     * The method subtracts two matrices together.
     * Let's consider matrices "A" and "B";
     * The method performs, A - B operation, and returns the resulting matrix.
     *
     * @param m the matrix to subtract
     *
     * @return the resulting matrix
     *
     * @throws InvalidMatrixOperationException if two matrices don't have the same dimension
     */
    @Override
    public Matrix subtract(final Matrix m) {
        if (!Arrays.equals(d, m.getDimension()))
            throw new InvalidMatrixOperationException("Matrices dimensions must be the same.");

        return new ArrayMatrix(addSubArrays(e, m.toArray(), false));
    }

    /**
     * The method will perform a scalar multiplication on a matrix and returns a new matrix.
     * For example, Let us consider a matrix A, and any scalar c. The scalar multiplication
     * can be defined as;
     * c x A = cA.
     *
     * @param s a scalar to scale the matrix with
     *
     * @return a new scalded matrix
     */
    @Override
    public Matrix multiply(final Number s) {
        var n = new Number[d[0]][d[1]];

        for (var i = 0; i < d[0]; i++)
            for (var j = 0; j < d[1]; j++)
                n[i][j] = e[i][j].doubleValue() * s.doubleValue();

        return new ArrayMatrix(n);
    }

    /**
     * The method will perform a matrix multiplication of a matrix and returns a new Matrix.
     * <p>
     * If the given matrices are A, and B, of respective dimensions m x n and n x p. then
     * number of column of a matrix A has to be equal to the number of rows B. The resulting
     * matrix would be the dimensions of m x p.
     * (A)mxn X (B)nxp = (C)mxp, where # or columns of A and and # of rows of B are equal.
     *
     * @param m the matrix to multiply
     *
     * @return the resulting matrix
     *
     * @throws InvalidMatrixOperationException if two matrices have different columns and rows
     */
    @Override
    public Matrix multiply(final Matrix m) {
        var _e = m.toArray();

        if (d[1] != _e.length)
            throw new InvalidMatrixOperationException("the rows and columns don't match");

        // If both matrix are square matrices and of 2 x 2 dimensions then do Strassen's algorithm
        // Otherwise use the native method.
        Number[][] n;
        if (d[0] == 2 && d[1] == 2 && _e[0].length == 2) {
            n = new Number[2][2];

            double _e1 = e[0][0].doubleValue(),
                    _e2 = e[0][1].doubleValue(),
                    _e3 = e[1][0].doubleValue(),
                    _e4 = e[1][1].doubleValue(),
                    _n1 = _e[0][0].doubleValue(),
                    _n2 = _e[0][1].doubleValue(),
                    _n3 = _e[1][0].doubleValue(),
                    _n4 = _e[1][1].doubleValue();

            double _m1 = (_e1 + _e4) * (_n1 + _n4),
                    _m2 = (_e3 + _e4) * _n1,
                    _m3 = _e1 * (_n2 - _n4),
                    _m4 = _e4 * (_n3 - _n1),
                    _m5 = (_e1 + _e2) * _n4,
                    _m6 = (_e3 - _e1) * (_n1 + _n2),
                    _m7 = (_e2 - _e4) * (_n3 + _n4);

            n[0][0] = _m1 + _m4 - _m5 + _m7;
            n[0][1] = _m3 + _m5;
            n[1][0] = _m2 + _m4;
            n[1][1] = _m1 - _m2 + _m3 + _m6;

        } else {
            n = new Number[d[0]][_e[1].length];
            for (var i = 0; i < d[0]; i++) {
                for (var j = 0; j < _e[1].length; j++) {
                    n[i][j] = 0;
                    for (var k = 0; k < d[1]; k++) {
                        n[i][j] = n[i][j].doubleValue() + (e[i][k].doubleValue() * _e[k][j].doubleValue());
                    }
                }
            }

        }
        return new ArrayMatrix(n);
    }

    /**
     * The method will do addition or subtraction on two two-dimensional arrays.
     *
     * @param n1  the first two-dimensional array
     * @param n2  the second two-dimensional array
     * @param add either addition or subtraction
     *
     * @return the resulting new array
     */
    private Number[][] addSubArrays(final Number[][] n1, final Number[][] n2, final boolean add) {
        var n = new Number[d[0]][d[1]];
        for (var i = 0; i < d[0]; i++)
            for (var j = 0; j < d[1]; j++)
                n[i][j] = (add) ? n1[i][j].doubleValue() + n2[i][j].doubleValue()
                                  : n1[i][j].doubleValue() - n2[i][j].doubleValue();

        return n;
    }

    /**
     * The method perform LU decomposition on a given matrix.
     *
     * @return the LU matrices
     */
    private Number[][][] lu() {
        Number[][][] lu = new Number[2][d[0]][d[0]];
        System.arraycopy(e, 0, lu[1], 0, d[0]);

        for (var i = 0; i < d[0]; i++) {
            if (i == 0)
                for (var j = 1; j < d[1]; j++)
                    lu[0][i][j] = 0;

            lu[0][i][i] = 1;
            for (var j = i + 1; j < d[0]; j++) {
                var factor = e[j][i].doubleValue() / e[i][i].doubleValue();
                for (var k = 0; k < d[1]; k++) {
                    lu[1][j][k] = Rounding.round(e[j][k].doubleValue() - (factor * e[i][k].doubleValue()),
                            Rounding.POINT.TEN);

                    if (k > j)
                        lu[0][j][k] = 0;
                }
                lu[0][j][i] = Rounding.round(factor, Rounding.POINT.TEN);
            }
        }

        return lu;
    }

    /**
     * The method checks if the main diagonal of a given matrix is non-zero.
     * This is very useful to determine if I need to implement LU decomposition or not.
     *
     * @return true if all entries are non-zero
     */
    private boolean isDiagonalNonZero() {
        for (var i = 0; i < d[0]; i++)
            if (e[i][i].doubleValue() == 0)
                return false;

        return true;
    }

    /**
     * The method calculates the rank of the matrix.
     * It uses a couple of methods based on the type of matrix.
     *
     * @return the rank
     */
    private int calculateRank() {

        if (isRowVector() || isColumnVector()) {
            return 1;
        }

        if (isSquareMatrix() && isDiagonalNonZero()) {
            var rank = 0;
            Number[][][] n = lu();
            for (var i = 0; i < n[1].length; i++) {
                if (n[1][i][i].doubleValue() != 0) {
                    ++rank;
                }
            }
            return rank;
        }

        return gaussianRank();
    }

    /**
     * The method implements gaussian algorithm to find out the rakn of a matrix.
     *
     * @return the rank
     */
    private int gaussianRank() {
        int rk = Math.min(d[0], d[1]);
        Number[][] n = new Number[d[0]][d[1]];
        System.arraycopy(e, 0, n, 0, d[0]);

        for (var r = 0; r < rk; r++) {
            if (n[r][r].doubleValue() == 0) {
                for (var i = r + 1; i < rk; i++) {
                    if (n[i][r].doubleValue() != 0) {
                        var t = n[r];
                        n[r] = n[i];
                        n[i] = t;
                    }
                }
            } else if (n[r][r].doubleValue() != 1) {
                for (var c = d[1] - 1; c >= r; c--) {
                    n[r][c] = n[r][c].doubleValue() / n[r][r].doubleValue();
                }
            }

            for (var _r = r + 1; _r < rk; _r++) {
                if (n[_r][r].doubleValue() != 0) {
                    for (var _c = d[1] - 1; _c >= r; _c--) {
                        n[_r][_c] = n[_r][_c].doubleValue()
                                            - (n[_r][r].doubleValue() * n[r][_c].doubleValue());
                    }
                }
            }
        }

        for (var r = 0; r < d[0]; r++) {
            var z = true;

            for (var c = 0; c < d[1]; c++) {
                if (n[r][c].doubleValue() != 0) {
                    z = false;
                    break;
                }
            }

            if (z) rk--;
        }

        return rk == 0 ? 1 : rk;
    }

    /**
     * The method will return a zero or null matrix, whose all the elements are zero.
     *
     * @param m the number of rows
     * @param n the number of columns
     *
     * @return an ArrayMatrix with all 0 entries
     *
     * @throws InvalidMatrixDimensionException when # of row + # of column &lt; 2;
     *                                         this ensures that at least one element
     *                                         exist all the time.
     */
    public static ArrayMatrix zeroMatrix(final int m, final int n) {
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
     * @throws InvalidMatrixDimensionException when m + n &lt; 2
     */
    public static ArrayMatrix oneMatrix(final int m, final int n) {
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

        for (var i = 1; i < e.length; i++)
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
     * @throws InvalidMatrixDimensionException when n &lt; 1, at least one element should exist.
     */
    public static ArrayMatrix pascalMatrix(final int n, final PascalMatrixType t) {
        if (n < 1)
            throw new InvalidMatrixDimensionException("Pascal's matrix should have at least one element");

        var r = new Number[n];
        var e = new Number[n][n];

        if (t == PascalMatrixType.UPPER) {
            for (var i = 0; i < n; i++) {
                for (var j = 0; j < n; j++)
                    e[i][j] = (i == j || i == 0) ? 1 : (j < i) ? 0 : e[i][j - 1].longValue() + r[j - 1].longValue();

                r = e[i];
            }
        }

        if (t == PascalMatrixType.LOWER) {
            for (var i = 0; i < n; i++) {
                for (var j = 0; j < n; j++)
                    e[i][j] = (i == j || j == 0) ? 1 : (i < j) ? 0 : r[j].longValue() + r[j - 1].longValue();

                r = e[i];
            }
        }

        if (t == PascalMatrixType.SYMMETRIC) {
            for (var i = 0; i < n; i++) {
                for (var j = 0; j < n; j++)
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
     * @return the lehmer matrix
     *
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    public static ArrayMatrix lehmerMatrix(final int n) {
        return new ArrayMatrix(
                calculateDoubleMatrix
                        (n, (i, j) -> (j >= i) ? (i + 1) / (j + 1) : (j + 1) / (i + 1)), LEHMER);
    }

    /**
     * A Hilbert matrix is a square matrix with entries being the unit fractions.
     *
     * @param n the number of rows and columns
     *
     * @return a hilbert {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    public static ArrayMatrix hilbertMatrix(final int n) {
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
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    public static ArrayMatrix identityMatrix(final int n) {
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
     * @return an exchange {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    public static ArrayMatrix exchangeMatrix(final int n) {
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
     * @return a redheffer {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    public static ArrayMatrix redhefferMatrix(final int n) {
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
     * @return a shift {@link ArrayMatrix}
     *
     * @throws InvalidMatrixDimensionException if n &lt; 1
     */
    public static ArrayMatrix shiftMatrix(final int n, final ShiftMatrixType t) {
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
        for (var i = 0; i < n; i++)
            for (var j = 0; j < n; j++)
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
        for (var i = 0; i < n; i++)
            for (var j = 0; j < n; j++)
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
            s.append(String.join(",", ls));
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
        int hash = 0;
        for (var n : e) hash += Arrays.hashCode(n);
        return hash;
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

        final Matrix that = (Matrix) obj;
        var o = that.toArray();

        if (!Arrays.equals(this.getDimension(), that.getDimension())) return false;
        for (var j = 0; j < this.e.length; j++)
            for (var k = 0; k < e[j].length; k++)
                if (e[j][k].doubleValue() != o[j][k].doubleValue())
                    return false;

        return true;
    }
}
