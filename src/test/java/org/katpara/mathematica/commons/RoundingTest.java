package org.katpara.mathematica.commons;

import org.junit.jupiter.api.Test;
import org.katpara.mathematica.util.Rounding;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundingTest {

    @Test
    void testRounding() {
        assertAll(
                () -> assertEquals("1", Rounding.round(1.0, Rounding.Decimals.ZERO)),
                () -> assertEquals("1.2000", Rounding.round(1.2)),
                () -> assertEquals("1.29889", Rounding.round(1.298893, Rounding.Decimals.FIVE))
        );
    }
}