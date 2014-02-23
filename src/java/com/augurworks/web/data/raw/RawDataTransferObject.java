package com.augurworks.web.data.raw;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class RawDataTransferObject {
    private Map<String, RawDataObject> data;
    private RawAnalysisParam analysis;

    private RawDataTransferObject() {
        // only instantiated on deserialization
    }

    public Map<String, RawDataObject> getRoot() {
        return ImmutableMap.copyOf(data);
    }

    public RawAnalysisParam getAnalysisParam() {
        return analysis;
    }
}
