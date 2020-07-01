package org.katpara.mathematica.linears.vectors;

import java.io.Serializable;

public interface Vector extends Cloneable, Serializable {
    int getDimension();

    double getMagnitude();
}
