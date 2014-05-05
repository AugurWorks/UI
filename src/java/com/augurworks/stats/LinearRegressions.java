package com.augurworks.stats;

import java.util.List;

import org.apache.commons.math3.stat.regression.MillerUpdatingRegression;
import org.apache.commons.math3.stat.regression.RegressionResults;
import org.apache.commons.math3.stat.regression.UpdatingMultipleLinearRegression;
import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

public class LinearRegressions {
    private static final Logger log = Logger.getLogger(LinearRegressions.class);

    public static String getJsonString(UpdatingMultipleLinearRegression reg) {
        log.info("Translating regression to JSON...");
        RegressionResults regress = reg.regress();
        log.info("Regression results completed.");
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("adjusted_r_squared", regress.getAdjustedRSquared());
        jsonObj.addProperty("r_squared", regress.getRSquared());
        List<Double> params = getList(regress.getParameterEstimates());
        jsonObj.addProperty("parameter_estimates", Joiner.on(",").join(params));
        List<Double> errs = getList(regress.getStdErrorOfEstimates());
        jsonObj.addProperty("parameter_std_error", Joiner.on(",").join(errs));
        log.info("Regression converted to JSON.");
        return jsonObj.toString();
    }

    private static List<Double> getList(double[] array) {
        List<Double> list = Lists.newArrayList();
        for (double item : array) {
            list.add(item);
        }
        return list;
    }

    public static UpdatingMultipleLinearRegression getLinearRegression(double[][] x, double[] y) {
        log.info("Getting linear regression...");
        validateDataLengths(x, y);
        normalizeValues(x, y);
        MillerUpdatingRegression regression = new MillerUpdatingRegression(x[0].length, true);
        regression.addObservations(x, y);
        log.info("Regression created.");
        return regression;
    }

    private static void normalizeValues(double[][] x, double[] y) {
        log.info("Normalizing values...");
        normalizeValues(y);
        for (int loc = 0; loc < x.length; loc++) {
            normalizeValues(x[loc]);
        }
        log.info("Values normalized.");
    }

    private static void normalizeValues(double[] ds) {
        if (ds[0] == 0) {
            log.info("Initial value cannot be zero. Setting it to 0.01.");
            ds[0] = 0.01;
        }
        double initial = ds[0];
        for (int i = 0; i < ds.length; i++) {
            // percent increase = (current - starting) / starting
            ds[i] = (ds[i] - initial) / initial;
        }
    }

    private static void validateDataLengths(double[][] x, double[] y) {
        if (x.length < 1) {
            throw new IllegalArgumentException("X must have at least one independent variable.");
        }
        if (x.length != y.length) {
            throw new IllegalArgumentException("X and Y should have the same length. Instead, "
                    + "X had length " + x.length + " and Y had length " + y.length);
        }
    }
}
