package com.augurworks.web.data;

import java.util.List;

import com.augurworks.web.data.raw.RawDataTransferObject;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class DataTransferObjects {
    public static DataTransferObject fromJsonString(String jsonString) {
        JsonElement json = new JsonParser().parse(jsonString);
        RawDataTransferObject dto = new Gson().fromJson(json, RawDataTransferObject.class);
        return DataTransferObject.fromRaw(dto);
    }

    public static List<String> getTitlesFromData(DataTransferObject data) {
        List<String> titles = Lists.newArrayList();
        for (DataObject obj : data.getSeriesObjects()) {
            titles.add(obj.getSeriesName());
        }
        return titles;
    }
}
