package org.katpara.mathematica.util;

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
     * Some of the constants used for rounding.
     */
    public enum Decimals {
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
        TEN("0.0000000000"),
        ELEVEN("0.00000000000"),
        TWELVE("0.000000000000");

        private final String value;

        Decimals(final String value) {
            this.value = value;
        }

        private String getValue() {
            return value;
        }
    }

    /**
     * The rounding format object
     */
    private static final DecimalFormat f = new DecimalFormat();

    /**
     * The method rounds a number to default 4 decimal places.
     *
     * @param n the number to be rounded
     *
     * @return the rounded number until 4 decimal places
     */
    public static String round(final Number n) {
        return round(n, Decimals.FOUR);
    }

    /**
     * The method helps to round a number at the given decimal places.
     * The last argument is the rounding mode, which are the same as {@link java.math.RoundingMode}
     *
     * @param n the number to be formatted
     * @param p the rounding configuration
     *
     * @return the rounded point
     */
    public static String round(final Number n, final Decimals p) {
        f.applyPattern(p.getValue());
        f.setRoundingMode(RoundingMode.HALF_UP);
        return f.format(n);
    }

    @Deprecated
    public static Number roundToDouble(final Number n, final Decimals p) {
        f.applyPattern(p.getValue());

        if (p == Decimals.ZERO) {
            return Integer.parseInt(f.format(n));
        } else {
            f.setRoundingMode(RoundingMode.HALF_UP);
            return Double.parseDouble(f.format(n));
        }
    }
}
