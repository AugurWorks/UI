package com.augurworks.stats;

import java.util.List;

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
        List<Double> params = getList(regress.getParameterEstimates());
        jsonObj.addProperty("parameter_estimates", Joiner.on(",").join(params));
        List<Double> errs = getList(regress.getStdErrorOfEstimates());
        jsonObj.addProperty("parameter_std_error", Joiner.on(",").join(errs));
        return jsonObj.toString();
    }

    private static List<Double> getList(double[] array) {
        List<Double> list = Lists.newArrayList();
        for (double item : array) {
            list.add(item);
        }
        return list;
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
