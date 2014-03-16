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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dates == null) ? 0 : dates.hashCode());
        result = prime * result
                + ((metadata == null) ? 0 : metadata.hashCode());
        return result;
    }

    @Override
	public String toString() {
		return "RawDataObject [dates=" + dates + ", metadata=" + metadata + "]";
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RawDataObject other = (RawDataObject) obj;
        if (dates == null) {
            if (other.dates != null)
                return false;
        } else if (!dates.equals(other.dates))
            return false;
        if (metadata == null) {
            if (other.metadata != null)
                return false;
        } else if (!metadata.equals(other.metadata))
            return false;
        return true;
    }
}
