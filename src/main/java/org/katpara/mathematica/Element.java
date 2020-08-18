package org.katpara.mathematica;

import org.katpara.mathematica.util.Rounding;

/**
 * The interface defines elements of a system.
 * It highlights the basic operation that an element can have.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public interface Element<T> {

    /**
     * The scalar addition of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    T add(final double scalar);

    /**
     * The addition of two elements.
     *
     * @param e the element
     *
     * @return the element
     */
    T add(final T e);

    /**
     * The subtraction of two elements.
     *
     * @param e the element
     *
     * @return the element
     */
    T subtract(final T e);

    /**
     * The scalar multiplication of the element.
     *
     * @param scalar the scalar
     *
     * @return the element
     */
    T multiply(final double scalar);

    /**
     * The multiplication of two elements.
     *
     * @param e the element
     *
     * @return the element
     */
    T multiply(final T e);

    /**
     * The division of two elements.
     *
     * @param e the element
     *
     * @return the element
     */
    T divide(final T e);

    /**
     * The multiplicative inverse of the elements.
     *
     * @return the element
     */
    T multiplicativeInverse();

    /**
     * The additive inverse of the elements.
     *
     * @return the element
     */
    T additiveInverse();

    /**
     * The power of an element.
     *
     * @param power the exponent
     *
     * @return the value after applying power
     */
    T power(final int power);

    /**
     * The absolute value of an element.
     *
     * @return the absolute value
     */
    double abs();

    /**
     * The absolute value of an element.
     *
     * @param decimals rounding to given decimal places
     *
     * @return the absolute value
     */
    double abs(final Rounding.Decimals decimals);

    /**
     * The element should be able to print at given decimal places.
     *
     * @param decimals the decimal places
     *
     * @return the string to print
     */
    String toString(final Rounding.Decimals decimals);
}
