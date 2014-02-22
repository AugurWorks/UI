package com.augurworks.web

import grails.transaction.Transactional

@Transactional
class LinearRegressionService {
	DataController dataController = new DataController();
	
	def performAnalysis(parameters) {
		def analysisParams = parameters.analysis;
		parameters.remove('analysis')
		def inputData = dataController.getData(parameters);
		
		def result // = analysis(inputData, analysisParams);
		
		return result
	}

    def serviceMethod() {

    }
}
