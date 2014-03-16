package com.augurworks.web.data.raw;

import com.augurworks.web.data.AnalysisParamType;

public class RawDtreeAnalysisParam extends RawAnalysisParam {
    private String treeDepth;
    private String cutoff;
    private String nameToPredict;

    private RawDtreeAnalysisParam() {
        super();
        // only instantiated on deserialization
    }

    public RawDtreeAnalysisParam(String depth, String cutoff, String nameToPredict) {
        this.cutoff = cutoff;
        this.treeDepth = depth;
        this.nameToPredict = nameToPredict;
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

    @Override
    public String toString() {
        return "RawDtreeAnalysisParam [treeDepth=" + treeDepth + ", cutoff="
                + cutoff + ", nameToPredict=" + nameToPredict + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cutoff == null) ? 0 : cutoff.hashCode());
        result = prime * result
                + ((nameToPredict == null) ? 0 : nameToPredict.hashCode());
        result = prime * result
                + ((treeDepth == null) ? 0 : treeDepth.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RawDtreeAnalysisParam other = (RawDtreeAnalysisParam) obj;
        if (cutoff == null) {
            if (other.cutoff != null)
                return false;
        } else if (!cutoff.equals(other.cutoff))
            return false;
        if (nameToPredict == null) {
            if (other.nameToPredict != null)
                return false;
        } else if (!nameToPredict.equals(other.nameToPredict))
            return false;
        if (treeDepth == null) {
            if (other.treeDepth != null)
                return false;
        } else if (!treeDepth.equals(other.treeDepth))
            return false;
        return true;
    }

    @Override
    public AnalysisParamType getType() {
        return AnalysisParamType.DTREE;
    }
}
