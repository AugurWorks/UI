package com.augurworks.test;

import static org.junit.Assert.assertEquals;

import org.apache.commons.math3.stat.regression.RegressionResults;
import org.apache.commons.math3.stat.regression.UpdatingMultipleLinearRegression;
import org.junit.Test;

import com.augurworks.stats.LinearRegressions;

public class LinearRegressionsTest {
    private static double tol = 0.001;

    @Test
    public void testSimpleRegression() {
        UpdatingMultipleLinearRegression regression = linearRegressionOf(bigarrayOf(arrayOf(1), arrayOf(2), arrayOf(3)), arrayOf(3, 6, 9));
        RegressionResults results = regression.regress();
        assertEquals(results.getRSquared(), 1.0, tol);
    }

    @Test
    public void testBadArrayLength() {
        try {
            linearRegressionOf(bigarrayOf(arrayOf(1, 2)), arrayOf(4, 5, 6));
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private double[][] bigarrayOf(double[]...inputs) {
        return inputs;
    }

    private double[] arrayOf(double...inputs) {
        return inputs;
    }

    private UpdatingMultipleLinearRegression linearRegressionOf(double[][] x, double[] y) {
        return LinearRegressions.getLinearRegression(x, y);
    }

}
