package com.augurworks.web.data.raw;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class RawDataTransferObject {
    private List<RawAnalysisParam> analysis;
    private Map<String, RawDataObject> data;

    private RawDataTransferObject() {
        // only instantiated on deserialization
    }

    public Map<String, RawDataObject> getData() {
        return ImmutableMap.copyOf(data);
    }

    public List<RawAnalysisParam> getAnalysis() {
        return ImmutableList.copyOf(analysis);
    }

    @Override
    public String toString() {
        return "RawDataTransferObject [data=" + data + ", analysis=" + analysis
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((analysis == null) ? 0 : analysis.hashCode());
        result = prime * result + ((data == null) ? 0 : data.hashCode());
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
        RawDataTransferObject other = (RawDataTransferObject) obj;
        if (analysis == null) {
            if (other.analysis != null)
                return false;
        } else if (!analysis.equals(other.analysis))
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        return true;
    }
}
