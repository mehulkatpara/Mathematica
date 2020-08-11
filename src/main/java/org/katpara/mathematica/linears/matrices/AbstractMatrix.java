package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linears.ColumnOutOfBoundException;
import org.katpara.mathematica.exceptions.linears.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linears.RowOutOfBoundException;
import org.katpara.mathematica.util.Rounding;

import java.util.Arrays;
import java.util.HashMap;

/**
 * The AbstractMatrix class is groups many matrices together,
 * and provides common functionalities.
 *
 * @author Mehul Katpara
 * @see 1.0.0
 */
public abstract class AbstractMatrix implements Matrix {
    /**
     * The field holds matrix data
     */
    protected Number[][] d;

    /**
     * The dimension of the matrix
     */
    protected int[] s;

    /**
     * Requires to chain down the construction
     */
    protected AbstractMatrix() {
    }

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected AbstractMatrix(final Number[][] d) {
        if (d == null || d[0] == null || (d.length + d[0].length) < 2)
            throw new NullArgumentProvidedException();

        for (Number[] row: d)
            for (Number e: row)
                if (e == null)
                    throw new NullArgumentProvidedException();

        this.d = d;
        this.s = new int[]{d.length, d[0].length};
    }

    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    @Override
    public final int[] getSize() {
        return s;
    }

    /**
     * The method returns all the elements of a matrix.
     *
     * @return the matrix elements
     */
    @Override
    public final Number[][] toArray() {
        return d;
    }

    /**
     * The method will return true if the matrix is a
     * row vector, which is 1 x n
     *
     * @return true if it is a row vector
     */
    @Override
    public boolean isRowVector() {
        return s[0] == 1 && s[1] > 1;
    }

    /**
     * The method will return true if the matrix is a
     * column vector, which is n x 1.
     *
     * @return true if it is a column vector
     */
    @Override
    public boolean isColumnVector() {
        return s[0] > 1 && s[1] == 1;
    }

    /**
     * The method will return true if the matrix is a
     * square matrix, which is n x n.
     *
     * @return true if it is a square matrix
     */
    @Override
    public boolean isSquareMatrix() {
        return s[0] == s[1];
    }

    /**
     * The method returns the row elements of a Matrix.
     *
     * @param row the row index
     *
     * @return the matrix elements as a list of vectors
     */
    @Override
    public final Number[] getRow(final int row) {
        if (row < 0 || row > d.length)
            throw new RowOutOfBoundException();

        var n = new Number[s[1]];
        System.arraycopy(d[row], 0, n, 0, s[1]);
        return n;
    }

    /**
     * The method returns the column elements of a Matrix.
     *
     * @param column the column index
     *
     * @return the matrix elements as a list of vectors
     */
    @Override
    public final Number[] getColumn(final int column) {
        if (column < 0 || column > d[0].length)
            throw new ColumnOutOfBoundException();

        var n = new Number[s[0]];
        for (int i = 0; i < s[0]; i++) {
            n[i] = d[i][column];
        }

        return n;
    }

    /**
     * The method transposes the data of the matrix.
     *
     * @return transposed array data
     */
    protected final Number[][] doTranspose() {
        var n = new Number[s[1]][s[0]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[j][i] = d[i][j];
            }
        }

