package com.augurworks.web.data.raw.serialization;

import java.lang.reflect.Type;

import com.augurworks.web.data.AnalysisParamType;
import com.augurworks.web.data.raw.RawAnalysisParam;
import com.augurworks.web.data.raw.RawDtreeAnalysisParam;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RawAnalysisParamSerializer implements
        JsonSerializer<RawAnalysisParam>, JsonDeserializer<RawAnalysisParam> {

    public RawAnalysisParamSerializer() {
        super();
    }

    @Override
    public JsonElement serialize(RawAnalysisParam arg0, Type arg1,
            JsonSerializationContext arg2) {
        final JsonObject jsonObj = new JsonObject();
        if (arg0.getType() == AnalysisParamType.DTREE) {
            jsonObj.addProperty("type", "dtree");
            RawDtreeAnalysisParam param = (RawDtreeAnalysisParam) arg0;
            jsonObj.addProperty("depth", param.getTreeDepth());
            jsonObj.addProperty("cutoff", param.getCutoff());
            jsonObj.addProperty("nameToPredict", param.getNameToPredict());
        }
        return jsonObj;
    }

    @Override
    public RawAnalysisParam deserialize(JsonElement arg0, Type arg1,
            JsonDeserializationContext arg2) throws JsonParseException {
        JsonObject object = arg0.getAsJsonObject();
        String typeString = object.get("type").getAsString();
        AnalysisParamType type = AnalysisParamType.fromId(typeString);
        if (type == AnalysisParamType.DTREE) {
            RawDtreeAnalysisParam tree = new RawDtreeAnalysisParam(object.get(
                    "depth").getAsString(), object.get("cutoff").getAsString(),
                    object.get("nameToPredict").getAsString());
            return tree;
        }
        return null;
    }
}
