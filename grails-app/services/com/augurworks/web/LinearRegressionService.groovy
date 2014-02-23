package com.augurworks.web

import grails.transaction.Transactional

@Transactional
class LinearRegressionService {
    DataController dataController = new DataController();

    def performAnalysis(/*request object*/ parameters) {
        def analysisParams = parameters.analysis;
        parameters.remove('analysis')
        def inputData = dataController.getData(parameters);

        def result= null // = analysis(inputData, analysisParams);

        return result
    }

    def serviceMethod() {
    }

}
