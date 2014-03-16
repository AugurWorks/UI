package com.augurworks.web.data;

import com.augurworks.web.data.raw.RawDtreeAnalysisParam;

public class DtreeAnalysisParam implements AnalysisParam {
    private int treeDepth;
    private double cutoff;
    private String nameToPredict;

    private DtreeAnalysisParam() {
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

    public static DtreeAnalysisParam from(int depth, double cutoff, String name) {
        DtreeAnalysisParam param = new DtreeAnalysisParam();
        param.treeDepth = depth;
        param.cutoff = cutoff;
        param.nameToPredict = name;
        return param;
    }

    public static DtreeAnalysisParam fromRaw(RawDtreeAnalysisParam rawParam) {
        DtreeAnalysisParam param = new DtreeAnalysisParam();
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

    @Override
    public AnalysisParamType getType() {
        return AnalysisParamType.DTREE;
    }
}
