package com.augurworks.stats;

import java.util.ArrayList;

import org.apache.commons.math3.stat.regression.RegressionResults;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

public class LinearRegressions {

    public static String getJsonString(SimpleRegression reg) {
        RegressionResults regress = reg.regress();
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("adjusted_r_squared", regress.getAdjustedRSquared());
        jsonObj.addProperty("r_squared", regress.getRSquared());
        ArrayList<double[]> params = Lists.newArrayList(regress.getParameterEstimates());
        jsonObj.addProperty("paremeter_estimates", Joiner.on(",").join(params));
        ArrayList<double[]> errs = Lists.newArrayList(regress.getStdErrorOfEstimates());
        jsonObj.addProperty("paremeter_estimates", Joiner.on(",").join(errs));
        return jsonObj.toString();
    }

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
