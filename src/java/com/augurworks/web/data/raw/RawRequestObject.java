package com.augurworks.web.data.raw;

public class RawRequestObject {
    private String agg;
    private String custom;
    private String page;
    private String dataType;
    private String endDate;
    private String longName;
    private String name;
    private String startDate;

    private RawRequestObject() {
        // only instantiated on deserialization
    }

    public String getAgg() {
        return agg;
    }

    public String getCustom() {
        return custom;
    }

    public String getPage() {
        return page;
    }

    public String getDataType() {
        return dataType;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getLongName() {
        return longName;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "RawRequestObject [agg=" + agg + ", custom=" + custom
                + ", page=" + page + ", dataType=" + dataType + ", endDate="
                + endDate + ", longName=" + longName + ", name=" + name
                + ", startDate=" + startDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((agg == null) ? 0 : agg.hashCode());
        result = prime * result + ((custom == null) ? 0 : custom.hashCode());
        result = prime * result
                + ((dataType == null) ? 0 : dataType.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result
                + ((longName == null) ? 0 : longName.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        result = prime * result
                + ((startDate == null) ? 0 : startDate.hashCode());
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
        RawRequestObject other = (RawRequestObject) obj;
        if (agg == null) {
            if (other.agg != null)
                return false;
        } else if (!agg.equals(other.agg))
            return false;
        if (custom == null) {
            if (other.custom != null)
                return false;
        } else if (!custom.equals(other.custom))
            return false;
        if (dataType == null) {
            if (other.dataType != null)
                return false;
        } else if (!dataType.equals(other.dataType))
            return false;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (longName == null) {
            if (other.longName != null)
                return false;
        } else if (!longName.equals(other.longName))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (page == null) {
            if (other.page != null)
                return false;
        } else if (!page.equals(other.page))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }
}
