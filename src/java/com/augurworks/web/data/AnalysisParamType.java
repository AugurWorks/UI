package com.augurworks.web.data;

import com.augurworks.web.data.raw.RawAnalysisParam;
import com.augurworks.web.data.raw.RawDtreeAnalysisParam;

public enum AnalysisParamType {
	DTREE("dtree") {
		@Override
		public DtreeAnalysisParam fromRaw(RawAnalysisParam rawData) {
			try {
				return DtreeAnalysisParam.fromRaw((RawDtreeAnalysisParam) rawData);
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("Unable to cast " + rawData + " to a DtreeAnalysisParam", e);
			}
		}
	},
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
			if (key.equalsIgnoreCase(type.id)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unrecognized analysis param type " + key);
	}
}
