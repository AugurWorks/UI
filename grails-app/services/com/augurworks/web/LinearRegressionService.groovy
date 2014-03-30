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
		def result = new JsonSlurper().parseText(
            LinearRegressions.getJsonString(
                LinearRegressions.getLinearRegression(independent, dependent)));
			
		def names = parameters.analysis.independent.split(', ')
		
		def coeff = result.parameter_estimates.split(', ').collect { it.toDouble() }
		def keys = [:]
		inputData.keySet().each { keys << [(inputData[it].metadata.req.name + '-' + it): it] }
		def tempData = [:]
		inputData[keys[parameters.analysis.dependent]].dates.keySet().eachWithIndex { date, i ->
			try {
				def tempVal = coeff.last()
				names.each {
					def me = inputData[keys[it]].dates
					tempVal += me[me.keySet()[i]]
				}
				tempData << [date: tempVal]
			} catch(e) {
				log.error 'performAnalysis: ' + e
			}
		}
		inputData['-1'] = ['dates': tempData, 'metadata': ['valid': true, 'errors': [:], unit: inputData[keys[parameters.analysis.dependent]].metadata.unit,'name': 'LinReg']]
		
        return ['root': inputData, 'metadata': result]
    }

    public double[] getDependentFromData(DataTransferObject data, LinRegAnalysisParam param) {
        return data.getAllValuesFor(param.getDependent());
    }

    public double[][] getIndependentFromData(DataTransferObject data, LinRegAnalysisParam param) {
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
