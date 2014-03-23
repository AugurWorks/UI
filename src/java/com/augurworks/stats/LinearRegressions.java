package com.augurworks.stats;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class LinearRegressions {

    public static SimpleRegression getLinearRegression(double[][] x, double[] y) {
        validateDataLengths(x, y);
        SimpleRegression reg = new SimpleRegression();
        reg.addObservations(x, y);
        return reg;
    }

    private static void validateDataLengths(double[][] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("X and Y should have the same length. Instead, "
                    + "X had length " + x.length + " and Y had length " + y.length);
        }
    }
}
