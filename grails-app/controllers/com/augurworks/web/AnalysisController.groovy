package com.augurworks.web

import grails.converters.JSON
import groovy.json.JsonOutput

import java.text.SimpleDateFormat

import org.apache.log4j.Logger

class AnalysisController {

	def springSecurityService
	def infiniteService
	def dataService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	def linearregression() {
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('linearRegression')
		}
		Map req = generateRequest(requestVal.dataSets, requestVal.id, !!params.id);
		def map = getDefault()
		
		map << [req: req as JSON, page: 'linearRegression', inputNum: null, sameSize: true]
	}
	
	def decisiontree() {
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('decisionTree')
		}
		Map req = generateRequest(requestVal.dataSets, requestVal.id, !!params.id);
		def map = getDefault()
		
		map << [req: req as JSON, page: 'decisionTree', inputNum: null, sameSize: true]
	}
	
	def neuralnet() {
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('neuralNet')
		}
		Map req = generateRequest(requestVal.dataSets, requestVal.id, !!params.id);
		def map = getDefault();
		if (params.id) {
			def inputData = dataService.getData(JSON.parse((req as JSON).toString())).root;
			if (requestVal.neuralNet) {
				def key = inputData.keySet().sort { a, b -> a.toInteger() - b.toInteger() }.first();
				def parsed = dataService.parseNetData(requestVal.neuralNet);
				def meta = inputData[key].metadata;
				meta.stats = parsed.stats;
				inputData['-1'] = [dates: parsed.dates, metadata: meta];
			}
			map << [data: inputData as JSON]
		}
		map << [req: req as JSON, page: 'neuralNet', inputNum: null, sameSize: true]
	}
	
	def neuralnetlist() {
		[nets: Request.findAllByUser(springSecurityService.currentUser).grep { it.neuralNet }]
	}
	
	def analyze() {
		def req = JSON.parse(params.req);
		def recordedReq = dataService.recordRequest(req, null)
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
			req.user = springSecurityService.currentUser.id
			result = grailsApplication.mainContext.getBean(req.analysis.type + "Service").performAnalysis(req, inputData, removedKeyNames, recordedReq)
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
		[startDate: halfYearAgo(), endDate: today(), agg: Aggregation.list(), dataTypes: DataType.findAll { valueType == 'Number' }, dataTypeJson: deep, pageType: 'analysis', analysis: true, numbers: true]
	}
	
	private Map generateRequest(Collection<Map> dataSets, long reqId, boolean keepDates) {
		Map req = [:];
		for (i in dataSets) {
			req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: reqId];
			if (!keepDates) {
				req[i.num].startDate = halfYearAgo(req[i.num].offset);
				req[i.num].endDate = today(req[i.num].offset);
			}
		}
		return req;
	}
	
	private String today() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT.format(cal.getTime());
	}
	
	private String today(offset) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, offset);
		return DATE_FORMAT.format(cal.getTime());
	}

	private String halfYearAgo() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -182);
		return DATE_FORMAT.format(cal.getTime());
	}

	private String halfYearAgo(int offset) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -182 + offset);
		return DATE_FORMAT.format(cal.getTime());
	}
}
