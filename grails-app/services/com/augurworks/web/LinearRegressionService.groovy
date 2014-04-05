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

    def performAnalysis(parameters, inputData, removed) {
		if (parameters.analysis.dependent in removed) {
			return ['root' : [:], 'metadata' : ['errors' : ['Invalid ticker' : 'The dependent variable is an invalid ticker.']]]
		}
		parameters.analysis.independent.split(', ').each { val ->
			if (val in removed) {
				parameters.analysis.independent = (parameters.analysis.independent.split(', ') - val).join(', ')
			}
		}
        def js = inputData as JSON
        def builder = new groovy.json.JsonBuilder()
        def temp = [];
		temp.push(parameters.analysis);
        def root = builder.root {
            analysis temp
            data js.target
        }

        DataTransferObject dataObject = DataTransferObjects.fromJsonString(builder.toString());
        LinRegAnalysisParam param = dataObject.getAnalysis().get(AnalysisParamType.LINREG);
        //println "data object: " + dataObject
        //println "param: " + param
        double[] dependent = getDependentFromData(dataObject, param);
        double[][] independent = getIndependentFromData(dataObject, param);
        def result = new JsonSlurper().parseText(
            LinearRegressions.getJsonString(
                LinearRegressions.getLinearRegression(independent, dependent)));

        //println "result: " + result
        def names = parameters.analysis.independent.split(', ')

        def coeff = result.parameter_estimates.split(',').collect { it.toDouble() }
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
                tempData << [(date): tempVal]
            } catch(e) {
                log.error 'performAnalysis: ' + e
            }
        }
        inputData['-1'] = ['dates': tempData, 'metadata': ['valid': true, 'errors': [:], unit: inputData[keys[parameters.analysis.dependent]].metadata.unit,'name': 'LinReg', req: inputData[keys[parameters.analysis.dependent]].metadata.req]]
        inputData['-1'].metadata.req.name = inputData['-1'].metadata.req.name + ' - LineReg'

        return ['root': inputData, 'metadata': result]
    }

    public double[] getDependentFromData(DataTransferObject data, LinRegAnalysisParam param) {
        List<Date> dates = data.getAllFullyRepresentedDates();
        double[] values = new double[dates.size()]
        for (int i = 0; i < values.length; i++) {
            values[i] = data.getValueOnDate(param.getDependent(), dates.get(i));
        }
        return values;
    }

    public double[][] getIndependentFromData(DataTransferObject data, LinRegAnalysisParam param) {
        List<String> independent = param.getIndependent();
        List<Date> dates = data.getAllFullyRepresentedDates();
        double[][] values = new double[dates.size()][independent.size()];
        int dateCounter = 0;
        for (Date d : dates) {
            int stockCounter = 0;
            for (String key : independent) {
                values[dateCounter][stockCounter] = data.getValueOnDate(key, d);
                stockCounter++;
            }
            dateCounter++;
        }
        //println "Number of dates: " + values.length
        //println "Number of independent variables: " + values[0].length
        return values;
    }
}
