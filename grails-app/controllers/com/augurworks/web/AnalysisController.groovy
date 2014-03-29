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
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
    def index() {
		[inputNum: null, sameSize: false, service : springSecurityService, dataTypes: DataType.list(), startDate: halfYearAgo(), endDate: today()]
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
		def result = grailsApplication.mainContext.getBean(req.analysis + "Service").performAnalysis(req)
		render(result as JSON)
	}
	
	def getDefault() {
		String deep;
		JSON.use('deep') {
			deep = DataType.findAll { valueType == 'Number' }.sort() as JSON
		}
		[service : springSecurityService, startDate: halfYearAgo(), endDate: today(), agg: Aggregation.list(), dataTypes: DataType.findAll { valueType == 'Number' }, dataTypeJson: deep, pageType: 'analysis']
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
