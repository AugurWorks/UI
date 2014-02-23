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
}
