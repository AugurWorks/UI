package com.augurworks.web

import grails.transaction.Transactional

@Transactional
class AnalysisService {
	DataController dataController = new DataController();
	def linearRegressionService;
	
	/**
	 * Takes standard request object with analysis parameters and performs a linear regression on it.
	 * @param parameters - Standard request object with analysis parameters
	 * @return Standard analysis object
	 */
	def linearRegression(parameters) {
		def analysisParams = parameters.analysis;
		parameters.remove('analysis')
		def inputData = dataController.getData(parameters);
		return linearRegressionService.performAnalysis(inputData, parameters);
	}
	
	def decisionTree(parameters) {
		
		def data = dataController.getData(parameters);
	}
	
    def serviceMethod() {
    }
}
