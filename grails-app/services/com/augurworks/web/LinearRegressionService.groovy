package com.augurworks.web

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonSlurper

import com.augurworks.stats.LinearRegressions
import com.augurworks.web.data.AnalysisParamType
import com.augurworks.web.data.DataTransferObject
import com.augurworks.web.data.DataTransferObjects
import com.augurworks.web.data.LinRegAnalysisParam

@Transactional
class LinearRegressionService {
    def dataService

    def performAnalysis(parameters) {
        def inputData = dataService.getData(parameters).root;
        def js = inputData as JSON
        def builder = new groovy.json.JsonBuilder()
        def temp = []; temp.push(parameters.analysis);
        def root = builder.root {
            analysis temp
            data js.target
        }
        DataTransferObject dataObject = DataTransferObjects.fromJsonString(builder.toString());
        LinRegAnalysisParam param = dataObject.getAnalysis().get(AnalysisParamType.LINREG);
        double[] dependent = getDependentFromData(dataObject, param);
        double[][] independent = getIndependentFromData(dataObject, param);
        return new JsonSlurper().parseText(
            LinearRegressions.getJsonString(
                LinearRegressions.getLinearRegression(independent, dependent)));
    }

    public static double[] getDependentFromData(DataTransferObject data, LinRegAnalysisParam param) {
        return data.getAllValuesFor(param.getDependent());
    }

    public static double[][] getIndependentFromData(DataTransferObject data, LinRegAnalysisParam param) {
        List<String> independent = param.getIndependent();
        List<Date> dates = data.getAllDates();
        double[][] values = new double[dates.size()][independent.size()];
        int locOn = 0;
        for (String key : independent) {
            values[locOn] = data.getAllValuesFor(key);
        }
        return values;
    }
}
