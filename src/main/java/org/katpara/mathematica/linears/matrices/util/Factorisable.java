package org.katpara.mathematica.linears.matrices.util;

import org.katpara.mathematica.linears.matrices.Matrix;

public interface Factorisable<T> {
    /**
     * The method resolves factorization.
     *
     * @param m the matrix to resolve
     *
     * @return the resolved object
     */
    T factorize(final Matrix m);
}
