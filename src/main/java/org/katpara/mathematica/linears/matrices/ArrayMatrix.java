package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.exceptions.InvalidMatrixDimension;
import org.katpara.mathematica.exceptions.NullArgumentProvided;

import java.util.Arrays;
import java.util.RandomAccess;

public class ArrayMatrix implements Matrix,
        RandomAccess, Cloneable, java.io.Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 8141142525669921693L;

    private final Number[][] e;

    public ArrayMatrix(final Number[][] e) {
        if (e.length < 1 ||
                (e.length == 1 && e[0].length < 2) ||
                (e.length == 2 && e[0].length < 1))
            throw new InvalidMatrixDimension();

        Arrays.stream(e).forEach(i ->
                Arrays.stream(i).forEach(ie -> {
                    if (ie == null) throw new NullArgumentProvided();
                }));
        
        this.e = e;
    }

    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    @Override
    public int[] getDimension() {
        return new int[0];
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
}
