package com.augurworks.web.data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.augurworks.web.data.raw.RawDataObject;
import com.augurworks.web.data.raw.RawDataTransferObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class DataTransferObject {
    private List<DataObject> seriesObjects;
    private AnalysisParam analysisParam;

    private DataTransferObject() {
        // prevents instantiation
    }

    public Set<Date> getAllDates() {
        if (seriesObjects.isEmpty()) {
            return Sets.newHashSet();
        }
        return Sets.newHashSet(seriesObjects.get(0).getDates().keySet());
    }

    public AnalysisParam getAnalysisParam() {
        return analysisParam;
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

    public static DataTransferObject fromRaw(RawDataTransferObject rawData) {
        DataTransferObject data = new DataTransferObject();
        List<DataObject> series = Lists.newArrayList();
        Map<String, RawDataObject> root = rawData.getRoot();
        for (int i = 0; i < root.keySet().size(); i++) {
            if (root.containsKey("" + i)) {
                RawDataObject rawDataObject = root.get("" + i);
                series.add(DataObject.fromRaw(rawDataObject));
            }
        }
        data.analysisParam = AnalysisParam.fromRaw(rawData.getAnalysisParam());
        return data;
    }

    @Override
    public String toString() {
        return "DataTransferObject [seriesObjects=" + seriesObjects + "]";
    }
}
