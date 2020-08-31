package org.katpara.mathematica.linears.matrices.util;

import org.katpara.mathematica.linears.matrices.Matrix;

public interface Decomposable {

    /**
     * The method decomposes matrices.
     *
     * @param m the matrix to be decomposed
     *
     * @return the decomposed matrices
     */
    double[][][] decompose(final Matrix m);
}
