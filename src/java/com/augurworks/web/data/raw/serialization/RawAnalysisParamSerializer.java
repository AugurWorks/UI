package com.augurworks.web.data.raw.serialization;

import java.lang.reflect.Type;

import com.augurworks.web.data.AnalysisParam;
import com.augurworks.web.data.AnalysisParamType;
import com.augurworks.web.data.DtreeAnalysisParam;
import com.augurworks.web.data.LinRegAnalysisParam;
import com.augurworks.web.data.raw.RawAnalysisParam;
import com.augurworks.web.data.raw.RawDtreeAnalysisParam;
import com.augurworks.web.data.raw.RawLinRegAnalysisParam;
import com.google.common.base.Joiner;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RawAnalysisParamSerializer implements
        JsonSerializer<AnalysisParam>, JsonDeserializer<RawAnalysisParam> {

    public RawAnalysisParamSerializer() {
        super();
    }

    @Override
    public JsonElement serialize(AnalysisParam arg0, Type arg1,
            JsonSerializationContext arg2) {
        final JsonObject jsonObj = new JsonObject();
        if (arg0.getType() == AnalysisParamType.DTREE) {
            jsonObj.addProperty("type", "decisionTree");
            DtreeAnalysisParam param = (DtreeAnalysisParam) arg0;
            jsonObj.addProperty("treeDepth", param.getTreeDepth());
            jsonObj.addProperty("cutoff", param.getCutoff());
            jsonObj.addProperty("nameToPredict", param.getNameToPredict());
        } else if (arg0.getType() == AnalysisParamType.LINREG) {
            jsonObj.addProperty("type", "linearRegression");
            LinRegAnalysisParam param = (LinRegAnalysisParam) arg0;
            jsonObj.addProperty("dependent", param.getDependent());
            jsonObj.addProperty("independent", Joiner.on(",").skipNulls().join(param.getIndependent()));
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
            RawDtreeAnalysisParam tree = new RawDtreeAnalysisParam(
                    object.get("treeDepth").getAsString(),
                    object.get("cutoff").getAsString(),
                    object.get("nameToPredict").getAsString());
            return tree;
        } else if (type == AnalysisParamType.LINREG) {
            RawLinRegAnalysisParam param = new RawLinRegAnalysisParam(object.get("dependent").getAsString(),
                    object.get("independent").getAsString());
            return param;
        }
        return null;
    }
}
