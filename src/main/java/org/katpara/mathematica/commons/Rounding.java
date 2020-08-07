package org.katpara.mathematica.commons;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * The class is written to solve Java Rounding problems for the library.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class Rounding {

    /**
     * The rounding format object
     */
    private static final DecimalFormat f = new DecimalFormat();

    /**
     * Some of the constants used for rounding.
     */
    public enum POINT {
        ZERO("0"),
        ONE("0.0"),
        TWO("0.00"),
        THREE("0.000"),
        FOUR("0.0000"),
        FIVE("0.00000"),
        SIX("0.000000"),
        SEVEN("0.0000000"),
        EIGHT("0.00000000"),
        NINE("0.000000000"),
        TEN("0.0000000000");

        private final String value;

        POINT(final String value) {
            this.value = value;
        }

        private String getValue() {
            return value;
        }
    }

    /**
     * The method helps to creat a rounding number until the given decimal point.
     * The default rounding mode for this method is RoundingMode.HALF_UP.
     *
     * @param n the number to be formatted
     * @param p the rounding configuration
     *
     * @return the rounded point
     */
    public static Number round(final Number n, final POINT p) {
        f.applyPattern(p.getValue());

        if(p == POINT.ZERO) {
            return Integer.parseInt(f.format(n));
        } else {
            f.setRoundingMode(RoundingMode.HALF_UP);
            return Double.parseDouble(f.format(n));
        }
    }
}
