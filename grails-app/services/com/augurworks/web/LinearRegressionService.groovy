package com.augurworks.web

import grails.converters.JSON
import grails.transaction.Transactional

import com.augurworks.web.data.AnalysisParamType
import com.augurworks.web.data.DataTransferObject
import com.augurworks.web.data.DataTransferObjects
import com.augurworks.web.data.DtreeAnalysisParam

@Transactional
class LinearRegressionService {
    DataController dataController = new DataController();

    def performAnalysis(parameters) {
        def inputData = dataController.getData(parameters);
        DataTransferObject dataObject = DataTransferObjects.fromJsonString((inputData as JSON).toString());
        DtreeAnalysisParam param = dataObject.getAnalysis().get(AnalysisParamType.LINREG);
        return null;
    }

    def serviceMethod() {
    }

}
