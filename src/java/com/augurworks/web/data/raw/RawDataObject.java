package com.augurworks.web.data.raw;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class RawDataObject {
    private Map<String, String> dates;
    private RawSeriesMetadata metadata;

    private RawDataObject() {
        // only instantiated on deserialization
    }

    public Map<String, String> getDates() {
        return ImmutableMap.copyOf(dates);
    }

    public RawSeriesMetadata getMetadata() {
        return metadata;
    }
}
