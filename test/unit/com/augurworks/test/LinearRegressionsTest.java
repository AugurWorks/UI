package com.augurworks.test;

import static org.junit.Assert.assertEquals;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.junit.Test;

import com.augurworks.stats.LinearRegressions;

public class LinearRegressionsTest {
    private static double tol = 0.001;

    @Test
    public void testSimpleRegression() {
        SimpleRegression reg = linearRegressionOf(arrayOf(1, 2, 3), arrayOf(3, 6, 9));
        assertEquals(reg.getRSquare(), 1.0, tol);
        assertEquals(reg.getSlope(), 3.0, tol);
        assertEquals(reg.getIntercept(), 0.0, tol);
    }

    @Test
    public void testNegativeSlope() {
        SimpleRegression reg = linearRegressionOf(arrayOf(1, 2, 3), arrayOf(9, 6, 3));
        assertEquals(reg.getRSquare(), 1.0, tol);
        assertEquals(reg.getSlope(), -3.0, tol);
        assertEquals(reg.getIntercept(), 12.0, tol);
    }

    @Test
    public void testBadArrayLength() {
        try {
            linearRegressionOf(arrayOf(1, 2), arrayOf(4, 5, 6));
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private double[] arrayOf(double...inputs) {
        return inputs;
    }

    private SimpleRegression linearRegressionOf(double[] x, double[] y) {
        return LinearRegressions.getLinearRegression(x, y);
    }

}
