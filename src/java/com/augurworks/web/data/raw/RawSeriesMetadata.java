package com.augurworks.web.data.raw;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class RawSeriesMetadata {
    private Map<String, String> errors;
    private RawRequestObject req;
    private String label;
    private String unit;
    private boolean valid;

    private RawSeriesMetadata() {
        // only instantiated on deserialization
    }

    public RawRequestObject getRequest() {
        return req;
    }

    public Map<String, String> getErrors() {
        if (errors == null) {
            return ImmutableMap.of();
        } else {
            return ImmutableMap.copyOf(errors);
        }
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

    @Override
    public String toString() {
        return "RawSeriesMetadata [errors=" + errors + ", request=" + req
                + ", label=" + label + ", unit=" + unit + ", valid=" + valid
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errors == null) ? 0 : errors.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((req == null) ? 0 : req.hashCode());
        result = prime * result + ((unit == null) ? 0 : unit.hashCode());
        result = prime * result + (valid ? 1231 : 1237);
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
        RawSeriesMetadata other = (RawSeriesMetadata) obj;
        if (errors == null) {
            if (other.errors != null)
                return false;
        } else if (!errors.equals(other.errors))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        if (req == null) {
            if (other.req != null)
                return false;
        } else if (!req.equals(other.req))
            return false;
        if (unit == null) {
            if (other.unit != null)
                return false;
        } else if (!unit.equals(other.unit))
            return false;
        if (valid != other.valid)
            return false;
        return true;
    }
}
