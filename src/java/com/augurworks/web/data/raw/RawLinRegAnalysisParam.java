package com.augurworks.web.data.raw;

import com.augurworks.web.data.AnalysisParamType;

public class RawLinRegAnalysisParam extends RawAnalysisParam {
    private String dependent;
    private String independent;

    @Override
    public AnalysisParamType getType() {
        return AnalysisParamType.LINREG;
    }

    public RawLinRegAnalysisParam(String dependent, String independent) {
        this.dependent = dependent;
        this.independent = independent;
    }

    public String getDependent() {
        return dependent;
    }

    public String getIndependent() {
        return independent;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((dependent == null) ? 0 : dependent.hashCode());
        result = prime * result
                + ((independent == null) ? 0 : independent.hashCode());
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
        RawLinRegAnalysisParam other = (RawLinRegAnalysisParam) obj;
        if (dependent == null) {
            if (other.dependent != null)
                return false;
        } else if (!dependent.equals(other.dependent))
            return false;
        if (independent == null) {
            if (other.independent != null)
                return false;
        } else if (!independent.equals(other.independent))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RawLinRegAnalysisParam [dependent=" + dependent
                + ", independent=" + independent + "]";
    }

}
