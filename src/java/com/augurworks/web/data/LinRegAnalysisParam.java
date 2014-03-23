package com.augurworks.web.data;

import java.util.List;

import com.augurworks.web.data.raw.RawLinRegAnalysisParam;
import com.google.common.collect.Lists;

public class LinRegAnalysisParam implements AnalysisParam  {
    private List<String> independent = Lists.newArrayList();
    private String dependent;

    private LinRegAnalysisParam() {
        //prevents instantiation
    }

    public static LinRegAnalysisParam fromRaw(RawLinRegAnalysisParam rawParam) {
        LinRegAnalysisParam param = new LinRegAnalysisParam();
        param.dependent = rawParam.getDependent();
        String[] indStrings = rawParam.getIndependent().split(",");
        for (String ind : indStrings) {
            param.independent.add(ind.trim());
        }
        return param;
    }

    public List<String> getIndependent() {
        return independent;
    }

    public String getDependent() {
        return dependent;
    }

    @Override
    public String toString() {
        return "LinRegAnalysisParam [dependent=" + dependent + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((dependent == null) ? 0 : dependent.hashCode());
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
        LinRegAnalysisParam other = (LinRegAnalysisParam) obj;
        if (dependent == null) {
            if (other.dependent != null)
                return false;
        } else if (!dependent.equals(other.dependent))
            return false;
        return true;
    }

    @Override
    public AnalysisParamType getType() {
        return AnalysisParamType.LINREG;
    }

}
