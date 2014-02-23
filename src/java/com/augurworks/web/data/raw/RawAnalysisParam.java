package com.augurworks.web.data.raw;

public class RawAnalysisParam {
    private String treeDepth;
    private String cutoff;
    private String nameToPredict;

    private RawAnalysisParam() {
        // only instantiated on deserialization
    }

    public String getTreeDepth() {
        return treeDepth;
    }

    public String getCutoff() {
        return cutoff;
    }

    public String getNameToPredict() {
        return nameToPredict;
    }
}
