package com.augurworks.web.data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.augurworks.web.data.raw.RawAnalysisParam;
import com.augurworks.web.data.raw.RawDataObject;
import com.augurworks.web.data.raw.RawDataTransferObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class DataTransferObject {
    private List<DataObject> seriesObjects;
    private Map<AnalysisParamType, AnalysisParam> analysisParam;

    private DataTransferObject() {
        // prevents instantiation
    }

    public Set<Date> getAllDates() {
        if (seriesObjects.isEmpty()) {
            return Sets.newHashSet();
        }
        return Sets.newHashSet(seriesObjects.get(0).getDates().keySet());
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

    public Double getValueOnDate(String series, Date date) {
        for (DataObject obj : seriesObjects) {
            if (obj.getSeriesName().equalsIgnoreCase(series)) {
                return obj.getValueOnDate(date);
            }
        }
        throw new IllegalArgumentException("Invalid series name given of " + series);
    }

    public static DataTransferObject fromRaw(RawDataTransferObject rawData) {
        DataTransferObject data = new DataTransferObject();
        List<DataObject> series = Lists.newArrayList();
        Map<String, RawDataObject> seriesMap = rawData.getData();
        for (int i = 0; i < seriesMap.keySet().size(); i++) {
            if (seriesMap.containsKey("" + i)) {
                RawDataObject rawDataObject = seriesMap.get("" + i);
                series.add(DataObject.fromRaw(rawDataObject));
            }
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
