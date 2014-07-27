package com.augurworks.web

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class NeuralNetService {
	
	def grailsApplication

    def performAnalysis(parameters, inputData, removed) {
		writeFile(parameters.analysis, inputData)
		return [success: true]
    }
	
	def writeFile(param, data) {
		def name = 'Test' + new Date().format('MM-dd-yy-HH-mm-ss-SSS') + '.augtrain';
		def f = grailsApplication.mainContext.getResource('neuralnet/' + name).file;
		f << 'net ' + data.size() + ',' + param.depth + '\n'
		f << 'train 1,' + param.rounds + ',' + param.learningConstant + ',' + param.rounds + ',' + param.cutoff + '\n'
		f << 'TITLES ' + data.values().collect { it.metadata.req.name }.join(',') + '\n'
		def dates = data.values().collect { it.dates.values() };
		(0..(dates[0].size() - 1)).each { i ->
			f << dates[0][i] + ' ' + dates[1..(dates.size() - 1)].collect { it[i] }.join(',') + '\n'
		}
		f.close()
	}
	
	def readResult(name) {
		def f = grailsApplication.mainContext.getResource('neuralnet/' + name).file;
	}
}
