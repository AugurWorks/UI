package com.augurworks.web

import grails.transaction.Transactional

@Transactional
class AnalysisService {
	DataController dataController = new DataController();
	
	def linearRegression(parameters) {
		def inputData = dataController.getData(parameters);
		
	}
	
	def decisionTree() {
		def data = dataController.getData(parameters);
		
		
	}
	
    def serviceMethod() {
    }
}
