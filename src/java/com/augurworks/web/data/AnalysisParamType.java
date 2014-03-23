package com.augurworks.web.data;

import com.augurworks.web.data.raw.RawAnalysisParam;
import com.augurworks.web.data.raw.RawDtreeAnalysisParam;
import com.augurworks.web.data.raw.RawLinRegAnalysisParam;

public enum AnalysisParamType {
    DTREE("decisionTree") {
        @Override
        public DtreeAnalysisParam fromRaw(RawAnalysisParam rawData) {
            try {
                return DtreeAnalysisParam.fromRaw((RawDtreeAnalysisParam) rawData);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Unable to cast " + rawData + " to a DtreeAnalysisParam", e);
            }
        }
    },
    LINREG("linearRegression") {
        @Override
        public LinRegAnalysisParam fromRaw(RawAnalysisParam rawData) {
            try {
                return LinRegAnalysisParam.fromRaw((RawLinRegAnalysisParam) rawData);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Unable to cast " + rawData + " to a LinRegAnalysisParam", e);
            }
        }
    }
    ;

    private String id;
    private AnalysisParamType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract AnalysisParam fromRaw(RawAnalysisParam rawData);

    public static AnalysisParamType fromId(String key) {
        for (AnalysisParamType type : values()) {
            if (key.equalsIgnoreCase(type.id.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unrecognized analysis param type " + key);
    }
}
