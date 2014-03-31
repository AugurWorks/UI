package com.augurworks.web.data;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.augurworks.web.data.raw.RawAnalysisParam;
import com.augurworks.web.data.raw.RawDataObject;
import com.augurworks.web.data.raw.RawDataTransferObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataTransferObject {
    private List<DataObject> seriesObjects;
    private Map<AnalysisParamType, AnalysisParam> analysisParam;

    private DataTransferObject() {
        // prevents instantiation
    }

    public List<Date> getAllDates() {
        if (seriesObjects.isEmpty()) {
            return Lists.newArrayList();
        }
        return seriesObjects.get(0).getAllDatesInOrder();
    }

    public List<Date> getAllFullyRepresentedDates() {
        List<Date> allDates = getAllDates();
        Iterator<Date> dateIterator = allDates.iterator();
        while (dateIterator.hasNext()) {
            Date d = dateIterator.next();
            for (DataObject series : seriesObjects) {
                if (!series.hasValueOnDate(d)) {
                    dateIterator.remove();
                    break;
                }
            }
        }
        return allDates;
    }

    public Map<AnalysisParamType, AnalysisParam> getAnalysis() {
        return ImmutableMap.copyOf(analysisParam);
    }

    public List<DataObject> getSeriesObjects() {
        return Lists.newArrayList(seriesObjects);
    }

    public Map<String, Double> getValuesOnDate(Date d) {
        Map<String, Double> dateValues = Maps.newHashMap();
        for (DataObject obj : seriesObjects) {
            dateValues.put(obj.getSeriesName(), obj.getValueOnDate(d));
        }
        return dateValues;
    }

    public Double[] getAllValuesFor(String series) {
        DataObject seriesObject = getSeriesObject(series);
        return seriesObject.getValuesInOrder();
    }

    public List<String> getAllSeriesNames() {
        List<String> names = Lists.newArrayList();
        for (DataObject obj : seriesObjects) {
            names.add(obj.getSeriesName());
        }
        return names;
    }

    public DataObject getSeriesObject(String series) {
        for (DataObject obj : seriesObjects) {
            if (obj.getSeriesName().equalsIgnoreCase(series)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Invalid series name given of " + series +
                ". Valid options are " + getAllSeriesNames());
    }

    public Double getValueOnDate(String series, Date date) {
        return getSeriesObject(series).getValueOnDate(date);
    }

    public static DataTransferObject fromRaw(RawDataTransferObject rawData) {
        DataTransferObject data = new DataTransferObject();
        List<DataObject> series = Lists.newArrayList();
        Map<String, RawDataObject> seriesMap = rawData.getData();
        int i = 0;
        while (series.size() < seriesMap.size()) {
            if (seriesMap.containsKey("" + i)) {
                RawDataObject rawDataObject = seriesMap.get("" + i);
                series.add(DataObject.fromRaw(rawDataObject));
            }
            i++;
        }
        data.seriesObjects = series;
        Map<AnalysisParamType, AnalysisParam> analysis = Maps.newHashMap();
        for (RawAnalysisParam param : rawData.getAnalysis()) {
            AnalysisParamType type = param.getType();
            analysis.put(type, type.fromRaw(param));
        }
        data.analysisParam = analysis;
        return data;
    }

    @Override
    public String toString() {
        return "DataTransferObject [seriesObjects=" + seriesObjects + "]";
    }
}
