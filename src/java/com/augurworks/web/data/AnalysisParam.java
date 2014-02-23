package com.augurworks.web.data;

import com.augurworks.web.data.raw.RawAnalysisParam;

public class AnalysisParam {
    private int treeDepth;
    private double cutoff;
    private String nameToPredict;

    private AnalysisParam() {
        // prevents instantiation
    }

    public int getTreeDepth() {
        return treeDepth;
    }

    public double getCutoff() {
        return cutoff;
    }

    public String getNameToPredict() {
        return nameToPredict;
    }

    public static AnalysisParam fromRaw(RawAnalysisParam rawParam) {
        AnalysisParam param = new AnalysisParam();
        param.nameToPredict = rawParam.getNameToPredict();
        try {
            param.treeDepth = Integer.parseInt(rawParam.getTreeDepth());
            param.cutoff = Double.parseDouble(rawParam.getCutoff());
        } catch (NumberFormatException n) {
            throw new IllegalArgumentException("Could not parse AnalysisParam. TreeDepth was " +
                    rawParam.getTreeDepth() + " and cutoff was " + rawParam.getCutoff());
        }
        return param;
    }
}
