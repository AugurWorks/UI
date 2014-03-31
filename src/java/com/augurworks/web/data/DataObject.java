package com.augurworks.web.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.augurworks.web.data.raw.RawDataObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataObject {
    private Map<Date, Double> dates;
    private SeriesMetadata metadata;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private DataObject() {
        // prevents instantiation
    }

    public String getSeriesName() {
        return metadata.getRequest().getName();
    }

    public Map<Date, Double> getDates() {
        return ImmutableMap.copyOf(dates);
    }

    public Double getValueOnDate(Date d) {
        return dates.get(d);
    }

    public boolean hasValueOnDate(Date d) {
        return dates.get(d) != null;
    }

    public List<Date> getAllDatesInOrder() {
        List<Date> entries = Lists.newArrayList(dates.keySet());
        Collections.sort(entries, new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                if (o1.before(o2)) {
                    return -1;
                } else if (o1.after(o2)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return entries;
    }

    public Double[] getValuesInOrder() {
        List<Date> entries = getAllDatesInOrder();
        Double[] values = new Double[entries.size()];
        for (int i = 0; i < entries.size(); i++) {
            values[i] = dates.get(entries.get(i));
        }
        return values;
    }

    @Override
    public String toString() {
        return "DataObject [dates=" + dates + ", metadata=" + metadata + "]";
    }

    public static DataObject fromRaw(RawDataObject rawDataObject) {
        DataObject dataObject = new DataObject();
        Map<Date, Double> dateMap = Maps.newHashMap();
        for (Map.Entry<String, String> entry : rawDataObject.getDates().entrySet()) {
            try {
                dateMap.put(DATE_FORMAT.parse(entry.getKey()), Double.parseDouble(entry.getValue()));
            } catch (NumberFormatException n) {
                throw new IllegalArgumentException("Failed to parse double from " + entry.getValue());
            } catch (ParseException p) {
                throw new IllegalArgumentException("Failed to parse date from " + entry.getKey());
            }
        }
        dataObject.dates = dateMap;
        dataObject.metadata = SeriesMetadata.fromRaw(rawDataObject.getMetadata());
        return dataObject;
    }
}
