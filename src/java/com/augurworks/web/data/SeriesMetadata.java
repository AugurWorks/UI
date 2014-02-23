package com.augurworks.web.data;

import java.util.Map;

import com.augurworks.web.data.raw.RawSeriesMetadata;
import com.google.common.collect.ImmutableMap;

public class SeriesMetadata {
    private Map<String, String> errors;
    private RequestObject request;
    private String unit;
    private String label;
    private boolean valid;

    private SeriesMetadata() {
        // prevents instantiation
    }

    public Map<String, String> getErrors() {
        return ImmutableMap.copyOf(errors);
    }

    public RequestObject getRequest() {
        return request;
    }

    public String getUnit() {
        return unit;
    }

    public String getLabel() {
        return label;
    }

    public boolean isValid() {
        return valid;
    }

    public static SeriesMetadata fromRaw(RawSeriesMetadata rawData) {
        SeriesMetadata seriesMetadata = new SeriesMetadata();
        seriesMetadata.request = RequestObject.fromRaw(rawData.getRequest());
        seriesMetadata.errors = ImmutableMap.copyOf(rawData.getErrors());
        seriesMetadata.unit = rawData.getUnit();
        seriesMetadata.valid = rawData.getValid();
        seriesMetadata.label = rawData.getLabel();
        return seriesMetadata;
    }

    @Override
    public String toString() {
        return "SeriesMetadata [errors=" + errors + ", request=" + request
                + ", unit=" + unit + ", label=" + label + ", valid=" + valid
                + "]";
    }
}