        return n;
    }

    /**
     * The method adds a scaar value to all the elements of a matrix.
     *
     * @param scalar the scalar to add
     *
     * @return the added scalar array
     */
    protected final Number[][] doAdd(final double scalar) {
        var n = new Number[s[0]][s[1]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = d[i][j].doubleValue() + scalar;
            }
        }
        return n;
    }

    /**
     * The method adds two matrix arrays.
     *
     * @param m the matrix to add
     *
     * @return the added arrays
     *
     * @throws MatrixDimensionMismatchException when two matrix have different dimensions
     */
    @Deprecated
    protected final Number[][] doAdd(final Matrix m) {
        if (!Arrays.equals(getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        Number[][] n = new Number[s[0]][s[1]], _d = m.toArray();
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = d[i][j].doubleValue() + _d[i][j].doubleValue();
            }
        }

        return n;
    }

    /**
     * The subtraction of two matrix arrays.
     *
     * @param m the element
     *
     * @return the subtracted array
     *
     * @throws MatrixDimensionMismatchException when two matrix have different dimensions
     */
    @Deprecated
    protected final Number[][] doSubtract(final Matrix m) {
        if (!Arrays.equals(getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        Number[][] n = new Number[s[0]][s[1]], _d = m.toArray();
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = d[i][j].doubleValue() - _d[i][j].doubleValue();
            }
        }


        return n;
    }

    /**
     * The method multiplies the scalar with matrix elements.
     *
     * @param scalar the scalar to multiply
     *
     * @return the multiplied array
     */
    protected final Number[][] doMultiply(final double scalar) {
        var n = new Number[s[0]][s[1]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = d[i][j].doubleValue() * scalar;
            }
        }

        return n;
    }

    /**
     * The method multiplies two matrices and returns the resulting array.
     *
     * @param m the matrix to multiply
     *
     * @return the multiplied array
     */
    @Deprecated
    protected final Number[][] doMultiply(final Matrix m) {
        var _s = m.getSize();

        if (s[1] != _s[0])
            throw new MatrixDimensionMismatchException();

        Number[][] n = new Number[s[0]][_s[1]], _d = m.toArray();
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < _s[1]; j++) {
                n[i][j] = d[i][j].doubleValue() * _d[j][i].doubleValue();
            }
        }

        return n;
    }

    /**
     * The subtraction of two matrix arrays.
     *
     * @param _d the elements
     *
     * @return the subtracted array
     *
     * @throws MatrixDimensionMismatchException when two matrix have different dimensions
     */
    protected final Number[][] doSubtract(final Number[][] _d) {
        Number[][] n = new Number[s[0]][s[1]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = d[i][j].doubleValue() - _d[i][j].doubleValue();
            }
        }


        return n;
    }

    /**
     * The method adds two matrix arrays.
     *
     * @param _d the matrix array elements
     *
     * @return the added arrays
     *
     * @throws MatrixDimensionMismatchException when two matrix have different dimensions
     */
    protected final Number[][] doAdd(final Number[][] _d) {
        Number[][] n = new Number[s[0]][s[1]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = d[i][j].doubleValue() + _d[i][j].doubleValue();
            }
        }

        return n;
    }

    /**
     * The method multiplies two matrices and returns the resulting array.
     *
     * @param _d the two dimensional array
     *
     * @return the multiplied array
     */
    protected final Number[][] doMultiply(final Number[][] _d) {
        Number[][] n = new Number[s[0]][_d[0].length];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < _d[0].length; j++) {
                n[i][j] = d[i][j].doubleValue() * _d[j][i].doubleValue();
            }
        }

        return n;
    }

    protected final Number[][] doAdditiveInverse() {
        var n = new Number[s[0]][s[1]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = -d[i][j].doubleValue();
            }
        }

        return n;
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
    public final String toString() {
        return this.toString(Rounding.Decimals.FOUR);
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
    public final String toString(final Rounding.Decimals decimals) {
        StringBuilder s = new StringBuilder();
        for (Number[] row: d) {
            s.append("|");
            for (Number col: row) {
                s.append(Rounding.round(col, decimals));
                s.append(" ");
            }

            s.append("|\n");
        }
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
    public final int hashCode() {
        int hash = 0;
        for (var n: d) hash += Arrays.hashCode(n);
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
    @Override
    public final boolean equals(final Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;
        if (this.hashCode() == obj.hashCode()) return true;

        final Matrix that = (Matrix) obj;
        var o = that.toArray();

        if (!Arrays.equals(this.getSize(), that.getSize())) return false;
        for (var j = 0; j < this.d.length; j++)
            for (var k = 0; k < d[j].length; k++)
                if (d[j][k].doubleValue() != o[j][k].doubleValue())
                    return false;

        return true;
    }
}
