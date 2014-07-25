package com.augurworks.web

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class NeuralNetService {

    def performAnalysis(parameters, inputData, removed) {
		println parameters.analysis
		return [success: true]
    }
}
