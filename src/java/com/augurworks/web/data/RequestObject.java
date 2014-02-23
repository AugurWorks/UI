package com.augurworks.web.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.augurworks.web.data.raw.RawRequestObject;

public class RequestObject {
    private String agg;
    private String custom;
    private String dataType;
    private Date endDate;
    private String longName;
    private String name;
    private Date startDate;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    private RequestObject() {
        // prevents instanstiation
    }

    public String getAgg() {
        return agg;
    }

    public String getCustom() {
        return custom;
    }

    public String getDataType() {
        return dataType;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getLongName() {
        return longName;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public static SimpleDateFormat getDateFormat() {
        return DATE_FORMAT;
    }

    @Override
    public String toString() {
        return "RequestObject [agg=" + agg + ", custom=" + custom
                + ", dataType=" + dataType + ", endDate=" + endDate
                + ", longName=" + longName + ", name=" + name + ", startDate="
                + startDate + "]";
    }

    public static RequestObject fromRaw(RawRequestObject rawRequest) {
        RequestObject requestObject = new RequestObject();
        if (rawRequest == null ) {
            return requestObject;
        }
        requestObject.agg = rawRequest.getAgg();
        requestObject.custom = rawRequest.getCustom();
        requestObject.dataType = rawRequest.getDataType();
        requestObject.longName = rawRequest.getLongName();
        requestObject.name = rawRequest.getName();
        try {
            requestObject.endDate = DATE_FORMAT.parse(rawRequest.getEndDate());
            requestObject.startDate = DATE_FORMAT.parse(rawRequest.getStartDate());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Couldn't parse dates. Inputs were " +
                    rawRequest.getStartDate() + " and " + rawRequest.getEndDate());
        }
        return requestObject;
    }
}
