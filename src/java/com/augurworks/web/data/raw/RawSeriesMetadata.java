package com.augurworks.web.data.raw;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class RawSeriesMetadata {
    private Map<String, String> errors;
    private RawRequestObject request;
    private String label;
    private String unit;
    private boolean valid;

    private RawSeriesMetadata() {
        // only instantiated on deserialization
    }

    public RawRequestObject getRequest() {
        return request;
    }

    public Map<String, String> getErrors() {
        return ImmutableMap.copyOf(errors);
    }

    public String getUnit() {
        return unit;
    }

    public boolean getValid() {
        return valid;
    }

    public String getLabel() {
        return label;
    }
}
