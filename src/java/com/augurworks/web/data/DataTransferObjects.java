package com.augurworks.web.data;

import java.util.List;

import org.apache.log4j.Logger;

import com.augurworks.web.data.raw.RawAnalysisParam;
import com.augurworks.web.data.raw.RawDataTransferObject;
import com.augurworks.web.data.raw.serialization.RawAnalysisParamSerializer;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class DataTransferObjects {
    private static final Logger log = Logger.getLogger(DataTransferObjects.class);

    public static DataTransferObject fromJsonString(String jsonString) {
        JsonElement json = new JsonParser().parse(jsonString);
        json = json.getAsJsonObject().get("root");
        Gson gson = new GsonBuilder().registerTypeAdapter(RawAnalysisParam.class, new RawAnalysisParamSerializer()).create();
        RawDataTransferObject dto = gson.fromJson(json, RawDataTransferObject.class);
        DataTransferObject fromRaw = null;
        try {
            fromRaw = DataTransferObject.fromRaw(dto);
        } catch (Exception e) {
            log.info("Unable to parse from Data Transfer Object with JSON: " + json, e);
            Throwables.propagate(e);
        }
        return fromRaw;
    }

    public static List<String> getTitlesFromData(DataTransferObject data) {
        List<String> titles = Lists.newArrayList();
        for (DataObject obj : data.getSeriesObjects()) {
            titles.add(obj.getSeriesName());
        }
        return titles;
    }

}