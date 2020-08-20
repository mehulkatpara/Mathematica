package org.katpara.mathematica.linears.matrices;

import org.katpara.mathematica.exceptions.NotInvertibleException;
import org.katpara.mathematica.exceptions.NullArgumentProvidedException;
import org.katpara.mathematica.exceptions.linear.ColumnOutOfBoundException;
import org.katpara.mathematica.exceptions.linear.MatrixDimensionMismatchException;
import org.katpara.mathematica.exceptions.linear.NotSquareMatrixException;
import org.katpara.mathematica.exceptions.linear.RowOutOfBoundException;
import org.katpara.mathematica.linears.matrices.constants.IdentityMatrix;
import org.katpara.mathematica.linears.matrices.constants.NullMatrix;
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
    private static final long serialVersionUID = 75155210358302303L;

    /**
     * The field holds matrix data
     */
    protected final double[][] d;

    /**
     * The dimension of the matrix
     */
    protected final int[] s;

    /**
     * The general constructor to build a matrix in the system.
     *
     * @param d the matrix elements.
     */
    protected AbstractMatrix(final double[][] d) {
        if (d == null || d[0] == null || (d.length + d[0].length) < 2)
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
    public final double[][] toArray() {
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
    public final double[] getRow(final int row) {
        if (row < 0 || row > d.length)
            throw new RowOutOfBoundException();

        var n = new double[s[1]];
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
    public final double[] getColumn(final int column) {
        if (column < 0 || column > d[0].length)
            throw new ColumnOutOfBoundException();

        var n = new double[s[0]];
        for (int i = 0; i < s[0]; i++) {
            n[i] = d[i][column];
        }

        return n;
    }

    /**
     * The scalar addition of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    @Override
    public final Matrix add(final double scalar) {
        if (scalar == 0)
            return this;

        return this.addScalar(scalar);
    }

    /**
     * The addition of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public final Matrix add(final Matrix m) {
        if (!Arrays.equals(getSize(), m.getSize()))
            throw new MatrixDimensionMismatchException();

        if (m instanceof NullMatrix)
            return this;

        return this.addMatrix(m);
    }

    /**
     * The subtraction of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public final Matrix subtract(final Matrix m) {
        if (this == m)
            return NullMatrix.getInstance(s[0], s[1]);

        return this.add(m.additiveInverse());
    }

    /**
     * The scalar multiplication of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    @Override
    public final Matrix multiply(final double scalar) {
        if (scalar == 0)
            return NullMatrix.getInstance(s[0]);
        if (scalar == 1)
            return this;
        if (scalar == -1)
            return this.additiveInverse();

        return this.multiplyScalar(scalar);
    }

    /**
     * The multiplication of two elements.
     *
     * @param m the element
     *
     * @return the element
     *
     * @throws MatrixDimensionMismatchException if the number of columns of the first matrix,
     *                                          doesn't match up with the number of rows of
     *                                          the second matrix
     */
    @Override
    public final Matrix multiply(final Matrix m) {
        var _s = m.getSize();

        if (s[1] != _s[0])
            throw new MatrixDimensionMismatchException();

        if (m instanceof IdentityMatrix)
            return this;

        if (m instanceof NullMatrix)
            return NullMatrix.getInstance(s[0], _s[1]);

        return this.multiplyMatrix(m);
    }

    /**
     * The division of two elements.
     *
     * @param m the element
     *
     * @return the element
     */
    @Override
    public final Matrix divide(final Matrix m) {
        if(!m.isSquareMatrix())
            throw new NotInvertibleException();

        if(this == m)
            return IdentityMatrix.getInstance(s[0]);

        return this.multiply(m.multiplicativeInverse());
    }

    /**
     * The method adds a scalar.
     *
     * @param scalar the scalar to add.
     *
     * @return The square matrix
     */
    protected abstract Matrix addScalar(final double scalar);

    /**
     * The method adds two matrices.
     *
     * @param m the matrix to add
     *
     * @return the resulting matrix
     */
    protected abstract Matrix addMatrix(final Matrix m);

    /**
     * The method multiplies a scalar.
     *
     * @param scalar the scalar to multiply
     *
     * @return the resulting matrix
     */
    protected abstract Matrix multiplyScalar(final double scalar);

    /**
     * The method multiplies two matrices.
     *
     * @param m the matrix to multiply with
     *
     * @return the resulting matrix
     */
    protected abstract Matrix multiplyMatrix(final Matrix m);

    /**
     * The method transposes the data of the matrix.
     *
     * @return transposed array data
     */
    protected final double[][] doTranspose() {
        var n = new double[s[1]][s[0]];
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
    protected final double[][] doAdd(final double scalar) {
        var n = new double[s[0]][s[1]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = d[i][j] + scalar;
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
    protected final double[][] doMultiply(final double scalar) {
        var n = new double[s[0]][s[1]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = d[i][j] * scalar;
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
    protected final double[][] doAdd(final double[][] _d) {
        double[][] n = new double[s[0]][s[1]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = d[i][j] + _d[i][j];
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
    protected final double[][] doMultiply(final double[][] _d) {
        double[][] n = new double[s[0]][_d[0].length];
        for (var i = 0; i < s[0]; i++) {
            for (var j = 0; j < _d[0].length; j++) {
                n[i][j] = 0;
                for (var k = 0; k < s[1]; k++) {
                    n[i][j] = n[i][j] + (d[i][k] * _d[k][j]);
                }
            }
        }

        return n;
    }

    protected final double[][] doAdditiveInverse() {
        var n = new double[s[0]][s[1]];
        for (int i = 0; i < s[0]; i++) {
            for (int j = 0; j < s[1]; j++) {
                n[i][j] = -d[i][j];
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
        for (double[] row: d) {
            s.append("|");
            for (double col: row) {
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
                if (d[j][k] != o[j][k])
                    return false;

        return true;
    }
}
