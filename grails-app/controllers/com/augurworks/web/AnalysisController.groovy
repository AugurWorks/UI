package com.augurworks.web

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import groovy.json.JsonOutput

import java.text.SimpleDateFormat

import org.apache.log4j.Logger

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class AnalysisController {

	def springSecurityService
	def infiniteService
	def dataService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	def index() {
		def req = [:]
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('linearRegression')
		}
		for (i in requestVal.dataSets) {
			req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: requestVal.id]
		}
		def map = getDefault()
		
		map << [req: req as JSON, page: 'linearRegression', inputNum: null, sameSize: true]
	}
	
	def decisiontree() {
		def req = [:]
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('decisionTree')
		}
		for (i in requestVal.dataSets) {
			req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: requestVal.id]
		}
		def map = getDefault()
		
		map << [req: req as JSON, page: 'decisionTree', inputNum: null, sameSize: true]
	}
	
	def analyze() {
		def req = JSON.parse(params.req);
		def inputData = dataService.getData(req).root;
		def removedKeys = []
		def removedKeyNames = []
		inputData.each { key, val ->
			if (val.metadata.errors) {
				removedKeys.push(key)
				removedKeyNames.push(req[key].name + '-' + key)
			}
		}
		removedKeys.each { key ->
			inputData.remove(key)
		}
		def result = [:];
		try {
			result = grailsApplication.mainContext.getBean(req.analysis.type + "Service").performAnalysis(req, inputData, removedKeyNames)
		} catch (e) {
			result << ['errorBoolean': true, 'error': 'There was an error in the ' + req.analysis.type + ' algorithm. Please select a different set of inputs.']
		}
		result << ['invalidInputs' : removedKeys.collect { req[it].name }]
		render(result as JSON)
	}
	
	def getDefault() {
		String deep;
		JSON.use('deep') {
			deep = DataType.findAll { valueType == 'Number' }.sort() as JSON
		}
		[service : springSecurityService, startDate: halfYearAgo(), endDate: today(), agg: Aggregation.list(), dataTypes: DataType.findAll { valueType == 'Number' }, dataTypeJson: deep, pageType: 'analysis', analysis: true, numbers: true]
	}
	
	private String today() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT.format(cal.getTime());
	}

	private String halfYearAgo() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -182);
		return DATE_FORMAT.format(cal.getTime());
	}
}
