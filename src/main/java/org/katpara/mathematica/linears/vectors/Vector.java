package org.katpara.mathematica.linears.vectors;

import java.io.Serializable;

/**
 * The Vector interface. The known implementation is ArrayVector.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public interface Vector extends Cloneable, Serializable {
    /**
     * To get dimension the dimension of the vector.
     *
     * @return the dimension
     */
    int getDimension();

    /**
     * To get magnitude of the vector.
     *
     * @return the magnitude
     */
    double getMagnitude();

    /**
     * To get elements of the vector.
     *
     * @return the number array containing elements
     */
    Number[] getElements();

    /**
     * To scale or shrink the vector.
     *
     * @param scalar the scalar
     * @return the scaled vector
     */
    Vector scale(final Number scalar);
}
